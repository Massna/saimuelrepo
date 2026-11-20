package com.Doramas;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import com.lagradost.cloudstream3.SearchResponse;
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.TvSeriesLoadResponse;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.cloudstream3.utils.ExtractorLinkType;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

/* JADX INFO: compiled from: Doramas.kt */
/* JADX INFO: loaded from: /tmp/cs3/Doramas/classes.dex */
/*
 * REESCRITO CONTRA O NOVO CONTRATO em 2026-11-20: doramasonline.net (WP + tema Dooplay).
 * O contrato antigo (doramasonline.co tema aa-cn + seriesboa.live/painel-aso.sbs) está MORTO
 * (doramasonline.co PARKED; seriesboa.live 404). Ground truth: nuvio doramasonline.js.
 * Cadeia validada ao vivo (Study Group S01E01):
 *   ficha /br/series/<slug>/ -> div.se-c / ul.episodios / div.numerando "S - E" ->
 *   /br/episodio/<slug>/ -> li.dooplay_player_option + #source-player-N iframe ->
 *   (a) player JSON booonceivu (?mode=to-salvando-seu-ip) -> midias[].url m3u8
 *       [Range probe: 200 application/x-mpegURL];
 *   (b) /aviso/?url=... -> csst.online|incvideo1.online embed -> Playerjs file: ".../get_file/..."
 *       -> 302 remote_control.php [Range probe: 206 video/mp4];
 *   (c) /aviso/?url=...png.strp2p.com (P2P) -> descartado honestamente.
 */
public final class Doramas extends MainAPI {

    @NotNull
    private String mainUrl = "https://doramasonline.net";

    @NotNull
    private String name = "Doramas";

