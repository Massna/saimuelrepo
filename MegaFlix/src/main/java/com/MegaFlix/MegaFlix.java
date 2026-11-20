package com.MegaFlix;

import com.lagradost.cloudstream3.Episode;
import com.lagradost.cloudstream3.HomePageResponse;
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
import com.lagradost.cloudstream3.TvSeriesSearchResponse;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
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
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: MegaFlix.kt */
/* JADX INFO: loaded from: /tmp/cs3/MegaFlix/classes.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0005H\u0002J\u001e\u0010\"\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0096@¢\u0006\u0002\u0010)J\u001e\u0010*\u001a\n\u0012\u0004\u0012\u00020+\u0018\u00010 2\u0006\u0010,\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010-J\u0016\u0010.\u001a\u00020/2\u0006\u0010\u001e\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010-JF\u00100\u001a\u00020\u000e2\u0006\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u000e2\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u000206042\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020604H\u0096@¢\u0006\u0002\u00109J\u0010\u0010:\u001a\u00020\u00172\u0006\u0010;\u001a\u00020\u0005H\u0002J\u0012\u0010<\u001a\u0004\u0018\u00010\u00052\u0006\u0010=\u001a\u00020\u0005H\u0002J\u0015\u0010>\u001a\u0004\u0018\u00010&2\u0006\u0010?\u001a\u00020\u0005¢\u0006\u0002\u0010@J\u0012\u0010A\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001e\u001a\u00020\u0005H\u0002J\u000e\u0010B\u001a\u00020\u000e2\u0006\u0010?\u001a\u00020\u0005R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0014\u0010\r\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0014\u0010\u0013\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0007\"\u0004\b\u001c\u0010\tR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u0006C"}, d2 = {"Lcom/MegaFlix/MegaFlix;", "Lcom/lagradost/cloudstream3/MainAPI;", "<init>", "()V", "name", "", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "lang", "getLang", "setLang", "hasQuickSearch", "", "getHasQuickSearch", "()Z", "hasDownloadSupport", "getHasDownloadSupport", "hasMainPage", "getHasMainPage", "supportedTypes", "", "Lcom/lagradost/cloudstream3/TvType;", "getSupportedTypes", "()Ljava/util/Set;", "mainUrl", "getMainUrl", "setMainUrl", "getUrl", "url", "mainPage", "", "Lcom/lagradost/cloudstream3/MainPageData;", "getMainPage", "()Ljava/util/List;", "Lcom/lagradost/cloudstream3/HomePageResponse;", "page", "", "request", "Lcom/lagradost/cloudstream3/MainPageRequest;", "(ILcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "Lcom/lagradost/cloudstream3/SearchResponse;", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "Lcom/lagradost/cloudstream3/LoadResponse;", "loadLinks", "data", "isCasting", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getType", "t", "extrairLink", "input", "extractNumber", "text", "(Ljava/lang/String;)Ljava/lang/Integer;", "extracturl", "isUrl", "MegaFlix_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nMegaFlix.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MegaFlix.kt\ncom/MegaFlix/MegaFlix\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,273:1\n1611#2,9:274\n1863#2:283\n1755#2,3:285\n1864#2:289\n1620#2:290\n1611#2,9:291\n1863#2:300\n1755#2,3:301\n1864#2:305\n1620#2:306\n1863#2,2:307\n1863#2,2:309\n1557#2:311\n1628#2,3:312\n1557#2:315\n1628#2,3:316\n1557#2:319\n1628#2,3:320\n1863#2,2:323\n1#3:284\n1#3:288\n1#3:304\n*S KotlinDebug\n*F\n+ 1 MegaFlix.kt\ncom/MegaFlix/MegaFlix\n*L\n72#1:274,9\n72#1:283\n82#1:285,3\n72#1:289\n72#1:290\n103#1:291,9\n103#1:300\n111#1:301,3\n103#1:305\n103#1:306\n147#1:307,2\n172#1:309,2\n199#1:311\n199#1:312,3\n226#1:315\n226#1:316,3\n233#1:319\n233#1:320,3\n234#1:323,2\n72#1:288\n103#1:304\n*E\n"})
public final class MegaFlix extends MainAPI {

    @NotNull
    private String name = "MegaFlix";

    @NotNull
    private String lang = "pt-br";
    private final boolean hasQuickSearch = true;
    private final boolean hasDownloadSupport = true;
    private final boolean hasMainPage = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Movie, TvType.TvSeries});

    @NotNull
    private String mainUrl = "https://megaflix.lol";

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to("/category/acao/page", "Ação"), TuplesKt.to("/category/animacao/page", "Animação"), TuplesKt.to("/category/comedia/page", "Comédia"), TuplesKt.to("/category/crime/page", "Crime"), TuplesKt.to("/category/documentario/page", "Documentário"), TuplesKt.to("/category/drama/page", "Drama"), TuplesKt.to("/category/familia/page", "Família"), TuplesKt.to("/category/fantasia/page", "Fantasia"), TuplesKt.to("/category/faroeste/page", "Faroeste"), TuplesKt.to("/category/guerra/page", "Guerra"), TuplesKt.to("/category/misterio/page", "Mistério"), TuplesKt.to("/category/ficcao-cientifica/page", "Sci-fi"), TuplesKt.to("/category/thriller/page", "Thriller")});

    /* JADX INFO: renamed from: com.MegaFlix.MegaFlix$getMainPage$1, reason: invalid class name */
    /* JADX INFO: compiled from: MegaFlix.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.MegaFlix.MegaFlix", f = "MegaFlix.kt", i = {0, 0}, l = {66}, m = "getMainPage", n = {"this", "request"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MegaFlix.this.getMainPage(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.MegaFlix.MegaFlix$load$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MegaFlix.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.MegaFlix.MegaFlix", f = "MegaFlix.kt", i = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {133, 161, 189, 203}, m = "load", n = {"this", "url", "type", "this", "url", "title", "descipt", "rating", "year", "backgroundPoster", "episodeList", "url1", "season"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8", "L$10"})
    static final class C00001 extends ContinuationImpl {
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

        C00001(Continuation<? super C00001> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MegaFlix.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.MegaFlix.MegaFlix$loadLinks$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MegaFlix.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.MegaFlix.MegaFlix", f = "MegaFlix.kt", i = {0, 0, 0, 1, 1, 1, 1, 2, 2, 2}, l = {225, 230, 238}, m = "loadLinks", n = {"data", "subtitleCallback", "callback", "data", "subtitleCallback", "callback", "destination$iv$iv", "data", "subtitleCallback", "callback"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2"})
    static final class C00011 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;

        C00011(Continuation<? super C00011> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MegaFlix.this.loadLinks(null, false, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.MegaFlix.MegaFlix$search$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MegaFlix.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.MegaFlix.MegaFlix", f = "MegaFlix.kt", i = {0}, l = {101}, m = "search", n = {"this"}, s = {"L$0"})
    static final class C00021 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C00021(Continuation<? super C00021> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MegaFlix.this.search(null, (Continuation) this);
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

    private final String getUrl(String url) {
        URI uri = new URI(url);
        StringBuilder sbAppend = new StringBuilder().append(getMainUrl());
        String path = uri.getPath();
        if (path == null) {
            path = "";
        }
        return sbAppend.append(path).append(uri.getQuery() != null ? '?' + uri.getQuery() : "").toString();
    }

    @NotNull
    public List<MainPageData> getMainPage() {
        return this.mainPage;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object getMainPage(int page, @NotNull MainPageRequest request, @NotNull Continuation<? super HomePageResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        MainPageRequest request2;
        MegaFlix megaFlix;
        String strAttr;
        AnonymousClass1 anonymousClass2;
        int $i$f$mapNotNull;
        boolean containsExtension;
        String strText;
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
                Map cookies = new LinkedHashMap();
                cookies.put("ordem", "3");
                Requests app = MainActivityKt.getApp();
                String url = getUrl(request.getData() + '/' + page);
                Map mapMapOf = MapsKt.mapOf(TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = request;
                anonymousClass1.label = 1;
                $result = Requests.get$default(app, url, mapMapOf, (String) null, (Map) null, cookies, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, anonymousClass1, 4076, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                request2 = request;
                megaFlix = this;
                break;
                break;
            case 1:
                request2 = (MainPageRequest) anonymousClass1.L$1;
                megaFlix = (MegaFlix) anonymousClass1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document soup = ((NiceResponse) $result).getDocument();
        Iterable $this$forEach$iv$iv$iv = soup.select("ul.post-lst li");
        int $i$f$mapNotNull2 = 0;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element item = (Element) element$iv$iv;
            Element elementSelectFirst = item.selectFirst("header.entry-header h2.entry-title");
            String str = "";
            String title = (elementSelectFirst == null || (strText = elementSelectFirst.text()) == null) ? "" : strText;
            Element elementSelectFirst2 = item.selectFirst("a.lnk-blk");
            if (elementSelectFirst2 == null || (strAttr = elementSelectFirst2.attr("href")) == null) {
                strAttr = "";
            }
            String link = strAttr;
            String it = item.select("div.post-thumbnail figure img").attr("src");
            if (!(it.length() > 0)) {
                it = null;
            }
            if (it == null) {
                String it2 = item.select("img").attr("src");
                if (!(it2.length() > 0)) {
                    it2 = null;
                }
                if (it2 != null) {
                    str = it2;
                }
            } else {
                str = it;
            }
            final String postUrl = str;
            Iterable extensions = CollectionsKt.listOf(new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp", ".svg", ".webp", ".tiff"});
            Iterable $this$any$iv = extensions;
            if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                anonymousClass2 = anonymousClass1;
                $i$f$mapNotNull = $i$f$mapNotNull2;
                containsExtension = false;
            } else {
                Iterator it3 = $this$any$iv.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Object element$iv = it3.next();
                        String ext = (String) element$iv;
                        anonymousClass2 = anonymousClass1;
                        $i$f$mapNotNull = $i$f$mapNotNull2;
                        if (StringsKt.endsWith$default(postUrl, ext, false, 2, (Object) null)) {
                            containsExtension = true;
                        } else {
                            anonymousClass1 = anonymousClass2;
                            $i$f$mapNotNull2 = $i$f$mapNotNull;
                        }
                    } else {
                        anonymousClass2 = anonymousClass1;
                        $i$f$mapNotNull = $i$f$mapNotNull2;
                        containsExtension = false;
                    }
                }
            }
            MovieSearchResponse movieSearchResponseNewMovieSearchResponse$default = containsExtension ? MainAPIKt.newMovieSearchResponse$default(megaFlix, title, link, StringsKt.contains$default(link, "filme", false, 2, (Object) null) ? TvType.Movie : TvType.TvSeries, false, new Function1() { // from class: com.MegaFlix.MegaFlix$$ExternalSyntheticLambda1
                public final Object invoke(Object obj) {
                    return MegaFlix.getMainPage$lambda$4$lambda$3(postUrl, (MovieSearchResponse) obj);
                }
            }, 8, (Object) null) : null;
            if (movieSearchResponseNewMovieSearchResponse$default != null) {
                destination$iv$iv.add(movieSearchResponseNewMovieSearchResponse$default);
            }
            anonymousClass1 = anonymousClass2;
            $i$f$mapNotNull2 = $i$f$mapNotNull;
        }
        List home = (List) destination$iv$iv;
        return MainAPIKt.newHomePageResponse$default(request2.getName(), home, (Boolean) null, 4, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getMainPage$lambda$4$lambda$3(String $postUrl, MovieSearchResponse $this$newMovieSearchResponse) {
        $this$newMovieSearchResponse.setPosterUrl($postUrl);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        C00021 c00021;
        MegaFlix megaFlix;
        String strAttr;
        C00021 c00022;
        boolean z;
        String strAttr2;
        String strText;
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
                $result = Requests.get$default(app, str, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00021, 4094, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                megaFlix = this;
                break;
                break;
            case 1:
                megaFlix = (MegaFlix) c00021.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document soup = ((NiceResponse) $result).getDocument();
        Iterable $this$forEach$iv$iv$iv = soup.select("ul.post-lst li");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element it = (Element) element$iv$iv;
            Element elementSelectFirst = it.selectFirst("header.entry-header h2.entry-title");
            String str2 = "";
            String title = (elementSelectFirst == null || (strText = elementSelectFirst.text()) == null) ? "" : strText;
            Element elementSelectFirst2 = it.selectFirst("a.lnk-blk");
            if (elementSelectFirst2 == null || (strAttr = elementSelectFirst2.attr("href")) == null) {
                strAttr = "";
            }
            String link = strAttr;
            Element elementSelectFirst3 = it.selectFirst("div.post-thumbnail figure img");
            if (elementSelectFirst3 != null && (strAttr2 = elementSelectFirst3.attr("src")) != null) {
                str2 = strAttr2;
            }
            final String postUrl = str2;
            Iterable extensions = CollectionsKt.listOf(new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp", ".svg", ".webp", ".tiff"});
            Iterable $this$any$iv = extensions;
            if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                c00022 = c00021;
                z = false;
            } else {
                Iterator it2 = $this$any$iv.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        Object element$iv = it2.next();
                        String ext = (String) element$iv;
                        c00022 = c00021;
                        if (StringsKt.endsWith$default(postUrl, ext, false, 2, (Object) null)) {
                            z = true;
                        } else {
                            c00021 = c00022;
                        }
                    } else {
                        c00022 = c00021;
                        z = false;
                    }
                }
            }
            boolean containsExtension = z;
            TvSeriesSearchResponse tvSeriesSearchResponseNewTvSeriesSearchResponse$default = containsExtension ? MainAPIKt.newTvSeriesSearchResponse$default(megaFlix, title, link, StringsKt.contains$default(link, "filme", false, 2, (Object) null) ? TvType.Movie : TvType.TvSeries, false, new Function1() { // from class: com.MegaFlix.MegaFlix$$ExternalSyntheticLambda0
                public final Object invoke(Object obj) {
                    return MegaFlix.search$lambda$7$lambda$6(postUrl, (TvSeriesSearchResponse) obj);
                }
            }, 8, (Object) null) : null;
            if (tvSeriesSearchResponseNewTvSeriesSearchResponse$default != null) {
                destination$iv$iv.add(tvSeriesSearchResponseNewTvSeriesSearchResponse$default);
            }
            c00021 = c00022;
        }
        List home = (List) destination$iv$iv;
        return home;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit search$lambda$7$lambda$6(String $postUrl, TvSeriesSearchResponse $this$newTvSeriesSearchResponse) {
        $this$newTvSeriesSearchResponse.setPosterUrl($postUrl);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:56:0x01c9  */
    /* JADX WARN: Code duplicated, block: B:58:0x0266 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:59:0x0267  */
    /* JADX WARN: Code duplicated, block: B:62:0x0286  */
    /* JADX WARN: Code duplicated, block: B:65:0x0293  */
    /* JADX WARN: Code duplicated, block: B:67:0x02a5  */
    /* JADX WARN: Code duplicated, block: B:68:0x02aa  */
    /* JADX WARN: Code duplicated, block: B:71:0x02c0  */
    /* JADX WARN: Code duplicated, block: B:72:0x02c5  */
    /* JADX WARN: Code duplicated, block: B:74:0x02c8  */
    /* JADX WARN: Code duplicated, block: B:75:0x02cf  */
    /* JADX WARN: Code duplicated, block: B:78:0x02f7  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x0267 -> B:60:0x0270). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:731)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public java.lang.Object load(@org.jetbrains.annotations.NotNull java.lang.String r50, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super com.lagradost.cloudstream3.LoadResponse> r51) {
        /*
            Method dump skipped, instruction units count: 984
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.MegaFlix.MegaFlix.load(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$10$lambda$9(String $name, Pair $season, Integer $epNum, Episode $this$newEpisode) {
        $this$newEpisode.setName($name);
        $this$newEpisode.setSeason((Integer) $season.getFirst());
        $this$newEpisode.setEpisode($epNum);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.MegaFlix.MegaFlix$load$4, reason: invalid class name */
    /* JADX INFO: compiled from: MegaFlix.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/TvSeriesLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.MegaFlix.MegaFlix$load$4", f = "MegaFlix.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass4 extends SuspendLambda implements Function2<TvSeriesLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $backgroundPoster;
        final /* synthetic */ String $descipt;
        final /* synthetic */ String $rating;
        final /* synthetic */ String $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(String str, String str2, String str3, String str4, Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
            this.$backgroundPoster = str;
            this.$year = str2;
            this.$descipt = str3;
            this.$rating = str4;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass4 = new AnonymousClass4(this.$backgroundPoster, this.$year, this.$descipt, this.$rating, continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        public final Object invoke(TvSeriesLoadResponse tvSeriesLoadResponse, Continuation<? super Unit> continuation) {
            return create(tvSeriesLoadResponse, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    TvSeriesLoadResponse $this$newTvSeriesLoadResponse = (TvSeriesLoadResponse) this.L$0;
                    $this$newTvSeriesLoadResponse.setPosterUrl(this.$backgroundPoster);
                    String str = this.$year;
                    $this$newTvSeriesLoadResponse.setYear(str != null ? StringsKt.toIntOrNull(str) : null);
                    $this$newTvSeriesLoadResponse.setPlot(this.$descipt);
                    $this$newTvSeriesLoadResponse.setScore(Score.Companion.from10(this.$rating));
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.MegaFlix.MegaFlix$load$5, reason: invalid class name */
    /* JADX INFO: compiled from: MegaFlix.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/MovieLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.MegaFlix.MegaFlix$load$5", f = "MegaFlix.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass5 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $backgroundPoster;
        final /* synthetic */ String $descipt;
        final /* synthetic */ String $rating;
        final /* synthetic */ String $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(String str, String str2, String str3, String str4, Continuation<? super AnonymousClass5> continuation) {
            super(2, continuation);
            this.$backgroundPoster = str;
            this.$year = str2;
            this.$descipt = str3;
            this.$rating = str4;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass5 = new AnonymousClass5(this.$backgroundPoster, this.$year, this.$descipt, this.$rating, continuation);
            anonymousClass5.L$0 = obj;
            return anonymousClass5;
        }

        public final Object invoke(MovieLoadResponse movieLoadResponse, Continuation<? super Unit> continuation) {
            return create(movieLoadResponse, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    MovieLoadResponse $this$newMovieLoadResponse = (MovieLoadResponse) this.L$0;
                    $this$newMovieLoadResponse.setPosterUrl(this.$backgroundPoster);
                    String str = this.$year;
                    $this$newMovieLoadResponse.setYear(str != null ? StringsKt.toIntOrNull(str) : null);
                    $this$newMovieLoadResponse.setPlot(this.$descipt);
                    $this$newMovieLoadResponse.setScore(Score.Companion.from10(this.$rating));
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x011b  */
    /* JADX WARN: Code duplicated, block: B:26:0x014d A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:27:0x014e  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x014e -> B:28:0x0151). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:731)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public java.lang.Object loadLinks(@org.jetbrains.annotations.NotNull java.lang.String r35, boolean r36, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.SubtitleFile, kotlin.Unit> r37, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.utils.ExtractorLink, kotlin.Unit> r38, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> r39) {
        /*
            Method dump skipped, instruction units count: 578
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.MegaFlix.MegaFlix.loadLinks(java.lang.String, boolean, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final TvType getType(String t) {
        return StringsKt.contains$default(t, "filme", false, 2, (Object) null) ? TvType.Movie : TvType.TvSeries;
    }

    private final String extrairLink(String input) {
        MatchGroupCollection groups;
        MatchGroup matchGroup;
        Regex regex = new Regex("window\\.location\\.href\\s*=\\s*[\"'](.*?)[\"']\\s*;?");
        MatchResult matchResult = Regex.find$default(regex, input, 0, 2, (Object) null);
        if (matchResult == null || (groups = matchResult.getGroups()) == null || (matchGroup = groups.get(1)) == null) {
            return null;
        }
        return matchGroup.getValue();
    }

    @Nullable
    public final Integer extractNumber(@NotNull String text) {
        MatchGroupCollection groups;
        MatchGroup matchGroup;
        String value;
        Regex regex = new Regex("(\\d+)");
        MatchResult matchResult = Regex.find$default(regex, text, 0, 2, (Object) null);
        if (matchResult == null || (groups = matchResult.getGroups()) == null || (matchGroup = groups.get(1)) == null || (value = matchGroup.getValue()) == null) {
            return null;
        }
        return Integer.valueOf(Integer.parseInt(value));
    }

    private final String extracturl(String url) {
        MatchGroupCollection groups;
        MatchGroup matchGroup;
        Regex regex = new Regex(".*/assistir/([^/]+)[/?]?.*");
        MatchResult matchResult = Regex.find$default(regex, url, 0, 2, (Object) null);
        if (matchResult == null || (groups = matchResult.getGroups()) == null || (matchGroup = groups.get(1)) == null) {
            return null;
        }
        return matchGroup.getValue();
    }

    public final boolean isUrl(@NotNull String text) {
        Regex regex = new Regex("^(http(s)?://)[^\\s]+?\\.[^\\s]+/?");
        return regex.matches(text);
    }
}
