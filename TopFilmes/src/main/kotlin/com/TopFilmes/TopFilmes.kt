package com.TopFilmes

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element
import java.net.URLEncoder

class TopFilmes : MainAPI() {
    override var mainUrl = "https://www.topfilmes.biz"
    override var name = "TopFilmes"
    override val hasMainPage = true
    override var lang = "pt-br"
    override val hasDownloadSupport = true
    override val supportedTypes = setOf(TvType.Movie)
    override val usesWebView = false

    companion object {
        private val MAIN_SECTIONS = listOf(
            "" to "Início",
            "/genero/acao" to "Ação",
            "/genero/comedia" to "Comédia",
            "/genero/terror" to "Terror",
            "/genero/drama" to "Drama",
            "/genero/suspense" to "Suspense",
            "/genero/animacao" to "Animação",
            "/genero/aventura" to "Aventura",
            "/genero/romance" to "Romance",
            "/genero/ficcao-cientifica" to "Ficção Científica",
            "/genero/crime" to "Crime"
        )
    }

    override val mainPage = mainPageOf(
        *MAIN_SECTIONS.map { (path, name) ->
            if (path.isEmpty()) mainUrl to name
            else "$mainUrl$path" to name
        }.toTypedArray()
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        return try {
            var url = request.data
            if (page > 1) {
                url = if (url.contains("?")) "$url&p=$page" else "$url?p=$page"
            }
            val document = app.get(url).document
            val items = if (request.data == mainUrl) {
                document.select("div.card").mapNotNull { it.toCardResult() }
            } else {
                document.select("div.filme").mapNotNull { it.toFilmeResult() }
            }
            val hasNextPage = items.isNotEmpty()
            newHomePageResponse(request.name, items.distinctBy { it.url }, hasNext = hasNextPage)
        } catch (e: Exception) {
            newHomePageResponse(request.name, emptyList(), hasNext = false)
        }
    }

    private fun Element.toCardResult(): SearchResponse? {
        val a = this.selectFirst("a[href*=/assistir/]") ?: return null
        val href = a.attr("href").ifBlank { return null }
        val title = a.attr("title").trim().ifBlank { null }
            ?: this.selectFirst("div.title")?.text()?.trim()?.ifBlank { null }
            ?: return null
        val poster = this.selectFirst("img[data-src]")?.attr("data-src")?.ifBlank { null }
            ?: this.selectFirst("img")?.attr("src")?.ifBlank { null }
        return newMovieSearchResponse(cleanTitle(title), fixUrl(href), TvType.Movie) {
            this.posterUrl = poster
        }
    }

    private fun Element.toFilmeResult(): SearchResponse? {
        val a = this.selectFirst("a[href]") ?: return null
        val href = a.attr("href").ifBlank { return null }
        val title = this.selectFirst("div.title")?.text()?.trim().ifNullOrBlank { return null }
        val poster = this.selectFirst("img.img")?.attr("data-src")?.ifBlank { null }
            ?: this.selectFirst("img.img")?.attr("src")?.ifBlank { null }
            ?: this.selectFirst("img")?.attr("src")?.ifBlank { null }
        val year = this.selectFirst("div.year")?.text()?.trim()
            ?.replace(Regex("[^0-9]"), "")?.take(4)?.toIntOrNull()
        return newMovieSearchResponse(cleanTitle(title), fixUrl(href), TvType.Movie) {
            this.posterUrl = poster
            this.year = year
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        if (query.length < 2) return emptyList()
        return try {
            val enc = URLEncoder.encode(query.trim(), "UTF-8")
            val document = app.get("$mainUrl/busca?q=$enc").document
            document.select("div.filme").mapNotNull {
                try {
                    it.toFilmeResult()
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun load(url: String): LoadResponse? {
        return try {
            val document = app.get(url).document
            val rawTitle = document.selectFirst("div.infos > h2")?.text()?.trim().ifNullOrBlank { return null }
            val title = cleanTitle(rawTitle)
            val year = document.selectFirst("div.info")?.text()?.trim()
                ?.let { Regex("\\d{4}").find(it)?.value?.toIntOrNull() }
            val plot = document.selectFirst("div.sinopse")?.text()?.trim()?.ifBlank { null }
            val poster = document.selectFirst("div.poster > img")?.attr("data-src")?.ifBlank { null }
                ?: document.selectFirst("div.poster > img")?.attr("src")?.ifBlank { null }

            // data = página de detalhe; loadLinks busca lá os players dub/leg
            newMovieLoadResponse(title, url, TvType.Movie, url) {
                this.posterUrl = poster
                this.year = year
                this.plot = plot
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        return try {
            val document = app.get(data).document
            val players = document.select("div.links_dub a[href*=player=], div.links_leg a[href*=player=]")
            var emitted = false
            for (a in players) {
                try {
                    val href = a.attr("href").trim()
                    if (href.isEmpty()) continue
                    val label = a.text().trim().ifBlank { "Player" }
                    val playerUrl = fixUrl(href)
                    val resp = app.get(playerUrl, referer = data)
                    val body = resp.text
                    var src = Regex("src=[\"']([^\"']*/direct/mf/[^\"']+)[\"']").find(body)
                        ?.groupValues?.get(1)
                    if (src == null) {
                        src = resp.document.selectFirst("video#player[src]")?.attr("src")?.ifBlank { null }
                    }
                    if (src == null) continue
                    // /direct/... responde 302 -> mediafire -> mp4 real
                    callback(
                        newExtractorLink(
                            this.name,
                            "${this.name} $label".trim(),
                            fixUrl(src),
                            ExtractorLinkType.VIDEO
                        ) {
                            this.referer = playerUrl
                            this.quality = 720
                        }
                    )
                    emitted = true
                } catch (e: Exception) {
                    // player=2 pode estar morto: pula silenciosamente
                }
            }
            emitted
        } catch (e: Exception) {
            false
        }
    }

    private fun cleanTitle(t: String): String {
        return t
            .replace(Regex("^Assistir\\s+", RegexOption.IGNORE_CASE), "")
            .replace(Regex("\\s+Online.*$", RegexOption.IGNORE_CASE), "")
            .trim()
            .ifBlank { t }
    }

    private fun fixUrl(href: String): String {
        val h = href.trim()
        return when {
            h.startsWith("http") -> h
            h.startsWith("//") -> "https:$h"
            h.startsWith("/") -> mainUrl + h
            else -> "$mainUrl/$h"
        }
    }
}

private inline fun <T : Any> T?.ifNullOrBlank(block: () -> Nothing): T {
    if (this == null || (this is CharSequence && this.isBlank())) block()
    return this
}
