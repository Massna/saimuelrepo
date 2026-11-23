package com.PobreFlix.extractor

import android.util.Base64
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.ExtractorLinkType
import com.lagradost.cloudstream3.utils.loadExtractor
import com.lagradost.cloudstream3.utils.newExtractorLink
import java.io.InputStream
import java.io.OutputStream
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.URL
import java.net.URLDecoder
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.thread
import org.json.JSONArray
import org.json.JSONObject

/**
 * Player do PobreFlix = FSHD (fshd.link). Contrato validado ao vivo (nov/2026):
 *
 *   filme:  pagina tem CONTENT_INFO='32407', CONTENT_TYPE='1' e
 *           <div class="server-selector" data-id="255189" data-lang="2">
 *           POST /api/players (FORM — JSON da 422!) content_info/content_type/video_id
 *           -> {"video_url":"https://fshd.link/video/<token>"}
 *
 *   serie:  /serie/<id>/<t>/<e> tem
 *           <div class="episodeOption active" data-contentid="12202">
 *           POST /api/options (FORM) content_id/content_type=2
 *           -> {"options":[{"ID":112507,"type":1},{"ID":112508,"type":2}]}  (type=audio)
 *           POST /api/players (FORM) content_info=<cid>/content_type=2/video_id=<ID>
 *
 *   GET video_url -> wrapper 346B com window.location.href = "<embed>?sub=<srt>&lang=..."
 *
 * Embeds (2 familias):
 *   - 112234152.xyz/player/index.php?data=<vhash>...  -> HLS PURO
 *       https://112234152.xyz/hls/<vhash>/master.txt  (m3u8 camuflado de .txt)
 *   - abyssplayer.com/<slug>... -> MP4 AES-CTR cifrado em partes
 *       const datas = "<b64>" -> {slug,md5_id,user_id,media:"<cifrado>"}
 *       chave = md5("user_id:slug:md5_id") hex em ASCII (32B), counter = 16
 *       primeiros bytes; texto = charCodeAt & 0xff (semantica atob/latin1!)
 *       media = {"mp4":{"sources":[{label,res_id,size,codec,path,url,partSize}]}}
 *       partes: url+'/'+path (N=0) e url+'/'+path+N (N>=1; "...3" -> "...31")
 *       bytes = AES-CTR continuo sobre o arquivo inteiro (ftypmp42 apos decrypt)
 *       -> exige o AbyssProxy local pra tocar no ExoPlayer.
 */
object PobreFlixExtractor {

    private const val FSHD = "https://fshd.link"
    private const val UA = "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0 Mobile Safari/537.36"

    private val HEADERS = mapOf(
        "User-Agent" to UA,
        "Referer" to "$FSHD/"
    )

    // FORM posts (x-www-form-urlencoded) — JSON da 422 em tudo
    private val FORM_HEADERS = HEADERS + mapOf(
        "X-Requested-With" to "XMLHttpRequest",
        "Origin" to FSHD
    )

    private val MEDIA_REGEX = Regex("""https?://[^\s"'<>\\]+?\.(?:m3u8|mp4|mkv)[^\s"'<>\\]*""")

