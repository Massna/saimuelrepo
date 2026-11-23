package com.AnimesCloud

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.extractors.VidStack
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element
import java.net.URLDecoder

class AnimesCloud : MainAPI() {
    override var mainUrl = "https://animesdrive.cloud"
    override var name = "AnimesCloud"
    override val hasMainPage = true
    override var lang = "pt-br"
    override val hasDownloadSupport = true
    override val hasQuickSearch = true
    override val supportedTypes = setOf(TvType.Anime, TvType.AnimeMovie)

    companion object {
        private const val USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36"
    }

    private val defaultHeaders: Map<String, String>
        get() = mapOf(
            "User-Agent" to USER_AGENT,
            "Referer" to "$mainUrl/"
        )

    override val mainPage = mainPageOf(
        "$mainUrl/anime" to "Animes",
        "$mainUrl/episodio" to "Últimos Episódios",
        "$mainUrl/filme" to "Filmes",
        mainUrl to "Lançamentos"
    )

    // --------------------------------------------------------------- listagem

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        return try {
            val url = if (page > 1) "${request.data.removeSuffix("/")}/page/$page/" else request.data
            val document = app.get(url, headers = defaultHeaders).document
            val items = document.select("article, li.wp-block-post").mapNotNull {
                try {
                    it.toSearchResult()
                } catch (e: Exception) {
                    null
                }
            }.ifEmpty {
                document.select("h2.wp-block-post-title a, h1.epih1 a").mapNotNull {
                    try {
                        it.toSearchResultFromLink()
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            val hasNext = document.select(
                ".nav-links .next, a.next, .wp-block-query-pagination-next, a:contains(Próxima)"
            ).isNotEmpty()
            newHomePageResponse(request.name, items.distinctBy { it.url }, hasNext = hasNext)
        } catch (e: Exception) {
            newHomePageResponse(request.name, emptyList(), hasNext = false)
        }
    }

    private fun cleanTitle(title: String): String {
        return title
            .replace(Regex("^Assistir\\s+", RegexOption.IGNORE_CASE), "")
            .replace(Regex("\\s+Online(\\s+Grátis)?$", RegexOption.IGNORE_CASE), "")
            .trim()
    }

    private fun extractPoster(el: Element): String? {
        return try {
            val img = el.selectFirst("img") ?: return null
            var src: String? = img.attr("data-src").ifBlank { null }
            if (src == null) {
                src = img.attr("src").takeIf { it.isNotBlank() && !it.contains("placeholder") }
            }
            if (src == null) {
                src = img.attr("data-lazy-src").ifBlank { null }
            }
            src?.let { fixUrl(it) }
        } catch (e: Exception) {
            null
        }
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val link = this.selectFirst("h2.wp-block-post-title a, .wp-block-post-title a, h1.epih1 a, a")
            ?: return null
        return link.toSearchResultFromLink(extractPoster(this))
    }

    private fun Element.toSearchResultFromLink(poster: String? = null): SearchResponse? {
        val href = this.attr("href").ifBlank { return null }
        val rawTitle = this.text().trim().ifBlank { return null }
        val title = cleanTitle(rawTitle).ifBlank { return null }
        val isSerie = !href.contains("/episodio") && !href.contains("/assistir-")
        return when {
            isSerie && href.contains("/filme") -> newMovieSearchResponse(title, fixUrl(href), TvType.AnimeMovie) {
                this.posterUrl = poster
            }
            isSerie -> newAnimeSearchResponse(title, fixUrl(href), TvType.Anime) {
                this.posterUrl = poster
            }
            else -> newAnimeSearchResponse(title, fixUrl(href), TvType.Anime) {
                this.posterUrl = poster
            }
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        if (query.length < 2) return emptyList()
        return try {
            val enc = query.trim().replace(" ", "+")
            val document = app.get("$mainUrl/?s=$enc", headers = defaultHeaders).document
            val items = document.select("article, li.wp-block-post").mapNotNull {
                try {
                    it.toSearchResult()
                } catch (e: Exception) {
                    null
                }
            }.ifEmpty {
                document.select("h2.wp-block-post-title a, h1.epih1 a").mapNotNull {
                    try {
                        it.toSearchResultFromLink()
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            items.distinctBy { it.url }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // ------------------------------------------------------------------- load

    override suspend fun load(url: String): LoadResponse? {
        return try {
            val document = app.get(url, headers = defaultHeaders).document
            var title = document.selectFirst("h1")?.text()?.trim()
            if (title.isNullOrBlank()) {
                title = document.selectFirst("meta[property=og:title]")?.attr("content")?.trim()
            }
            if (title.isNullOrBlank()) return null
            val cleanTitle = cleanTitle(title).ifBlank { title }

            var poster = document.selectFirst("meta[property=og:image]")?.attr("content")?.trim()?.ifBlank { null }
            if (poster == null) {
                poster = document.selectFirst("div.g-item a")?.attr("href")?.trim()?.ifBlank { null }
            }
            var synopsis = document.selectFirst("meta[property=og:description]")?.attr("content")?.trim()
            if (synopsis.isNullOrBlank()) {
                synopsis = document.selectFirst("div.wp-content")?.text()?.trim()
            }
            if (synopsis.isNullOrBlank()) synopsis = null

            val scoreValue = try {
                document.select("b#repimdb strong").text().trim().toFloatOrNull()
            } catch (e: Exception) {
                null
            }
            val year = Regex("Ano:\\s*(\\d{4})").find(document.text())?.groupValues?.get(1)?.toIntOrNull()

            // episódios: layout antigo (dooplay) + rail do animeq v22
            val episodes = mutableListOf<Episode>()
            try {
                document.select("div#episodes ul.episodios li").forEach { li ->
                    try {
                        val epUrl = fixUrl(li.select("div.episodiotitle a").attr("href"))
                        if (epUrl.isNotBlank()) {
                            val numText = li.select("div.numerando").text()
                            val match = Regex("(\\d+)\\s*-\\s*(\\d+)").find(numText)
                            val season = match?.groupValues?.get(1)?.toIntOrNull() ?: 1
                            val epNum = match?.groupValues?.get(2)?.toIntOrNull() ?: 1
                            episodes.add(newEpisode(epUrl) {
                                this.name = li.select("div.episodiotitle a").text().trim()
                                this.season = season
                                this.episode = epNum
                            })
                        }
                    } catch (e: Exception) {
                    }
                }
            } catch (e: Exception) {
            }
            if (episodes.isEmpty()) {
                try {
                    document.select("a[href*='/episodio/'], a[href*='/assistir-']")
                        .map { fixUrl(it.attr("href")) }
                        .filter { it.startsWith("http") }
                        .distinct()
                        .forEach { epUrl ->
                            try {
                                val epNum = Regex("(?:episodio[-\\s]*)(\\d+)", RegexOption.IGNORE_CASE)
                                    .find(epUrl)?.groupValues?.get(1)?.toIntOrNull() ?: 1
                                episodes.add(newEpisode(epUrl) {
                                    this.name = "Episódio $epNum"
                                    this.episode = epNum
                                })
                            } catch (e: Exception) {
                            }
                        }
                } catch (e: Exception) {
                }
            }

            val isMovie = url.contains("/filme/") || episodes.isEmpty()
            if (isMovie) {
                newMovieLoadResponse(cleanTitle, url, TvType.AnimeMovie, url) {
                    this.posterUrl = poster
                    this.plot = synopsis
                    this.year = year
                    if (scoreValue != null) this.score = Score.from10(scoreValue)
                }
            } else {
                newTvSeriesLoadResponse(cleanTitle, url, TvType.Anime, episodes.distinctBy { it.url }) {
                    this.posterUrl = poster
                    this.plot = synopsis
                    this.year = year
                    if (scoreValue != null) this.score = Score.from10(scoreValue)
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    // -------------------------------------------------------------- loadLinks

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        return try {
            var found = false
            val html = try {
                app.get(data, headers = defaultHeaders).text
            } catch (e: Exception) {
                ""
            }

            // animeq v22: botões SD/HD/FHD -> blocos data-animeq-source com mp4 direto ou iframe
            val serverNames = HashMap<String, String>()
            try {
                Regex("data-animeq-switch=\"(\\d+)\"[^>]*data-source-name=\"([^\"]+)\"").findAll(html).forEach {
                    serverNames[it.groupValues[1]] = it.groupValues[2]
                }
            } catch (e: Exception) {
            }

            val candidates = mutableListOf<Pair<String, String>>()
            try {
                val markers = Regex("data-animeq-source=\"(\\d+)\"").findAll(html).toList()
                markers.forEachIndexed { idx, m ->
                    try {
                        val key = m.groupValues[1]
                        val start = m.range.first
                        val end = markers.getOrNull(idx + 1)?.range?.first ?: html.length
                        val block = html.substring(start, end)
                        val label = serverNames[key] ?: "Server $key"
                        Regex("<source[^>]+src=\"([^\"]+)\"").findAll(block).forEach {
                            candidates.add(cleanEntity(it.groupValues[1]) to label)
                        }
                        Regex("<iframe[^>]+src=\"([^\"]+)\"").findAll(block).forEach {
                            candidates.add(cleanEntity(it.groupValues[1]) to label)
                        }
                    } catch (e: Exception) {
                    }
                }
            } catch (e: Exception) {
            }

            // fallback genérico (layout antigo / players avulsos)
            try {
                Regex("<iframe[^>]+src=\"([^\"]+)\"").findAll(html).forEach {
                    candidates.add(cleanEntity(it.groupValues[1]) to "")
                }
                Regex("<source[^>]+src=\"([^\"]+)\"").findAll(html).forEach {
                    candidates.add(cleanEntity(it.groupValues[1]) to "")
                }
            } catch (e: Exception) {
            }

            for ((rawEmbed, label) in candidates.distinctBy { it.first }) {
                try {
                    val quality = when (label.uppercase().trim()) {
                        "FHD" -> 1080
                        "HD" -> 720
                        "SD" -> 480
                        else -> 0
                    }
                    val title = if (label.isBlank()) "AnimesCloud" else "AnimesCloud $label"
                    val embedUrl = cleanEntity(rawEmbed)
                    when {
                        // contrato decompilado: source=<url-decodificada> chega em mp4/m3u8 direto
                        embedUrl.contains("source=") &&
                            (embedUrl.contains(".mp4") || embedUrl.contains(".m3u8")) -> {
                            val src = Regex("source=([^&]+)").find(embedUrl)?.groupValues?.get(1)
                            if (src != null) {
                                val decoded = try {
                                    URLDecoder.decode(src, "UTF-8")
                                } catch (e: Exception) {
                                    src
                                }
                                callback.invoke(
                                    newExtractorLink(title, title, decoded, ExtractorLinkType.VIDEO) {
                                        this.referer = mainUrl
                                        this.quality = quality
                                    }
                                )
                                found = true
                            }
                        }
                        embedUrl.contains("animeshd.cloud") -> {
                            try {
                                val extractor = AnimesHD()
                                extractor.name = "$title HD"
                                extractor.getUrl(embedUrl, mainUrl, subtitleCallback, callback)
                                found = true
                            } catch (e: Exception) {
                            }
                        }
                        embedUrl.contains("animes.strp2p.com") -> {
                            try {
                                val extractor = AnimesSTRP()
                                extractor.name = "$title STRP"
                                extractor.getUrl(embedUrl, mainUrl, subtitleCallback, callback)
                                found = true
                            } catch (e: Exception) {
                            }
                        }
                        embedUrl.contains(".m3u8") || embedUrl.contains(".mp4") -> {
                            val type =
                                if (embedUrl.contains(".m3u8")) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO
                            callback.invoke(
                                newExtractorLink(title, title, embedUrl, type) {
                                    this.referer = mainUrl
                                    this.quality = quality
                                }
                            )
                            found = true
                        }
                        embedUrl.startsWith("http") -> {
                            try {
                                loadExtractor(embedUrl, mainUrl, subtitleCallback, callback)
                                found = true
                            } catch (e: Exception) {
                            }
                        }
                    }
                } catch (e: Exception) {
                }
            }
            found
        } catch (e: Exception) {
            false
        }
    }

    private fun cleanEntity(s: String): String {
        return s.replace("&#038;", "&")
            .replace("&amp;", "&")
            .replace("\\/", "/")
            .trim()
    }
}

/** Players VidStack usados pelo site (contrato decompilado). */
class AnimesHD : VidStack() {
    override var name = "AnimesHD"
    override var mainUrl = "https://animeshd.cloud"
    override var requiresReferer = true
}

class AnimesSTRP : VidStack() {
    override var name = "Animes STRP"
    override var mainUrl = "https://animes.strp2p.com"
    override var requiresReferer = true
}
