package com.OverFlix;

import com.lagradost.cloudstream3.Actor;
import com.lagradost.cloudstream3.ActorData;
import com.lagradost.cloudstream3.ActorRole;
import com.lagradost.cloudstream3.Episode;
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
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.TvSeriesLoadResponse;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: OverFlix.kt */
/* JADX INFO: loaded from: /tmp/cs3/OverFlix/classes.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 =2\u00020\u0001:\u0001=B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\f\u0010 \u001a\u00020\u0005*\u00020!H\u0002J\u000e\u0010\"\u001a\u0004\u0018\u00010\u0005*\u00020!H\u0002J\u001a\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0005H\u0002J\u001e\u0010\u001e\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0096@¢\u0006\u0002\u0010+J\u001c\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u001c2\u0006\u0010.\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010/J\u0016\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010/JF\u00103\u001a\u00020\u000e2\u0006\u00104\u001a\u00020\u00052\u0006\u00105\u001a\u00020\u000e2\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u000209072\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020907H\u0096@¢\u0006\u0002\u0010<R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0014\u0010\r\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0007\"\u0004\b\u0013\u0010\tR\u0014\u0010\u0014\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006>"}, d2 = {"Lcom/OverFlix/OverFlix;", "Lcom/lagradost/cloudstream3/MainAPI;", "<init>", "()V", "mainUrl", "", "getMainUrl", "()Ljava/lang/String;", "setMainUrl", "(Ljava/lang/String;)V", "name", "getName", "setName", "hasMainPage", "", "getHasMainPage", "()Z", "lang", "getLang", "setLang", "hasQuickSearch", "getHasQuickSearch", "supportedTypes", "", "Lcom/lagradost/cloudstream3/TvType;", "getSupportedTypes", "()Ljava/util/Set;", "mainPage", "", "Lcom/lagradost/cloudstream3/MainPageData;", "getMainPage", "()Ljava/util/List;", "posterUrl", "Lorg/jsoup/nodes/Element;", "backdropUrl", "buildData", "link", "backdrop", "Lcom/lagradost/cloudstream3/HomePageResponse;", "page", "", "request", "Lcom/lagradost/cloudstream3/MainPageRequest;", "(ILcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "Lcom/lagradost/cloudstream3/SearchResponse;", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "Lcom/lagradost/cloudstream3/LoadResponse;", "url", "loadLinks", "data", "isCasting", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "OverFlix_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nOverFlix.kt\nKotlin\n*S Kotlin\n*F\n+ 1 OverFlix.kt\ncom/OverFlix/OverFlix\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,208:1\n1#2:209\n1#2:220\n1#2:233\n1#2:246\n1#2:259\n1611#3,9:210\n1863#3:219\n1864#3:221\n1620#3:222\n1611#3,9:223\n1863#3:232\n1864#3:234\n1620#3:235\n1611#3,9:236\n1863#3:245\n1864#3:247\n1620#3:248\n1611#3,9:249\n1863#3:258\n1864#3:260\n1620#3:261\n295#3,2:262\n295#3,2:264\n1368#3:266\n1454#3,2:267\n1557#3:269\n1628#3,3:270\n1456#3,3:273\n295#3,2:276\n*S KotlinDebug\n*F\n+ 1 OverFlix.kt\ncom/OverFlix/OverFlix\n*L\n59#1:220\n81#1:233\n111#1:246\n120#1:259\n59#1:210,9\n59#1:219\n59#1:221\n59#1:222\n81#1:223,9\n81#1:232\n81#1:234\n81#1:235\n111#1:236,9\n111#1:245\n111#1:247\n111#1:248\n120#1:249,9\n120#1:258\n120#1:260\n120#1:261\n145#1:262,2\n150#1:264,2\n165#1:266\n165#1:267,2\n171#1:269\n171#1:270,3\n165#1:273,3\n95#1:276,2\n*E\n"})
public final class OverFlix extends MainAPI {

    @NotNull
    private static final String BASE_URL = "https://overflix.cc";

    @NotNull
    private static final String LANG = "pt-br";
    private final boolean hasQuickSearch;

    @NotNull
    private static final Map<String, String> UA_HEADERS = MapsKt.mapOf(TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));

    @NotNull
    private String mainUrl = BASE_URL;

    @NotNull
    private String name = "OverFlix";
    private final boolean hasMainPage = true;

    @NotNull
    private String lang = LANG;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Movie, TvType.TvSeries});

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to("/filmes/acao", "Filmes - Ação"), TuplesKt.to("/series/action-and-adventure", "Séries - Ação"), TuplesKt.to("/filmes/animacao", "Filmes - Animação"), TuplesKt.to("/series/animacao", "Séries - Animação"), TuplesKt.to("/filmes/comedia", "Filmes - Comédia"), TuplesKt.to("/series/comedia", "Séries - Comédia"), TuplesKt.to("/filmes/crime", "Filmes - Crime"), TuplesKt.to("/series/crime", "Séries - Crime"), TuplesKt.to("/filmes/ficcao-cientifica", "Filmes - Ficção-Cientifíca"), TuplesKt.to("/series/sci-fi-and-fantasy", "Séries - Ficção-Cientifíca"), TuplesKt.to("/filmes/drama", "Filmes - Drama"), TuplesKt.to("/series/drama", "Séries - Drama")});

    /* JADX INFO: renamed from: com.OverFlix.OverFlix$getMainPage$1, reason: invalid class name */
    /* JADX INFO: compiled from: OverFlix.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlix", f = "OverFlix.kt", i = {0, 0}, l = {57}, m = "getMainPage", n = {"this", "request"}, s = {"L$0", "L$1"})
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
            return OverFlix.this.getMainPage(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlix$load$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: OverFlix.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlix", f = "OverFlix.kt", i = {0, 0, 0, 1, 1, 1, 1, 1, 1}, l = {107, 119, 156, 188}, m = "load", n = {"this", "realUrl", "forcedBackdrop", "this", "realUrl", "forcedBackdrop", "document", "title", "isMovie"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$4", "Z$0"})
    static final class C00001 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        C00001(Continuation<? super C00001> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OverFlix.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlix$search$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: OverFlix.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlix", f = "OverFlix.kt", i = {0}, l = {79}, m = "search", n = {"this"}, s = {"L$0"})
    static final class C00011 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C00011(Continuation<? super C00011> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OverFlix.this.search(null, (Continuation) this);
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

    public boolean getHasQuickSearch() {
        return this.hasQuickSearch;
    }

    @NotNull
    public Set<TvType> getSupportedTypes() {
        return this.supportedTypes;
    }

    @NotNull
    public List<MainPageData> getMainPage() {
        return this.mainPage;
    }

    private final String posterUrl(Element $this$posterUrl) {
        Element img = $this$posterUrl.selectFirst("img");
        if (img == null) {
            return "";
        }
        String src = img.attr("src");
        return (StringsKt.contains$default(src, "transparent", false, 2, (Object) null) || StringsKt.isBlank(src)) ? img.attr("data-src") : src;
    }

    private final String backdropUrl(Element $this$backdropUrl) {
        String strReplace$default;
        String strAttr = $this$backdropUrl.select("figure img.aspect-video").attr("src");
        if (StringsKt.isBlank(strAttr)) {
            strAttr = null;
        }
        String str = strAttr;
        if (str == null || (strReplace$default = StringsKt.replace$default(str, "/w300/", "/original/", false, 4, (Object) null)) == null) {
            return null;
        }
        return StringsKt.replace$default(strReplace$default, "/w1280/", "/original/", false, 4, (Object) null);
    }

    private final String buildData(String link, String backdrop) {
        return backdrop != null ? link + "|BACKDROP|" + backdrop : link;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object getMainPage(int page, @NotNull MainPageRequest request, @NotNull Continuation<? super HomePageResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        StringBuilder sb;
        StringBuilder sbAppend;
        final OverFlix overFlix;
        MainPageRequest request2;
        MovieSearchResponse movieSearchResponseNewMovieSearchResponse$default;
        String link;
        String strAttr;
        String title;
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
                if (page <= 1) {
                    sb = new StringBuilder();
                    sbAppend = sb.append(getMainUrl()).append(request.getData());
                } else {
                    sb = new StringBuilder();
                    sbAppend = sb.append(getMainUrl()).append(request.getData()).append("?page=").append(page);
                }
                String url = sbAppend.toString();
                Requests app = MainActivityKt.getApp();
                Map<String, String> map = UA_HEADERS;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = request;
                anonymousClass1.label = 1;
                $result = Requests.get$default(app, url, map, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, anonymousClass1, 4092, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                overFlix = this;
                request2 = request;
                break;
                break;
            case 1:
                request2 = (MainPageRequest) anonymousClass1.L$1;
                overFlix = (OverFlix) anonymousClass1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document document = ((NiceResponse) $result).getDocument();
        Iterable $this$forEach$iv$iv$iv = document.select("article[class*='group/item']");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            final Element item = (Element) element$iv$iv;
            Element elementSelectFirst = item.selectFirst("a");
            if (elementSelectFirst == null || (link = elementSelectFirst.attr("href")) == null) {
                movieSearchResponseNewMovieSearchResponse$default = null;
            } else {
                Element elementSelectFirst2 = item.selectFirst("h2");
                if (elementSelectFirst2 == null || (strText = elementSelectFirst2.text()) == null) {
                    Element elementSelectFirst3 = item.selectFirst("img");
                    if (elementSelectFirst3 == null || (strAttr = elementSelectFirst3.attr("alt")) == null) {
                        anonymousClass1 = anonymousClass1;
                        movieSearchResponseNewMovieSearchResponse$default = null;
                    } else {
                        title = StringsKt.replace$default(strAttr, " poster", "", false, 4, (Object) null);
                    }
                } else {
                    title = strText;
                }
                anonymousClass1 = anonymousClass1;
                boolean isMovie = StringsKt.contains$default(link, "/filme/", false, 2, (Object) null);
                TvType type = isMovie ? TvType.Movie : TvType.TvSeries;
                String backdrop = isMovie ? overFlix.backdropUrl(item) : null;
                String data = overFlix.buildData(link, backdrop);
                movieSearchResponseNewMovieSearchResponse$default = MainAPIKt.newMovieSearchResponse$default(overFlix, title, data, type, false, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda2
                    public final Object invoke(Object obj) {
                        return OverFlix.getMainPage$lambda$2$lambda$1(this.f$0, item, (MovieSearchResponse) obj);
                    }
                }, 8, (Object) null);
            }
            if (movieSearchResponseNewMovieSearchResponse$default != null) {
                destination$iv$iv.add(movieSearchResponseNewMovieSearchResponse$default);
            }
            anonymousClass1 = anonymousClass1;
        }
        List items = (List) destination$iv$iv;
        return MainAPIKt.newHomePageResponse$default(request2.getName(), items, (Boolean) null, 4, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getMainPage$lambda$2$lambda$1(OverFlix this$0, Element $item, MovieSearchResponse $this$newMovieSearchResponse) {
        $this$newMovieSearchResponse.setPosterUrl(this$0.posterUrl($item));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00fc A[PHI: r14
      0x00fc: PHI (r14v10 'title' java.lang.String) = (r14v8 'title' java.lang.String), (r14v11 'title' java.lang.String) binds: [B:33:0x00f6, B:28:0x00e4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:37:0x0110  */
    /* JADX WARN: Code duplicated, block: B:38:0x0113  */
    /* JADX WARN: Code duplicated, block: B:41:0x0119  */
    /* JADX WARN: Code duplicated, block: B:42:0x011e  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        C00011 c00011;
        final OverFlix overFlix;
        MovieSearchResponse movieSearchResponseNewMovieSearchResponse$default;
        String link;
        String title;
        boolean isMovie;
        TvType tvType;
        String strBackdropUrl;
        if (continuation instanceof C00011) {
            c00011 = (C00011) continuation;
            if ((c00011.label & Integer.MIN_VALUE) != 0) {
                c00011.label -= Integer.MIN_VALUE;
            } else {
                c00011 = new C00011(continuation);
            }
        } else {
            c00011 = new C00011(continuation);
        }
        Object $result = c00011.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00011.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Requests app = MainActivityKt.getApp();
                String str = getMainUrl() + "/pesquisa?s=" + query;
                c00011.L$0 = this;
                c00011.label = 1;
                $result = Requests.get$default(app, str, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00011, 4094, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                overFlix = this;
                break;
                break;
            case 1:
                overFlix = (OverFlix) c00011.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document document = ((NiceResponse) $result).getDocument();
        Iterable $this$forEach$iv$iv$iv = document.select("article.relative.group\\/item");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            final Element item = (Element) element$iv$iv;
            Element elementSelectFirst = item.selectFirst("a");
            if (elementSelectFirst == null || (link = elementSelectFirst.attr("href")) == null) {
                MovieSearchResponse movieSearchResponse = null;
                movieSearchResponseNewMovieSearchResponse$default = movieSearchResponse;
            } else {
                Element elementSelectFirst2 = item.selectFirst("h2");
                if (elementSelectFirst2 == null || (title = elementSelectFirst2.text()) == null) {
                    Element elementSelectFirst3 = item.selectFirst("img");
                    title = elementSelectFirst3 != null ? elementSelectFirst3.attr("alt") : null;
                    if (title == null) {
                        c00011 = c00011;
                        movieSearchResponseNewMovieSearchResponse$default = null;
                    } else {
                        c00011 = c00011;
                        isMovie = StringsKt.contains$default(link, "/filme/", false, 2, (Object) null);
                        if (isMovie) {
                            tvType = TvType.Movie;
                        } else {
                            tvType = TvType.TvSeries;
                        }
                        TvType type = tvType;
                        if (isMovie) {
                            strBackdropUrl = overFlix.backdropUrl(item);
                        } else {
                            strBackdropUrl = null;
                        }
                        String backdrop = strBackdropUrl;
                        String data = overFlix.buildData(link, backdrop);
                        movieSearchResponseNewMovieSearchResponse$default = MainAPIKt.newMovieSearchResponse$default(overFlix, StringsKt.trim(title).toString(), data, type, false, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda1
                            public final Object invoke(Object obj) {
                                return OverFlix.search$lambda$5$lambda$4(this.f$0, item, (MovieSearchResponse) obj);
                            }
                        }, 8, (Object) null);
                    }
                } else {
                    c00011 = c00011;
                    isMovie = StringsKt.contains$default(link, "/filme/", false, 2, (Object) null);
                    if (isMovie) {
                        tvType = TvType.Movie;
                    } else {
                        tvType = TvType.TvSeries;
                    }
                    TvType type2 = tvType;
                    if (isMovie) {
                        strBackdropUrl = overFlix.backdropUrl(item);
                    } else {
                        strBackdropUrl = null;
                    }
                    String backdrop2 = strBackdropUrl;
                    String data2 = overFlix.buildData(link, backdrop2);
                    movieSearchResponseNewMovieSearchResponse$default = MainAPIKt.newMovieSearchResponse$default(overFlix, StringsKt.trim(title).toString(), data2, type2, false, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda1
                        public final Object invoke(Object obj) {
                            return OverFlix.search$lambda$5$lambda$4(this.f$0, item, (MovieSearchResponse) obj);
                        }
                    }, 8, (Object) null);
                }
            }
            if (movieSearchResponseNewMovieSearchResponse$default != null) {
                destination$iv$iv.add(movieSearchResponseNewMovieSearchResponse$default);
            }
            c00011 = c00011;
        }
        return (List) destination$iv$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit search$lambda$5$lambda$4(OverFlix this$0, Element $item, MovieSearchResponse $this$newMovieSearchResponse) {
        Integer intOrNull;
        Object element$iv;
        String strText;
        Element it;
        $this$newMovieSearchResponse.setPosterUrl(this$0.posterUrl($item));
        Iterable $this$firstOrNull$iv = $item.select("div.text-subs span");
        Iterator it2 = $this$firstOrNull$iv.iterator();
        do {
            intOrNull = null;
            if (!it2.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it2.next();
            it = (Element) element$iv;
        } while (!new Regex("\\d+").matches(it.text()));
        Element element = (Element) element$iv;
        if (element != null && (strText = element.text()) != null) {
            intOrNull = StringsKt.toIntOrNull(strText);
        }
        $this$newMovieSearchResponse.setYear(intOrNull);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x03ab  */
    /* JADX WARN: Code duplicated, block: B:105:0x03c0  */
    /* JADX WARN: Code duplicated, block: B:107:0x03c8  */
    /* JADX WARN: Code duplicated, block: B:110:0x03da  */
    /* JADX WARN: Code duplicated, block: B:114:0x03f1  */
    /* JADX WARN: Code duplicated, block: B:120:0x0414  */
    /* JADX WARN: Code duplicated, block: B:123:0x0421  */
    /* JADX WARN: Code duplicated, block: B:127:0x0435  */
    /* JADX WARN: Code duplicated, block: B:133:0x045c  */
    /* JADX WARN: Code duplicated, block: B:135:0x0471  */
    /* JADX WARN: Code duplicated, block: B:140:0x0485  */
    /* JADX WARN: Code duplicated, block: B:142:0x0488  */
    /* JADX WARN: Code duplicated, block: B:145:0x0494  */
    /* JADX WARN: Code duplicated, block: B:148:0x04a1  */
    /* JADX WARN: Code duplicated, block: B:150:0x04a5  */
    /* JADX WARN: Code duplicated, block: B:152:0x04d0 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:154:0x04d5  */
    /* JADX WARN: Code duplicated, block: B:157:0x04f1  */
    /* JADX WARN: Code duplicated, block: B:159:0x050f  */
    /* JADX WARN: Code duplicated, block: B:160:0x0515  */
    /* JADX WARN: Code duplicated, block: B:164:0x0539  */
    /* JADX WARN: Code duplicated, block: B:166:0x0557  */
    /* JADX WARN: Code duplicated, block: B:167:0x055c  */
    /* JADX WARN: Code duplicated, block: B:169:0x055f  */
    /* JADX WARN: Code duplicated, block: B:172:0x0573  */
    /* JADX WARN: Code duplicated, block: B:180:0x0591 A[ADDED_TO_REGION, REMOVE] */
    /* JADX WARN: Code duplicated, block: B:184:0x05a0  */
    /* JADX WARN: Code duplicated, block: B:185:0x05a7  */
    /* JADX WARN: Code duplicated, block: B:187:0x05ac  */
    /* JADX WARN: Code duplicated, block: B:192:0x0627 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:197:0x0327 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:198:0x040e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:199:0x040c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:200:? A[LOOP:1: B:112:0x03eb->B:200:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:202:0x0453 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:207:0x05ae A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:210:0x01e6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:25:0x0154  */
    /* JADX WARN: Code duplicated, block: B:27:0x0157  */
    /* JADX WARN: Code duplicated, block: B:30:0x015c  */
    /* JADX WARN: Code duplicated, block: B:33:0x017b  */
    /* JADX WARN: Code duplicated, block: B:48:0x01e1  */
    /* JADX WARN: Code duplicated, block: B:51:0x0204  */
    /* JADX WARN: Code duplicated, block: B:53:0x0210  */
    /* JADX WARN: Code duplicated, block: B:54:0x0217  */
    /* JADX WARN: Code duplicated, block: B:57:0x021d  */
    /* JADX WARN: Code duplicated, block: B:59:0x027b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:60:0x027c  */
    /* JADX WARN: Code duplicated, block: B:64:0x02ac  */
    /* JADX WARN: Code duplicated, block: B:66:0x02bd  */
    /* JADX WARN: Code duplicated, block: B:76:0x0313 A[ADDED_TO_REGION, REMOVE] */
    /* JADX WARN: Code duplicated, block: B:79:0x0321  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Code duplicated, block: B:82:0x034a  */
    /* JADX WARN: Code duplicated, block: B:85:0x036a A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:87:0x036e  */
    /* JADX WARN: Code duplicated, block: B:89:0x0379  */
    /* JADX WARN: Code duplicated, block: B:94:0x0390  */
    /* JADX WARN: Code duplicated, block: B:96:0x0398  */
    /* JADX WARN: Code duplicated, block: B:97:0x039e  */
    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public Object load(@NotNull String url, @NotNull Continuation<? super LoadResponse> continuation) {
        C00001 c00001;
        Object obj;
        Object obj2;
        String realUrl;
        OverFlix overFlix;
        String forcedBackdrop;
        Document document;
        boolean isMovie;
        Element elementSelectFirst;
        String string;
        String title;
        Element elementSelectFirst2;
        String str;
        String strAttr;
        String firstEpUrl;
        String firstEpUrl2;
        String str2;
        OverFlix overFlix2;
        String realUrl2;
        Object obj3;
        List actors;
        String title2;
        Document document2;
        String forcedBackdrop2;
        String str3;
        String str4;
        String forcedBackdrop3;
        OverFlix overFlix3;
        String realUrl3;
        String title3;
        boolean isMovie2;
        Document document3;
        String forcedBackdrop4;
        OverFlix overFlix4;
        String realUrl4;
        Collection destination$iv$iv;
        String str5;
        Element elementSelectFirst3;
        ActorData actorData;
        String strText;
        String name;
        String strAttr2;
        String strText2;
        Element elementSelectFirst4;
        Element elementSelectFirst5;
        String poster;
        String strAttr3;
        String strReplace$default;
        Element elementSelectFirst6;
        Element elementSelectFirst7;
        String plot;
        String strText3;
        Iterator it;
        Object obj4;
        Element element;
        Integer year;
        Iterator it2;
        Integer num;
        int i;
        int i2;
        Object element$iv;
        Element it3;
        int i3;
        Integer intOrNull;
        Integer duration;
        String str6;
        Element elementSelectFirst8;
        Double doubleOrNull;
        int $i$f$flatMap;
        Collection destination$iv$iv2;
        int $i$f$flatMapTo;
        Iterator it4;
        Integer intOrNull2;
        final int seasonNumber;
        int $i$f$map;
        Collection destination$iv$iv3;
        Element elementSelectFirst9;
        String strText4;
        Element elementSelectFirst10;
        String str7;
        String strAttr4;
        List groupValues;
        String str8;
        Integer intOrNull3;
        String strText5;
        MatchResult matchResultFind$default;
        List groupValues2;
        String strText6;
        Object element$iv2;
        String strText7;
        String string2;
        Collection destination$iv$iv4;
        Element elementSelectFirst11;
        ActorData actorData2;
        String strText8;
        String name2;
        String image;
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
        String str9 = "img";
        String str10 = "a.text-sm";
        switch (c00001.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                List parts = StringsKt.split$default(url, new String[]{"|BACKDROP|"}, false, 0, 6, (Object) null);
                String realUrl5 = (String) parts.get(0);
                String forcedBackdrop5 = (String) CollectionsKt.getOrNull(parts, 1);
                Requests app = MainActivityKt.getApp();
                c00001.L$0 = this;
                c00001.L$1 = realUrl5;
                c00001.L$2 = forcedBackdrop5;
                c00001.label = 1;
                Object obj5 = Requests.get$default(app, realUrl5, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00001, 4094, (Object) null);
                obj = coroutine_suspended;
                if (obj5 == obj) {
                    return obj;
                }
                obj2 = obj5;
                realUrl = realUrl5;
                overFlix = this;
                forcedBackdrop = forcedBackdrop5;
                document = ((NiceResponse) obj2).getDocument();
                isMovie = StringsKt.contains$default(realUrl, "/filme/", false, 2, (Object) null);
                elementSelectFirst = document.selectFirst("h1.text-3xl, h2.text-3xl");
                if (elementSelectFirst != null || (strText2 = elementSelectFirst.text()) == null) {
                    string = null;
                } else {
                    string = StringsKt.trim(strText2).toString();
                }
                if (string == null) {
                    string = "";
                }
                title = string;
                if (isMovie) {
                    Iterable $this$forEach$iv$iv$iv = document.select("div.group\\/item");
                    destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
                        Element it5 = (Element) element$iv$iv;
                        str10 = str10;
                        elementSelectFirst3 = it5.selectFirst(str10);
                        if (elementSelectFirst3 == null && (strText = elementSelectFirst3.text()) != null && (name = StringsKt.trim(strText).toString()) != null) {
                            str10 = str10;
                            str9 = str9;
                            Element elementSelectFirst12 = it5.selectFirst(str9);
                            if (elementSelectFirst12 != null) {
                                str5 = str5;
                                strAttr2 = elementSelectFirst12.attr(str5);
                            } else {
                                str5 = str5;
                                strAttr2 = null;
                            }
                            String image2 = strAttr2;
                            actorData = new ActorData(new Actor(name, image2), (ActorRole) null, (String) null, (Actor) null, 14, (DefaultConstructorMarker) null);
                        }
                        if (actorData != null) {
                            destination$iv$iv.add(actorData);
                        }
                        str5 = str5;
                    }
                    actors = (List) destination$iv$iv;
                    title2 = title;
                    firstEpUrl2 = str5;
                    forcedBackdrop2 = forcedBackdrop;
                    document2 = document;
                    overFlix2 = overFlix;
                    realUrl2 = realUrl;
                    str2 = "href";
                    obj3 = obj;
                } else {
                    elementSelectFirst2 = document.selectFirst("div[id^=season-] article a");
                    if (elementSelectFirst2 != null) {
                        str = r13;
                        strAttr = elementSelectFirst2.attr(str);
                    } else {
                        str = r13;
                        strAttr = null;
                    }
                    firstEpUrl = strAttr;
                    if (firstEpUrl != null) {
                        Requests app2 = MainActivityKt.getApp();
                        Map<String, String> map = UA_HEADERS;
                        c00001.L$0 = overFlix;
                        c00001.L$1 = realUrl;
                        c00001.L$2 = forcedBackdrop;
                        c00001.L$3 = document;
                        c00001.L$4 = title;
                        c00001.Z$0 = isMovie;
                        c00001.label = 2;
                        str3 = "div.group\\/item";
                        str4 = "src";
                        str2 = str;
                        forcedBackdrop3 = forcedBackdrop;
                        overFlix3 = overFlix;
                        realUrl3 = realUrl;
                        $result = Requests.get$default(app2, firstEpUrl, map, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00001, 4092, (Object) null);
                        obj3 = obj;
                        if ($result == obj3) {
                            return obj3;
                        }
                        title3 = title;
                        isMovie2 = isMovie;
                        document3 = document;
                        forcedBackdrop4 = forcedBackdrop3;
                        overFlix4 = overFlix3;
                        realUrl4 = realUrl3;
                        Document epDoc = ((NiceResponse) $result).getDocument();
                        Iterable $this$forEach$iv$iv$iv2 = epDoc.select(str3);
                        destination$iv$iv4 = new ArrayList();
                        for (Object element$iv$iv2 : $this$forEach$iv$iv$iv2) {
                            Element it6 = (Element) element$iv$iv2;
                            String str11 = str10;
                            elementSelectFirst11 = it6.selectFirst(str11);
                            if (elementSelectFirst11 == null && (strText8 = elementSelectFirst11.text()) != null && (name2 = StringsKt.trim(strText8).toString()) != null) {
                                str11 = str11;
                                isMovie2 = isMovie2;
                                String str12 = str9;
                                Element elementSelectFirst13 = it6.selectFirst(str12);
                                if (elementSelectFirst13 != null) {
                                    str4 = str4;
                                    image = elementSelectFirst13.attr(str4);
                                } else {
                                    str4 = str4;
                                    image = null;
                                }
                                str9 = str12;
                                title3 = title3;
                                actorData2 = new ActorData(new Actor(name2, image), (ActorRole) null, (String) null, (Actor) null, 14, (DefaultConstructorMarker) null);
                            }
                            if (actorData2 != null) {
                                destination$iv$iv4.add(actorData2);
                            }
                            str4 = str4;
                            isMovie2 = isMovie2;
                            title3 = title3;
                            str10 = str11;
                        }
                        title2 = title3;
                        firstEpUrl2 = str4;
                        realUrl2 = realUrl4;
                        overFlix2 = overFlix4;
                        actors = (List) destination$iv$iv4;
                        document2 = document3;
                        forcedBackdrop2 = forcedBackdrop4;
                        isMovie = isMovie2;
                    } else {
                        firstEpUrl2 = "src";
                        str2 = str;
                        overFlix2 = overFlix;
                        realUrl2 = realUrl;
                        obj3 = obj;
                        actors = CollectionsKt.emptyList();
                        title2 = title;
                        document2 = document;
                        forcedBackdrop2 = forcedBackdrop;
                    }
                }
                if (isMovie || forcedBackdrop2 == null) {
                    elementSelectFirst4 = document2.selectFirst("img[src*=w1280]");
                    if (elementSelectFirst4 != null || (strAttr3 = elementSelectFirst4.attr(firstEpUrl2)) == null || (strReplace$default = StringsKt.replace$default(strAttr3, "w1280", "original", false, 4, (Object) null)) == null) {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    } else {
                        poster = strReplace$default;
                    }
                } else {
                    poster = forcedBackdrop2;
                }
                elementSelectFirst6 = document2.selectFirst("div.text-subs.md\\:text-lg");
                if (elementSelectFirst6 != null || (strText7 = elementSelectFirst6.text()) == null || (string2 = StringsKt.trim(strText7).toString()) == null) {
                    elementSelectFirst7 = document2.selectFirst("div.text-subs");
                    if (elementSelectFirst7 != null || (strText3 = elementSelectFirst7.text()) == null) {
                        plot = null;
                    } else {
                        plot = StringsKt.trim(strText3).toString();
                    }
                } else {
                    plot = string2;
                }
                Iterable $this$firstOrNull$iv = document2.select("span");
                it = $this$firstOrNull$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        element$iv2 = it.next();
                        if (new Regex("\\d{4}").matches(((Element) element$iv2).text())) {
                            obj4 = element$iv2;
                        }
                    } else {
                        obj4 = null;
                    }
                }
                element = (Element) obj4;
                if (element != null || (strText6 = element.text()) == null) {
                    year = null;
                } else {
                    year = StringsKt.toIntOrNull(strText6);
                }
                Iterable $this$firstOrNull$iv2 = document2.select("span");
                it2 = $this$firstOrNull$iv2.iterator();
                do {
                    if (it2.hasNext()) {
                        element$iv = it2.next();
                        num = null;
                        i = 2;
                        i2 = 0;
                    } else {
                        num = null;
                        i = 2;
                        i2 = 0;
                        element$iv = null;
                    }
                    it3 = (Element) element$iv;
                    if (it3 != null) {
                        matchResultFind$default = Regex.find$default(new Regex("(\\d+)"), it3.text(), i2, i, num);
                        if (matchResultFind$default != null || (groupValues2 = matchResultFind$default.getGroupValues()) == null) {
                            i3 = 1;
                        } else {
                            i3 = 1;
                            String str13 = (String) groupValues2.get(1);
                            if (str13 != null) {
                                intOrNull = StringsKt.toIntOrNull(str13);
                            }
                        }
                        intOrNull = num;
                    } else {
                        i3 = 1;
                        intOrNull = num;
                    }
                    duration = intOrNull;
                    str6 = "span.text-main";
                    elementSelectFirst8 = document2.selectFirst("span.text-main");
                    if (elementSelectFirst8 != null || (strText5 = elementSelectFirst8.text()) == null) {
                        doubleOrNull = num;
                    } else {
                        doubleOrNull = StringsKt.toDoubleOrNull(strText5);
                    }
                    if (isMovie) {
                        TvType tvType = TvType.Movie;
                        AnonymousClass2 anonymousClass2 = new AnonymousClass2(poster, plot, year, duration, doubleOrNull, actors, null);
                        c00001.L$0 = num;
                        c00001.L$1 = num;
                        c00001.L$2 = num;
                        c00001.L$3 = num;
                        c00001.L$4 = num;
                        c00001.label = 3;
                        $result = MainAPIKt.newMovieLoadResponse(overFlix2, title2, realUrl2, tvType, realUrl2, anonymousClass2, c00001);
                        if ($result == obj3) {
                            return obj3;
                        }
                        return (LoadResponse) $result;
                    }
                    Iterable $this$flatMapTo$iv$iv = document2.select("div[id^=season-]");
                    $i$f$flatMap = 0;
                    destination$iv$iv2 = new ArrayList();
                    $i$f$flatMapTo = 0;
                    it4 = $this$flatMapTo$iv$iv.iterator();
                    while (it4.hasNext()) {
                        Object element$iv$iv3 = it4.next();
                        Element seasonEl = (Element) element$iv$iv3;
                        intOrNull2 = StringsKt.toIntOrNull(StringsKt.removePrefix(seasonEl.attr("id"), "season-"));
                        if (intOrNull2 != null) {
                            seasonNumber = intOrNull2.intValue() + i3;
                        } else {
                            seasonNumber = 1;
                        }
                        Iterable $this$map$iv = seasonEl.select("article");
                        $i$f$map = 0;
                        destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        for (Object item$iv$iv : $this$map$iv) {
                            int $i$f$map2 = $i$f$map;
                            final Element ep = (Element) item$iv$iv;
                            int $i$f$flatMap2 = $i$f$flatMap;
                            Regex regex = new Regex("E(\\d+)");
                            elementSelectFirst9 = ep.selectFirst(str6);
                            if (elementSelectFirst9 != null) {
                                strText4 = elementSelectFirst9.text();
                            } else {
                                strText4 = null;
                            }
                            if (strText4 == null) {
                                strText4 = "";
                            }
                            String str14 = str6;
                            int $i$f$flatMapTo2 = $i$f$flatMapTo;
                            Iterator it7 = it4;
                            MatchResult matchResultFind$default2 = Regex.find$default(regex, strText4, 0, 2, (Object) null);
                            final int epNum = (matchResultFind$default2 != null || (groupValues = matchResultFind$default2.getGroupValues()) == null || (str8 = (String) groupValues.get(1)) == null || (intOrNull3 = StringsKt.toIntOrNull(str8)) == null) ? 1 : intOrNull3.intValue();
                            OverFlix overFlix5 = overFlix2;
                            elementSelectFirst10 = ep.selectFirst("a");
                            if (elementSelectFirst10 != null) {
                                str7 = str2;
                                strAttr4 = elementSelectFirst10.attr(str7);
                            } else {
                                str7 = str2;
                                strAttr4 = null;
                            }
                            if (strAttr4 == null) {
                                strAttr4 = "";
                            }
                            destination$iv$iv3.add(MainAPIKt.newEpisode(overFlix5, strAttr4, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda0
                                public final Object invoke(Object obj6) {
                                    return OverFlix.load$lambda$13$lambda$12$lambda$11(ep, seasonNumber, epNum, (Episode) obj6);
                                }
                            }));
                            $i$f$map = $i$f$map2;
                            str2 = str7;
                            $i$f$flatMap = $i$f$flatMap2;
                            str6 = str14;
                            $i$f$flatMapTo = $i$f$flatMapTo2;
                            it4 = it7;
                        }
                        Iterable list$iv$iv = (List) destination$iv$iv3;
                        CollectionsKt.addAll(destination$iv$iv2, list$iv$iv);
                        $i$f$flatMap = $i$f$flatMap;
                        i3 = 1;
                    }
                    List episodes = (List) destination$iv$iv2;
                    TvType tvType2 = TvType.TvSeries;
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3(poster, plot, year, duration, doubleOrNull, actors, null);
                    c00001.L$0 = null;
                    c00001.L$1 = null;
                    c00001.L$2 = null;
                    c00001.L$3 = null;
                    c00001.L$4 = null;
                    c00001.label = 4;
                    $result = MainAPIKt.newTvSeriesLoadResponse(overFlix2, title2, realUrl2, tvType2, episodes, anonymousClass3, c00001);
                    if ($result == obj3) {
                        return obj3;
                    }
                    return (LoadResponse) $result;
                } while (!StringsKt.contains$default(((Element) element$iv).text(), "minutos", false, 2, (Object) null));
                it3 = (Element) element$iv;
                if (it3 != null) {
                    matchResultFind$default = Regex.find$default(new Regex("(\\d+)"), it3.text(), i2, i, num);
                    if (matchResultFind$default != null) {
                        i3 = 1;
                        intOrNull = num;
                    } else {
                        i3 = 1;
                        intOrNull = num;
                    }
                } else {
                    i3 = 1;
                    intOrNull = num;
                }
                duration = intOrNull;
                str6 = "span.text-main";
                elementSelectFirst8 = document2.selectFirst("span.text-main");
                if (elementSelectFirst8 != null) {
                    doubleOrNull = num;
                } else {
                    doubleOrNull = num;
                }
                if (isMovie) {
                    TvType tvType3 = TvType.Movie;
                    AnonymousClass2 anonymousClass4 = new AnonymousClass2(poster, plot, year, duration, doubleOrNull, actors, null);
                    c00001.L$0 = num;
                    c00001.L$1 = num;
                    c00001.L$2 = num;
                    c00001.L$3 = num;
                    c00001.L$4 = num;
                    c00001.label = 3;
                    $result = MainAPIKt.newMovieLoadResponse(overFlix2, title2, realUrl2, tvType3, realUrl2, anonymousClass4, c00001);
                    if ($result == obj3) {
                        return obj3;
                    }
                    return (LoadResponse) $result;
                }
                Iterable $this$flatMapTo$iv$iv2 = document2.select("div[id^=season-]");
                $i$f$flatMap = 0;
                destination$iv$iv2 = new ArrayList();
                $i$f$flatMapTo = 0;
                it4 = $this$flatMapTo$iv$iv2.iterator();
                while (it4.hasNext()) {
                    Object element$iv$iv4 = it4.next();
                    Element seasonEl2 = (Element) element$iv$iv4;
                    intOrNull2 = StringsKt.toIntOrNull(StringsKt.removePrefix(seasonEl2.attr("id"), "season-"));
                    if (intOrNull2 != null) {
                        seasonNumber = intOrNull2.intValue() + i3;
                    } else {
                        seasonNumber = 1;
                    }
                    Iterable $this$map$iv2 = seasonEl2.select("article");
                    $i$f$map = 0;
                    destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                    while (r21.hasNext()) {
                        int $i$f$map3 = $i$f$map;
                        final Element ep2 = (Element) item$iv$iv;
                        int $i$f$flatMap3 = $i$f$flatMap;
                        Regex regex2 = new Regex("E(\\d+)");
                        elementSelectFirst9 = ep2.selectFirst(str6);
                        if (elementSelectFirst9 != null) {
                            strText4 = elementSelectFirst9.text();
                        } else {
                            strText4 = null;
                        }
                        if (strText4 == null) {
                            strText4 = "";
                        }
                        String str15 = str6;
                        int $i$f$flatMapTo3 = $i$f$flatMapTo;
                        Iterator it8 = it4;
                        MatchResult matchResultFind$default3 = Regex.find$default(regex2, strText4, 0, 2, (Object) null);
                        if (matchResultFind$default3 != null) {
                        }
                        OverFlix overFlix6 = overFlix2;
                        elementSelectFirst10 = ep2.selectFirst("a");
                        if (elementSelectFirst10 != null) {
                            str7 = str2;
                            strAttr4 = elementSelectFirst10.attr(str7);
                        } else {
                            str7 = str2;
                            strAttr4 = null;
                        }
                        if (strAttr4 == null) {
                            strAttr4 = "";
                        }
                        destination$iv$iv3.add(MainAPIKt.newEpisode(overFlix6, strAttr4, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda0
                            public final Object invoke(Object obj6) {
                                return OverFlix.load$lambda$13$lambda$12$lambda$11(ep2, seasonNumber, epNum, (Episode) obj6);
                            }
                        }));
                        $i$f$map = $i$f$map3;
                        str2 = str7;
                        $i$f$flatMap = $i$f$flatMap3;
                        str6 = str15;
                        $i$f$flatMapTo = $i$f$flatMapTo3;
                        it4 = it8;
                    }
                    Iterable list$iv$iv2 = (List) destination$iv$iv3;
                    CollectionsKt.addAll(destination$iv$iv2, list$iv$iv2);
                    $i$f$flatMap = $i$f$flatMap;
                    i3 = 1;
                }
                List episodes2 = (List) destination$iv$iv2;
                TvType tvType4 = TvType.TvSeries;
                AnonymousClass3 anonymousClass5 = new AnonymousClass3(poster, plot, year, duration, doubleOrNull, actors, null);
                c00001.L$0 = null;
                c00001.L$1 = null;
                c00001.L$2 = null;
                c00001.L$3 = null;
                c00001.L$4 = null;
                c00001.label = 4;
                $result = MainAPIKt.newTvSeriesLoadResponse(overFlix2, title2, realUrl2, tvType4, episodes2, anonymousClass5, c00001);
                if ($result == obj3) {
                    return obj3;
                }
                return (LoadResponse) $result;
            case 1:
                String forcedBackdrop6 = (String) c00001.L$2;
                String realUrl6 = (String) c00001.L$1;
                OverFlix overFlix7 = (OverFlix) c00001.L$0;
                ResultKt.throwOnFailure($result);
                forcedBackdrop = forcedBackdrop6;
                obj2 = $result;
                realUrl = realUrl6;
                obj = coroutine_suspended;
                overFlix = overFlix7;
                document = ((NiceResponse) obj2).getDocument();
                isMovie = StringsKt.contains$default(realUrl, "/filme/", false, 2, (Object) null);
                elementSelectFirst = document.selectFirst("h1.text-3xl, h2.text-3xl");
                if (elementSelectFirst != null) {
                    string = null;
                } else {
                    string = null;
                }
                if (string == null) {
                    string = "";
                }
                title = string;
                if (isMovie) {
                    Iterable $this$forEach$iv$iv$iv3 = document.select("div.group\\/item");
                    destination$iv$iv = new ArrayList();
                    while (r18.hasNext()) {
                        Element it9 = (Element) element$iv$iv;
                        str10 = str10;
                        elementSelectFirst3 = it9.selectFirst(str10);
                        actorData = elementSelectFirst3 == null ? null : null;
                        if (actorData != null) {
                            destination$iv$iv.add(actorData);
                        }
                        str5 = str5;
                    }
                    actors = (List) destination$iv$iv;
                    title2 = title;
                    firstEpUrl2 = str5;
                    forcedBackdrop2 = forcedBackdrop;
                    document2 = document;
                    overFlix2 = overFlix;
                    realUrl2 = realUrl;
                    str2 = "href";
                    obj3 = obj;
                } else {
                    elementSelectFirst2 = document.selectFirst("div[id^=season-] article a");
                    if (elementSelectFirst2 != null) {
                        str = r13;
                        strAttr = elementSelectFirst2.attr(str);
                    } else {
                        str = r13;
                        strAttr = null;
                    }
                    firstEpUrl = strAttr;
                    if (firstEpUrl != null) {
                        Requests app3 = MainActivityKt.getApp();
                        Map<String, String> map2 = UA_HEADERS;
                        c00001.L$0 = overFlix;
                        c00001.L$1 = realUrl;
                        c00001.L$2 = forcedBackdrop;
                        c00001.L$3 = document;
                        c00001.L$4 = title;
                        c00001.Z$0 = isMovie;
                        c00001.label = 2;
                        str3 = "div.group\\/item";
                        str4 = "src";
                        str2 = str;
                        forcedBackdrop3 = forcedBackdrop;
                        overFlix3 = overFlix;
                        realUrl3 = realUrl;
                        $result = Requests.get$default(app3, firstEpUrl, map2, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00001, 4092, (Object) null);
                        obj3 = obj;
                        if ($result == obj3) {
                            return obj3;
                        }
                        title3 = title;
                        isMovie2 = isMovie;
                        document3 = document;
                        forcedBackdrop4 = forcedBackdrop3;
                        overFlix4 = overFlix3;
                        realUrl4 = realUrl3;
                        Document epDoc2 = ((NiceResponse) $result).getDocument();
                        Iterable $this$forEach$iv$iv$iv4 = epDoc2.select(str3);
                        destination$iv$iv4 = new ArrayList();
                        while (r14.hasNext()) {
                            Element it10 = (Element) element$iv$iv2;
                            String str16 = str10;
                            elementSelectFirst11 = it10.selectFirst(str16);
                            actorData2 = elementSelectFirst11 == null ? null : null;
                            if (actorData2 != null) {
                                destination$iv$iv4.add(actorData2);
                            }
                            str4 = str4;
                            isMovie2 = isMovie2;
                            title3 = title3;
                            str10 = str16;
                        }
                        title2 = title3;
                        firstEpUrl2 = str4;
                        realUrl2 = realUrl4;
                        overFlix2 = overFlix4;
                        actors = (List) destination$iv$iv4;
                        document2 = document3;
                        forcedBackdrop2 = forcedBackdrop4;
                        isMovie = isMovie2;
                    } else {
                        firstEpUrl2 = "src";
                        str2 = str;
                        overFlix2 = overFlix;
                        realUrl2 = realUrl;
                        obj3 = obj;
                        actors = CollectionsKt.emptyList();
                        title2 = title;
                        document2 = document;
                        forcedBackdrop2 = forcedBackdrop;
                    }
                }
                if (isMovie) {
                    elementSelectFirst4 = document2.selectFirst("img[src*=w1280]");
                    if (elementSelectFirst4 != null) {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    } else {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    }
                } else {
                    elementSelectFirst4 = document2.selectFirst("img[src*=w1280]");
                    if (elementSelectFirst4 != null) {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    } else {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    }
                }
                elementSelectFirst6 = document2.selectFirst("div.text-subs.md\\:text-lg");
                if (elementSelectFirst6 != null) {
                    elementSelectFirst7 = document2.selectFirst("div.text-subs");
                    if (elementSelectFirst7 != null) {
                        plot = null;
                    } else {
                        plot = null;
                    }
                } else {
                    elementSelectFirst7 = document2.selectFirst("div.text-subs");
                    if (elementSelectFirst7 != null) {
                        plot = null;
                    } else {
                        plot = null;
                    }
                }
                Iterable $this$firstOrNull$iv3 = document2.select("span");
                it = $this$firstOrNull$iv3.iterator();
                while (true) {
                    if (it.hasNext()) {
                        element$iv2 = it.next();
                        if (new Regex("\\d{4}").matches(((Element) element$iv2).text())) {
                            obj4 = element$iv2;
                        }
                    } else {
                        obj4 = null;
                    }
                }
                element = (Element) obj4;
                if (element != null) {
                    year = null;
                } else {
                    year = null;
                }
                Iterable $this$firstOrNull$iv4 = document2.select("span");
                it2 = $this$firstOrNull$iv4.iterator();
                do {
                    if (it2.hasNext()) {
                        element$iv = it2.next();
                        num = null;
                        i = 2;
                        i2 = 0;
                    } else {
                        num = null;
                        i = 2;
                        i2 = 0;
                        element$iv = null;
                    }
                    it3 = (Element) element$iv;
                    if (it3 != null) {
                        matchResultFind$default = Regex.find$default(new Regex("(\\d+)"), it3.text(), i2, i, num);
                        if (matchResultFind$default != null) {
                            i3 = 1;
                            intOrNull = num;
                        } else {
                            i3 = 1;
                            intOrNull = num;
                        }
                    } else {
                        i3 = 1;
                        intOrNull = num;
                    }
                    duration = intOrNull;
                    str6 = "span.text-main";
                    elementSelectFirst8 = document2.selectFirst("span.text-main");
                    if (elementSelectFirst8 != null) {
                        doubleOrNull = num;
                    } else {
                        doubleOrNull = num;
                    }
                    if (isMovie) {
                        TvType tvType5 = TvType.Movie;
                        AnonymousClass2 anonymousClass6 = new AnonymousClass2(poster, plot, year, duration, doubleOrNull, actors, null);
                        c00001.L$0 = num;
                        c00001.L$1 = num;
                        c00001.L$2 = num;
                        c00001.L$3 = num;
                        c00001.L$4 = num;
                        c00001.label = 3;
                        $result = MainAPIKt.newMovieLoadResponse(overFlix2, title2, realUrl2, tvType5, realUrl2, anonymousClass6, c00001);
                        if ($result == obj3) {
                            return obj3;
                        }
                        return (LoadResponse) $result;
                    }
                    Iterable $this$flatMapTo$iv$iv3 = document2.select("div[id^=season-]");
                    $i$f$flatMap = 0;
                    destination$iv$iv2 = new ArrayList();
                    $i$f$flatMapTo = 0;
                    it4 = $this$flatMapTo$iv$iv3.iterator();
                    while (it4.hasNext()) {
                        Object element$iv$iv5 = it4.next();
                        Element seasonEl3 = (Element) element$iv$iv5;
                        intOrNull2 = StringsKt.toIntOrNull(StringsKt.removePrefix(seasonEl3.attr("id"), "season-"));
                        if (intOrNull2 != null) {
                            seasonNumber = intOrNull2.intValue() + i3;
                        } else {
                            seasonNumber = 1;
                        }
                        Iterable $this$map$iv3 = seasonEl3.select("article");
                        $i$f$map = 0;
                        destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
                        while (r21.hasNext()) {
                            int $i$f$map4 = $i$f$map;
                            final Element ep3 = (Element) item$iv$iv;
                            int $i$f$flatMap4 = $i$f$flatMap;
                            Regex regex3 = new Regex("E(\\d+)");
                            elementSelectFirst9 = ep3.selectFirst(str6);
                            if (elementSelectFirst9 != null) {
                                strText4 = elementSelectFirst9.text();
                            } else {
                                strText4 = null;
                            }
                            if (strText4 == null) {
                                strText4 = "";
                            }
                            String str17 = str6;
                            int $i$f$flatMapTo4 = $i$f$flatMapTo;
                            Iterator it11 = it4;
                            MatchResult matchResultFind$default4 = Regex.find$default(regex3, strText4, 0, 2, (Object) null);
                            if (matchResultFind$default4 != null) {
                            }
                            OverFlix overFlix8 = overFlix2;
                            elementSelectFirst10 = ep3.selectFirst("a");
                            if (elementSelectFirst10 != null) {
                                str7 = str2;
                                strAttr4 = elementSelectFirst10.attr(str7);
                            } else {
                                str7 = str2;
                                strAttr4 = null;
                            }
                            if (strAttr4 == null) {
                                strAttr4 = "";
                            }
                            destination$iv$iv3.add(MainAPIKt.newEpisode(overFlix8, strAttr4, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda0
                                public final Object invoke(Object obj6) {
                                    return OverFlix.load$lambda$13$lambda$12$lambda$11(ep3, seasonNumber, epNum, (Episode) obj6);
                                }
                            }));
                            $i$f$map = $i$f$map4;
                            str2 = str7;
                            $i$f$flatMap = $i$f$flatMap4;
                            str6 = str17;
                            $i$f$flatMapTo = $i$f$flatMapTo4;
                            it4 = it11;
                        }
                        Iterable list$iv$iv3 = (List) destination$iv$iv3;
                        CollectionsKt.addAll(destination$iv$iv2, list$iv$iv3);
                        $i$f$flatMap = $i$f$flatMap;
                        i3 = 1;
                    }
                    List episodes3 = (List) destination$iv$iv2;
                    TvType tvType6 = TvType.TvSeries;
                    AnonymousClass3 anonymousClass7 = new AnonymousClass3(poster, plot, year, duration, doubleOrNull, actors, null);
                    c00001.L$0 = null;
                    c00001.L$1 = null;
                    c00001.L$2 = null;
                    c00001.L$3 = null;
                    c00001.L$4 = null;
                    c00001.label = 4;
                    $result = MainAPIKt.newTvSeriesLoadResponse(overFlix2, title2, realUrl2, tvType6, episodes3, anonymousClass7, c00001);
                    if ($result == obj3) {
                        return obj3;
                    }
                    return (LoadResponse) $result;
                } while (!StringsKt.contains$default(((Element) element$iv).text(), "minutos", false, 2, (Object) null));
                it3 = (Element) element$iv;
                if (it3 != null) {
                    matchResultFind$default = Regex.find$default(new Regex("(\\d+)"), it3.text(), i2, i, num);
                    if (matchResultFind$default != null) {
                        i3 = 1;
                        intOrNull = num;
                    } else {
                        i3 = 1;
                        intOrNull = num;
                    }
                } else {
                    i3 = 1;
                    intOrNull = num;
                }
                duration = intOrNull;
                str6 = "span.text-main";
                elementSelectFirst8 = document2.selectFirst("span.text-main");
                if (elementSelectFirst8 != null) {
                    doubleOrNull = num;
                } else {
                    doubleOrNull = num;
                }
                if (isMovie) {
                    TvType tvType7 = TvType.Movie;
                    AnonymousClass2 anonymousClass8 = new AnonymousClass2(poster, plot, year, duration, doubleOrNull, actors, null);
                    c00001.L$0 = num;
                    c00001.L$1 = num;
                    c00001.L$2 = num;
                    c00001.L$3 = num;
                    c00001.L$4 = num;
                    c00001.label = 3;
                    $result = MainAPIKt.newMovieLoadResponse(overFlix2, title2, realUrl2, tvType7, realUrl2, anonymousClass8, c00001);
                    if ($result == obj3) {
                        return obj3;
                    }
                    return (LoadResponse) $result;
                }
                Iterable $this$flatMapTo$iv$iv4 = document2.select("div[id^=season-]");
                $i$f$flatMap = 0;
                destination$iv$iv2 = new ArrayList();
                $i$f$flatMapTo = 0;
                it4 = $this$flatMapTo$iv$iv4.iterator();
                while (it4.hasNext()) {
                    Object element$iv$iv6 = it4.next();
                    Element seasonEl4 = (Element) element$iv$iv6;
                    intOrNull2 = StringsKt.toIntOrNull(StringsKt.removePrefix(seasonEl4.attr("id"), "season-"));
                    if (intOrNull2 != null) {
                        seasonNumber = intOrNull2.intValue() + i3;
                    } else {
                        seasonNumber = 1;
                    }
                    Iterable $this$map$iv4 = seasonEl4.select("article");
                    $i$f$map = 0;
                    destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv4, 10));
                    while (r21.hasNext()) {
                        int $i$f$map5 = $i$f$map;
                        final Element ep4 = (Element) item$iv$iv;
                        int $i$f$flatMap5 = $i$f$flatMap;
                        Regex regex4 = new Regex("E(\\d+)");
                        elementSelectFirst9 = ep4.selectFirst(str6);
                        if (elementSelectFirst9 != null) {
                            strText4 = elementSelectFirst9.text();
                        } else {
                            strText4 = null;
                        }
                        if (strText4 == null) {
                            strText4 = "";
                        }
                        String str18 = str6;
                        int $i$f$flatMapTo5 = $i$f$flatMapTo;
                        Iterator it12 = it4;
                        MatchResult matchResultFind$default5 = Regex.find$default(regex4, strText4, 0, 2, (Object) null);
                        if (matchResultFind$default5 != null) {
                        }
                        OverFlix overFlix9 = overFlix2;
                        elementSelectFirst10 = ep4.selectFirst("a");
                        if (elementSelectFirst10 != null) {
                            str7 = str2;
                            strAttr4 = elementSelectFirst10.attr(str7);
                        } else {
                            str7 = str2;
                            strAttr4 = null;
                        }
                        if (strAttr4 == null) {
                            strAttr4 = "";
                        }
                        destination$iv$iv3.add(MainAPIKt.newEpisode(overFlix9, strAttr4, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda0
                            public final Object invoke(Object obj6) {
                                return OverFlix.load$lambda$13$lambda$12$lambda$11(ep4, seasonNumber, epNum, (Episode) obj6);
                            }
                        }));
                        $i$f$map = $i$f$map5;
                        str2 = str7;
                        $i$f$flatMap = $i$f$flatMap5;
                        str6 = str18;
                        $i$f$flatMapTo = $i$f$flatMapTo5;
                        it4 = it12;
                    }
                    Iterable list$iv$iv4 = (List) destination$iv$iv3;
                    CollectionsKt.addAll(destination$iv$iv2, list$iv$iv4);
                    $i$f$flatMap = $i$f$flatMap;
                    i3 = 1;
                }
                List episodes4 = (List) destination$iv$iv2;
                TvType tvType8 = TvType.TvSeries;
                AnonymousClass3 anonymousClass9 = new AnonymousClass3(poster, plot, year, duration, doubleOrNull, actors, null);
                c00001.L$0 = null;
                c00001.L$1 = null;
                c00001.L$2 = null;
                c00001.L$3 = null;
                c00001.L$4 = null;
                c00001.label = 4;
                $result = MainAPIKt.newTvSeriesLoadResponse(overFlix2, title2, realUrl2, tvType8, episodes4, anonymousClass9, c00001);
                if ($result == obj3) {
                    return obj3;
                }
                return (LoadResponse) $result;
            case 2:
                isMovie2 = c00001.Z$0;
                title3 = (String) c00001.L$4;
                document3 = (Document) c00001.L$3;
                forcedBackdrop4 = (String) c00001.L$2;
                realUrl4 = (String) c00001.L$1;
                overFlix4 = (OverFlix) c00001.L$0;
                ResultKt.throwOnFailure($result);
                str4 = "src";
                str3 = "div.group\\/item";
                str2 = "href";
                obj3 = coroutine_suspended;
                Document epDoc3 = ((NiceResponse) $result).getDocument();
                Iterable $this$forEach$iv$iv$iv5 = epDoc3.select(str3);
                destination$iv$iv4 = new ArrayList();
                while (r14.hasNext()) {
                    Element it13 = (Element) element$iv$iv2;
                    String str19 = str10;
                    elementSelectFirst11 = it13.selectFirst(str19);
                    if (elementSelectFirst11 == null) {
                    }
                    if (actorData2 != null) {
                        destination$iv$iv4.add(actorData2);
                    }
                    str4 = str4;
                    isMovie2 = isMovie2;
                    title3 = title3;
                    str10 = str19;
                }
                title2 = title3;
                firstEpUrl2 = str4;
                realUrl2 = realUrl4;
                overFlix2 = overFlix4;
                actors = (List) destination$iv$iv4;
                document2 = document3;
                forcedBackdrop2 = forcedBackdrop4;
                isMovie = isMovie2;
                if (isMovie) {
                    elementSelectFirst4 = document2.selectFirst("img[src*=w1280]");
                    if (elementSelectFirst4 != null) {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    } else {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    }
                } else {
                    elementSelectFirst4 = document2.selectFirst("img[src*=w1280]");
                    if (elementSelectFirst4 != null) {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    } else {
                        elementSelectFirst5 = document2.selectFirst("article img");
                        if (elementSelectFirst5 != null) {
                            poster = elementSelectFirst5.attr(firstEpUrl2);
                        } else {
                            poster = null;
                        }
                    }
                }
                elementSelectFirst6 = document2.selectFirst("div.text-subs.md\\:text-lg");
                if (elementSelectFirst6 != null) {
                    elementSelectFirst7 = document2.selectFirst("div.text-subs");
                    if (elementSelectFirst7 != null) {
                        plot = null;
                    } else {
                        plot = null;
                    }
                } else {
                    elementSelectFirst7 = document2.selectFirst("div.text-subs");
                    if (elementSelectFirst7 != null) {
                        plot = null;
                    } else {
                        plot = null;
                    }
                }
                Iterable $this$firstOrNull$iv5 = document2.select("span");
                it = $this$firstOrNull$iv5.iterator();
                while (true) {
                    if (it.hasNext()) {
                        element$iv2 = it.next();
                        if (new Regex("\\d{4}").matches(((Element) element$iv2).text())) {
                            obj4 = element$iv2;
                        }
                    } else {
                        obj4 = null;
                    }
                }
                element = (Element) obj4;
                if (element != null) {
                    year = null;
                } else {
                    year = null;
                }
                Iterable $this$firstOrNull$iv6 = document2.select("span");
                it2 = $this$firstOrNull$iv6.iterator();
                do {
                    if (it2.hasNext()) {
                        element$iv = it2.next();
                        num = null;
                        i = 2;
                        i2 = 0;
                    } else {
                        num = null;
                        i = 2;
                        i2 = 0;
                        element$iv = null;
                    }
                    it3 = (Element) element$iv;
                    if (it3 != null) {
                        matchResultFind$default = Regex.find$default(new Regex("(\\d+)"), it3.text(), i2, i, num);
                        if (matchResultFind$default != null) {
                            i3 = 1;
                            intOrNull = num;
                        } else {
                            i3 = 1;
                            intOrNull = num;
                        }
                    } else {
                        i3 = 1;
                        intOrNull = num;
                    }
                    duration = intOrNull;
                    str6 = "span.text-main";
                    elementSelectFirst8 = document2.selectFirst("span.text-main");
                    if (elementSelectFirst8 != null) {
                        doubleOrNull = num;
                    } else {
                        doubleOrNull = num;
                    }
                    if (isMovie) {
                        TvType tvType9 = TvType.Movie;
                        AnonymousClass2 anonymousClass10 = new AnonymousClass2(poster, plot, year, duration, doubleOrNull, actors, null);
                        c00001.L$0 = num;
                        c00001.L$1 = num;
                        c00001.L$2 = num;
                        c00001.L$3 = num;
                        c00001.L$4 = num;
                        c00001.label = 3;
                        $result = MainAPIKt.newMovieLoadResponse(overFlix2, title2, realUrl2, tvType9, realUrl2, anonymousClass10, c00001);
                        if ($result == obj3) {
                            return obj3;
                        }
                        return (LoadResponse) $result;
                    }
                    Iterable $this$flatMapTo$iv$iv5 = document2.select("div[id^=season-]");
                    $i$f$flatMap = 0;
                    destination$iv$iv2 = new ArrayList();
                    $i$f$flatMapTo = 0;
                    it4 = $this$flatMapTo$iv$iv5.iterator();
                    while (it4.hasNext()) {
                        Object element$iv$iv7 = it4.next();
                        Element seasonEl5 = (Element) element$iv$iv7;
                        intOrNull2 = StringsKt.toIntOrNull(StringsKt.removePrefix(seasonEl5.attr("id"), "season-"));
                        if (intOrNull2 != null) {
                            seasonNumber = intOrNull2.intValue() + i3;
                        } else {
                            seasonNumber = 1;
                        }
                        Iterable $this$map$iv5 = seasonEl5.select("article");
                        $i$f$map = 0;
                        destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv5, 10));
                        while (r21.hasNext()) {
                            int $i$f$map6 = $i$f$map;
                            final Element ep5 = (Element) item$iv$iv;
                            int $i$f$flatMap6 = $i$f$flatMap;
                            Regex regex5 = new Regex("E(\\d+)");
                            elementSelectFirst9 = ep5.selectFirst(str6);
                            if (elementSelectFirst9 != null) {
                                strText4 = elementSelectFirst9.text();
                            } else {
                                strText4 = null;
                            }
                            if (strText4 == null) {
                                strText4 = "";
                            }
                            String str110 = str6;
                            int $i$f$flatMapTo6 = $i$f$flatMapTo;
                            Iterator it14 = it4;
                            MatchResult matchResultFind$default6 = Regex.find$default(regex5, strText4, 0, 2, (Object) null);
                            if (matchResultFind$default6 != null) {
                            }
                            OverFlix overFlix10 = overFlix2;
                            elementSelectFirst10 = ep5.selectFirst("a");
                            if (elementSelectFirst10 != null) {
                                str7 = str2;
                                strAttr4 = elementSelectFirst10.attr(str7);
                            } else {
                                str7 = str2;
                                strAttr4 = null;
                            }
                            if (strAttr4 == null) {
                                strAttr4 = "";
                            }
                            destination$iv$iv3.add(MainAPIKt.newEpisode(overFlix10, strAttr4, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda0
                                public final Object invoke(Object obj6) {
                                    return OverFlix.load$lambda$13$lambda$12$lambda$11(ep5, seasonNumber, epNum, (Episode) obj6);
                                }
                            }));
                            $i$f$map = $i$f$map6;
                            str2 = str7;
                            $i$f$flatMap = $i$f$flatMap6;
                            str6 = str110;
                            $i$f$flatMapTo = $i$f$flatMapTo6;
                            it4 = it14;
                        }
                        Iterable list$iv$iv5 = (List) destination$iv$iv3;
                        CollectionsKt.addAll(destination$iv$iv2, list$iv$iv5);
                        $i$f$flatMap = $i$f$flatMap;
                        i3 = 1;
                    }
                    List episodes5 = (List) destination$iv$iv2;
                    TvType tvType10 = TvType.TvSeries;
                    AnonymousClass3 anonymousClass11 = new AnonymousClass3(poster, plot, year, duration, doubleOrNull, actors, null);
                    c00001.L$0 = null;
                    c00001.L$1 = null;
                    c00001.L$2 = null;
                    c00001.L$3 = null;
                    c00001.L$4 = null;
                    c00001.label = 4;
                    $result = MainAPIKt.newTvSeriesLoadResponse(overFlix2, title2, realUrl2, tvType10, episodes5, anonymousClass11, c00001);
                    if ($result == obj3) {
                        return obj3;
                    }
                    return (LoadResponse) $result;
                } while (!StringsKt.contains$default(((Element) element$iv).text(), "minutos", false, 2, (Object) null));
                it3 = (Element) element$iv;
                if (it3 != null) {
                    matchResultFind$default = Regex.find$default(new Regex("(\\d+)"), it3.text(), i2, i, num);
                    if (matchResultFind$default != null) {
                        i3 = 1;
                        intOrNull = num;
                    } else {
                        i3 = 1;
                        intOrNull = num;
                    }
                } else {
                    i3 = 1;
                    intOrNull = num;
                }
                duration = intOrNull;
                str6 = "span.text-main";
                elementSelectFirst8 = document2.selectFirst("span.text-main");
                if (elementSelectFirst8 != null) {
                    doubleOrNull = num;
                } else {
                    doubleOrNull = num;
                }
                if (isMovie) {
                    TvType tvType11 = TvType.Movie;
                    AnonymousClass2 anonymousClass12 = new AnonymousClass2(poster, plot, year, duration, doubleOrNull, actors, null);
                    c00001.L$0 = num;
                    c00001.L$1 = num;
                    c00001.L$2 = num;
                    c00001.L$3 = num;
                    c00001.L$4 = num;
                    c00001.label = 3;
                    $result = MainAPIKt.newMovieLoadResponse(overFlix2, title2, realUrl2, tvType11, realUrl2, anonymousClass12, c00001);
                    if ($result == obj3) {
                        return obj3;
                    }
                    return (LoadResponse) $result;
                }
                Iterable $this$flatMapTo$iv$iv6 = document2.select("div[id^=season-]");
                $i$f$flatMap = 0;
                destination$iv$iv2 = new ArrayList();
                $i$f$flatMapTo = 0;
                it4 = $this$flatMapTo$iv$iv6.iterator();
                while (it4.hasNext()) {
                    Object element$iv$iv8 = it4.next();
                    Element seasonEl6 = (Element) element$iv$iv8;
                    intOrNull2 = StringsKt.toIntOrNull(StringsKt.removePrefix(seasonEl6.attr("id"), "season-"));
                    if (intOrNull2 != null) {
                        seasonNumber = intOrNull2.intValue() + i3;
                    } else {
                        seasonNumber = 1;
                    }
                    Iterable $this$map$iv6 = seasonEl6.select("article");
                    $i$f$map = 0;
                    destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv6, 10));
                    while (r21.hasNext()) {
                        int $i$f$map7 = $i$f$map;
                        final Element ep6 = (Element) item$iv$iv;
                        int $i$f$flatMap7 = $i$f$flatMap;
                        Regex regex6 = new Regex("E(\\d+)");
                        elementSelectFirst9 = ep6.selectFirst(str6);
                        if (elementSelectFirst9 != null) {
                            strText4 = elementSelectFirst9.text();
                        } else {
                            strText4 = null;
                        }
                        if (strText4 == null) {
                            strText4 = "";
                        }
                        String str111 = str6;
                        int $i$f$flatMapTo7 = $i$f$flatMapTo;
                        Iterator it15 = it4;
                        MatchResult matchResultFind$default7 = Regex.find$default(regex6, strText4, 0, 2, (Object) null);
                        if (matchResultFind$default7 != null) {
                        }
                        OverFlix overFlix11 = overFlix2;
                        elementSelectFirst10 = ep6.selectFirst("a");
                        if (elementSelectFirst10 != null) {
                            str7 = str2;
                            strAttr4 = elementSelectFirst10.attr(str7);
                        } else {
                            str7 = str2;
                            strAttr4 = null;
                        }
                        if (strAttr4 == null) {
                            strAttr4 = "";
                        }
                        destination$iv$iv3.add(MainAPIKt.newEpisode(overFlix11, strAttr4, new Function1() { // from class: com.OverFlix.OverFlix$$ExternalSyntheticLambda0
                            public final Object invoke(Object obj6) {
                                return OverFlix.load$lambda$13$lambda$12$lambda$11(ep6, seasonNumber, epNum, (Episode) obj6);
                            }
                        }));
                        $i$f$map = $i$f$map7;
                        str2 = str7;
                        $i$f$flatMap = $i$f$flatMap7;
                        str6 = str111;
                        $i$f$flatMapTo = $i$f$flatMapTo7;
                        it4 = it15;
                    }
                    Iterable list$iv$iv6 = (List) destination$iv$iv3;
                    CollectionsKt.addAll(destination$iv$iv2, list$iv$iv6);
                    $i$f$flatMap = $i$f$flatMap;
                    i3 = 1;
                }
                List episodes6 = (List) destination$iv$iv2;
                TvType tvType12 = TvType.TvSeries;
                AnonymousClass3 anonymousClass13 = new AnonymousClass3(poster, plot, year, duration, doubleOrNull, actors, null);
                c00001.L$0 = null;
                c00001.L$1 = null;
                c00001.L$2 = null;
                c00001.L$3 = null;
                c00001.L$4 = null;
                c00001.label = 4;
                $result = MainAPIKt.newTvSeriesLoadResponse(overFlix2, title2, realUrl2, tvType12, episodes6, anonymousClass13, c00001);
                if ($result == obj3) {
                    return obj3;
                }
                return (LoadResponse) $result;
            case 3:
                ResultKt.throwOnFailure($result);
                return (LoadResponse) $result;
            case 4:
                ResultKt.throwOnFailure($result);
                return (LoadResponse) $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlix$load$2, reason: invalid class name */
    /* JADX INFO: compiled from: OverFlix.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/MovieLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlix$load$2", f = "OverFlix.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<ActorData> $actors;
        final /* synthetic */ Integer $duration;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ Double $score;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, String str2, Integer num, Integer num2, Double d, List<ActorData> list, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$poster = str;
            this.$plot = str2;
            this.$year = num;
            this.$duration = num2;
            this.$score = d;
            this.$actors = list;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass2 = new AnonymousClass2(this.$poster, this.$plot, this.$year, this.$duration, this.$score, this.$actors, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                    $this$newMovieLoadResponse.setPosterUrl(this.$poster);
                    $this$newMovieLoadResponse.setPlot(this.$plot);
                    $this$newMovieLoadResponse.setYear(this.$year);
                    $this$newMovieLoadResponse.setDuration(this.$duration);
                    $this$newMovieLoadResponse.setScore(Score.Companion.from10(this.$score));
                    $this$newMovieLoadResponse.setActors(this.$actors);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$13$lambda$12$lambda$11(Element $ep, int $seasonNumber, int $epNum, Episode $this$newEpisode) {
        String strText;
        Element elementSelectFirst = $ep.selectFirst("h2");
        $this$newEpisode.setName(elementSelectFirst != null ? elementSelectFirst.text() : null);
        $this$newEpisode.setSeason(Integer.valueOf($seasonNumber));
        $this$newEpisode.setEpisode(Integer.valueOf($epNum));
        Element elementSelectFirst2 = $ep.selectFirst("div.line-clamp-2.text-xs");
        $this$newEpisode.setDescription((elementSelectFirst2 == null || (strText = elementSelectFirst2.text()) == null) ? null : StringsKt.trim(strText).toString());
        Element elementSelectFirst3 = $ep.selectFirst("img");
        $this$newEpisode.setPosterUrl(elementSelectFirst3 != null ? elementSelectFirst3.attr("src") : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlix$load$3, reason: invalid class name */
    /* JADX INFO: compiled from: OverFlix.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/TvSeriesLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlix$load$3", f = "OverFlix.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function2<TvSeriesLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<ActorData> $actors;
        final /* synthetic */ Integer $duration;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ Double $score;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(String str, String str2, Integer num, Integer num2, Double d, List<ActorData> list, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$poster = str;
            this.$plot = str2;
            this.$year = num;
            this.$duration = num2;
            this.$score = d;
            this.$actors = list;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass3 = new AnonymousClass3(this.$poster, this.$plot, this.$year, this.$duration, this.$score, this.$actors, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
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
                    $this$newTvSeriesLoadResponse.setPosterUrl(this.$poster);
                    $this$newTvSeriesLoadResponse.setPlot(this.$plot);
                    $this$newTvSeriesLoadResponse.setYear(this.$year);
                    $this$newTvSeriesLoadResponse.setDuration(this.$duration);
                    $this$newTvSeriesLoadResponse.setScore(Score.Companion.from10(this.$score));
                    $this$newTvSeriesLoadResponse.setActors(this.$actors);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public Object loadLinks(@NotNull String data, boolean isCasting, @NotNull Function1<? super SubtitleFile, Unit> function1, @NotNull Function1<? super ExtractorLink, Unit> function2, @NotNull Continuation<? super Boolean> continuation) {
        return new OverFlixExtractor().extractLinks(data, function1, function2, continuation);
    }
}