    suspend fun getStreams(
        id: String,
        type: String,
        season: Int,
        episode: Int,
        subtitleCallback: (SubtitleFile) -> Unit
    ): List<ExtractorLink> {
        val found = mutableListOf<ExtractorLink>()
        try {
            val isMovie = type.equals("filme", true)
            val pageUrl = if (isMovie) "$FSHD/filme/$id" else "$FSHD/serie/$id/$season/$episode"
            val html = app.get(pageUrl, headers = HEADERS).text

            if (isMovie) {
                val info = Regex("CONTENT_INFO\\s*=\\s*'([^']+)'").find(html)?.groupValues?.get(1)
                val ctype = Regex("CONTENT_TYPE\\s*=\\s*'([^']+)'").find(html)?.groupValues?.get(1) ?: "1"
                if (!info.isNullOrBlank()) {
                    val langLabel = HashMap<String, String>()
                    Regex("class=\"audio-selector[^\"]*\"[^>]*data-lang=\"(\\d+)\"[^>]*>[\\s\\S]{0,220}?class=\"audio-text\">([^<]+)<")
                        .findAll(html).forEach {
                            langLabel[it.groupValues[1]] = it.groupValues[2].trim()
                        }
                    val servers = mutableListOf<Pair<String, String>>()
                    Regex("class=\"server-selector\"[^>]*data-id=\"(\\d+)\"[^>]*data-lang=\"(\\d+)\"")
                        .findAll(html).forEach {
                            val vid = it.groupValues[1]
                            val lang = it.groupValues[2]
                            servers.add(vid to (langLabel[lang] ?: if (lang == "1") "Dublado" else "Legendado"))
                        }
                    for ((vid, label) in servers) {
                        val vu = apiPost(
                            "$FSHD/api/players",
                            mapOf("content_info" to info, "content_type" to ctype, "video_id" to vid),
                            pageUrl
                        )?.let { r -> videoUrlOf(r) }
                        if (vu.isNullOrBlank()) continue
                        resolveEmbed(vu, label, found, subtitleCallback)
                    }
                }
            } else {
                val cid = Regex("class=\"episodeOption[^\"]*active[^\"]*\"[^>]*data-contentid=\"(\\d+)\"")
                    .find(html)?.groupValues?.get(1)
                    ?: Regex("data-contentid=\"(\\d+)\"").find(html)?.groupValues?.get(1)
                if (!cid.isNullOrBlank()) {
                    val options = mutableListOf<Pair<String, String>>()
                    val optRaw = apiPost("$FSHD/api/options", mapOf("content_id" to cid, "content_type" to "2"), pageUrl)
                    if (!optRaw.isNullOrBlank()) {
                        try {
                            val arr = JSONObject(optRaw).optJSONArray("options") ?: JSONArray()
                            for (i in 0 until arr.length()) {
                                val o = arr.optJSONObject(i) ?: continue
                                val oid = o.opt("ID")?.toString() ?: continue
                                val t = o.optInt("type", 1)
                                options.add(oid to (if (t == 1) "Dublado" else "Legendado"))
                            }
                        } catch (_: Exception) {
                        }
                    }
                    for ((oid, label) in options) {
                        val vu = apiPost(
                            "$FSHD/api/players",
                            mapOf("content_info" to cid, "content_type" to "2", "video_id" to oid),
                            pageUrl
                        )?.let { r -> videoUrlOf(r) }
                        if (vu.isNullOrBlank()) continue
                        resolveEmbed(vu, label, found, subtitleCallback)
                    }
                }
            }
        } catch (_: Exception) {
        }
        return found.distinctBy { it.url }
    }

    // ---------------------------------------------------------------- helpers

    private fun videoUrlOf(json: String): String? =
        Regex("\"video_url\"\\s*:\\s*\"([^\"]+)\"").find(json)?.groupValues?.get(1)?.replace("\\/", "/")

    /** POST form-urlencoded — a API do FSHD recusa JSON com 422. */
    private suspend fun apiPost(url: String, form: Map<String, String>, referer: String): String? {
        return try {
            app.post(url, headers = FORM_HEADERS + mapOf("Referer" to referer), data = form).text
        } catch (_: Exception) {
            null
        }
    }

    /** GET video_url -> wrapper -> embed (+ ?sub= legenda) -> despacha por familia. */
    private suspend fun resolveEmbed(
        videoUrl: String,
        label: String,
        found: MutableList<ExtractorLink>,
        subtitleCallback: (SubtitleFile) -> Unit
    ) {
        try {
            val wrap = app.get(videoUrl, headers = HEADERS).text
            val embed = Regex("window\\.location\\.href\\s*=\\s*[\"']([^\"']+)").find(wrap)?.groupValues?.get(1)
                ?: Regex("https?://[^\\s\"'<>]+").find(wrap)?.groupValues?.get(0)
                ?: return
            val embedUrl = embed.replace("\\/", "/")
            val q = try {
                URLDecoder.decode(embedUrl.substringAfter("?", ""), "UTF-8")
            } catch (_: Exception) {
                embedUrl.substringAfter("?", "")
            }
            val sub = Regex("(?:^|&)sub\\d*=([^&]+)").find(q)?.groupValues?.get(1)
            val subLang = Regex("(?:^|&)lang\\d*=([^&]+)").find(q)?.groupValues?.get(1) ?: "Português"
            if (!sub.isNullOrBlank() && sub.startsWith("http")) {
                subtitleCallback.invoke(SubtitleFile(sub, subLang))
            }

            val host = embedUrl.substringAfter("://", "").substringBefore("/").lowercase()
            when {
                host.endsWith("112234152.xyz") -> venomHls(embedUrl, label, found)
                host.contains("abyssplayer") || host.contains("abyss.to") -> abyss(embedUrl, label, found)
                else -> {
                    try {
                        loadExtractor(embedUrl, "$FSHD/", subtitleCallback, { found.add(it) })
                    } catch (_: Exception) {
                    }
                    genericPage(embedUrl, label, found)
                }
            }
        } catch (_: Exception) {
        }
    }

