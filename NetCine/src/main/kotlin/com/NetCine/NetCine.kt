package com.NetCine

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element
import java.net.URI
import java.net.URLEncoder

class NetCine : MainAPI() {
    override var mainUrl = "https://netcinexx.lat"
    override var name = "NetCine"
    override val hasMainPage = true
    override var lang = "pt-br"
    override val hasDownloadSupport = true
    override val supportedTypes = setOf(TvType.Movie, TvType.TvSeries)
    override val usesWebView = false

    companion object {
        private val MAIN_SECTIONS = listOf(
            "" to "Início",
            "/tvshows/" to "Séries",
            "/category/acao/" to "Ação",
            "/category/comedia/" to "Comédia",
            "/category/terror/" to "Terror",
            "/category/drama/" to "Drama",
            "/category/suspense/" to "Suspense",
            "/category/animacao/" to "Animação",
            "/category/aventura/" to "Aventura",
            "/category/romance/" to "Romance",
            "/category/ficcao-cientifica/" to "Ficção Científica",
            "/category/crime/" to "Crime"
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
            val document = app.get(request.data).document
            val items = document.select("#box_movies > div.movie").mapNotNull { it.toSearchResult() }
            newHomePageResponse(request.name, items.distinctBy { it.url }, hasNext = false)
        } catch (e: Exception) {
            newHomePageResponse(request.name, emptyList(), hasNext = false)
        }
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val link = this.selectFirst("div.imagen a[href]")?.attr("href")?.ifBlank { null } ?: return null
        val title = this.selectFirst("h2")?.text()?.trim().ifNullOrBlank { return null }
        val poster = this.selectFirst("img[data-src]")?.attr("data-src")?.ifBlank { null }
            ?: this.selectFirst("img")?.attr("src")?.ifBlank { null }
        val year = this.selectFirst("span.year")?.text()?.trim()
            ?.replace(Regex("[^0-9]"), "")?.take(4)?.toIntOrNull()
        val scoreValue = this.selectFirst("div.imdb")?.text()?.trim()
            ?.replace(Regex("[^0-9.,]"), "")?.replace(",", ".")?.toFloatOrNull()
        val isSerie = link.contains("/tvshows") || link.contains("/episode")

        return when {
            isSerie -> newTvSeriesSearchResponse(title, fixUrl(link), TvType.TvSeries) {
                this.posterUrl = poster
                this.year = year
                if (scoreValue != null) this.score = Score.from10(scoreValue.toInt())
            }
            else -> newMovieSearchResponse(title, fixUrl(link), TvType.Movie) {
                this.posterUrl = poster
                this.year = year
                if (scoreValue != null) this.score = Score.from10(scoreValue.toInt())
            }
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        if (query.length < 2) return emptyList()
        return try {
            val enc = URLEncoder.encode(query.trim(), "UTF-8")
            val document = app.get("$mainUrl/?s=$enc").document
            document.select("#box_movies > div.movie").mapNotNull {
                try {
                    it.toSearchResult()
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
            val title = document.selectFirst("h1")?.text()?.trim().ifNullOrBlank { return null }
            val synopsis = document.selectFirst("#dato-2 p")?.text()?.trim()?.ifBlank { null }
            val year = document.selectFirst("div.xmll p.xcsd")?.text()?.trim()
                ?.replace(Regex("[^0-9]"), "")?.take(4)?.toIntOrNull()
            var poster = document.selectFirst("meta[property=og:image]")?.attr("content")?.ifBlank { null }
            if (poster == null) poster = document.selectFirst("img[data-src]")?.attr("data-src")?.ifBlank { null }

            val recommendations = document.select(".relacionados div.movie").mapNotNull {
                try {
                    it.toSearchResult()
                } catch (e: Exception) {
                    null
                }
            }.distinctBy { it.url }

            val episodes = document.select("#cssmenu a[href*=/episode/]").mapNotNull { a ->
                try {
                    val href = a.attr("href").ifBlank { return@mapNotNull null }
                    val se = a.selectFirst("span.datex")?.text()?.trim() ?: ""
                    val m = Regex("(\\d+)\\s*[-–]\\s*(\\d+)").find(se)
                    val season = m?.groupValues?.get(1)?.toIntOrNull()
                    val episode = m?.groupValues?.get(2)?.toIntOrNull()
                    if (season == null || episode == null) return@mapNotNull null
                    val epName = a.selectFirst("span.datix")?.text()?.trim()?.ifBlank { null }
                        ?: "T$season E$episode"
                    newEpisode(fixUrl(href)) {
                        this.name = epName
                        this.season = season
                        this.episode = episode
                    }
                } catch (e: Exception) {
                    null
                }
            }

            if (episodes.isNotEmpty()) {
                newTvSeriesLoadResponse(title, url, TvType.TvSeries, episodes) {
                    this.posterUrl = poster
                    this.year = year
                    this.plot = synopsis
                    this.recommendations = recommendations.takeIf { it.isNotEmpty() }
                }
            } else {
                newMovieLoadResponse(title, url, TvType.Movie, url) {
                    this.posterUrl = poster
                    this.year = year
                    this.plot = synopsis
                    this.recommendations = recommendations.takeIf { it.isNotEmpty() }
                }
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
            val labels = mutableMapOf<String, String>()
            document.select("ul.player-menu a[href^=#play-]").forEach { a ->
                val key = a.attr("href").removePrefix("#").ifBlank { return@forEach }
                val label = a.text().trim().ifBlank { return@forEach }
                labels[key] = label
            }
            val iframes = document.select("div[id^=play-] > iframe[src*=media-player/hlsarchive.php]")
            var emitted = false
            for (iframe in iframes) {
                try {
                    val divId = iframe.parent()?.attr("id")?.ifBlank { null }
                    val label = divId?.let { labels[it] } ?: "Player ${divId?.removePrefix("play-") ?: ""}"
                    val rawSrc = iframe.attr("src").trim()
                    if (rawSrc.isEmpty()) continue
                    val src = fixUrl(rawSrc)
                    val inner = app.get(src, referer = data).text
                    val m = Regex("(?:https?://[^\\s\"'<>]*)?hls/hls\\.php\\?[^\\s\"'<>]+").find(inner) ?: continue
                    val streamUrl = resolveUrl(src, m.value)
                    // A URL final hoje pode responder 403 (CAPTCHA Cap.js PoW). Não tentamos
                    // resolver o desafio: só emitimos o que vier pronto.
                    val resp = app.get(streamUrl, referer = src)
                    val body = resp.text
                    val blocked = resp.code == 403 ||
                        body.contains("cap.js", ignoreCase = true) ||
                        body.contains("captcha", ignoreCase = true) ||
                        body.contains("challenge", ignoreCase = true)
                    if (blocked) continue
                    val ok = body.contains("#EXTM3U") ||
                        (resp.code in 200..299 && (streamUrl.contains(".m3u8") || streamUrl.contains("hls")))
                    if (!ok) continue
                    callback(
                        newExtractorLink(
                            this.name,
                            "${this.name} $label".trim(),
                            streamUrl,
                            ExtractorLinkType.M3U8
                        ) {
                            this.referer = src
                            this.quality = 720
                        }
                    )
                    emitted = true
                } catch (e: Exception) {
                    // segue para o proximo player
                }
            }
            emitted
        } catch (e: Exception) {
            false
        }
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

    private fun resolveUrl(base: String, rel: String): String {
        val r = rel.trim()
        return try {
            when {
                r.startsWith("http") -> r
                r.startsWith("//") -> "https:$r"
                else -> URI(base).resolve(r).toString()
            }
        } catch (e: Exception) {
            fixUrl(r)
        }
    }
}

private inline fun <T : Any> T?.ifNullOrBlank(block: () -> Nothing): T {
    if (this == null || (this is CharSequence && this.isBlank())) block()
    return this
}
