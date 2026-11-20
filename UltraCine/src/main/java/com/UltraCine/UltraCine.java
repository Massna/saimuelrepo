package com.UltraCine;

import com.lagradost.cloudstream3.Actor;
import com.lagradost.cloudstream3.ActorRole;
import com.lagradost.cloudstream3.Episode;
import com.lagradost.cloudstream3.HomePageResponse;
import com.lagradost.cloudstream3.LoadResponse;
import com.lagradost.cloudstream3.MainAPI;
import com.lagradost.cloudstream3.MainAPIKt;
import com.lagradost.cloudstream3.MainPageData;
import com.lagradost.cloudstream3.MainPageRequest;
import com.lagradost.cloudstream3.MovieLoadResponse;
import com.lagradost.cloudstream3.MovieSearchResponse;
import com.lagradost.cloudstream3.Score;
import com.lagradost.cloudstream3.SearchQuality;
import com.lagradost.cloudstream3.SearchResponse;
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.TvSeriesLoadResponse;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: UltraCine.kt */
/* JADX INFO: loaded from: /tmp/cs3/UltraCine/classes.dex */
/*
 * REESCRITO CONTRA O NOVO CONTRATO em 2026-11-20: ultracine.autos (www.ultracine.blog redireciona
 * para cá). O contrato antigo (tema aa-cn / post-lst / lnk-blk / data-season-id / player
 * painel-aso.sbs DNS-dead / seriesboa.live 404) está MORTO.
 * Novo contrato validado ao vivo:
 *   listas: /lancamentos /filmes /series /episodios (cards div.grid-item, links /filme/online/<slug>
 *           e /serie/online/<slug>); busca: GET /pesquisa?s=<query>;
 *   ficha: <title> "Título (ANO) — assistir online | UltraCine" (metadados TMDB-like:
 *          posters image.tmdb.org/t/p/...); episódios: /episodio/online/assistir-<slug>-<S>x<E>;
 *   player: botões com data-iframe="//fshd.link/filme/<imdbId>" (docs: fshd.link/doc;
 *          /filme/tt0371746 -> 200; /serie/<id>/<temp>/<ep> suportado). Resolução dos embeds
 *          conhecidos via loadExtractor (fshd.link é app JS ofuscado — ver report_laneC.md).
 */
public final class UltraCine extends MainAPI {

    @NotNull
    private String mainUrl = "https://ultracine.autos";

    @NotNull
    private String name = "UltraCine";

