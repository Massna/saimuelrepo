package com.OverFlix

import com.OverFlix.extractor.OverFlixExtractor
import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element
import java.net.URLEncoder

class OverFlix : MainAPI() {
    override var mainUrl = "https://overflix.cc"
    override var name = "OverFlix"
    override val hasMainPage = true
    override var lang = "pt-br"
    override val hasDownloadSupport = true
    override val supportedTypes = setOf(TvType.Movie, TvType.TvSeries)
    override val usesWebView = false

    companion object {
        private val MAIN_SECTIONS = listOf(
            "/inicio" to "Início",
            "/filmes" to "Filmes",
            "/series" to "Séries"
        )
    }

    override val mainPage = mainPageOf(
        *MAIN_SECTIONS.map { (path, name) -> "$mainUrl$path" to name }.toTypedArray()
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        return try {
            var url = request.data
            if (page > 1) {
                url = if (url.contains("?")) "$url&page=$page" else "$url?page=$page"
            }
            val document = app.get(url).document
            val items = document.select("article.relative").mapNotNull { it.toSearchResult() }
            val hasNextPage = document
                .select("a[href*='page/'], a[href*='pagina/'], a:contains(Próxima)")
                .isNotEmpty()
            newHomePageResponse(request.name, items.distinctBy { it.url }, hasNext = hasNextPage)
        } catch (e: Exception) {
            newHomePageResponse(request.name, emptyList(), hasNext = false)
        }
    }