    /** 112234152.xyz: data=<vhash> -> https://<host>/hls/<vhash>/master.txt (m3u8). */
    private suspend fun venomHls(embedUrl: String, label: String, found: MutableList<ExtractorLink>) {
        try {
            val vhash = Regex("[?&]data=([0-9a-fA-F]+)").find(embedUrl)?.groupValues?.get(1) ?: return
            val hosts = listOf(
                "https://112234152.xyz",
                "https://venom.00000410.xyz",
                "https://carnage.00000410.xyz"
            )
            for (h in hosts) {
                val master = "$h/hls/$vhash/master.txt"
                try {
                    val txt = app.get(master, headers = HEADERS + mapOf("Referer" to "https://112234152.xyz/")).text
                    if (!txt.startsWith("#EXTM3U")) continue
                    val q = Regex("RESOLUTION=\\d+x(\\d+)").findAll(txt).map { it.groupValues[1].toInt() }.maxOrNull() ?: 1080
                    found.add(
                        newExtractorLink("PobreFlix", "$label HD", master, ExtractorLinkType.M3U8) {
                            this.quality = q
                            this.referer = "https://112234152.xyz/"
                            this.headers = mapOf("User-Agent" to UA, "Referer" to "https://112234152.xyz/")
                        }
                    )
                    return
                } catch (_: Exception) {
                }
            }
        } catch (_: Exception) {
        }
    }

    /** abyssplayer: blob `datas` -> AES-CTR decrypt -> sources mp4 (via AbyssProxy). */
    private suspend fun abyss(embedUrl: String, label: String, found: MutableList<ExtractorLink>) {
        try {
            val html = app.get(embedUrl, headers = HEADERS).text
            val b64 = Regex("const datas = \"([^\"]+)\"").find(html)?.groupValues?.get(1) ?: return
            val json = JSONObject(String(Base64.decode(b64, Base64.DEFAULT), Charsets.ISO_8859_1))
            val keyStr = json.opt("user_id").toString() + ":" + json.optString("slug") + ":" + json.opt("md5_id").toString()
            val mediaStr = json.optString("media")
            if (mediaStr.isBlank()) return
            val plain = AesCtr(keyStr).decryptString(mediaStr)
            val media = JSONObject(plain)
            val sources = media.optJSONObject("mp4")?.optJSONArray("sources") ?: return
            for (i in 0 until sources.length()) {
                val s = sources.optJSONObject(i) ?: continue
                val sLabel = s.optString("label").ifBlank { label }
                val quality = Regex("(\\d{3,4})").find(sLabel)?.groupValues?.get(1)?.toIntOrNull() ?: 0
                val direct = s.optString("file")
                if (direct.isNotBlank() && direct.startsWith("http")) {
                    found.add(
                        newExtractorLink("PobreFlix", "$label $sLabel", direct, ExtractorLinkType.VIDEO) {
                            this.quality = quality
                        }
                    )
                    continue
                }
                val base = s.optString("url").trimEnd('/')
                val path = s.optString("path")
                val size = s.optLong("size", 0L)
                val partSize = s.optLong("partSize", size)
                if (base.isBlank() || path.isBlank() || size <= 0) continue
                val parts = ArrayList<String>()
                val sizes = ArrayList<Long>()
                val count = if (partSize > 0) ((size + partSize - 1) / partSize).toInt() else 1
                for (n in 0 until count) {
                    parts.add(if (n == 0) "$base/$path" else "$base/$path$n")
                    sizes.add(if (n + 1 == count) (size % partSize).takeIf { it != 0L } ?: partSize else partSize)
                }
                val basename = path.substringAfterLast('/')
                val link = AbyssProxy.linkFor(parts, sizes, basename)
                found.add(
                    newExtractorLink("PobreFlix", "$label $sLabel", link, ExtractorLinkType.VIDEO) {
                        this.quality = quality
                    }
                )
            }
        } catch (_: Exception) {
        }
    }