    @NotNull
    private String lang = "pt-br";
    private final boolean hasMainPage = true;
    private final boolean hasDownloadSupport = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Movie, TvType.TvSeries});

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to(getMainUrl() + "/lancamentos", "Lançamentos"), TuplesKt.to(getMainUrl() + "/filmes", "Filmes"), TuplesKt.to(getMainUrl() + "/series", "Séries"), TuplesKt.to(getMainUrl() + "/episodios", "Episódios")});

    @NotNull
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36";

    @NotNull
    private static final Regex TITLE_YEAR = new Regex("^(.+?)\\s*\\((\\d{4})\\)");

    @NotNull
    private static final Regex SEASON_EPISODE = new Regex("(\\d+)x(\\d+)");

    /* JADX INFO: renamed from: com.UltraCine.UltraCine$load$2, reason: invalid class name */
    @DebugMetadata(c = "com.UltraCine.UltraCine$load$2", f = "UltraCine.kt", i = {}, l = {95}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<TvSeriesLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<Pair<Actor, ActorRole>> $actors;
        final /* synthetic */ List<String> $genres;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ Double $score;
        final /* synthetic */ String $trailer;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, Integer num, String str2, List<String> list, Double d, List<? extends Pair<Actor, ? extends ActorRole>> list2, String str3, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$poster = str;
            this.$year = num;
            this.$plot = str2;
            this.$genres = list;
            this.$score = d;
            this.$actors = list2;
            this.$trailer = str3;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass2 = new AnonymousClass2(this.$poster, this.$year, this.$plot, this.$genres, this.$score, this.$actors, this.$trailer, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        public final Object invoke(TvSeriesLoadResponse tvSeriesLoadResponse, Continuation<? super Unit> continuation) {
            return create(tvSeriesLoadResponse, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    LoadResponse loadResponse = (TvSeriesLoadResponse) this.L$0;
                    loadResponse.setPosterUrl(this.$poster);
                    loadResponse.setYear(this.$year);
                    loadResponse.setPlot(this.$plot);
                    loadResponse.setTags(this.$genres);
                    loadResponse.setScore(Score.Companion.from10(this.$score));
                    List<Pair<Actor, ActorRole>> list = this.$actors;
                    if (!(list == null || list.isEmpty())) {
                        LoadResponse.Companion.addActorsRole(loadResponse, this.$actors);
                    }
                    this.label = 1;
                    if (LoadResponse.Companion.addTrailer$default(LoadResponse.Companion, loadResponse, this.$trailer, (String) null, false, (Continuation) this, 6, (Object) null) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.UltraCine.UltraCine$load$3, reason: invalid class name */
    @DebugMetadata(c = "com.UltraCine.UltraCine$load$3", f = "UltraCine.kt", i = {}, l = {106}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<Pair<Actor, ActorRole>> $actors;
        final /* synthetic */ String $durationText;
        final /* synthetic */ List<String> $genres;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ Double $score;
        final /* synthetic */ String $trailer;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ UltraCine this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(String str, Integer num, String str2, List<String> list, UltraCine ultraCine, String str3, Double d, List<? extends Pair<Actor, ? extends ActorRole>> list2, String str4, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$poster = str;
            this.$year = num;
            this.$plot = str2;
            this.$genres = list;
            this.this$0 = ultraCine;
            this.$durationText = str3;
            this.$score = d;
            this.$actors = list2;
            this.$trailer = str4;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass3 = new AnonymousClass3(this.$poster, this.$year, this.$plot, this.$genres, this.this$0, this.$durationText, this.$score, this.$actors, this.$trailer, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        public final Object invoke(MovieLoadResponse movieLoadResponse, Continuation<? super Unit> continuation) {
            return create(movieLoadResponse, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    LoadResponse loadResponse = (MovieLoadResponse) this.L$0;
                    loadResponse.setPosterUrl(this.$poster);
                    loadResponse.setYear(this.$year);
                    loadResponse.setPlot(this.$plot);
                    loadResponse.setTags(this.$genres);
                    loadResponse.setDuration(this.this$0.parseDuration(this.$durationText));
                    loadResponse.setScore(Score.Companion.from10(this.$score));
                    List<Pair<Actor, ActorRole>> list = this.$actors;
                    if (!(list == null || list.isEmpty())) {
                        LoadResponse.Companion.addActorsRole(loadResponse, this.$actors);
                    }
                    this.label = 1;
                    if (LoadResponse.Companion.addTrailer$default(LoadResponse.Companion, loadResponse, this.$trailer, (String) null, false, (Continuation) this, 6, (Object) null) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Unit.INSTANCE;
        }
    }

    @NotNull
    public String getMainUrl() {
        return this.mainUrl;
    }

    public void setMainUrl(@NotNull String str) {
        this.mainUrl = str;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(@NotNull String str) {
        this.name = str;
    }

    @NotNull
    public String getLang() {
        return this.lang;
    }

    public void setLang(@NotNull String str) {
        this.lang = str;
    }

    public boolean getHasMainPage() {
        return this.hasMainPage;
    }

    public boolean getHasDownloadSupport() {
        return this.hasDownloadSupport;
    }

    @NotNull
    public Set<TvType> getSupportedTypes() {
        return this.supportedTypes;
    }

    @NotNull
    public List<MainPageData> getMainPage() {
        return this.mainPage;
    }

    @Nullable
    public Object getMainPage(int page, @NotNull MainPageRequest request, @NotNull Continuation<? super HomePageResponse> continuation) {
        try {
            String url = request.getData() + (page > 1 ? "?page=" + page : "");
            Document doc = Jsoup.connect(url).userAgent(UA).timeout(20000).execute().parse();
            Collection destination = new ArrayList();
            for (Object element : doc.select("div.grid-item, div.article-card, div.product")) {
                SearchResponse searchResult = toSearchResult((Element) element);
                if (searchResult != null) {
                    destination.add(searchResult);
                }
            }
            List home = (List) destination;
            return MainAPIKt.newHomePageResponse$default(request.getName(), home, (Boolean) null, 4, (Object) null);
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        try {
            Document doc = Jsoup.connect(getMainUrl() + "/pesquisa?s=" + query).userAgent(UA).timeout(20000).execute().parse();
            Collection destination = new ArrayList();
            for (Object element : doc.select("div.grid-item, div.article-card, div.product, article")) {
                SearchResponse searchResult = toSearchResult((Element) element);
                if (searchResult != null) {
                    destination.add(searchResult);
                }
            }
            return (List) destination;
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    private final SearchResponse toSearchResult(Element el) {
        Element link = el.selectFirst("a[href*='/filme/online/'], a[href*='/serie/online/'], a[href*='/episodio/online/'], a[href*='/online/']");
        if (link == null) {
            return null;
        }
        String href = link.attr("href");
        if (StringsKt.isBlank(href)) {
            return null;
        }
        Element titleEl = el.selectFirst("h5.h5-size, h5, h4, h3, h2, .product-description, .titulo");
        String title = titleEl != null ? titleEl.text() : link.attr("title");
        if (StringsKt.isBlank(title)) {
            Element img = el.selectFirst("img");
            title = img != null ? img.attr("alt") : "";
        }
        final String poster = selectPoster(el, "img", "/w300/");
        Element yearEl = el.selectFirst("span.ano, .ano, .year");
        final Integer year = (yearEl == null || yearEl.text() == null) ? null : yearOf(yearEl.text());
        Element qualityEl = el.selectFirst("span.modo-c, span.post-ql, .modo-c");
        final SearchQuality quality = MainAPIKt.getQualityFromString(qualityEl != null ? qualityEl.text() : null);
        Element voteEl = el.selectFirst("span.vote, .nota, .rating");
        final String ratingText = voteEl != null ? voteEl.text() : null;
        final boolean isSerie = StringsKt.contains$default(href, "/serie/", false, 2, (Object) null) || StringsKt.contains$default(href, "/episodio/", false, 2, (Object) null);
        return MainAPIKt.newMovieSearchResponse$default(this, StringsKt.trim(title).toString(), href, isSerie ? TvType.TvSeries : TvType.Movie, false, new Function1() { // from class: com.UltraCine.UltraCine$$ExternalSyntheticLambda1
            public final Object invoke(Object obj) {
                return UltraCine.toSearchResult$lambda$2(poster, year, quality, ratingText, (MovieSearchResponse) obj);
            }
        }, 8, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResult$lambda$2(String $poster, Integer $year, SearchQuality $quality, String $ratingText, MovieSearchResponse $this$newMovieSearchResponse) {
        $this$newMovieSearchResponse.setPosterUrl($poster);
        $this$newMovieSearchResponse.setYear($year);
        $this$newMovieSearchResponse.setQuality($quality);
        $this$newMovieSearchResponse.setScore(Score.Companion.from10($ratingText));
        return Unit.INSTANCE;
    }

    @Nullable
    public Object load(@NotNull String url, @NotNull Continuation<? super LoadResponse> continuation) throws IOException {
        try {
            Document document = Jsoup.connect(url).userAgent(UA).timeout(20000).execute().parse();
            /* Metadados TMDB-like do <title>: "Título (ANO) — assistir online | UltraCine" */
            String title = null;
            Integer year = null;
            String headTitle = document.title();
            MatchResult m = headTitle == null ? null : Regex.find$default(TITLE_YEAR, headTitle, 0, 2, (Object) null);
            if (m != null) {
                title = StringsKt.trim((String) m.getGroupValues().get(1)).toString();
                year = StringsKt.toIntOrNull((String) m.getGroupValues().get(2));
            }
            if (StringsKt.isBlank(title)) {
                Element titleEl = document.selectFirst(".titulo-page");
                String raw = titleEl != null ? titleEl.text() : null;
                title = raw == null ? null : StringsKt.trim(StringsKt.replace$default(raw, "Online", "", false, 4, (Object) null)).toString();
            }
            if (StringsKt.isBlank(title)) {
                return null;
            }
            if (year == null) {
                year = extractInt(document, "span.ano, .ano-estudio, .ano");
            }
            String poster = selectPoster(document, "img.TPostBg", "/w1280/");
            if (poster == null) {
                Element tmdb = document.selectFirst("link[href*='image.tmdb.org/t/p/'], img[src*='image.tmdb.org/t/p/'], img[data-src*='image.tmdb.org/t/p/']");
                String rawPoster = tmdb != null ? (StringsKt.isBlank(tmdb.attr("href")) ? (StringsKt.isBlank(tmdb.attr("data-src")) ? tmdb.attr("src") : tmdb.attr("data-src")) : tmdb.attr("href")) : null;
                poster = rawPoster == null ? null : StringsKt.replace$default(rawPoster, "/w300/", "/original/", false, 4, (Object) null);
            }
            if (poster != null) {
                poster = StringsKt.replace$default(poster, "/w500/", "/original/", false, 4, (Object) null);
                poster = StringsKt.replace$default(poster, "/w1280/", "/original/", false, 4, (Object) null);
            }
            String durationText = extractText(document, "span.duration, .duration, .ano-estudio");
            Element scoreEl = document.selectFirst("span.vote span.num, span.vote, .nota, .rating");
            Double score = (scoreEl == null || scoreEl.text() == null) ? null : StringsKt.toDoubleOrNull(scoreEl.text());
            Element plotEl = document.selectFirst(".descricao, .article-summary, .description");
            String plot = plotEl != null ? plotEl.text() : null;
            List genres = new ArrayList();
            for (Object it : document.select("span.genres a, .genres a, a[href*='/genero']")) {
                genres.add(((Element) it).text());
            }
            List<Pair<Actor, ActorRole>> listSelectActors = selectActors(document);
            Element trailerEl = document.selectFirst("iframe[src*='youtube.com'], iframe[src*='youtu.be']");
            String trailer = trailerEl != null ? trailerEl.attr("src") : null;
            boolean isSerie = StringsKt.contains$default(url, "/serie/", false, 2, (Object) null);
            if (!isSerie) {
                return MainAPIKt.newMovieLoadResponse(this, title, url, TvType.Movie, url, new AnonymousClass3(poster, year, plot, genres, this, durationText, score, listSelectActors, trailer, null), continuation);
            }
            List<Episode> episodes = parseEpisodes(document);
            return MainAPIKt.newTvSeriesLoadResponse(this, title, url, TvType.TvSeries, episodes, new AnonymousClass2(poster, year, plot, genres, score, listSelectActors, trailer, null), continuation);
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    @Nullable
    public Object loadLinks(@NotNull String data, boolean isCasting, @NotNull Function1<? super SubtitleFile, Unit> subtitleCallback, @NotNull Function1<? super ExtractorLink, Unit> callback, @NotNull Continuation<? super Boolean> continuation) {
        List<String> embeds;
        try {
            Document doc = Jsoup.connect(data).userAgent(UA).timeout(20000).execute().parse();
            embeds = extractEmbedLinks(doc);
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (embeds.isEmpty()) {
            return Boolean.TRUE;
        }
        processChain(embeds, 0, data, subtitleCallback, callback, continuation);
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }

    /* Cadeia de continuação (padrão PobreFlix): um embed por vez; loadExtractor é suspend, então
     * o avanço acontece ou síncrono (retorna valor) ou via resumeWith do step. Falha de um
     * servidor não derruba os demais. */
    private final void processChain(final List<String> embeds, int start, final String referer, final Function1<? super SubtitleFile, Unit> subtitleCallback, final Function1<? super ExtractorLink, Unit> callback, final Continuation<? super Boolean> continuation) {
        int i = start;
        while (i < embeds.size()) {
            final int next = i + 1;
            Continuation<Object> step = new Continuation<Object>() {
                @NotNull
                public CoroutineContext getContext() {
                    return continuation.getContext();
                }

                public void resumeWith(@NotNull Object result) {
                    UltraCine.this.processChain(embeds, next, referer, subtitleCallback, callback, continuation);
                }
            };
            Object r;
            try {
                r = ExtractorApiKt.loadExtractor(embeds.get(i), referer, subtitleCallback, callback, step);
            } catch (Throwable th) {
                r = ResultKt.createFailure(th);
            }
            if (r == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return;
            }
            i++;
        }
        continuation.resumeWith(Boolean.TRUE);
    }

    /* Novo: episódios /episodio/online/assistir-<slug>-<S>x<E> (links na página da série).
     * O contrato antigo (data-season-id do player seriesboa.live) está morto. */
    private final List<Episode> parseEpisodes(Document doc) {
        List<Episode> episodes = new ArrayList<>();
        for (Element a : doc.select("a[href*='/episodio/online/']")) {
            String href = a.attr("href");
            MatchResult m = Regex.find$default(SEASON_EPISODE, href, 0, 2, (Object) null);
            if (m == null) {
                continue;
            }
            Integer seasonOrNull = StringsKt.toIntOrNull((String) m.getGroupValues().get(1));
            Integer episodeOrNull = StringsKt.toIntOrNull((String) m.getGroupValues().get(2));
            final int seasonNumber = seasonOrNull != null ? seasonOrNull.intValue() : 1;
            final int number = episodeOrNull != null ? episodeOrNull.intValue() : 1;
            final String name = StringsKt.isBlank(a.text()) ? "Episódio " + number : a.text();
            episodes.add(MainAPIKt.newEpisode(this, href, new Function1() { // from class: com.UltraCine.UltraCine$$ExternalSyntheticLambda0
                public final Object invoke(Object obj) {
                    return UltraCine.parseEpisodes$lambda$8$lambda$7$lambda$6(name, seasonNumber, number, (Episode) obj);
                }
            }));
        }
        /* distinct por data (contrato antigo preservado) */
        Iterable $this$distinctBy$iv = episodes;
        HashSet set$iv = new HashSet();
        ArrayList list$iv = new ArrayList();
        for (Object e$iv : $this$distinctBy$iv) {
            Episode it2 = (Episode) e$iv;
            if (set$iv.add(it2.getData())) {
                list$iv.add(e$iv);
            }
        }
        return list$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit parseEpisodes$lambda$8$lambda$7$lambda$6(String $name, int $seasonNumber, int $number, Episode $this$newEpisode) {
        $this$newEpisode.setName(StringsKt.trim($name).toString());
        $this$newEpisode.setSeason(Integer.valueOf($seasonNumber));
        $this$newEpisode.setEpisode(Integer.valueOf($number));
        return Unit.INSTANCE;
    }

    /* Novo: players nos botões data-iframe (fshd.link/filme/<imdbId>) + iframes da área do player. */
    private final List<String> extractEmbedLinks(Document doc) {
        List<String> out = new ArrayList<>();
        for (Element it : doc.select("[data-iframe]")) {
            String strAttr = it.attr("data-iframe");
            if (!StringsKt.isBlank(strAttr)) {
                out.add(StringsKt.startsWith$default(strAttr, "//", false, 2, (Object) null) ? "https:" + strAttr : strAttr);
            }
        }
        for (Element it2 : doc.select("div#players iframe, div.tab-video iframe, div.player-video-filme iframe, iframe[src*='fshd.link']")) {
            String strAttr2 = it2.attr("src");
            if (StringsKt.isBlank(strAttr2)) {
                strAttr2 = it2.attr("data-src");
            }
            if (!StringsKt.isBlank(strAttr2)) {
                String src = StringsKt.startsWith$default(strAttr2, "//", false, 2, (Object) null) ? "https:" + strAttr2 : strAttr2;
                if (!StringsKt.contains$default(src, "youtube", false, 2, (Object) null) && !StringsKt.contains$default(src, "youtu.be", false, 2, (Object) null)) {
                    out.add(src);
                }
            }
        }
        return CollectionsKt.distinct(out);
    }

    private final List<Pair<Actor, ActorRole>> selectActors(Document doc) {
        List<Pair<Actor, ActorRole>> actors = new ArrayList<>();
        for (Object it : doc.select("div.cast a, ul.cast-lst a, .elenco a, .person a")) {
            Element el = (Element) it;
            String name = el.text();
            if (!StringsKt.isBlank(name)) {
                actors.add(TuplesKt.to(new Actor(name, el.attr("href")), (Object) null));
            }
        }
        if (!actors.isEmpty()) {
            return actors;
        }
        return null;
    }

    private final String selectPoster(Element $this$selectPoster, String selector, String replace) {
        Element img = $this$selectPoster.selectFirst(selector);
        if (img == null) {
            return null;
        }
        String strAttr = img.attr("src");
        if (StringsKt.isBlank(strAttr)) {
            strAttr = img.attr("data-src");
        }
        String src = strAttr;
        String it = !StringsKt.isBlank(src) ? src : null;
        if (it == null) {
            return null;
        }
        String str = StringsKt.startsWith$default(it, "//", false, 2, (Object) null) ? "https:" + it : it;
        if (str != null) {
            return StringsKt.replace$default(str, replace, "/original/", false, 4, (Object) null);
        }
        return null;
    }

    private final String selectPoster(Document $this$selectPoster, String selector, String replace) {
        Element img = $this$selectPoster.selectFirst(selector);
        if (img == null) {
            return null;
        }
        String strAttr = img.attr("src");
        if (StringsKt.isBlank(strAttr)) {
            strAttr = img.attr("data-src");
        }
        String src = strAttr;
        String it = !StringsKt.isBlank(src) ? src : null;
        if (it == null) {
            return null;
        }
        String str = StringsKt.startsWith$default(it, "//", false, 2, (Object) null) ? "https:" + it : it;
        if (str != null) {
            return StringsKt.replace$default(str, replace, "/original/", false, 4, (Object) null);
        }
        return null;
    }

    private final Integer extractInt(Document $this$extractInt, String selector) throws IOException {
        CharSequence $this$filter$iv;
        Element elementSelectFirst = $this$extractInt.selectFirst(selector);
        if (elementSelectFirst != null && ($this$filter$iv = elementSelectFirst.text()) != null) {
            CharSequence $this$filterTo$iv$iv = $this$filter$iv;
            Appendable destination$iv$iv = new StringBuilder();
            int length = $this$filterTo$iv$iv.length();
            for (int index$iv$iv = 0; index$iv$iv < length; index$iv$iv++) {
                char element$iv$iv = $this$filterTo$iv$iv.charAt(index$iv$iv);
                if (Character.isDigit(element$iv$iv)) {
                    destination$iv$iv.append(element$iv$iv);
                }
            }
            String $this$filter$iv2 = ((StringBuilder) destination$iv$iv).toString();
            if ($this$filter$iv2 != null) {
                return StringsKt.toIntOrNull($this$filter$iv2);
            }
        }
        return null;
    }

    private final String extractText(Document $this$extractText, String selector) {
        Element elementSelectFirst = $this$extractText.selectFirst(selector);
        if (elementSelectFirst != null) {
            return elementSelectFirst.text();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Integer parseDuration(String text) {
        List groupValues;
        String str;
        if (text == null) {
            return null;
        }
        MatchResult it = Regex.find$default(new Regex("(\\d+)h\\s*(\\d+)m"), text, 0, 2, (Object) null);
        if (it == null) {
            MatchResult matchResultFind$default = Regex.find$default(new Regex("(\\d+)m"), text, 0, 2, (Object) null);
            if (matchResultFind$default == null || (groupValues = matchResultFind$default.getGroupValues()) == null || (str = (String) groupValues.get(1)) == null) {
                return null;
            }
            return StringsKt.toIntOrNull(str);
        }
        Integer intOrNull = StringsKt.toIntOrNull((String) it.getGroupValues().get(1));
        int iIntValue = (intOrNull != null ? intOrNull.intValue() : 0) * 60;
        Integer intOrNull2 = StringsKt.toIntOrNull((String) it.getGroupValues().get(2));
        return Integer.valueOf(iIntValue + (intOrNull2 != null ? intOrNull2.intValue() : 0));
    }

    private final Integer yearOf(String text) {
        if (text == null) {
            return null;
        }
        MatchResult m = Regex.find$default(new Regex("(\\d{4})"), text, 0, 2, (Object) null);
        return m == null ? null : StringsKt.toIntOrNull(m.getValue());
    }
}
