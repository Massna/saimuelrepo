package com.Doramas

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.LoadResponse.Companion.addActorsRole
import com.lagradost.cloudstream3.LoadResponse.Companion.addTrailer
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class Doramas : MainAPI() {
    override var mainUrl = "https://doramasonline.net"
    override var name = "Doramas"
    override var lang = "pt-br"
    override val hasMainPage = true
    override val hasDownloadSupport = true
    override val supportedTypes = setOf(TvType.Movie, TvType.TvSeries)

    companion object {
        private val MEDIA_REGEX = Regex("""https?://[^\s"'<>\\]+?\.(?:m3u8|mp4|mkv)[^\s"'<>\\]*""")
    }

    override val mainPage = mainPageOf(
        "$mainUrl/category/lancamentos/" to "Lançamentos",
        "$mainUrl/category/comedia/" to "Comédia",
        "$mainUrl/category/crime/" to "Crime",
        "$mainUrl/category/documentario/" to "Documentário",
        "$mainUrl/category/drama/" to "Drama",
        "$mainUrl/category/familia/" to "Família",
        "$mainUrl/category/misterio/" to "Mistério"
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        return try {
            val url = request.data + if (page > 1) "page/$page/" else ""
            val document = app.get(url).document
            val home = document.select("div.aa-cn div#movies-a ul.post-lst li").mapNotNull {
                try {
                    toSearchResult(it)
                } catch (e: Exception) {
                    null
                }
            }
            newHomePageResponse(request.name, home)
        } catch (e: Exception) {
            newHomePageResponse(request.name, emptyList())
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        return try {
            val document = app.get("$mainUrl/?s=$query").document
            document.select("div.aa-cn div#movies-a ul.post-lst li").mapNotNull {
                try {
                    toSearchResult(it)
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun toSearchResult(el: Element): SearchResponse? {
        val title = el.selectFirst("header.entry-header h2.entry-title")?.text() ?: return null
        val href = el.selectFirst("a.lnk-blk")?.attr("href") ?: return null
        val poster = selectPoster(el, "div.post-thumbnail figure img", "/w500/")
        val year = el.selectFirst("span.year")?.text()?.toIntOrNull()
        val ratingText = el.selectFirst("div.entry-meta span.vote")?.text()?.replace("TMDB", "")?.trim()
        return newMovieSearchResponse(title, href, TvType.Movie) {
            this.posterUrl = poster
            this.year = year
            this.quality = getQualityFromString(el.selectFirst("span.post-ql")?.text())
            this.score = ratingText?.toFloatOrNull()?.let { Score.from10(it) }
        }
    }

    override suspend fun load(url: String): LoadResponse? {
        return try {
            val document = app.get(url).document
            val title = document.selectFirst("aside.fg1 header.entry-header h1.entry-title")?.text() ?: return null
            val poster = selectPoster(document, "div.bghd img.TPostBg", "/w1280/")
            val year = extractInt(document, "span.year")
            val durationText = extractText(document, "span.duration")
            val score = document.selectFirst("div.vote-cn span.vote span.num")?.text()?.toDoubleOrNull()
            val plot = document.selectFirst("aside.fg1 div.description p")?.text()
            val genres = document.select("span.genres a").map { it.text() }
            val actors = selectActors(document)
            val trailer = document.selectFirst("div.mdl-cn iframe")?.attr("src")
            val iframeUrl = document.selectFirst("iframe[src*='seriesboa.live']")?.attr("src")

            if (!url.contains("/serie/")) {
                return newMovieLoadResponse(title, url, TvType.Movie, iframeUrl ?: "") {
                    this.posterUrl = poster
                    this.year = year
                    this.plot = plot
                    this.tags = genres
                    this.duration = parseDuration(durationText)
                    this.score = score?.let { Score.from10(it.toFloat()) }
                    if (!actors.isNullOrEmpty()) addActorsRole(actors)
                    addTrailer(trailer)
                }
            }

            val episodes = if (iframeUrl != null) {
                parseEpisodes(app.get(iframeUrl).document)
            } else {
                emptyList()
            }
            newTvSeriesLoadResponse(title, url, TvType.TvSeries, episodes) {
                this.posterUrl = poster
                this.year = year
                this.plot = plot
                this.tags = genres
                this.score = score?.let { Score.from10(it.toFloat()) }
                if (!actors.isNullOrEmpty()) addActorsRole(actors)
                addTrailer(trailer)
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
            val links = extractEmbedLinks(document)
            for (link in links) {
                val direct = MEDIA_REGEX.find(link)?.value
                if (direct != null) {
                    val t = if (direct.contains(".m3u8")) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO
                    callback(newExtractorLink(name, name, direct, t) {})
                } else {
                    loadExtractor(link, data, subtitleCallback, callback)
                }
            }
            if (links.isEmpty()) {
                // fallback: midia direta na propria pagina do player (m3u8 200 / mp4 206)
                MEDIA_REGEX.findAll(document.html()).map { it.value.trimEnd('\\') }.distinct().forEach { u ->
                    val t = if (u.contains(".m3u8")) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO
                    callback(newExtractorLink(name, name, u, t) {})
                }
            }
            links.isNotEmpty() || true
        } catch (e: Exception) {
            false
        }
    }

    private fun parseEpisodes(doc: Document): List<Episode> {
        val episodes = mutableListOf<Episode>()
        for (season in doc.select("ul.header-navigation li[data-season-id]")) {
            val seasonNumber = season.attr("data-season-number").toIntOrNull() ?: 1
            val seasonId = season.attr("data-season-id")
            for (ep in doc.select("li[data-season-id='$seasonId']")) {
                val epId = ep.attr("data-episode-id")
                if (epId.isBlank()) continue
                val name = ep.selectFirst("a")?.text() ?: ""
                val number = Regex("\\d+").find(name)?.value?.toIntOrNull() ?: 1
                episodes.add(newEpisode(epId) {
                    this.name = name.trim()
                    this.season = seasonNumber
                    this.episode = number
                })
            }
        }
        return episodes.distinctBy { it.data }
    }

    private fun extractEmbedLinks(doc: Document): List<String> {
        val buttons = doc.select("button[data-source]").map { it.attr("data-source") }
        val iframes = doc.select("div#player iframe, div.play-overlay iframe").map { it.attr("src") }
        return (buttons + iframes).distinct().filter { it.isNotBlank() }
    }

    private fun selectActors(doc: Document): List<Pair<Actor, ActorRole?>>? {
        val actors = doc.select("ul.cast-lst a").map { el ->
            Pair<Actor, ActorRole?>(Actor(el.text(), el.attr("href")), null)
        }
        return actors.takeIf { it.isNotEmpty() }
    }

    private fun selectPoster(el: Element, selector: String, replace: String): String? {
        val img = el.selectFirst(selector) ?: return null
        val src = img.attr("src").ifBlank { img.attr("data-src") }.ifBlank { return null }
        val fixed = if (src.startsWith("//")) "https:$src" else src
        return fixed.replace(replace, "/original/")
    }

    private fun selectPoster(doc: Document, selector: String, replace: String): String? =
        selectPoster(doc as Element, selector, replace)

    private fun extractInt(doc: Document, selector: String): Int? {
        val text = doc.selectFirst(selector)?.text() ?: return null
        return text.filter { it.isDigit() }.toIntOrNull()
    }

    private fun extractText(doc: Document, selector: String): String? =
        doc.selectFirst(selector)?.text()

    private fun parseDuration(text: String?): Int? {
        if (text == null) return null
        val h = Regex("(\\d+)h\\s*(\\d+)m").find(text)
        if (h != null) {
            val hours = h.groupValues[1].toIntOrNull() ?: 0
            val minutes = h.groupValues[2].toIntOrNull() ?: 0
            return hours * 60 + minutes
        }
        return Regex("(\\d+)m").find(text)?.groupValues?.get(1)?.toIntOrNull()
    }
}
