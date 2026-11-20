package com.PobreFlix;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lagradost.cloudstream3.Actor;
import com.lagradost.cloudstream3.ActorRole;
import com.lagradost.cloudstream3.AnimeSearchResponse;
import com.lagradost.cloudstream3.DubStatus;
import com.lagradost.cloudstream3.Episode;
import com.lagradost.cloudstream3.HomePageResponse;
import com.lagradost.cloudstream3.LoadResponse;
import com.lagradost.cloudstream3.MainAPI;
import com.lagradost.cloudstream3.MainAPIKt;
import com.lagradost.cloudstream3.MainPageData;
import com.lagradost.cloudstream3.MainPageRequest;
import com.lagradost.cloudstream3.MovieLoadResponse;
import com.lagradost.cloudstream3.Score;
import com.lagradost.cloudstream3.SearchResponse;
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.TvSeriesLoadResponse;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: PobreFlix.kt */
/* JADX INFO: loaded from: /tmp/cs3/PobreFlix/classes.dex */
/*
 * PROVIDER REPARADO CONTRA O NOVO CONTRATO (IPS/Invision "videobox") em 2026-11-20.
 * Contrato antigo (C_Video/getplay.php/div.caption/...): 100% morto — removido.
 * Classe estruturada com as mesmas assinaturas MainAPI (getMainPage/search/load/
 * loadLinks/loadEpisodes/extractPoster/toSearchResult) e os mesmos estados
 * (Movie/TvSeries, DubStatus, "movie|"/"ep|"). Relatorio completo:
 * /home/massna/recon/report_pobreflix.md
 */
public final class PobreFlix extends MainAPI {

    @NotNull
    private static final String BASE_URL = "https://www.pobreflixtv.codes";
    private static final int CURRENT_YEAR = Calendar.getInstance().get(1);

    @NotNull
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36";

    @NotNull
    private String name = "PobreFlix";