    @NotNull
    private String lang = "pt-br";
    private final boolean hasMainPage = true;
    private final boolean hasDownloadSupport = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Movie, TvType.TvSeries});

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to(getMainUrl() + "/", "Lançamentos"), TuplesKt.to(getMainUrl() + "/br/series/", "Séries"), TuplesKt.to(getMainUrl() + "/br/filmes/", "Filmes"), TuplesKt.to(getMainUrl() + "/br/generos/k-drama/", "K-Drama"), TuplesKt.to(getMainUrl() + "/br/generos/dublado/", "Dublado"), TuplesKt.to(getMainUrl() + "/br/generos/legendado/", "Legendado"), TuplesKt.to(getMainUrl() + "/br/generos/comedia/", "Comédia"), TuplesKt.to(getMainUrl() + "/br/generos/acao/", "Ação")});

    @NotNull
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36";

    @NotNull
    private static final Regex SEASON_EPISODE = new Regex("^\\s*(\\d+)\\s*-\\s*(\\d+)\\s*$");

    @NotNull
    private static final Regex FOUR_DIGIT_YEAR = new Regex("(\\d{4})");

    @NotNull
    private static final Pattern PLAYERJS_FILE = Pattern.compile("file\\s*:\\s*\"([^\"]+)\"");

    @NotNull
    private static final Pattern PLAYERJS_SOURCES = Pattern.compile("(?:\\[([^\\]]+)\\])?\\s*(https?://[^,\\s\"']+)");

    private static final Pattern INCVIDEO_HOST = Pattern.compile("(?:^|\\.)(?:csst\\.online|incvideo1\\.online)$", 2);


    /* JADX INFO: renamed from: com.Doramas.Doramas$load$2, reason: invalid class name */
    @DebugMetadata(c = "com.Doramas.Doramas$load$2", f = "Doramas.kt", i = {}, l = {95}, m = "invokeSuspend", n = {}, s = {})
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
        AnonymousClass2(String str, Integer num, String str2, List<String> list, Double d, List<? extends Pair<Actor, ? extends ActorRole>> list2, String str3, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$year = num;
            this.$plot = str2;
            this.$genres = list;
            this.$score = d;
            this.$actors = (java.util.List) list2;
            this.$trailer = str3;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$poster, this.$year, this.$plot, this.$genres, this.$score, this.$actors, this.$trailer, continuation);
            anonymousClass2.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass2;
        }

        public final Object invoke(TvSeriesLoadResponse tvSeriesLoadResponse, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) (Object) create(tvSeriesLoadResponse, continuation)).invokeSuspend(Unit.INSTANCE);
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
                    if (LoadResponse.Companion.addTrailer(loadResponse, this.$trailer, (String) null, false, (Continuation) this) == coroutine_suspended) {
                        return (Continuation<Unit>) (Object) coroutine_suspended;
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

    /* JADX INFO: renamed from: com.Doramas.Doramas$load$3, reason: invalid class name */
    @DebugMetadata(c = "com.Doramas.Doramas$load$3", f = "Doramas.kt", i = {}, l = {106}, m = "invokeSuspend", n = {}, s = {})
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
        final /* synthetic */ Doramas this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(String str, Integer num, String str2, List<String> list, Doramas doramas, String str3, Double d, List<? extends Pair<Actor, ? extends ActorRole>> list2, String str4, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$year = num;
            this.$plot = str2;
            this.$genres = list;
            this.this$0 = doramas;
            this.$durationText = str3;
            this.$score = d;
            this.$actors = (java.util.List) list2;
            this.$trailer = str4;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$poster, this.$year, this.$plot, this.$genres, this.this$0, this.$durationText, this.$score, this.$actors, this.$trailer, continuation);
            anonymousClass3.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass3;
        }

        public final Object invoke(MovieLoadResponse movieLoadResponse, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) (Object) create(movieLoadResponse, continuation)).invokeSuspend(Unit.INSTANCE);
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
                    if (LoadResponse.Companion.addTrailer(loadResponse, this.$trailer, (String) null, false, (Continuation) this) == coroutine_suspended) {
                        return (Continuation<Unit>) (Object) coroutine_suspended;
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

    /* Lambda de inicialização de ExtractorLink (referer + qualidade). */
    static final class LinkInit extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $quality;
        final /* synthetic */ String $referer;
        private /* synthetic */ Object L$0;
        int label;

        LinkInit(String str, int i, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$referer = str;
            this.$quality = i;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            LinkInit linkInit = new LinkInit(this.$referer, this.$quality, continuation);
            linkInit.L$0 = obj;
            return (Continuation<Unit>) (Object) linkInit;
        }

        public final Object invoke(ExtractorLink extractorLink, Continuation<? super Unit> continuation) {
            return ((LinkInit) (Object) create(extractorLink, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object $result) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure($result);
            ExtractorLink $this$apply = (ExtractorLink) this.L$0;
            $this$apply.setReferer(this.$referer);
            $this$apply.setQuality(this.$quality);
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
            String url = request.getData() + (page > 1 ? "page/" + page + '/' : "");
            Document doc = Jsoup.connect(url).userAgent(UA).timeout(20000).execute().parse();
            Collection destination = new ArrayList();
            for (Object element : readCards(doc)) {
                SearchResponse searchResult = toSearchResult((Element) element);
                if (searchResult != null) {
                    destination.add(searchResult);
                }
            }
            List home = (List) destination;
            return MainAPIKt.newHomePageResponse(request.getName(), home, (Boolean) null);
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        try {
            Document doc = Jsoup.connect(getMainUrl() + "/?s=" + query).userAgent(UA).timeout(20000).execute().parse();
            Collection destination = new ArrayList();
            for (Object element : readCards(doc)) {
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

    /* Cartões Dooplay: .search-page article (busca) ou div.items article (listagens). */
    private final List<Element> readCards(Document doc) {
        List<Element> out = new ArrayList<>();
        for (Element el : doc.select(".search-page article, div.items article, div.items .item, article")) {
            Element link = cardLink(el);
            if (link != null && !out.contains(el)) {
                out.add(el);
            }
        }
        return out;
    }

    private final Element cardLink(Element el) {
        return el.selectFirst(".title a[href], .data h3 a[href], a[href*='/br/series/'], a[href*='/br/filmes/']");
    }

    private final SearchResponse toSearchResult(Element el) {
        Element link = cardLink(el);
        if (link == null) {
            return null;
        }
        String href = link.attr("href");
        if (StringsKt.isBlank(href)) {
            return null;
        }
        String title = link.text();
        if (StringsKt.isBlank(title)) {
            Element t = el.selectFirst(".data h3, h2, h3");
            title = t != null ? t.text() : "";
        }
        final String poster = firstAttr(el.selectFirst("img"), "/w500/");
        String yearText = firstText(el, ".meta span.year", "span.year", ".date", ".year");
        final Integer year = yearText == null ? null : yearOf(yearText);
        String ratingText = firstText(el, ".meta span.rating", "span.vote", ".rating");
        final String rating = ratingText;
        final boolean isSerie = StringsKt.contains(href, "/br/series/", false);
        return MainAPIKt.newMovieSearchResponse(this, StringsKt.trim(title).toString(), href, isSerie ? TvType.TvSeries : TvType.Movie, false, new Function1() { // from class: com.Doramas.Doramas$$ExternalSyntheticLambda1
            public final Object invoke(Object obj) {
                return Doramas.toSearchResult$lambda$1(poster, year, rating, (MovieSearchResponse) obj);
            }
        });
    }


    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResult$lambda$1(String $poster, Integer $year, String $rating, MovieSearchResponse $this$newMovieSearchResponse) {
        $this$newMovieSearchResponse.setPosterUrl($poster);
        $this$newMovieSearchResponse.setYear($year);
        $this$newMovieSearchResponse.setScore(Score.Companion.from10($rating));
        return Unit.INSTANCE;
    }

    @Nullable
    public Object load(@NotNull String url, @NotNull Continuation<? super LoadResponse> continuation) {
        try {
            Document document = Jsoup.connect(url).userAgent(UA).timeout(20000).execute().parse();
            String title = firstText(document, ".sheader .data h1");
            if (StringsKt.isBlank(title)) {
                return null;
            }
            String poster = firstAttr(document.selectFirst(".sheader .poster img"), (String) null);
            Integer year = yearOf(firstText(document, ".sheader .extra span.date", ".sheader .extra .date"));
            String durationText = firstText(document, ".sheader .extra span.runtime", "span.duration");
            String ratingText = firstText(document, ".sheader .data .meta .rating", "span.vote", ".rating");
            Double score = ratingText == null ? null : StringsKt.toDoubleOrNull(ratingText);
            String plot = firstText(document, ".synopsis", ".sbox .wp-content", ".description", ".wp-content");
            List genres = new ArrayList();
            for (Object it : document.select("div.sgeneros a, span.genres a")) {
                genres.add(((Element) it).text());
            }
            List<Pair<Actor, ActorRole>> listSelectActors = selectActors(document);
            Element trailerEl = document.selectFirst("iframe[src*='youtube.com'], iframe[src*='youtu.be']");
            String trailer = trailerEl != null ? trailerEl.attr("src") : null;
            boolean isSerie = StringsKt.contains(url, "/br/series/", false);
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
        List<String[]> jobs;
        try {
            jobs = resolvePage(data, subtitleCallback, callback);
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (jobs.isEmpty()) {
            return Boolean.TRUE;
        }
        processChain(jobs, 0, data, subtitleCallback, callback, continuation);
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }

    /* Cadeia de continuação (padrão PobreFlix): um job por vez; loadExtractor/newExtractorLink
     * são suspend, então o avanço acontece síncrono (retorno de valor) ou via resumeWith do step.
     * Falha de um servidor não derruba os demais. */
    private final void processChain(final List<String[]> jobs, int start, final String referer, final Function1<? super SubtitleFile, Unit> subtitleCallback, final Function1<? super ExtractorLink, Unit> callback, final Continuation<? super Boolean> continuation) {
        int i = start;
        while (i < jobs.size()) {
            final int next = i + 1;
            final String[] job = jobs.get(i);
            Continuation<Object> step = new Continuation<Object>() {
                @NotNull
                public CoroutineContext getContext() {
                    return continuation.getContext();
                }

                public void resumeWith(@NotNull Object result) {
                    if ("L".equals(job[0]) && (result instanceof ExtractorLink)) {
                        callback.invoke((ExtractorLink) result);
                    }
                    Doramas.this.processChain(jobs, next, referer, subtitleCallback, callback, continuation);
                }
            };
            Object r;
            try {
                if ("E".equals(job[0])) {
                    r = ExtractorApiKt.loadExtractor(job[1], referer, subtitleCallback, callback, step);
                } else {
                    r = ExtractorApiKt.newExtractorLink(job[1], job[2], job[3], "M3U8".equals(job[6]) ? ExtractorLinkType.M3U8 : ExtractorLinkType.VIDEO, new LinkInit(job[4], StringsKt.toIntOrNull(job[5]) != null ? StringsKt.toIntOrNull(job[5]).intValue() : 0, null), step);
                }
            } catch (Throwable th) {
                r = ResultKt.createFailure(th);
            }
            if (r == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return;
            }
            if ("L".equals(job[0]) && (r instanceof ExtractorLink)) {
                callback.invoke((ExtractorLink) r);
            }
            i++;
        }
        continuation.resumeWith(Boolean.TRUE);
    }

    /* Resolve todos os players conhecidos de forma bloqueante (Jsoup) e devolve os jobs:
     * "L" = link direto (source|name|url|referer|quality|type); "E" = embed p/ loadExtractor. */
    private final List<String[]> resolvePage(String pageUrl, Function1<? super SubtitleFile, Unit> subtitleCallback, Function1<? super ExtractorLink, Unit> callback) throws Exception {
        List<String[]> jobs = new ArrayList<>();
        Document doc = Jsoup.connect(pageUrl).userAgent(UA).timeout(20000).execute().parse();
        for (Element item : doc.select("li.dooplay_player_option")) {
            String nume = item.attr("data-nume");
            String label = firstText(item, ".title");
            if (StringsKt.isBlank(nume) || (label != null && StringsKt.contains(label.toLowerCase(), "trailer", false))) {
                continue;
            }
            Element container = doc.selectFirst("#source-player-" + nume);
            Element iframe = container != null ? container.selectFirst("iframe") : null;
            String src = iframe != null ? iframe.attr("src") : null;
            if (StringsKt.isBlank(src)) {
                src = iframe != null ? iframe.attr("data-src") : null;
            }
            if (StringsKt.isBlank(src)) {
                continue;
            }
            String iframeUrl = abs(src, pageUrl);
            try {
                resolveOption(iframeUrl, pageUrl, label, jobs);
            } catch (Throwable th) {
                /* opção descartada; demais seguem */
            }
        }
        return jobs;
    }

    private final void resolveOption(String iframeUrl, String pageUrl, String label, List<String[]> jobs) throws Exception {
        String host = hostOf(iframeUrl);
        String path = pathOf(iframeUrl);
        if (StringsKt.contains(host, "booonceivu", false)) {
            resolveJsonPlayer(iframeUrl, pageUrl, label, jobs);
            return;
        }
        if (INCVIDEO_HOST.matcher(host).find()) {
            resolveIncVideo(iframeUrl, pageUrl, label, jobs);
            return;
        }
        if (StringsKt.contains(path, "/aviso", false)) {
            resolveAviso(iframeUrl, pageUrl, label, jobs);
            return;
        }
        jobs.add(new String[]{"E", iframeUrl});
    }

    /* Player JSON Booonceivu: GET + mode=to-salvando-seu-ip -> {status:"success",midias:[{url,qualidade}]}.
     * legendas[] existe no contrato, mas não emitimos SubtitleFile direto (sem construtor comprovado);
     * legendas de hosts conhecidos seguem via loadExtractor. */
    private final void resolveJsonPlayer(String iframeUrl, String pageUrl, String label, List<String[]> jobs) throws Exception {
        String requestUrl = StringsKt.contains(iframeUrl, "mode=", false) ? iframeUrl : iframeUrl + (StringsKt.contains(iframeUrl, "?", false) ? "&" : "?") + "mode=to-salvando-seu-ip";
        String json = Jsoup.connect(requestUrl).userAgent(UA).referrer(pageUrl).header("Accept", "application/json").ignoreContentType(true).timeout(20000).execute().body();
        JsonObject root = new Gson().fromJson(json, JsonObject.class);
        if (root == null || !"success".equals(root.has("status") ? root.get("status").getAsString() : null) || !root.has("midias") || root.get("midias").isJsonNull()) {
            return;
        }
        JsonArray midias = root.getAsJsonArray("midias");
        for (JsonElement el : midias) {
            JsonObject midia = el.getAsJsonObject();
            if (!midia.has("url")) {
                continue;
            }
            String url = midia.get("url").getAsString();
            if (!StringsKt.contains(url, ".m3u8", false)) {
                continue;
            }
            String qualidade = midia.has("qualidade") && !midia.get("qualidade").isJsonNull() ? midia.get("qualidade").getAsString() : null;
            jobs.add(new String[]{"L", "Doramas Booonceivu", (label != null ? label + " " : "") + (qualidade != null ? qualidade : "HLS"), url, pageUrl, String.valueOf(mapQuality(qualidade)), "M3U8"});
        }
    }

    /* /aviso/?url=<enc> decodifica (até 2x) o destino; só IncVideo é resolvido (P2P é descartado). */
    private final void resolveAviso(String iframeUrl, String pageUrl, String label, List<String[]> jobs) throws Exception {
        String raw = null;
        int q = iframeUrl.indexOf("url=");
        if (q >= 0) {
            raw = iframeUrl.substring(q + 4);
            int amp = raw.indexOf('&');
            if (amp >= 0) {
                raw = raw.substring(0, amp);
            }
        }
        if (StringsKt.isBlank(raw)) {
            return;
        }
        String value = raw;
        for (int i = 0; i < 2; i++) {
            String decoded = URLDecoder.decode(value, java.nio.charset.StandardCharsets.UTF_8);
            if (decoded.equals(value)) {
                break;
            }
            value = decoded;
        }
        String target = value;
        if (!INCVIDEO_HOST.matcher(hostOf(target)).find()) {
            return; /* ex.: png.strp2p.com (P2P) — sem suporte */
        }
        resolveIncVideo(target, pageUrl, label, jobs);
    }

    /* IncVideo (csst.online|incvideo1.online): Playerjs file: "[q]https://.../get_file/...mp4/".
     * Cada /get_file/ redireciona (302) para remote_control.php que serve o MP4.
     * Validado ao vivo: Range bytes=0-0 -> 206 video/mp4. Emitimos a URL /get_file/ (estável). */
    private final void resolveIncVideo(String embedUrl, String pageUrl, String label, List<String[]> jobs) throws Exception {
        org.jsoup.Connection.Response resp = Jsoup.connect(embedUrl).userAgent(UA).referrer(pageUrl).followRedirects(true).ignoreContentType(true).timeout(20000).execute();
        if (!INCVIDEO_HOST.matcher(hostOf(resp.url().toString())).find()) {
            return;
        }
        String embedFinal = resp.url().toString();
        Matcher fileMatcher = PLAYERJS_FILE.matcher(resp.body());
        if (!fileMatcher.find()) {
            return;
        }
        Matcher srcMatcher = PLAYERJS_SOURCES.matcher(fileMatcher.group(1));
        while (srcMatcher.find()) {
            String url = srcMatcher.group(2);
            if (url == null || !StringsKt.contains(url, "/get_file/", false)) {
                continue;
            }
            String qualityLabel = srcMatcher.group(1) != null ? srcMatcher.group(1).trim() : null;
            org.jsoup.Connection.Response probe = Jsoup.connect(url).userAgent(UA).referrer(embedFinal).header("Range", "bytes=0-0").followRedirects(true).ignoreContentType(true).maxBodySize(1024).timeout(20000).execute();
            int code = probe.statusCode();
            if (code != 200 && code != 206) {
                continue;
            }
            jobs.add(new String[]{"L", "Doramas IncVideo", (label != null ? label + " " : "") + (qualityLabel != null ? qualityLabel : "MP4"), url, embedFinal, String.valueOf(mapQuality(qualityLabel)), "VIDEO"});
        }
    }

    /* Dooplay: div.se-c (temporada) -> ul.episodios li -> div.numerando "S - E" +
     * div.episodiotitle a[href] -> /br/episodio/<slug>/. Validado ao vivo. */
    private final List<Episode> parseEpisodes(Document doc) {
        List<Episode> episodes = new ArrayList<>();
        int blockIndex = 0;
        for (Element block : doc.select("div.se-c")) {
            blockIndex++;
            String seasonText = firstText(block, "span.se-t");
            Integer seasonNum = seasonText == null ? null : StringsKt.toIntOrNull(seasonText.trim());
            final int seasonNumber = seasonNum != null ? seasonNum.intValue() : blockIndex;
            for (Element item : block.select("ul.episodios li")) {
                Element num = item.selectFirst("div.numerando");
                String numerando = num != null ? num.text() : "";
                MatchResult m = SEASON_EPISODE.find(numerando, 0);
                Element a = item.selectFirst("div.episodiotitle a[href]");
                if (a == null) {
                    continue;
                }
                String href = abs(a.attr("href"), getMainUrl());
                if (StringsKt.isBlank(href)) {
                    continue;
                }
                final int itemSeason = (m == null || StringsKt.toIntOrNull((String) m.getGroupValues().get(1)) == null) ? seasonNumber : StringsKt.toIntOrNull((String) m.getGroupValues().get(1)).intValue();
                final int number = (m == null || StringsKt.toIntOrNull((String) m.getGroupValues().get(2)) == null) ? 1 : StringsKt.toIntOrNull((String) m.getGroupValues().get(2)).intValue();
                final String name = a.text();
                episodes.add(MainAPIKt.newEpisode(this, href, new Function1() { // from class: com.Doramas.Doramas$$ExternalSyntheticLambda0
                    public final Object invoke(Object obj) {
                        return Doramas.parseEpisodes$lambda$5(name, itemSeason, number, (Episode) obj);
                    }
                }));
            }
        }
        /* distinct por data (contrato antigo preservado) */
        HashSet set = new HashSet();
        ArrayList list = new ArrayList();
        for (Object e : episodes) {
            Episode it2 = (Episode) e;
            if (set.add(it2.getData())) {
                list.add(e);
            }
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit parseEpisodes$lambda$5(String $name, int $itemSeason, int $number, Episode $this$newEpisode) {
        $this$newEpisode.setName(StringsKt.trim($name).toString());
        $this$newEpisode.setSeason(Integer.valueOf($itemSeason));
        $this$newEpisode.setEpisode(Integer.valueOf($number));
        return Unit.INSTANCE;
    }

    private final List<Pair<Actor, ActorRole>> selectActors(Document doc) {
        List<Pair<Actor, ActorRole>> actors = new ArrayList<>();
        for (Object it : doc.select("#cast .person a, .persons .person a, ul.cast-lst a, .cast a")) {
            Element el = (Element) it;
            String name = el.text();
            if (!StringsKt.isBlank(name)) {
                actors.add(TuplesKt.to(new Actor(name, el.attr("href")), (ActorRole) null));
            }
        }
        if (!actors.isEmpty()) {
            return actors;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Integer parseDuration(String text) {
        if (text == null) {
            return null;
        }
        MatchResult it = new Regex("(\\d+)h\\s*(\\d+)m").find(text, 0);
        if (it == null) {
            MatchResult matchResultFind$default = new Regex("(\\d+)m").find(text, 0);
            if (matchResultFind$default == null) {
                return null;
            }
            return StringsKt.toIntOrNull((String) matchResultFind$default.getGroupValues().get(1));
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
        MatchResult m = FOUR_DIGIT_YEAR.find(text, 0);
        return m == null ? null : StringsKt.toIntOrNull(m.getValue());
    }

    private final String firstText(Element root, String... selectors) {
        if (root == null) {
            return null;
        }
        for (String selector : selectors) {
            Element el = root.selectFirst(selector);
            if (el != null && !StringsKt.isBlank(el.text())) {
                return el.text();
            }
        }
        return null;
    }

    private final String firstAttr(Element img, String replace) {
        if (img == null) {
            return null;
        }
        String src = img.attr("src");
        if (StringsKt.isBlank(src)) {
            src = img.attr("data-src");
        }
        if (StringsKt.isBlank(src)) {
            return null;
        }
        String str = StringsKt.startsWith(src, "//", false) ? "https:" + src : src;
        return str;
    }

    private final String abs(String href, String base) {
        if (StringsKt.isBlank(href)) {
            return href;
        }
        if (StringsKt.startsWith(href, "//", false)) {
            return "https:" + href;
        }
        if (StringsKt.startsWith(href, "http", false)) {
            return href;
        }
        if (StringsKt.startsWith(href, "/", false)) {
            return getMainUrl() + href;
        }
        return base + href;
    }

    private final String hostOf(String url) {
        try {
            return new java.net.URI(url).getHost() != null ? new java.net.URI(url).getHost().toLowerCase() : "";
        } catch (Throwable th) {
            return "";
        }
    }

    private final String pathOf(String url) {
        try {
            String p = new java.net.URI(url).getPath();
            return p != null ? p : "";
        } catch (Throwable th) {
            return "";
        }
    }

    /* Mapa de qualidade do player Booonceivu/IncVideo: SD=1080, LD=720, FD=360 (contrato nuvio). */
    private final int mapQuality(String label) {
        if (label == null) {
            return 0;
        }
        String key = StringsKt.trim(label).toString().toUpperCase();
        if (StringsKt.contains(key, "SD", false) && !StringsKt.contains(key, "360", false)) {
            return 1080;
        }
        if (StringsKt.contains(key, "LD", false)) {
            return 720;
        }
        if (StringsKt.contains(key, "FD", false)) {
            return 360;
        }
        MatchResult m = new Regex("(\\d{3,4})").find(key, 0);
        Integer value = m == null ? null : StringsKt.toIntOrNull(m.getValue());
        return value != null ? value.intValue() : 0;
    }
}