    /**
     * Card validado ao vivo: article.relative -> a[href]
     * (/filme/<slug>, /serie/<slug>, /episodio/assistir-<slug>-<T>x<E>)
     * + img (src = https://image.tmdb.org/t/p/w500<arquivo>; fallback
     * alt='w300 <arquivo>' = poster https://image.tmdb.org/t/p/w300<arquivo>).
     */
    private fun Element.toSearchResult(): SearchResponse? {
        val a = this.selectFirst("a[href]") ?: return null
        val href = a.attr("href").ifBlank { return null }
        val img = this.selectFirst("img")
        val alt = img?.attr("alt")?.trim().orEmpty()
        val src = img?.attr("src")?.trim().orEmpty()

        // alt='w300 /arquivo.jpg' -> poster TMDB construido a partir do alt
        val posterFromAlt = Regex("^w\\d+\\s+(/\\S+)").find(alt)?.let {
            val size = Regex("^w\\d+").find(alt)?.value ?: "w300"
            "https://image.tmdb.org/t/p/$size${it.groupValues[1]}"
        }
        val poster = src.ifBlank { posterFromAlt.orEmpty() }.ifBlank { null }

        val isPosterAlt = posterFromAlt != null
        val title = if (isPosterAlt || alt.isBlank() || alt.startsWith("w3")) {
            href.substringAfterLast("/").replace('-', ' ').trim()
        } else alt
        if (title.isBlank()) return null

        val isMovie = href.contains("/filme/")
        val isEpisode = href.contains("/episodio/")
        val epMatch = if (isEpisode) Regex("-(\\d+)x(\\d+)").findAll(href).lastOrNull() else null
        val label = if (epMatch != null) "$title ${epMatch.groupValues[1]}x${epMatch.groupValues[2]}" else title

        // episodio avulso (cards "Em Alta") abre direto como reproducao
        return if (isMovie || isEpisode) {
            newMovieSearchResponse(label, href, TvType.Movie) {
                this.posterUrl = poster
            }
        } else {
            newTvSeriesSearchResponse(title, href, TvType.TvSeries) {
                this.posterUrl = poster
            }
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        if (query.length < 2) return emptyList()
        return try {
            val enc = URLEncoder.encode(query.trim(), "UTF-8")
            val document = app.get("$mainUrl/pesquisa?s=$enc").document
            document.select("article.relative").mapNotNull {
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

            // detalhe: h1 'Ver X Online' (limpar)
            val rawTitle = document.selectFirst("h1")?.text()?.trim() ?: return null
            val title = rawTitle
                .replace(Regex("^Ver\\s+", RegexOption.IGNORE_CASE), "")
                .replace(Regex("\\s+Online.*$", RegexOption.IGNORE_CASE), "")
                .trim()
                .ifBlank { rawTitle }

            val poster = document.selectFirst("meta[property=og:image]")?.attr("content")?.ifBlank { null }
            val plot = document.selectFirst("meta[name=description]")?.attr("content")?.trim()?.ifBlank { null }
            val year = document.selectFirst("meta[property=article:published_time]")
                ?.attr("content")?.take(4)?.toIntOrNull()
            val html = document.html()

            if (!url.contains("/serie/")) {
                // filme: player = iframe fshd.link/filme/<id> | episodio avulso: pagina joga direto
                val fshdId = Regex("fshd\\.link/filme/(\\w+)").find(html)?.groupValues?.get(1)
                val data = if (!fshdId.isNullOrBlank()) "$fshdId|movie" else url
                return newMovieLoadResponse(title, url, TvType.Movie, data) {
                    this.posterUrl = poster
                    this.year = year
                    this.plot = plot
                }
            }

            // serie: episodios 'a[href*=/episodio/]' slug 'assistir-<slug>-<T>x<E>'
            val episodes = document.select("a[href*=/episodio/]").mapNotNull { a ->
                val href = a.attr("href").ifBlank { return@mapNotNull null }
                val m = Regex("-(\\d+)x(\\d+)").findAll(href).lastOrNull() ?: return@mapNotNull null
                val season = m.groupValues[1].toIntOrNull() ?: return@mapNotNull null
                val episode = m.groupValues[2].toIntOrNull() ?: return@mapNotNull null
                newEpisode(href) {
                    this.name = "T$season E$episode"
                    this.season = season
                    this.episode = episode
                }
            }.distinctBy { it.data }

            newTvSeriesLoadResponse(title, url, TvType.TvSeries, episodes) {
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
            when {
                data.endsWith("|movie") -> {
                    val id = data.removeSuffix("|movie").trim()
                    if (id.isBlank() || id == "0") return false
                    val streams = OverFlixExtractor.getStreams(id, "filme", 1, 1, subtitleCallback)
                    streams.forEach { callback(it) }
                    streams.isNotEmpty()
                }
                data.contains("|") -> {
                    val parts = data.split("|")
                    val id = parts[0].trim()
                    if (id.isBlank() || id == "0") return false
                    val season = parts.getOrNull(1)?.toIntOrNull() ?: 1
                    val episode = parts.getOrNull(2)?.toIntOrNull() ?: 1
                    val streams = OverFlixExtractor.getStreams(id, "serie", season, episode, subtitleCallback)
                    streams.forEach { callback(it) }
                    streams.isNotEmpty()
                }
                else -> {
                    // pagina do site: no episodio regex fshd.link/serie/<id>/<T>/<E>,
                    // no filme fshd.link/filme/<id>
                    val html = app.get(data).text
                    val serie = Regex("fshd\\.link/serie/(\\w+)/(\\d+)/(\\d+)").find(html)
                    val streams = if (serie != null) {
                        val id = serie.groupValues[1]
                        val season = serie.groupValues[2].toIntOrNull() ?: 1
                        val episode = serie.groupValues[3].toIntOrNull() ?: 1
                        OverFlixExtractor.getStreams(id, "serie", season, episode, subtitleCallback)
                    } else {
                        val id = Regex("fshd\\.link/filme/(\\w+)").find(html)?.groupValues?.get(1)
                            ?: return false
                        OverFlixExtractor.getStreams(id, "filme", 1, 1, subtitleCallback)
                    }
                    streams.forEach { callback(it) }
                    streams.isNotEmpty()
                }
            }
        } catch (e: Exception) {
            false
        }
    }
}