    @NotNull
    private String lang = "pt-br";
    private final boolean hasQuickSearch = true;
    private final boolean hasDownloadSupport = true;
    private final boolean hasMainPage = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Movie, TvType.TvSeries});

    @NotNull
    private String mainUrl = BASE_URL;

    /* Contrato novo: listagens /filmes/ e /series/ (paginação via /page/N/). */
    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to(getMainUrl() + "/filmes/", "Filmes"), TuplesKt.to(getMainUrl() + "/series/", "Séries")});

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlix$extractPoster$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlix.this.extractPoster(null, null, false, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlix$getMainPage$1, reason: invalid class name and case insensitive filesystem */
    final class C00001 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C00001(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlix.this.getMainPage(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlix$load$1, reason: invalid class name */
    final class C00011 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$10;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;
        /* synthetic */ Object result;

        C00011(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlix.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlix$loadEpisodes$1, reason: invalid class name */
    final class C00021 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C00021(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlix.this.loadEpisodes(null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlix$search$1, reason: invalid class name */
    final class C00031 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C00031(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlix.this.search(null, (Continuation) this);
        }
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

    public boolean getHasQuickSearch() {
        return this.hasQuickSearch;
    }

    public boolean getHasDownloadSupport() {
        return this.hasDownloadSupport;
    }

    public boolean getHasMainPage() {
        return this.hasMainPage;
    }

    @NotNull
    public Set<TvType> getSupportedTypes() {
        return this.supportedTypes;
    }

    @NotNull
    public String getMainUrl() {
        return this.mainUrl;
    }

    public void setMainUrl(@NotNull String str) {
        this.mainUrl = str;
    }

    private final String normalizeUrl(String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (java.net.URISyntaxException e) {
            throw new RuntimeException(e);
        }
        StringBuilder $this$normalizeUrl_u24lambda_u241 = new StringBuilder();
        $this$normalizeUrl_u24lambda_u241.append(getMainUrl());
        String path = uri.getPath();
        if (path == null) {
            path = "";
        }
        $this$normalizeUrl_u24lambda_u241.append(path);
        String it = uri.getQuery();
        if (it != null) {
            $this$normalizeUrl_u24lambda_u241.append("?").append(it);
        }
        return $this$normalizeUrl_u24lambda_u241.toString();
    }

    @NotNull
    public List<MainPageData> getMainPage() {
        return this.mainPage;
    }

    /* ===================== helpers HTTP/JSON do novo contrato ===================== */

    private final Connection newConnection(String url) {
        return Jsoup.connect(url).userAgent(UA).header("X-Requested-With", "XMLHttpRequest").timeout(20000);
    }

    private final Document fetchDocument(String url) throws Exception {
        return newConnection(url).execute().parse();
    }

    private final String fetchBody(String url) throws Exception {
        return newConnection(url).ignoreContentType(true).execute().body();
    }

    /* JSON-LD (@graph com @type Movie/TVSeries) — fonte de ano/gêneros/duração/nota. */
    private final JsonObject findJsonLd(Document document) {
        for (Element el : document.select("script[type=application/ld+json]")) {
            try {
                JsonElement root = new Gson().fromJson(el.data(), JsonElement.class);
                if (root == null) {
                    continue;
                }
                List<JsonObject> candidates = new ArrayList<>();
                if (root.isJsonObject()) {
                    JsonObject obj = root.getAsJsonObject();
                    candidates.add(obj);
                    if (obj.has("@graph") && obj.get("@graph").isJsonArray()) {
                        for (JsonElement g : obj.getAsJsonArray("@graph")) {
                            if (g.isJsonObject()) {
                                candidates.add(g.getAsJsonObject());
                            }
                        }
                    }
                }
                for (JsonObject c : candidates) {
                    if (!c.has("@type")) {
                        continue;
                    }
                    String type = c.get("@type").getAsString();
                    if ("Movie".equals(type) || "TVSeries".equals(type) || "TVEpisode".equals(type)) {
                        return c;
                    }
                }
            } catch (Throwable th) {
                /* JSON-LD malformado: ignora */
            }
        }
        return null;
    }

    private final Integer parseDurationMinutes(JsonObject node) {
        if (node == null || !node.has("duration")) {
            return null;
        }
        Matcher m = Pattern.compile("PT(?:(\\d+)H)?(?:(\\d+)M)?").matcher(node.get("duration").getAsString());
        if (!m.find()) {
            return null;
        }
        int h = m.group(1) != null ? Integer.parseInt(m.group(1)) : 0;
        int min = m.group(2) != null ? Integer.parseInt(m.group(2)) : 0;
        int total = (h * 60) + min;
        return total > 0 ? Integer.valueOf(total) : null;
    }

    private final Double parseRating(JsonObject node) {
        if (node == null || !node.has("aggregateRating")) {
            return null;
        }
        try {
            JsonObject r = node.getAsJsonObject("aggregateRating");
            return r.has("ratingValue") ? Double.valueOf(r.get("ratingValue").getAsDouble()) : null;
        } catch (Throwable th) {
            return null;
        }
    }

    private final Integer parseYear(JsonObject node) {
        if (node == null || !node.has("datePublished")) {
            return null;
        }
        Matcher m = Pattern.compile("(\\d{4})").matcher(node.get("datePublished").getAsString());
        return m.find() ? StringsKt.toIntOrNull(m.group(1)) : null;
    }

    private final List<String> parseGenres(JsonObject node) {
        List<String> out = new ArrayList<>();
        if (node != null && node.has("genre") && node.get("genre").isJsonArray()) {
            JsonArray arr = node.getAsJsonArray("genre");
            for (JsonElement g : arr) {
                String s = g.getAsString().trim();
                if (!s.isEmpty()) {
                    out.add(s);
                }
            }
        }
        return out;
    }

    /* "Diretor e Elenco • diretor • ator1, ator2, ..." (p.extra) — semântica antiga "Elenco:". */
    private final List<Pair<Actor, String>> parseActors(Document document) {
        List<Pair<Actor, String>> out = new ArrayList<>();
        for (Element p : document.select("p.extra")) {
            if (!StringsKt.contains(p.text(), "Elenco", false)) {
                continue;
            }
            String[] parts = p.text().split("•");
            if (parts.length < 3) {
                continue;
            }
            for (String a : parts[2].split(",")) {
                String n = a.trim();
                if (!n.isEmpty()) {
                    out.add(new Pair<Actor, String>(new Actor(n, (String) null), (String) null));
                }
            }
            break;
        }
        return out;
    }

    /* Poster novo: div.vbItemImage img[src] (TMDB w185 -> original; semântica antiga preservada). */
    private final String posterFrom(Document document) {
        Element img = document.selectFirst("div.vbItemImage img");
        if (img == null) {
            img = document.selectFirst("img[src*=image.tmdb.org]");
        }
        String src = img != null ? img.attr("src") : null;
        if (src == null || src.isEmpty()) {
            return null;
        }
        return MainAPIKt.fixUrl(this, StringsKt.replace(src, "w185", "original", false));
    }

    /* "movie|<videoId>" / "ep|<videoId>" (data do LoadResponse/Episode; ver report). */
    private final String videoIdOf(String url, Document document) {
        Matcher m = Pattern.compile("-(\\d+)/?$").matcher(url != null ? url : "");
        if (m.find()) {
            return m.group(1);
        }
        if (document != null) {
            Element view = document.selectFirst("#view[data-video-id]");
            if (view != null && !view.attr("data-video-id").isEmpty()) {
                return view.attr("data-video-id");
            }
        }
        return url;
    }

    /* ===================== MainAPI overrides (novo contrato) ===================== */

    @Nullable
    public Object getMainPage(int page, @NotNull MainPageRequest request, @NotNull Continuation<? super HomePageResponse> continuation) {
        try {
            String base = request.getData();
            /* Paginação nova: /filmes/page/N/ (o antigo "?page=" é IGNORADO pelo servidor). */
            String url = page <= 1 ? base : (base.endsWith("/") ? base : base + "/") + "page/" + page + "/";
            Document document = fetchDocument(url);
            List<SearchResponse> items = new ArrayList<>();
            for (Element it : document.select("div.swiper-slide.vbTabSliderItem")) {
                SearchResponse searchResult = toSearchResult(it);
                if (searchResult != null) {
                    items.add(searchResult);
                }
            }
            return MainAPIKt.newHomePageResponse(request.getName(), items, Boxing.boxBoolean(!items.isEmpty()));
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        try {
            /* Busca AJAX nova: GET do=buscarContent&q=&type=todos&genre=&year=&page= -> JSON {"html":...}. */
            String q = URLEncoder.encode(query, "UTF-8");
            String url = getMainUrl() + "/index.php?app=videobox&module=video&controller=index&do=buscarContent&q=" + q + "&type=todos&genre=&year=&page=1";
            String body = fetchBody(url);
            String html = null;
            try {
                JsonObject root = new Gson().fromJson(body, JsonObject.class);
                if (root != null && root.has("html")) {
                    html = root.get("html").getAsString();
                }
            } catch (Throwable th) {
                /* fallback: regex simples */
                Matcher m = Pattern.compile("\"html\"\\s*:\\s*\"(.*)\"\\s*}", Pattern.DOTALL).matcher(body);
                if (m.find()) {
                    html = m.group(1).replace("\\/", "/").replace("\\\"", "\"").replace("\\n", "\n");
                }
            }
            Document document = Jsoup.parse(html != null ? html : "");
            List<SearchResponse> out = new ArrayList<>();
            for (Element a : document.select("a.block[href]")) {
                SearchResponse searchResult = toSearchResult(a);
                if (searchResult != null) {
                    out.add(searchResult);
                }
            }
            return out;
        } catch (Throwable th2) {
            continuation.resumeWith(ResultKt.createFailure(th2));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    /* Card novo: a.block[href] (ou wrapper .vbTabSliderItem) com img, .top badges, .info h3/.info p(ano). */
    private final SearchResponse toSearchResult(Element $this$toSearchResult) {
        Element a = "a".equals($this$toSearchResult.tagName()) ? $this$toSearchResult : $this$toSearchResult.selectFirst("a.block[href], a[href]");
        if (a == null) {
            return null;
        }
        String link = MainAPIKt.fixUrlNull(this, a.attr("href"));
        if (link == null) {
            return null;
        }
        Element h3 = a.selectFirst(".info h3");
        String title = h3 != null ? h3.ownText().trim() : null;
        if (title == null || title.isEmpty()) {
            title = a.attr("title").replace("Assistir ", "").trim();
        }
        if (title == null || title.isEmpty()) {
            title = "Sem título";
        }
        Element img = a.selectFirst("img");
        final String poster = img != null ? img.attr("src") : null;
        StringBuilder badges = new StringBuilder();
        for (Element d : a.select(".top div")) {
            badges.append(d.text()).append(' ');
        }
        final String qualityStr = badges.toString();
        final String audio = badges.toString();
        Element yearEl = a.selectFirst(".info p");
        final Integer year = yearEl != null ? StringsKt.toIntOrNull(yearEl.text().trim()) : null;
        final TvType tvType = StringsKt.contains(link, "/series/", false) ? TvType.TvSeries : TvType.Movie;
        return MainAPIKt.newAnimeSearchResponse(this, title, link, tvType, false, new Function1() {
                public final Object invoke(Object obj) {
                AnimeSearchResponse resp = (AnimeSearchResponse) obj;
                Unit u = PobreFlix.toSearchResult$lambda$5(poster, qualityStr, audio, resp);
                resp.setYear(year);
                return u;
                }
            });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResult$lambda$5(String $poster, String $qualityStr, String $audio, AnimeSearchResponse $this$newAnimeSearchResponse) {
        EnumSet enumSetOf;
        $this$newAnimeSearchResponse.setPosterUrl($poster != null ? StringsKt.replace($poster, "w185", "original", false) : null);
        $this$newAnimeSearchResponse.setQuality(MainAPIKt.getQualityFromString($qualityStr));
        if (StringsKt.contains($audio, "DUB", true)) {
            enumSetOf = EnumSet.of(DubStatus.Dubbed);
        } else {
            enumSetOf = EnumSet.of(DubStatus.Subbed);
        }
        $this$newAnimeSearchResponse.setDubStatus(enumSetOf);
        return Unit.INSTANCE;
    }

    @Nullable
    public Object load(@NotNull String str, @NotNull Continuation<? super LoadResponse> continuation) {
        try {
            Document document = fetchDocument(str);
            JsonObject node = findJsonLd(document);
            boolean isSeries = !document.select("section.vbEpisodes, ol.vbEpisodeList").isEmpty()
                    || StringsKt.contains(str, "/series/", false);
            /* Título novo: h2.one (limpo); fallback h1.type "Assistir X Online". */
            Element titleEl = document.selectFirst("h2.one");
            String title;
            if (titleEl != null && !titleEl.ownText().trim().isEmpty()) {
                title = titleEl.ownText().trim();
            } else {
                Element h1 = document.selectFirst("h1.type");
                String t = h1 != null ? h1.ownText().trim() : null;
                if (t == null) {
                    t = "Sem título";
                }
                title = t.replace("Assistir ", "").replace(" Online", "").trim();
            }
            /* Sinopse nova (sobrevivente): div.sinopse p.sinopse-text. */
            Element plotEl = document.selectFirst("div.sinopse p.sinopse-text, p.sinopse-text");
            String plot = plotEl != null ? StringsKt.trim(plotEl.text().replace("...", "")).toString() : null;
            Integer duration = parseDurationMinutes(node);
            Double score = parseRating(node);
            Integer year = parseYear(node);
            List<String> tags = parseGenres(node);
            List<Pair<Actor, String>> actors = parseActors(document);
            String poster = posterFrom(document);
            String videoId = videoIdOf(str, document);
            if (!isSeries) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(poster, plot, duration, tags, year, score, actors, null);
                return MainAPIKt.newMovieLoadResponse(this, title, str, TvType.Movie, "movie|" + videoId, anonymousClass3, continuation);
            }
            List<Episode> episodes = parseEpisodes(document);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(poster, plot, duration, tags, year, score, actors, null);
            return MainAPIKt.newTvSeriesLoadResponse(this, title, str, TvType.TvSeries, episodes, anonymousClass2, continuation);
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlix$load$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<TvSeriesLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<Pair<Actor, String>> $actors;
        final /* synthetic */ Integer $duration;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ Double $score;
        final /* synthetic */ List<String> $tags;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass2(String str, String str2, Integer num, List<String> list, Integer num2, Double d, List<Pair<Actor, String>> list2, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$plot = str2;
            this.$duration = num;
            this.$tags = list;
            this.$year = num2;
            this.$score = d;
            this.$actors = (java.util.List) list2;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$poster, this.$plot, this.$duration, this.$tags, this.$year, this.$score, this.$actors, continuation);
            anonymousClass2.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass2;
        }

        public final Object invoke(TvSeriesLoadResponse tvSeriesLoadResponse, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) (Object) create(tvSeriesLoadResponse, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    LoadResponse loadResponse = (TvSeriesLoadResponse) this.L$0;
                    loadResponse.setPosterUrl(this.$poster);
                    loadResponse.setPlot(this.$plot);
                    loadResponse.setDuration(this.$duration);
                    loadResponse.setTags(this.$tags);
                    loadResponse.setYear(this.$year);
                    loadResponse.setScore(Score.Companion.from10(this.$score));
                    LoadResponse.Companion.addActors(loadResponse, this.$actors);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlix$load$3, reason: invalid class name */
    static final class AnonymousClass3 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<Pair<Actor, String>> $actors;
        final /* synthetic */ Integer $duration;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ Double $score;
        final /* synthetic */ List<String> $tags;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass3(String str, String str2, Integer num, List<String> list, Integer num2, Double d, List<Pair<Actor, String>> list2, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$plot = str2;
            this.$duration = num;
            this.$tags = list;
            this.$year = num2;
            this.$score = d;
            this.$actors = (java.util.List) list2;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$poster, this.$plot, this.$duration, this.$tags, this.$year, this.$score, this.$actors, continuation);
            anonymousClass3.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass3;
        }

        public final Object invoke(MovieLoadResponse movieLoadResponse, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) (Object) create(movieLoadResponse, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    LoadResponse loadResponse = (MovieLoadResponse) this.L$0;
                    loadResponse.setPosterUrl(this.$poster);
                    loadResponse.setPlot(this.$plot);
                    loadResponse.setDuration(this.$duration);
                    loadResponse.setTags(this.$tags);
                    loadResponse.setYear(this.$year);
                    loadResponse.setScore(Score.Companion.from10(this.$score));
                    LoadResponse.Companion.addActors(loadResponse, this.$actors);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* Assinatura preservada; o contrato antigo (div#video_embed) morreu — poster vive no detalhe. */
    @Nullable
    public final Object extractPoster(Document document, String url, boolean isSeries, Continuation<? super String> continuation) {
        return document != null ? posterFrom(document) : null;
    }

    /* Episódios novos: li.vbEpisodeCard (a.vbEpisodeCard__link[href], data-title, data-number,
     * .vbEpisodeCard__duration, .vbEpisodeCard__synopsis). Temporada/episódio redundantes na URL
     * do episódio: /episodios/online/<slug>-<S>x<E>-<audio>-<id>/. data = "ep|<id>" (id == data-video-id). */
    private final List<Episode> parseEpisodes(Document document) {
        List<Episode> out = new ArrayList<>();
        for (Element li : document.select("li.vbEpisodeCard")) {
            Element a = li.selectFirst("a[href]");
            if (a == null) {
                continue;
            }
            String epUrl = a.attr("href");
            final String epId = videoIdOf(epUrl, null);
            Integer seasonNum = null;
            Integer epNum = null;
            Matcher sx = Pattern.compile("-(\\d+)x(\\d+)-").matcher(epUrl);
            if (sx.find()) {
                seasonNum = StringsKt.toIntOrNull(sx.group(1));
                epNum = StringsKt.toIntOrNull(sx.group(2));
            }
            if (epNum == null) {
                epNum = StringsKt.toIntOrNull(li.attr("data-number"));
            }
            final Integer fSeason = seasonNum;
            final Integer fEp = epNum;
            String nameAttr = li.attr("data-title");
            Element nameEl = li.selectFirst(".vbEpisodeCard__title");
            String name = nameAttr != null && !nameAttr.isEmpty() ? nameAttr : (nameEl != null ? nameEl.ownText().trim() : null);
            final String fName = name;
            Element descEl = li.selectFirst(".vbEpisodeCard__synopsis");
            final String fDesc = descEl != null ? descEl.text().trim() : null;
            Element img = li.selectFirst("img");
            final String fPoster = img != null ? StringsKt.replace(img.attr("src"), "w185", "original", false) : null;
            out.add(MainAPIKt.newEpisode(this, "ep|" + epId, new Function1() {
                public final Object invoke(Object obj) {
                    return PobreFlix.applyEpisode(fName, fSeason, fEp, fDesc, fPoster, (Episode) obj);
                }
            }));
        }
        return out;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit applyEpisode(String $name, Integer $season, Integer $ep, String $description, String $poster, Episode $this$newEpisode) {
        $this$newEpisode.setName($name);
        $this$newEpisode.setSeason($season);
        $this$newEpisode.setEpisode($ep);
        $this$newEpisode.setDescription($description);
        $this$newEpisode.setPosterUrl($poster);
        return Unit.INSTANCE;
    }

    /* Assinatura preservada; implementação nova delega para parseEpisodes (síncrono). */
    @Nullable
    public final Object loadEpisodes(Document document, String url, Continuation<? super List<Episode>> continuation) {
        try {
            return parseEpisodes(document);
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadEpisodes$lambda$18$lambda$17$lambda$16(int $season, String $epId, Episode $this$newEpisode) {
        $this$newEpisode.setSeason(Integer.valueOf($season));
        $this$newEpisode.setEpisode(StringsKt.toIntOrNull($epId));
        return Unit.INSTANCE;
    }

    @Nullable
    public Object loadLinks(@NotNull String data, boolean isCasting, @NotNull Function1<? super SubtitleFile, Unit> function1, @NotNull Function1<? super ExtractorLink, Unit> function2, @NotNull Continuation<? super Boolean> continuation) {
        return PobreFlixExtractor.INSTANCE.getLinks(data, function1, function2, continuation);
    }
}
