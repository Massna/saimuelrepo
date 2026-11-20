package com.NetCine;

import com.lagradost.cloudstream3.Episode;
import com.lagradost.cloudstream3.HomePageList;
import com.lagradost.cloudstream3.HomePageResponse;
import com.lagradost.cloudstream3.LoadResponse;
import com.lagradost.cloudstream3.MainAPI;
import com.lagradost.cloudstream3.MainAPIKt;
import com.lagradost.cloudstream3.MainActivityKt;
import com.lagradost.cloudstream3.MainPageData;
import com.lagradost.cloudstream3.MainPageRequest;
import com.lagradost.cloudstream3.MovieLoadResponse;
import com.lagradost.cloudstream3.MovieSearchResponse;
import com.lagradost.cloudstream3.Score;
import com.lagradost.cloudstream3.SearchResponse;
import com.lagradost.cloudstream3.TvSeriesLoadResponse;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.cloudstream3.utils.ExtractorLinkType;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.comparisons.ComparisonsKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: NetCine.kt */
/* JADX INFO: loaded from: /tmp/cs3/NetCine/classes.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 C2\u00020\u0001:\u0001CB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\"\u001a\u0004\u0018\u00010#*\u00020$H\u0002J\u001e\u0010 \u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0096@¢\u0006\u0002\u0010*J\u001c\u0010+\u001a\b\u0012\u0004\u0012\u00020#0\u001e2\u0006\u0010,\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010-J\u0016\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010-JE\u00101\u001a\u000202*\u00020/2\u0006\u00103\u001a\u00020\u00052\b\u00104\u001a\u0004\u0018\u00010\u00052\b\u00105\u001a\u0004\u0018\u00010'2\b\u00106\u001a\u0004\u0018\u0001072\f\u00108\u001a\b\u0012\u0004\u0012\u00020#0\u001eH\u0002¢\u0006\u0002\u00109JF\u0010:\u001a\u00020\u000e2\u0006\u0010;\u001a\u00020\u00052\u0006\u0010<\u001a\u00020\u000e2\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u0002020>2\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u0002020>H\u0096@¢\u0006\u0002\u0010BR\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0014\u0010\r\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0007\"\u0004\b\u0013\u0010\tR\u0014\u0010\u0014\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0014\u0010\u0016\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006D"}, d2 = {"Lcom/NetCine/NetCine;", "Lcom/lagradost/cloudstream3/MainAPI;", "<init>", "()V", "mainUrl", "", "getMainUrl", "()Ljava/lang/String;", "setMainUrl", "(Ljava/lang/String;)V", "name", "getName", "setName", "hasMainPage", "", "getHasMainPage", "()Z", "lang", "getLang", "setLang", "hasDownloadSupport", "getHasDownloadSupport", "hasQuickSearch", "getHasQuickSearch", "supportedTypes", "", "Lcom/lagradost/cloudstream3/TvType;", "getSupportedTypes", "()Ljava/util/Set;", "mainPage", "", "Lcom/lagradost/cloudstream3/MainPageData;", "getMainPage", "()Ljava/util/List;", "toSearchResult", "Lcom/lagradost/cloudstream3/SearchResponse;", "Lorg/jsoup/nodes/Element;", "Lcom/lagradost/cloudstream3/HomePageResponse;", "page", "", "request", "Lcom/lagradost/cloudstream3/MainPageRequest;", "(ILcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "Lcom/lagradost/cloudstream3/LoadResponse;", "url", "fillMeta", "", "p", "pl", "y", "s", "", "rec", "(Lcom/lagradost/cloudstream3/LoadResponse;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Double;Ljava/util/List;)V", "loadLinks", "data", "isCasting", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "NetCine_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNetCine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NetCine.kt\ncom/NetCine/NetCine\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 5 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,174:1\n1#2:175\n1#2:186\n1#2:199\n1#2:212\n1#2:225\n1611#3,9:176\n1863#3:185\n1864#3:187\n1620#3:188\n1611#3,9:189\n1863#3:198\n1864#3:200\n1620#3:201\n1611#3,9:202\n1863#3:211\n1864#3:213\n1620#3:214\n1611#3,9:215\n1863#3:224\n1864#3:226\n1620#3:227\n1062#3:233\n1863#3,2:234\n662#4:228\n743#4,4:229\n434#5:236\n507#5,5:237\n434#5:242\n507#5,5:243\n*S KotlinDebug\n*F\n+ 1 NetCine.kt\ncom/NetCine/NetCine\n*L\n58#1:186\n65#1:199\n78#1:212\n85#1:225\n58#1:176,9\n58#1:185\n58#1:187\n58#1:188\n65#1:189,9\n65#1:198\n65#1:200\n65#1:201\n78#1:202,9\n78#1:211\n78#1:213\n78#1:214\n85#1:215,9\n85#1:224\n85#1:226\n85#1:227\n130#1:233\n132#1:234,2\n126#1:228\n126#1:229,4\n90#1:236\n90#1:237,5\n91#1:242\n91#1:243,5\n*E\n"})
public final class NetCine extends MainAPI {

    @NotNull
    public static final String NETCINE_URL = "https://netcinexx.lat";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Regex iframeRegex = new Regex("<div\\s+id=\"(play-\\d+)\"[^>]*>.*?<iframe\\s+src=\"([^\"]+)", RegexOption.DOT_MATCHES_ALL);

    @NotNull
    private static final Regex labelRegex = new Regex("<a\\s+href=\"#(play-\\d+)\">([^<]+)</a>");

    @NotNull
    private static final Regex videoSourceRegex = new Regex("<source\\s+[^>]*src=[\"']([^\"']+)[\"']");

    @NotNull
    private static final Regex nextRegex = new Regex("href\\s*=\\s*[\"']([^\"']*(?:hls\\.php|hlsarchive\\.php\\?hls|gc\\d+\\.php|playerarchive\\.php)[^\"']*)[\"']");

    @NotNull
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

    @NotNull
    private static final Map<String, String> defaultHeaders = MapsKt.mapOf(new Pair[]{TuplesKt.to("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8"), TuplesKt.to("cookie", "XCRF=XCRF; PHPSESSID=v8fk5egon2jcqo69hs7d9cail1"), TuplesKt.to("user-agent", USER_AGENT)});

    @NotNull
    private String mainUrl = NETCINE_URL;

    @NotNull
    private String name = "NetCine";
    private final boolean hasMainPage = true;

    @NotNull
    private String lang = "pt-br";
    private final boolean hasDownloadSupport = true;
    private final boolean hasQuickSearch = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Movie, TvType.Anime, TvType.TvSeries});

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to("category/ultimos-filmes", "Últimas Atualizações Filmes"), TuplesKt.to("category/acao", "Ação"), TuplesKt.to("category/animacao", "Animação"), TuplesKt.to("category/aventura", "Aventura"), TuplesKt.to("category/comedia", "Comédia"), TuplesKt.to("category/crime", "Crime"), TuplesKt.to("tvshows", "Últimas Atualizações Séries"), TuplesKt.to("tvshows/category/acao", "Séries de Ação"), TuplesKt.to("tvshows/category/animacao", "Séries de Animação")});

    /* JADX INFO: renamed from: com.NetCine.NetCine$getMainPage$1, reason: invalid class name */
    /* JADX INFO: compiled from: NetCine.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.NetCine.NetCine", f = "NetCine.kt", i = {0, 0}, l = {57}, m = "getMainPage", n = {"this", "request"}, s = {"L$0", "L$1"})
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
            return NetCine.this.getMainPage(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.NetCine.NetCine$load$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NetCine.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.NetCine.NetCine", f = "NetCine.kt", i = {0, 0}, l = {69, 94, 99}, m = "load", n = {"this", "url"}, s = {"L$0", "L$1"})
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
            return NetCine.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.NetCine.NetCine$loadLinks$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NetCine.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.NetCine.NetCine", f = "NetCine.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3}, l = {122, 137, 144, 157}, m = "loadLinks", n = {"this", "data", "callback", "sessionHeaders", "this", "data", "callback", "sessionHeaders", "labels", "iframeUrl", "label", "this", "data", "callback", "sessionHeaders", "labels", "iframeUrl", "label", "videoUrl", "ref", "this", "data", "callback", "sessionHeaders", "labels"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$4", "L$6", "L$7", "L$0", "L$1", "L$2", "L$3", "L$4", "L$6", "L$7", "L$8", "L$9", "L$0", "L$1", "L$2", "L$3", "L$4"})
    final class C00011 extends ContinuationImpl {
        Object L$0;
        Object L$1;
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
            return NetCine.this.loadLinks(null, false, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.NetCine.NetCine$search$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NetCine.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.NetCine.NetCine", f = "NetCine.kt", i = {0}, l = {63}, m = "search", n = {"this"}, s = {"L$0"})
    final class C00021 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C00021(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return NetCine.this.search(null, (Continuation) this);
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

    public boolean getHasMainPage() {
        return this.hasMainPage;
    }

    @NotNull
    public String getLang() {
        return this.lang;
    }

    public void setLang(@NotNull String str) {
        this.lang = str;
    }

    public boolean getHasDownloadSupport() {
        return this.hasDownloadSupport;
    }

    public boolean getHasQuickSearch() {
        return this.hasQuickSearch;
    }

    @NotNull
    public Set<TvType> getSupportedTypes() {
        return this.supportedTypes;
    }

    /* JADX INFO: compiled from: NetCine.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010$\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u000f\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\nR\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lcom/NetCine/NetCine$Companion;", "", "<init>", "()V", "NETCINE_URL", "", "USER_AGENT", "iframeRegex", "Lkotlin/text/Regex;", "getIframeRegex", "()Lkotlin/text/Regex;", "labelRegex", "getLabelRegex", "videoSourceRegex", "getVideoSourceRegex", "nextRegex", "getNextRegex", "defaultHeaders", "", "getDefaultHeaders", "()Ljava/util/Map;", "NetCine_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final Regex getIframeRegex() {
            return NetCine.iframeRegex;
        }

        @NotNull
        public final Regex getLabelRegex() {
            return NetCine.labelRegex;
        }

        @NotNull
        public final Regex getVideoSourceRegex() {
            return NetCine.videoSourceRegex;
        }

        @NotNull
        public final Regex getNextRegex() {
            return NetCine.nextRegex;
        }

        @NotNull
        public final Map<String, String> getDefaultHeaders() {
            return NetCine.defaultHeaders;
        }
    }

    @NotNull
    public List<MainPageData> getMainPage() {
        return this.mainPage;
    }

    private final SearchResponse toSearchResult(Element $this$toSearchResult) {
        String strText = null;
        String title;
        Element elementSelectFirst;
        String href;
        Element elementSelectFirst2 = $this$toSearchResult.selectFirst("h2");
        String poster = null;
        if (elementSelectFirst2 == null || (strText = elementSelectFirst2.text()) == null || (title = StringsKt.trim(strText).toString()) == null || (elementSelectFirst = $this$toSearchResult.selectFirst("a")) == null || (href = elementSelectFirst.attr("href")) == null) {
            return null;
        }
        Element it = $this$toSearchResult.selectFirst("img");
        if (it != null) {
            String strAttr = it.attr("data-src");
            if (strAttr.length() == 0) {
                strAttr = it.attr("src");
            }
            poster = strAttr;
        }
        final String posterF = poster;
        return MainAPIKt.newMovieSearchResponse(this, title, MainAPIKt.fixUrl(this, href), TvType.Movie, false, new Function1() { // from class: com.NetCine.NetCine$$ExternalSyntheticLambda2
            public final Object invoke(Object obj) {
                return NetCine.toSearchResult$lambda$2(posterF, (MovieSearchResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResult$lambda$2(String $poster, MovieSearchResponse $this$newMovieSearchResponse) {
        $this$newMovieSearchResponse.setPosterUrl($poster);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object getMainPage(int i, @NotNull MainPageRequest request, @NotNull Continuation<? super HomePageResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        boolean z = false;
        MainPageRequest request2;
        NetCine netCine;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object $result = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (anonymousClass1.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Requests app = MainActivityKt.getApp();
                String str = getMainUrl() + '/' + request.getData();
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = request;
                anonymousClass1.label = 1;
                z = true;
                $result = app.get(str, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), anonymousClass1);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                request2 = request;
                netCine = this;
                break;
            case 1:
                request2 = (MainPageRequest) anonymousClass1.L$1;
                netCine = (NetCine) anonymousClass1.L$0;
                ResultKt.throwOnFailure($result);
                z = true;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document document = ((NiceResponse) $result).getDocument();
        Iterable $this$forEach$iv$iv$iv = document.select("#box_movies > div.movie");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element it = (Element) element$iv$iv;
            SearchResponse searchResult = netCine.toSearchResult(it);
            if (searchResult != null) {
                destination$iv$iv.add(searchResult);
            }
        }
        List items = (List) destination$iv$iv;
        return MainAPIKt.newHomePageResponse(new HomePageList(request2.getName(), items, false), Boxing.boxBoolean(z));
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        C00021 c00021;
        NetCine netCine;
        if (continuation instanceof C00021) {
            c00021 = (C00021) continuation;
            if ((c00021.label & Integer.MIN_VALUE) != 0) {
                c00021.label -= Integer.MIN_VALUE;
            } else {
                c00021 = new C00021(continuation);
            }
        } else {
            c00021 = new C00021(continuation);
        }
        Object $result = c00021.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00021.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Requests app = MainActivityKt.getApp();
                String str = getMainUrl() + "/?s=" + query;
                c00021.L$0 = this;
                c00021.label = 1;
                $result = app.get(str, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00021);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                netCine = this;
                break;
            case 1:
                netCine = (NetCine) c00021.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Iterable $this$forEach$iv$iv$iv = ((NiceResponse) $result).getDocument().select("#box_movies > div.movie");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element it = (Element) element$iv$iv;
            SearchResponse searchResult = netCine.toSearchResult(it);
            if (searchResult != null) {
                destination$iv$iv.add(searchResult);
            }
        }
        return (List) destination$iv$iv;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:34:0x0102  */
    /* JADX WARN: Code duplicated, block: B:40:0x0131  */
    /* JADX WARN: Code duplicated, block: B:44:0x0152  */
    /* JADX WARN: Code duplicated, block: B:50:0x0187  */
    /* JADX WARN: Code duplicated, block: B:52:0x018a  */
    /* JADX WARN: Code duplicated, block: B:56:0x019d  */
    /* JADX WARN: Code duplicated, block: B:59:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:68:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:72:0x0239 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:74:0x023d  */
    /* JADX WARN: Code duplicated, block: B:76:0x0271 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:79:0x0192 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Code duplicated, block: B:81:0x018f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x01fe A[SYNTHETIC] */
    @Nullable
    public Object load(@NotNull String url, @NotNull Continuation<? super LoadResponse> continuation) {
        C00001 c00001;
        String url2;
        NetCine netCine;
        boolean z = false;
        Object obj;
        Document doc;
        boolean isTv;
        Element elementSelectFirst;
        String title;
        String poster;
        Element elementSelectFirst2;
        String plot;
        Integer year;
        Element elementSelectFirst3;
        Double score;
        Collection destination$iv$iv;
        Iterator it;
        String str;
        List recommendations;
        int $i$f$mapNotNull;
        Collection destination$iv$iv2;
        Element elementSelectFirst4;
        Episode episodeNewEpisode;
        String epHref;
        Element elementSelectFirst5;
        MovieSearchResponse movieSearchResponseNewMovieSearchResponse$default;
        String strText = null;
        String strText2;
        String strText3;
        if (continuation instanceof C00001) {
            c00001 = (C00001) continuation;
            if ((c00001.label & Integer.MIN_VALUE) != 0) {
                c00001.label -= Integer.MIN_VALUE;
            } else {
                c00001 = new C00001(continuation);
            }
        } else {
            c00001 = new C00001(continuation);
        }
        Object $result = c00001.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00001.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Requests app = MainActivityKt.getApp();
                c00001.L$0 = this;
                c00001.L$1 = url;
                c00001.label = 1;
                url2 = url;
                netCine = this;
                z = true;
                $result = app.get(url, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00001);
                obj = coroutine_suspended;
                if ($result == obj) {
                    return obj;
                }
                doc = ((NiceResponse) $result).getDocument();
                if (!StringsKt.contains(url2, "tvshows", false) && !StringsKt.contains(url2, "/episode/", false)) {
                    z = false;
                }
                isTv = z;
                elementSelectFirst = doc.selectFirst("div.dataplus h1, div.dataplus span.original");
                if (elementSelectFirst != null || (title = elementSelectFirst.text()) == null) {
                    title = "";
                }
                poster = MainAPIKt.fixUrl(netCine, doc.select("div.headingder > div.cover").attr("data-bg"));
                elementSelectFirst2 = doc.selectFirst("#dato-2 p");
                if (elementSelectFirst2 != null || (strText3 = elementSelectFirst2.text()) == null) {
                    plot = null;
                } else {
                    plot = StringsKt.trim(strText3).toString();
                }
                year = StringsKt.toIntOrNull(StringsKt.trim(doc.select("#dato-1 > div:nth-child(5)").text()).toString());
                elementSelectFirst3 = doc.selectFirst("div.rank");
                if (elementSelectFirst3 != null || (strText2 = elementSelectFirst3.text()) == null) {
                    score = null;
                } else {
                    score = StringsKt.toDoubleOrNull(strText2);
                }
                Iterable $this$forEach$iv$iv$iv = doc.select("div.links a");
                destination$iv$iv = new ArrayList();
                it = $this$forEach$iv$iv$iv.iterator();
                while (true) {
                    str = "href";
                    if (it.hasNext()) {
                        recommendations = (List) destination$iv$iv;
                        if (isTv) {
                            TvType tvType = TvType.Movie;
                            AnonymousClass3 anonymousClass3 = netCine.new AnonymousClass3(poster, plot, year, score, recommendations, doc, null);
                            c00001.L$0 = null;
                            c00001.L$1 = null;
                            c00001.label = 3;
                            $result = MainAPIKt.newMovieLoadResponse(netCine, title, url2, tvType, url2, anonymousClass3, c00001);
                            if ($result == obj) {
                                return obj;
                            }
                            return (LoadResponse) $result;
                        }
                        Iterable $this$forEach$iv$iv$iv2 = doc.select("div.post #cssmenu > ul li > ul > li");
                        $i$f$mapNotNull = 0;
                        destination$iv$iv2 = new ArrayList();
                        for (Object element$iv$iv : $this$forEach$iv$iv$iv2) {
                            final Element it2 = (Element) element$iv$iv;
                            elementSelectFirst4 = it2.selectFirst("a");
                            if (elementSelectFirst4 != null || (epHref = elementSelectFirst4.attr(str)) == null) {
                                episodeNewEpisode = null;
                            } else {
                                final String dateText = it2.select("a > span.datex").text();
                                $i$f$mapNotNull = $i$f$mapNotNull;
                                str = str;
                                episodeNewEpisode = MainAPIKt.newEpisode(netCine, epHref, new Function1() { // from class: com.NetCine.NetCine$$ExternalSyntheticLambda1
                                    public final Object invoke(Object obj2) {
                                        try {
                                            return NetCine.load$lambda$10$lambda$9(it2, dateText, (Episode) obj2);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });
                            }
                            if (episodeNewEpisode != null) {
                                destination$iv$iv2.add(episodeNewEpisode);
                            }
                            $i$f$mapNotNull = $i$f$mapNotNull;
                            str = str;
                        }
                        List episodes = (List) destination$iv$iv2;
                        TvType tvType2 = TvType.TvSeries;
                        AnonymousClass2 anonymousClass2 = netCine.new AnonymousClass2(poster, plot, year, score, recommendations, doc, null);
                        c00001.L$0 = null;
                        c00001.L$1 = null;
                        c00001.label = 2;
                        $result = MainAPIKt.newTvSeriesLoadResponse(netCine, title, url2, tvType2, episodes, anonymousClass2, c00001);
                        if ($result == obj) {
                            return obj;
                        }
                        return (LoadResponse) $result;
                    }
                    Object element$iv$iv2 = it.next();
                    final Element it3 = (Element) element$iv$iv2;
                    NetCine netCine2 = netCine;
                    elementSelectFirst5 = it3.selectFirst("h4");
                    if (elementSelectFirst5 != null || (strText = elementSelectFirst5.text()) == null) {
                        movieSearchResponseNewMovieSearchResponse$default = null;
                    } else {
                        movieSearchResponseNewMovieSearchResponse$default = MainAPIKt.newMovieSearchResponse(netCine2, strText, it3.attr("href"), TvType.Movie, false, new Function1() { // from class: com.NetCine.NetCine$$ExternalSyntheticLambda0
                            public final Object invoke(Object obj2) {
                                return NetCine.load$lambda$6$lambda$5(it3, (MovieSearchResponse) obj2);
                            }
                        });
                    }
                    if (movieSearchResponseNewMovieSearchResponse$default != null) {
                        destination$iv$iv.add(movieSearchResponseNewMovieSearchResponse$default);
                    }
                }
            case 1:
                String url3 = (String) c00001.L$1;
                NetCine netCine3 = (NetCine) c00001.L$0;
                ResultKt.throwOnFailure($result);
                url2 = url3;
                netCine = netCine3;
                obj = coroutine_suspended;
                z = true;
                doc = ((NiceResponse) $result).getDocument();
                if (!StringsKt.contains(url2, "tvshows", false)) {
                    z = false;
                }
                isTv = z;
                elementSelectFirst = doc.selectFirst("div.dataplus h1, div.dataplus span.original");
                if (elementSelectFirst != null) {
                    title = "";
                } else {
                    title = "";
                }
                poster = MainAPIKt.fixUrl(netCine, doc.select("div.headingder > div.cover").attr("data-bg"));
                elementSelectFirst2 = doc.selectFirst("#dato-2 p");
                if (elementSelectFirst2 != null) {
                    plot = null;
                } else {
                    plot = null;
                }
                year = StringsKt.toIntOrNull(StringsKt.trim(doc.select("#dato-1 > div:nth-child(5)").text()).toString());
                elementSelectFirst3 = doc.selectFirst("div.rank");
                if (elementSelectFirst3 != null) {
                    score = null;
                } else {
                    score = null;
                }
                Iterable $this$forEach$iv$iv$iv3 = doc.select("div.links a");
                destination$iv$iv = new ArrayList();
                it = $this$forEach$iv$iv$iv3.iterator();
                while (true) {
                    str = "href";
                    if (it.hasNext()) {
                        recommendations = (List) destination$iv$iv;
                        if (isTv) {
                            TvType tvType3 = TvType.Movie;
                            AnonymousClass3 anonymousClass4 = netCine.new AnonymousClass3(poster, plot, year, score, recommendations, doc, null);
                            c00001.L$0 = null;
                            c00001.L$1 = null;
                            c00001.label = 3;
                            $result = MainAPIKt.newMovieLoadResponse(netCine, title, url2, tvType3, url2, anonymousClass4, c00001);
                            if ($result == obj) {
                                return obj;
                            }
                            return (LoadResponse) $result;
                        }
                        Iterable $this$forEach$iv$iv$iv4 = doc.select("div.post #cssmenu > ul li > ul > li");
                        $i$f$mapNotNull = 0;
                        destination$iv$iv2 = new ArrayList();
                        Iterator<? extends Element> it4$iv = $this$forEach$iv$iv$iv4.iterator();
                        while (it4$iv.hasNext()) {
                            final Element it4 = it4$iv.next();
                            elementSelectFirst4 = it4.selectFirst("a");
                            if (elementSelectFirst4 != null) {
                            }
                            episodeNewEpisode = null;
                            if (episodeNewEpisode != null) {
                                destination$iv$iv2.add(episodeNewEpisode);
                            }
                            $i$f$mapNotNull = $i$f$mapNotNull;
                            str = str;
                        }
                        List episodes2 = (List) destination$iv$iv2;
                        TvType tvType4 = TvType.TvSeries;
                        AnonymousClass2 anonymousClass5 = netCine.new AnonymousClass2(poster, plot, year, score, recommendations, doc, null);
                        c00001.L$0 = null;
                        c00001.L$1 = null;
                        c00001.label = 2;
                        $result = MainAPIKt.newTvSeriesLoadResponse(netCine, title, url2, tvType4, episodes2, anonymousClass5, c00001);
                        if ($result == obj) {
                            return obj;
                        }
                        return (LoadResponse) $result;
                    }
                    Object element$iv$iv3 = it.next();
                    final Element it5 = (Element) element$iv$iv3;
                    NetCine netCine4 = netCine;
                    elementSelectFirst5 = it5.selectFirst("h4");
                    if (elementSelectFirst5 != null) {
                        movieSearchResponseNewMovieSearchResponse$default = null;
                    } else {
                        movieSearchResponseNewMovieSearchResponse$default = null;
                    }
                    if (movieSearchResponseNewMovieSearchResponse$default != null) {
                        destination$iv$iv.add(movieSearchResponseNewMovieSearchResponse$default);
                    }
                }
            case 2:
                ResultKt.throwOnFailure($result);
                return (LoadResponse) $result;
            case 3:
                ResultKt.throwOnFailure($result);
                return (LoadResponse) $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$6$lambda$5(Element $it, MovieSearchResponse $this$newMovieSearchResponse) {
        Element elementSelectFirst = $it.selectFirst("img");
        $this$newMovieSearchResponse.setPosterUrl(elementSelectFirst != null ? elementSelectFirst.attr("src") : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$10$lambda$9(Element $it, String $dateText, Episode $this$newEpisode) throws IOException {
        $this$newEpisode.setName(StringsKt.trim($it.select("a > span.datix").text()).toString());
        CharSequence $this$filter$iv = StringsKt.substringBefore($dateText, "-", $dateText);
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
        $this$newEpisode.setSeason(StringsKt.toIntOrNull($this$filter$iv2));
        CharSequence $this$filter$iv3 = StringsKt.substringAfter($dateText, "-", $dateText);
        CharSequence $this$filterTo$iv$iv2 = $this$filter$iv3;
        Appendable destination$iv$iv2 = new StringBuilder();
        int length2 = $this$filterTo$iv$iv2.length();
        for (int index$iv$iv2 = 0; index$iv$iv2 < length2; index$iv$iv2++) {
            char element$iv$iv2 = $this$filterTo$iv$iv2.charAt(index$iv$iv2);
            if (Character.isDigit(element$iv$iv2)) {
                destination$iv$iv2.append(element$iv$iv2);
            }
        }
        String $this$filter$iv4 = ((StringBuilder) destination$iv$iv2).toString();
        $this$newEpisode.setEpisode(StringsKt.toIntOrNull($this$filter$iv4));
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.NetCine.NetCine$load$2, reason: invalid class name */
    /* JADX INFO: compiled from: NetCine.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/TvSeriesLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.NetCine.NetCine$load$2", f = "NetCine.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nNetCine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NetCine.kt\ncom/NetCine/NetCine$load$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,174:1\n1557#2:175\n1628#2,3:176\n*S KotlinDebug\n*F\n+ 1 NetCine.kt\ncom/NetCine/NetCine$load$2\n*L\n96#1:175\n96#1:176,3\n*E\n"})
    final class AnonymousClass2 extends SuspendLambda implements Function2<TvSeriesLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ Document $doc;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ List<MovieSearchResponse> $recommendations;
        final /* synthetic */ Double $score;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, String str2, Integer num, Double d, List<MovieSearchResponse> list, Document document, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$plot = str2;
            this.$year = num;
            this.$score = d;
            this.$recommendations = list;
            this.$doc = document;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = NetCine.this.new AnonymousClass2(this.$poster, this.$plot, this.$year, this.$score, this.$recommendations, this.$doc, continuation);
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
                    NetCine.this.fillMeta(loadResponse, this.$poster, this.$plot, this.$year, this.$score, this.$recommendations);
                    LoadResponse.Companion companion = LoadResponse.Companion;
                    LoadResponse loadResponse2 = loadResponse;
                    Iterable $this$map$iv = this.$doc.select("#dato-1 > div:nth-child(4) a");
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        Element it = (Element) item$iv$iv;
                        destination$iv$iv.add(it.text());
                    }
                    companion.addActorNames(loadResponse2, (List) destination$iv$iv);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.NetCine.NetCine$load$3, reason: invalid class name */
    /* JADX INFO: compiled from: NetCine.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/MovieLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.NetCine.NetCine$load$3", f = "NetCine.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nNetCine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NetCine.kt\ncom/NetCine/NetCine$load$3\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,174:1\n1557#2:175\n1628#2,3:176\n*S KotlinDebug\n*F\n+ 1 NetCine.kt\ncom/NetCine/NetCine$load$3\n*L\n101#1:175\n101#1:176,3\n*E\n"})
    final class AnonymousClass3 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ Document $doc;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ List<MovieSearchResponse> $recommendations;
        final /* synthetic */ Double $score;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(String str, String str2, Integer num, Double d, List<MovieSearchResponse> list, Document document, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$plot = str2;
            this.$year = num;
            this.$score = d;
            this.$recommendations = list;
            this.$doc = document;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = NetCine.this.new AnonymousClass3(this.$poster, this.$plot, this.$year, this.$score, this.$recommendations, this.$doc, continuation);
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
                    NetCine.this.fillMeta(loadResponse, this.$poster, this.$plot, this.$year, this.$score, this.$recommendations);
                    LoadResponse.Companion companion = LoadResponse.Companion;
                    LoadResponse loadResponse2 = loadResponse;
                    Iterable $this$map$iv = this.$doc.select("#dato-1 > div:nth-child(4) a");
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        Element it = (Element) item$iv$iv;
                        destination$iv$iv.add(it.text());
                    }
                    companion.addActorNames(loadResponse2, (List) destination$iv$iv);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fillMeta(LoadResponse $this$fillMeta, String p, String pl, Integer y, Double s, List<? extends SearchResponse> list) {
        Score scoreFrom10;
        $this$fillMeta.setPosterUrl(p);
        $this$fillMeta.setPlot(pl);
        $this$fillMeta.setYear(y);
        if (s != null) {
            double it = s.doubleValue();
            scoreFrom10 = Score.Companion.from10(Double.valueOf(it));
        } else {
            scoreFrom10 = null;
        }
        $this$fillMeta.setScore(scoreFrom10);
        $this$fillMeta.setRecommendations(list);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0208  */
    /* JADX WARN: Code duplicated, block: B:33:0x0223  */
    /* JADX WARN: Code duplicated, block: B:36:0x0286 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:37:0x0287  */
    /* JADX WARN: Code duplicated, block: B:40:0x02be  */
    /* JADX WARN: Code duplicated, block: B:43:0x02cc  */
    /* JADX WARN: Code duplicated, block: B:46:0x02de  */
    /* JADX WARN: Code duplicated, block: B:50:0x02e7  */
    /* JADX WARN: Code duplicated, block: B:52:0x02ea  */
    /* JADX WARN: Code duplicated, block: B:54:0x02ff  */
    /* JADX WARN: Code duplicated, block: B:70:0x03ce  */
    /* JADX WARN: Code duplicated, block: B:74:0x03ec  */
    /* JADX WARN: Code duplicated, block: B:76:0x0409  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Code duplicated, block: B:80:0x0419  */
    /* JADX WARN: Code duplicated, block: B:83:0x043d  */
    /* JADX WARN: Code duplicated, block: B:84:0x0441  */
    /* JADX WARN: Code duplicated, block: B:87:0x0473 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:88:0x0474  */
    /* JADX WARN: Code duplicated, block: B:90:0x0495  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:88:0x0474 -> B:89:0x0484). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:90:0x0495 -> B:91:0x04b3). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:731)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public java.lang.Object loadLinks(@org.jetbrains.annotations.NotNull java.lang.String r44, boolean r45, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.SubtitleFile, kotlin.Unit> r46, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.utils.ExtractorLink, kotlin.Unit> r47, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> r48) {
        C00011 c00011;
        if (r48 instanceof C00011) {
            c00011 = (C00011) r48;
            if ((c00011.label & Integer.MIN_VALUE) != 0) {
                c00011.label -= Integer.MIN_VALUE;
            } else {
                c00011 = new C00011(r48);
            }
        } else {
            c00011 = new C00011(r48);
        }
        Object $result = c00011.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        String data = r44;
        Function1 callback = r47;
        NetCine netCine = this;
        Map<String, String> sessionHeaders = null;
        Map<String, String> labels = null;
        Iterator iterator = null;
        String label = null;
        String iframeUrl = null;
        String videoUrl = null;
        Ref.ObjectRef ref = null;
        int state = c00011.label;
        while (true) {
            switch (state) {
                case 0: {
                    ResultKt.throwOnFailure($result);
                    sessionHeaders = MapsKt.toMutableMap(NetCine.defaultHeaders);
                    sessionHeaders.put("referer", netCine.getMainUrl() + '/');
                    c00011.L$0 = netCine;
                    c00011.L$1 = data;
                    c00011.L$2 = callback;
                    c00011.L$3 = sessionHeaders;
                    c00011.label = 1;
                    $result = MainActivityKt.getApp().get(data, sessionHeaders, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00011);
                    if ($result == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    state = 1;
                    break;
                }
                case 1: {
                    sessionHeaders = (Map) c00011.L$3;
                    callback = (Function1) c00011.L$2;
                    data = (String) c00011.L$1;
                    netCine = (NetCine) c00011.L$0;
                    ResultKt.throwOnFailure($result);
                    String text = ((NiceResponse) $result).getText();
                    List matches = SequencesKt.toList(NetCine.iframeRegex.findAll((CharSequence) text, 0));
                    if (matches.isEmpty()) {
                        return Boxing.boxBoolean(false);
                    }
                    Map<String, String> labelsMap = new LinkedHashMap();
                    Iterator labelIterator = NetCine.labelRegex.findAll((CharSequence) text, 0).iterator();
                    while (labelIterator.hasNext()) {
                        MatchResult labelMatch = (MatchResult) labelIterator.next();
                        Pair pair = TuplesKt.to(labelMatch.getGroupValues().get(1), StringsKt.trim((CharSequence) labelMatch.getGroupValues().get(2)).toString());
                        labelsMap.put((String) pair.getFirst(), (String) pair.getSecond());
                    }
                    labels = labelsMap;
                    iterator = CollectionsKt.sortedWith(matches, new NetCine$loadLinks$$inlined$sortedByDescending$1(labelsMap)).iterator();
                    state = 5;
                    break;
                }
                case 2: {
                    label = (String) c00011.L$7;
                    iframeUrl = (String) c00011.L$6;
                    iterator = (Iterator) c00011.L$5;
                    labels = (Map) c00011.L$4;
                    sessionHeaders = (Map) c00011.L$3;
                    callback = (Function1) c00011.L$2;
                    data = (String) c00011.L$1;
                    netCine = (NetCine) c00011.L$0;
                    ResultKt.throwOnFailure($result);
                    NiceResponse iframeResponse = (NiceResponse) $result;
                    MatchResult videoSourceMatch = NetCine.videoSourceRegex.find((CharSequence) iframeResponse.getText(), 0);
                    videoUrl = (videoSourceMatch == null || videoSourceMatch.getGroupValues() == null) ? null : (String) videoSourceMatch.getGroupValues().get(1);
                    ref = new Ref.ObjectRef();
                    ref.element = data;
                    if (videoUrl == null || videoUrl.length() == 0) {
                        MatchResult nextMatch = NetCine.nextRegex.find((CharSequence) iframeResponse.getText(), 0);
                        String next = (nextMatch == null || nextMatch.getGroupValues() == null) ? null : (String) nextMatch.getGroupValues().get(1);
                        if (next != null) {
                            String nextUrl = MainAPIKt.fixUrl(netCine, next);
                            Map<String, String> nextHeaders = MapsKt.toMutableMap(sessionHeaders);
                            nextHeaders.put("referer", iframeUrl);
                            nextHeaders.put("cookie", "XCRF=XCRF; PHPSESSID=3o6atiuojr31rthqvefimlhtl8");
                            c00011.L$0 = netCine;
                            c00011.L$1 = data;
                            c00011.L$2 = callback;
                            c00011.L$3 = sessionHeaders;
                            c00011.L$4 = labels;
                            c00011.L$5 = iterator;
                            c00011.L$6 = iframeUrl;
                            c00011.L$7 = label;
                            c00011.L$8 = videoUrl;
                            c00011.L$9 = ref;
                            c00011.label = 3;
                            $result = MainActivityKt.getApp().get(nextUrl, nextHeaders, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00011);
                            if ($result == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            state = 3;
                            break;
                        }
                    }
                    state = 6;
                    break;
                }
                case 3: {
                    ref = (Ref.ObjectRef) c00011.L$9;
                    videoUrl = (String) c00011.L$8;
                    label = (String) c00011.L$7;
                    iframeUrl = (String) c00011.L$6;
                    iterator = (Iterator) c00011.L$5;
                    labels = (Map) c00011.L$4;
                    sessionHeaders = (Map) c00011.L$3;
                    callback = (Function1) c00011.L$2;
                    data = (String) c00011.L$1;
                    netCine = (NetCine) c00011.L$0;
                    ResultKt.throwOnFailure($result);
                    NiceResponse nextResponse = (NiceResponse) $result;
                    MatchResult nextSourceMatch = NetCine.videoSourceRegex.find((CharSequence) nextResponse.getText(), 0);
                    videoUrl = (nextSourceMatch == null || nextSourceMatch.getGroupValues() == null) ? null : (String) nextSourceMatch.getGroupValues().get(1);
                    ref.element = iframeUrl;
                    state = 6;
                    break;
                }
                case 4: {
                    callback = (Function1) c00011.L$6;
                    iterator = (Iterator) c00011.L$5;
                    labels = (Map) c00011.L$4;
                    sessionHeaders = (Map) c00011.L$3;
                    callback = (Function1) c00011.L$2;
                    data = (String) c00011.L$1;
                    netCine = (NetCine) c00011.L$0;
                    ResultKt.throwOnFailure($result);
                    callback.invoke($result);
                    state = 5;
                    break;
                }
                case 5: {
                    if (!iterator.hasNext()) {
                        return Boxing.boxBoolean(true);
                    }
                    MatchResult match = (MatchResult) iterator.next();
                    String labelValue = (String) labels.get((String) match.getGroupValues().get(1));
                    label = labelValue != null ? labelValue : "Player";
                    iframeUrl = MainAPIKt.fixUrl(netCine, (String) match.getGroupValues().get(2));
                    Map<String, String> iframeHeaders = MapsKt.toMutableMap(sessionHeaders);
                    iframeHeaders.put("referer", data);
                    c00011.L$0 = netCine;
                    c00011.L$1 = data;
                    c00011.L$2 = callback;
                    c00011.L$3 = sessionHeaders;
                    c00011.L$4 = labels;
                    c00011.L$5 = iterator;
                    c00011.L$6 = iframeUrl;
                    c00011.L$7 = label;
                    c00011.L$8 = null;
                    c00011.L$9 = null;
                    c00011.label = 2;
                    $result = MainActivityKt.getApp().get(iframeUrl, iframeHeaders, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00011);
                    if ($result == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    state = 2;
                    break;
                }
                case 6: {
                    if (videoUrl != null) {
                        boolean isM3u8OrPhp = StringsKt.contains((CharSequence) videoUrl, (CharSequence) ".m3u8", false) || StringsKt.contains((CharSequence) videoUrl, (CharSequence) ".php", false);
                        String source = netCine.getName();
                        String linkName = netCine.getName() + ' ' + label;
                        ExtractorLinkType type = isM3u8OrPhp ? ExtractorLinkType.M3U8 : ExtractorLinkType.VIDEO;
                        c00011.L$0 = netCine;
                        c00011.L$1 = data;
                        c00011.L$2 = callback;
                        c00011.L$3 = sessionHeaders;
                        c00011.L$4 = labels;
                        c00011.L$5 = iterator;
                        c00011.L$6 = callback;
                        c00011.L$7 = null;
                        c00011.L$8 = null;
                        c00011.L$9 = null;
                        c00011.label = 4;
                        Object link = ExtractorApiKt.newExtractorLink(source, linkName, videoUrl, type, (Function2) new NetCine$loadLinks$3$2$1(netCine, ref, null), c00011);
                        if (link == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        callback.invoke(link);
                    }
                    state = 5;
                    break;
                }
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }
}

/* JADX INFO: renamed from: com.NetCine.NetCine$loadLinks$$inlined$sortedByDescending$1, reason: invalid class name */
/* JADX INFO: compiled from: NetCine.kt */
final class NetCine$loadLinks$$inlined$sortedByDescending$1 implements Comparator<MatchResult> {
    final /* synthetic */ Map<String, String> $labels$inlined;

    NetCine$loadLinks$$inlined$sortedByDescending$1(Map<String, String> map) {
        this.$labels$inlined = map;
    }

    /* JADX DEBUG: compare sortedByDescending selector: labels[match.groupValues[1]]?.contains("Dub") */
    public final int compare(MatchResult matchResult, MatchResult matchResult2) {
        String str = (String) this.$labels$inlined.get(matchResult2.getGroupValues().get(1));
        Boolean bool = str != null ? Boolean.valueOf(StringsKt.contains((CharSequence) str, (CharSequence) "Dub", true)) : null;
        String str2 = (String) this.$labels$inlined.get(matchResult.getGroupValues().get(1));
        Boolean bool2 = str2 != null ? Boolean.valueOf(StringsKt.contains((CharSequence) str2, (CharSequence) "Dub", true)) : null;
        return ComparisonsKt.compareValues(bool, bool2);
    }
}