    /** Fallback generico: raspa a pagina do embed por midia direta. */
    private suspend fun genericPage(embedUrl: String, label: String, found: MutableList<ExtractorLink>) {
        try {
            val html = app.get(embedUrl, headers = HEADERS).text
            MEDIA_REGEX.findAll(html).map { it.value.trimEnd('\\') }.distinct().forEach { u ->
                val t = if (u.contains(".m3u8")) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO
                found.add(newExtractorLink("PobreFlix", label, u, t) {})
            }
        } catch (_: Exception) {
        }
    }
}

/** AES-256-CTR: chave = ASCII(md5hex(keyStr)), counter = 16 primeiros bytes. */
class AesCtr(keyStr: String) {
    private val keyBytes = md5Hex(keyStr).toByteArray(Charsets.US_ASCII)
    private val ctr0 = keyBytes.copyOfRange(0, 16)

    private fun md5Hex(s: String): String =
        MessageDigest.getInstance("MD5").digest(s.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }

    private fun cipherAt(offset: Long): Cipher {
        val blockOffset = offset / 16
        val skip = (offset % 16).toInt()
        val ctrInt = BigInteger(1, ctr0).add(BigInteger.valueOf(blockOffset))
        var cb = ctrInt.toByteArray()
        if (cb.size > 16) cb = cb.copyOfRange(cb.size - 16, cb.size)
        val iv = ByteArray(16)
        System.arraycopy(cb, 0, iv, 16 - cb.size, cb.size)
        val c = Cipher.getInstance("AES/CTR/NoPadding")
        c.init(Cipher.DECRYPT_MODE, SecretKeySpec(keyBytes, "AES"), IvParameterSpec(iv))
        if (skip > 0) c.update(ByteArray(skip))
        return c
    }

    /** texto (chars 0-255, semantica atob) -> plaintext utf-8 */
    fun decryptString(s: String): String {
        val ct = ByteArray(s.length) { i -> (s[i].code and 0xff).toByte() }
        return String(cipherAt(0).doFinal(ct), Charsets.UTF_8)
    }

    fun decryptRange(input: InputStream, offset: Long, out: OutputStream) {
        val c = cipherAt(offset)
        val buf = ByteArray(64 * 1024)
        while (true) {
            val n = input.read(buf)
            if (n < 0) break
            val p = c.update(buf, 0, n)
            if (p != null && p.isNotEmpty()) out.write(p)
        }
        val f = c.doFinal()
        if (f != null && f.isNotEmpty()) out.write(f)
        out.flush()
    }
}

/**
 * Proxy local que entrega o MP4 descriptografado do abyss em http://127.0.0.1.
 * As partes no CDN formam um unico fluxo AES-CTR sobre o arquivo inteiro; o
 * proxy remonta (com Range/seek) e decripta sob demanda pro ExoPlayer.
 */
object AbyssProxy {
    private var server: ServerSocket? = null
    private var port = 0
    private val lock = Any()

    private const val UA = "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0 Mobile Safari/537.36"

    fun linkFor(parts: List<String>, sizes: List<Long>, basename: String): String {
        ensure()
        val o = JSONObject()
        o.put("p", JSONArray(parts))
        o.put("s", JSONArray(sizes))
        o.put("b", basename)
        val token = Base64.encodeToString(
            o.toString().toByteArray(Charsets.UTF_8),
            Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
        )
        return "http://127.0.0.1:$port/p/$token"
    }

    private fun ensure() = synchronized(lock) {
        if (server == null) {
            val s = ServerSocket(0, 16, InetAddress.getByName("127.0.0.1"))
            server = s
            port = s.localPort
            thread(isDaemon = true, name = "pf-abyss-proxy") {
                while (true) {
                    try {
                        val c = s.accept()
                        thread(isDaemon = true) { handle(c) }
                    } catch (_: Exception) {
                        break
                    }
                }
            }
        }
    }

    private fun handle(sock: Socket) {
        try {
            sock.soTimeout = 30000
            val inp = sock.getInputStream().bufferedReader(Charsets.ISO_8859_1)
            val reqLine = inp.readLine() ?: return
            var range: String? = null
            while (true) {
                val l = inp.readLine() ?: break
                if (l.isBlank()) break
                if (l.startsWith("Range:", true)) range = l.substringAfter(":").trim()
            }
            val out = sock.getOutputStream()
            val path = reqLine.split(" ").getOrNull(1) ?: return
            val token = path.substringAfter("/p/", "")
            if (token.isBlank()) {
                out.write("HTTP/1.1 404 Not Found\r\nConnection: close\r\n\r\n".toByteArray())
                return
            }
            val o = JSONObject(
                String(
                    Base64.decode(token, Base64.URL_SAFE or Base64.NO_WRAP),
                    Charsets.UTF_8
                )
            )
            val parts = o.getJSONArray("p")
            val sizes = o.getJSONArray("s")
            val basename = o.optString("b")
            val total = (0 until sizes.length()).sumOf { sizes.getLong(it) }

            var start = 0L
            var end = total - 1
            var partial = false
            if (range != null) {
                val m = Regex("bytes=(\\d+)-(\\d*)").find(range)
                if (m != null) {
                    start = m.groupValues[1].toLongOrNull() ?: 0L
                    val e = m.groupValues[2]
                    if (e.isNotBlank()) end = e.toLong()
                    partial = true
                }
            }
            if (start >= total) {
                out.write("HTTP/1.1 416 Range Not Satisfiable\r\nContent-Range: bytes */$total\r\nConnection: close\r\n\r\n".toByteArray())
                return
            }
            if (end >= total) end = total - 1
            val len = end - start + 1

            val head = StringBuilder()
            head.append(if (partial) "HTTP/1.1 206 Partial Content\r\n" else "HTTP/1.1 200 OK\r\n")
            head.append("Content-Type: video/mp4\r\n")
            head.append("Accept-Ranges: bytes\r\n")
            if (partial) head.append("Content-Range: bytes $start-$end/$total\r\n")
            head.append("Content-Length: $len\r\n")
            head.append("Connection: close\r\n\r\n")
            out.write(head.toString().toByteArray())

            val cipher = AesCtr(basename)
            var global = 0L
            for (i in 0 until parts.length()) {
                val pSize = sizes.getLong(i)
                val pStart = global
                val pEnd = global + pSize - 1
                global += pSize
                if (pEnd < start || pStart > end) continue
                val lo = (start - pStart).coerceAtLeast(0L)
                val hi = (end - pStart).coerceAtMost(pSize - 1)
                val url = parts.getString(i)
                try {
                    val conn = URL(url).openConnection() as HttpURLConnection
                    conn.setRequestProperty("User-Agent", UA)
                    conn.setRequestProperty("Referer", "https://abyssplayer.com/")
                    conn.setRequestProperty("Origin", "https://abyssplayer.com")
                    conn.setRequestProperty("Sec-Fetch-Dest", "empty")
                    conn.setRequestProperty("Sec-Fetch-Mode", "cors")
                    conn.setRequestProperty("Sec-Fetch-Site", "cross-site")
                    if (lo != 0L || hi != pSize - 1) {
                        conn.setRequestProperty("Range", "bytes=$lo-$hi")
                    }
                    conn.connectTimeout = 20000
                    conn.readTimeout = 30000
                    val code = conn.responseCode
                    if (code !in 200..209) {
                        conn.disconnect()
                        continue
                    }
                    conn.getInputStream().use { ins ->
                        cipher.decryptRange(ins, pStart + lo, out)
                    }
                    conn.disconnect()
                } catch (_: Exception) {
                    break
                }
            }
        } catch (_: Exception) {
        } finally {
            try {
                sock.close()
            } catch (_: Exception) {
            }
        }
    }
}
