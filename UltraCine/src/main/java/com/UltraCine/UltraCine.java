package com.UltraCine;

import com.lagradost.cloudstream3.Actor;
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
import com.lagradost.cloudstream3.SearchQuality;
import com.lagradost.cloudstream3.SearchResponse;
import com.lagradost.cloudstream3.TvSeriesLoadResponse;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* JADX INFO: compiled from: UltraCine.kt */
/* JADX INFO: loaded from: /tmp/cs3/UltraCine/classes.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u001e\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0096@¢\u0006\u0002\u0010%J\u001c\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u001c2\u0006\u0010(\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010)J\u000e\u0010*\u001a\u0004\u0018\u00010'*\u00020+H\u0002J\u0018\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010)JF\u0010/\u001a\u00020\u00112\u0006\u00100\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u00112\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u000205032\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u00020503H\u0096@¢\u0006\u0002\u00108J\u0016\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u001c2\u0006\u0010;\u001a\u00020<H\u0002J\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00050\u001c2\u0006\u0010;\u001a\u00020<H\u0002J\"\u0010>\u001a\u0018\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020@\u0012\u0006\u0012\u0004\u0018\u00010A0?\u0018\u00010\u001c*\u00020<H\u0002J\u001e\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020+2\u0006\u0010C\u001a\u00020\u00052\u0006\u0010D\u001a\u00020\u0005H\u0002J\u001e\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020<2\u0006\u0010C\u001a\u00020\u00052\u0006\u0010D\u001a\u00020\u0005H\u0002J\u001b\u0010E\u001a\u0004\u0018\u00010\"*\u00020<2\u0006\u0010C\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010FJ\u0016\u0010G\u001a\u0004\u0018\u00010\u0005*\u00020<2\u0006\u0010C\u001a\u00020\u0005H\u0002J\u0019\u0010H\u001a\u0004\u0018\u00010\"2\b\u0010I\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0002\u0010JR\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u0014\u0010\u0010\u001a\u00020\u0011X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u0011X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006K"}, d2 = {"Lcom/UltraCine/UltraCine;", "Lcom/lagradost/cloudstream3/MainAPI;", "<init>", "()V", "mainUrl", "", "getMainUrl", "()Ljava/lang/String;", "setMainUrl", "(Ljava/lang/String;)V", "name", "getName", "setName", "lang", "getLang", "setLang", "hasMainPage", "", "getHasMainPage", "()Z", "hasDownloadSupport", "getHasDownloadSupport", "supportedTypes", "", "Lcom/lagradost/cloudstream3/TvType;", "getSupportedTypes", "()Ljava/util/Set;", "mainPage", "", "Lcom/lagradost/cloudstream3/MainPageData;", "getMainPage", "()Ljava/util/List;", "Lcom/lagradost/cloudstream3/HomePageResponse;", "page", "", "request", "Lcom/lagradost/cloudstream3/MainPageRequest;", "(ILcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "Lcom/lagradost/cloudstream3/SearchResponse;", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toSearchResult", "Lorg/jsoup/nodes/Element;", "load", "Lcom/lagradost/cloudstream3/LoadResponse;", "url", "loadLinks", "data", "isCasting", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseEpisodes", "Lcom/lagradost/cloudstream3/Episode;", "doc", "Lorg/jsoup/nodes/Document;", "extractEmbedLinks", "selectActors", "Lkotlin/Pair;", "Lcom/lagradost/cloudstream3/Actor;", "Lcom/lagradost/cloudstream3/ActorRole;", "selectPoster", "selector", "replace", "extractInt", "(Lorg/jsoup/nodes/Document;Ljava/lang/String;)Ljava/lang/Integer;", "extractText", "parseDuration", "text", "(Ljava/lang/String;)Ljava/lang/Integer;", "UltraCine_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nUltraCine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UltraCine.kt\ncom/UltraCine/UltraCine\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,219:1\n1611#2,9:220\n1863#2:229\n1864#2:231\n1620#2:232\n1611#2,9:233\n1863#2:242\n1864#2:244\n1620#2:245\n1557#2:246\n1628#2,3:247\n1863#2,2:251\n1863#2:253\n1863#2,2:254\n1864#2:256\n1663#2,8:257\n1611#2,9:265\n1863#2:274\n1864#2:276\n1620#2:277\n1611#2,9:278\n1863#2:287\n1864#2:289\n1620#2:290\n774#2:291\n865#2,2:292\n1557#2:294\n1628#2,3:295\n1#3:230\n1#3:243\n1#3:250\n1#3:275\n1#3:288\n1#3:298\n434#4:299\n507#4,5:300\n*S KotlinDebug\n*F\n+ 1 UltraCine.kt\ncom/UltraCine/UltraCine\n*L\n41#1:220,9\n41#1:229\n41#1:231\n41#1:232\n52#1:233,9\n52#1:242\n52#1:244\n52#1:245\n80#1:246\n80#1:247,3\n138#1:251,2\n156#1:253\n160#1:254,2\n156#1:256\n175#1:257,8\n179#1:265,9\n179#1:274\n179#1:276\n179#1:277\n181#1:278,9\n181#1:287\n181#1:289\n181#1:290\n182#1:291\n182#1:292,2\n186#1:294\n186#1:295,3\n41#1:230\n52#1:243\n179#1:275\n181#1:288\n207#1:299\n207#1:300,5\n*E\n"})
public final class UltraCine extends MainAPI {

    @NotNull
    private String mainUrl = "https://ultracine.org";

    @NotNull
    private String name = "UltraCine";

    @NotNull
    private String lang = "pt-br";
    private final boolean hasMainPage = true;
    private final boolean hasDownloadSupport = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Movie, TvType.TvSeries});

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to(getMainUrl() + "/category/lancamentos/", "Lançamentos"), TuplesKt.to(getMainUrl() + "/category/acao/", "Ação"), TuplesKt.to(getMainUrl() + "/category/animacao/", "Animação"), TuplesKt.to(getMainUrl() + "/category/comedia/", "Comédia"), TuplesKt.to(getMainUrl() + "/category/crime/", "Crime"), TuplesKt.to(getMainUrl() + "/category/documentario/", "Documentário"), TuplesKt.to(getMainUrl() + "/category/drama/", "Drama"), TuplesKt.to(getMainUrl() + "/category/familia/", "Família"), TuplesKt.to(getMainUrl() + "/category/fantasia/", "Fantasia"), TuplesKt.to(getMainUrl() + "/category/ficcao-cientifica/", "Ficção Científica"), TuplesKt.to(getMainUrl() + "/category/guerra/", "Guerra"), TuplesKt.to(getMainUrl() + "/category/kids/", "Kids"), TuplesKt.to(getMainUrl() + "/category/misterio/", "Mistério"), TuplesKt.to(getMainUrl() + "/category/romance/", "Romance"), TuplesKt.to(getMainUrl() + "/category/terror/", "Terror"), TuplesKt.to(getMainUrl() + "/category/thriller/", "Thriller")});

    /* JADX INFO: renamed from: com.UltraCine.UltraCine$getMainPage$1, reason: invalid class name */
    /* JADX INFO: compiled from: UltraCine.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.UltraCine.UltraCine", f = "UltraCine.kt", i = {0, 0}, l = {41}, m = "getMainPage", n = {"this", "request"}, s = {"L$0", "L$1"})
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
            return UltraCine.this.getMainPage(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.UltraCine.UltraCine$load$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UltraCine.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.UltraCine.UltraCine", f = "UltraCine.kt", i = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {72, 87, 88, 98}, m = "load", n = {"this", "url", "this", "url", "title", "poster", "year", "score", "plot", "genres", "actors", "trailer"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8", "L$9"})
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
            return UltraCine.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.UltraCine.UltraCine$loadLinks$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UltraCine.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.UltraCine.UltraCine", f = "UltraCine.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4}, l = {127, 135, 141, 144, 146}, m = "loadLinks", n = {"this", "data", "subtitleCallback", "callback", "this", "subtitleCallback", "callback", "targetUrl", "subtitleCallback", "callback", "targetUrl", "links", "subtitleCallback", "callback", "targetUrl", "links", "subtitleCallback", "callback", "targetUrl", "links"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3"})
    static final class C00011 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        C00011(Continuation<? super C00011> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UltraCine.this.loadLinks(null, false, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.UltraCine.UltraCine$search$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UltraCine.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.UltraCine.UltraCine", f = "UltraCine.kt", i = {0}, l = {49}, m = "search", n = {"this"}, s = {"L$0"})
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
            return UltraCine.this.search(null, (Continuation) this);
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

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object getMainPage(int page, @NotNull MainPageRequest request, @NotNull Continuation<? super HomePageResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        UltraCine ultraCine;
        MainPageRequest request2;
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
                String url = request.getData() + (page > 1 ? "page/" + page + "/" : "");
                Requests app = MainActivityKt.getApp();
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = request;
                anonymousClass1.label = 1;
                $result = Requests.get$default(app, url, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, anonymousClass1, 4094, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                ultraCine = this;
                request2 = request;
                break;
                break;
            case 1:
                request2 = (MainPageRequest) anonymousClass1.L$1;
                ultraCine = (UltraCine) anonymousClass1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Iterable $this$forEach$iv$iv$iv = ((NiceResponse) $result).getDocument().select("div.aa-cn div#movies-a ul.post-lst li");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element it = (Element) element$iv$iv;
            SearchResponse searchResult = ultraCine.toSearchResult(it);
            if (searchResult != null) {
                destination$iv$iv.add(searchResult);
            }
        }
        List home = (List) destination$iv$iv;
        return MainAPIKt.newHomePageResponse$default(request2.getName(), home, (Boolean) null, 4, (Object) null);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        C00021 c00021;
        UltraCine ultraCine;
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
                ultraCine = this;
                break;
                break;
            case 1:
                ultraCine = (UltraCine) c00021.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Iterable $this$forEach$iv$iv$iv = ((NiceResponse) $result).getDocument().select("div.aa-cn div#movies-a ul.post-lst li");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element it = (Element) element$iv$iv;
            SearchResponse searchResult = ultraCine.toSearchResult(it);
            if (searchResult != null) {
                destination$iv$iv.add(searchResult);
            }
        }
        return (List) destination$iv$iv;
    }

    private final SearchResponse toSearchResult(Element $this$toSearchResult) {
        String title;
        Element elementSelectFirst;
        String href;
        String strText;
        String strReplace$default;
        String strText2;
        Element elementSelectFirst2 = $this$toSearchResult.selectFirst("header.entry-header h2.entry-title");
        final String ratingText = null;
        if (elementSelectFirst2 == null || (title = elementSelectFirst2.text()) == null || (elementSelectFirst = $this$toSearchResult.selectFirst("a.lnk-blk")) == null || (href = elementSelectFirst.attr("href")) == null) {
            return null;
        }
        final String poster = selectPoster($this$toSearchResult, "div.post-thumbnail figure img", "/w500/");
        Element elementSelectFirst3 = $this$toSearchResult.selectFirst("span.year");
        final Integer year = (elementSelectFirst3 == null || (strText2 = elementSelectFirst3.text()) == null) ? null : StringsKt.toIntOrNull(strText2);
        Element elementSelectFirst4 = $this$toSearchResult.selectFirst("span.post-ql");
        final SearchQuality quality = MainAPIKt.getQualityFromString(elementSelectFirst4 != null ? elementSelectFirst4.text() : null);
        Element elementSelectFirst5 = $this$toSearchResult.selectFirst("div.entry-meta span.vote");
        if (elementSelectFirst5 != null && (strText = elementSelectFirst5.text()) != null && (strReplace$default = StringsKt.replace$default(strText, "TMDB", "", false, 4, (Object) null)) != null) {
            ratingText = StringsKt.trim(strReplace$default).toString();
        }
        return MainAPIKt.newMovieSearchResponse$default(this, title, href, TvType.Movie, false, new Function1() { // from class: com.UltraCine.UltraCine$$ExternalSyntheticLambda1
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

    /* JADX WARN: Code duplicated, block: B:56:0x0202  */
    /* JADX WARN: Code duplicated, block: B:57:0x020c  */
    /* JADX WARN: Code duplicated, block: B:62:0x0283 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object load(@NotNull String url, @NotNull Continuation<? super LoadResponse> continuation) throws IOException {
        C00001 c00001;
        UltraCine ultraCine;
        Object obj;
        String url2;
        Element elementSelectFirst;
        String strText;
        String url3;
        String title;
        Integer year;
        Double score;
        String plot;
        List genres;
        List<Pair<Actor, ActorRole>> listSelectActors;
        String trailer;
        UltraCine ultraCine2;
        List genres2;
        String trailer2;
        String url4;
        List<Pair<Actor, ActorRole>> list;
        String plot2;
        Double score2;
        Integer year2;
        String title2;
        String poster;
        String strText2;
        String title3;
        String url5;
        String poster2;
        Integer year3;
        Double score3;
        String url6;
        List genres3;
        List<Pair<Actor, ActorRole>> list2;
        List<Episode> listEmptyList;
        String trailer3;
        List<Episode> episodes;
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
                ultraCine = this;
                Requests app = MainActivityKt.getApp();
                c00001.L$0 = ultraCine;
                c00001.L$1 = url;
                c00001.label = 1;
                Object obj2 = Requests.get$default(app, url, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00001, 4094, (Object) null);
                coroutine_suspended = coroutine_suspended;
                if (obj2 == coroutine_suspended) {
                    return coroutine_suspended;
                }
                obj = obj2;
                url2 = url;
                Document document = ((NiceResponse) obj).getDocument();
                elementSelectFirst = document.selectFirst("aside.fg1 header.entry-header h1.entry-title");
                if (elementSelectFirst == null && (strText = elementSelectFirst.text()) != null) {
                    url3 = strText;
                    title = ultraCine.selectPoster(document, "div.bghd img.TPostBg", "/w1280/");
                    year = ultraCine.extractInt(document, "span.year");
                    String durationText = ultraCine.extractText(document, "span.duration");
                    Element elementSelectFirst2 = document.selectFirst("div.vote-cn span.vote span.num");
                    score = (elementSelectFirst2 == null || (strText2 = elementSelectFirst2.text()) == null) ? null : StringsKt.toDoubleOrNull(strText2);
                    Element elementSelectFirst3 = document.selectFirst("aside.fg1 div.description p");
                    plot = elementSelectFirst3 != null ? elementSelectFirst3.text() : null;
                    Iterable $this$map$iv = document.select("span.genres a");
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        Element it = (Element) item$iv$iv;
                        destination$iv$iv.add(it.text());
                    }
                    genres = (List) destination$iv$iv;
                    listSelectActors = ultraCine.selectActors(document);
                    Element elementSelectFirst4 = document.selectFirst("div.mdl-cn iframe");
                    String trailer4 = elementSelectFirst4 != null ? elementSelectFirst4.attr("src") : null;
                    Element elementSelectFirst5 = document.selectFirst("iframe[src*='painel-aso.sbs']");
                    String iframeUrl = elementSelectFirst5 != null ? elementSelectFirst5.attr("src") : null;
                    Object obj3 = coroutine_suspended;
                    boolean isSerie = StringsKt.contains$default(url2, "/serie/", false, 2, (Object) null);
                    if (!isSerie) {
                        String str = url2;
                        UltraCine ultraCine3 = ultraCine;
                        TvType tvType = TvType.Movie;
                        String str2 = iframeUrl == null ? "" : iframeUrl;
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3(title, year, plot, genres, ultraCine, durationText, score, listSelectActors, trailer4, null);
                        c00001.L$0 = null;
                        c00001.L$1 = null;
                        c00001.label = 4;
                        $result = MainAPIKt.newMovieLoadResponse(ultraCine3, url3, str, tvType, str2, anonymousClass3, c00001);
                        if ($result == obj3) {
                            return obj3;
                        }
                        return (LoadResponse) $result;
                    }
                    if (iframeUrl != null) {
                        Requests app2 = MainActivityKt.getApp();
                        c00001.L$0 = ultraCine;
                        c00001.L$1 = url2;
                        c00001.L$2 = url3;
                        c00001.L$3 = title;
                        c00001.L$4 = year;
                        c00001.L$5 = score;
                        c00001.L$6 = plot;
                        c00001.L$7 = genres;
                        c00001.L$8 = listSelectActors;
                        c00001.L$9 = trailer4;
                        c00001.L$10 = ultraCine;
                        c00001.label = 2;
                        String url7 = url2;
                        $result = Requests.get$default(app2, iframeUrl, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00001, 4094, (Object) null);
                        coroutine_suspended = obj3;
                        if ($result == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        ultraCine2 = ultraCine;
                        genres2 = genres;
                        trailer2 = trailer4;
                        url4 = url7;
                        list = listSelectActors;
                        plot2 = plot;
                        score2 = score;
                        year2 = year;
                        title2 = url3;
                        poster = title;
                        episodes = ultraCine2.parseEpisodes(((NiceResponse) $result).getDocument());
                        if (episodes == null) {
                            genres = genres2;
                            url2 = url4;
                            url3 = title2;
                            trailer = trailer2;
                            title = poster;
                            year = year2;
                            score = score2;
                            plot = plot2;
                            listSelectActors = list;
                        } else {
                            year3 = year2;
                            poster2 = poster;
                            url5 = url4;
                            title3 = title2;
                            list2 = list;
                            genres3 = genres2;
                            url6 = plot2;
                            score3 = score2;
                            listEmptyList = episodes;
                            trailer3 = trailer2;
                        }
                        TvType tvType2 = TvType.TvSeries;
                        AnonymousClass2 anonymousClass2 = new AnonymousClass2(poster2, year3, url6, genres3, score3, list2, trailer3, null);
                        c00001.L$0 = null;
                        c00001.L$1 = null;
                        c00001.L$2 = null;
                        c00001.L$3 = null;
                        c00001.L$4 = null;
                        c00001.L$5 = null;
                        c00001.L$6 = null;
                        c00001.L$7 = null;
                        c00001.L$8 = null;
                        c00001.L$9 = null;
                        c00001.L$10 = null;
                        c00001.label = 3;
                        $result = MainAPIKt.newTvSeriesLoadResponse(ultraCine, title3, url5, tvType2, listEmptyList, anonymousClass2, c00001);
                        if ($result == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        return (LoadResponse) $result;
                    }
                    coroutine_suspended = obj3;
                    trailer = trailer4;
                    title3 = url3;
                    poster2 = title;
                    url5 = url2;
                    url6 = plot;
                    score3 = score;
                    year3 = year;
                    genres3 = genres;
                    trailer3 = trailer;
                    list2 = listSelectActors;
                    listEmptyList = CollectionsKt.emptyList();
                    TvType tvType3 = TvType.TvSeries;
                    AnonymousClass2 anonymousClass4 = new AnonymousClass2(poster2, year3, url6, genres3, score3, list2, trailer3, null);
                    c00001.L$0 = null;
                    c00001.L$1 = null;
                    c00001.L$2 = null;
                    c00001.L$3 = null;
                    c00001.L$4 = null;
                    c00001.L$5 = null;
                    c00001.L$6 = null;
                    c00001.L$7 = null;
                    c00001.L$8 = null;
                    c00001.L$9 = null;
                    c00001.L$10 = null;
                    c00001.label = 3;
                    $result = MainAPIKt.newTvSeriesLoadResponse(ultraCine, title3, url5, tvType3, listEmptyList, anonymousClass4, c00001);
                    if ($result == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return (LoadResponse) $result;
                }
            case 1:
                String url8 = (String) c00001.L$1;
                UltraCine ultraCine4 = (UltraCine) c00001.L$0;
                ResultKt.throwOnFailure($result);
                url2 = url8;
                ultraCine = ultraCine4;
                obj = $result;
                Document document2 = ((NiceResponse) obj).getDocument();
                elementSelectFirst = document2.selectFirst("aside.fg1 header.entry-header h1.entry-title");
                return elementSelectFirst == null ? null : null;
            case 2:
                ultraCine2 = (UltraCine) c00001.L$10;
                trailer2 = (String) c00001.L$9;
                list = (List) c00001.L$8;
                genres2 = (List) c00001.L$7;
                plot2 = (String) c00001.L$6;
                score2 = (Double) c00001.L$5;
                year2 = (Integer) c00001.L$4;
                poster = (String) c00001.L$3;
                title2 = (String) c00001.L$2;
                url4 = (String) c00001.L$1;
                ultraCine = (UltraCine) c00001.L$0;
                ResultKt.throwOnFailure($result);
                episodes = ultraCine2.parseEpisodes(((NiceResponse) $result).getDocument());
                if (episodes == null) {
                    genres = genres2;
                    url2 = url4;
                    url3 = title2;
                    trailer = trailer2;
                    title = poster;
                    year = year2;
                    score = score2;
                    plot = plot2;
                    listSelectActors = list;
                    title3 = url3;
                    poster2 = title;
                    url5 = url2;
                    url6 = plot;
                    score3 = score;
                    year3 = year;
                    genres3 = genres;
                    trailer3 = trailer;
                    list2 = listSelectActors;
                    listEmptyList = CollectionsKt.emptyList();
                } else {
                    year3 = year2;
                    poster2 = poster;
                    url5 = url4;
                    title3 = title2;
                    list2 = list;
                    genres3 = genres2;
                    url6 = plot2;
                    score3 = score2;
                    listEmptyList = episodes;
                    trailer3 = trailer2;
                }
                TvType tvType4 = TvType.TvSeries;
                AnonymousClass2 anonymousClass5 = new AnonymousClass2(poster2, year3, url6, genres3, score3, list2, trailer3, null);
                c00001.L$0 = null;
                c00001.L$1 = null;
                c00001.L$2 = null;
                c00001.L$3 = null;
                c00001.L$4 = null;
                c00001.L$5 = null;
                c00001.L$6 = null;
                c00001.L$7 = null;
                c00001.L$8 = null;
                c00001.L$9 = null;
                c00001.L$10 = null;
                c00001.label = 3;
                $result = MainAPIKt.newTvSeriesLoadResponse(ultraCine, title3, url5, tvType4, listEmptyList, anonymousClass5, c00001);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
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

    /* JADX INFO: renamed from: com.UltraCine.UltraCine$load$2, reason: invalid class name */
    /* JADX INFO: compiled from: UltraCine.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/TvSeriesLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
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
    /* JADX INFO: compiled from: UltraCine.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/MovieLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
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

    /* JADX WARN: Code duplicated, block: B:29:0x0183  */
    /* JADX WARN: Code duplicated, block: B:31:0x0191  */
    /* JADX WARN: Code duplicated, block: B:33:0x01de A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:34:0x01df  */
    /* JADX WARN: Code duplicated, block: B:40:0x0202  */
    /* JADX WARN: Code duplicated, block: B:41:0x0207  */
    /* JADX WARN: Code duplicated, block: B:49:0x0285  */
    /* JADX WARN: Code duplicated, block: B:51:0x029f  */
    /* JADX WARN: Code duplicated, block: B:53:0x02c4 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:54:0x02c5  */
    /* JADX WARN: Code duplicated, block: B:56:0x02dd  */
    /* JADX WARN: Code duplicated, block: B:58:0x02f2  */
    /* JADX WARN: Code duplicated, block: B:60:0x0311 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:61:0x0312  */
    /* JADX WARN: Code duplicated, block: B:63:0x0327  */
    /* JADX WARN: Code duplicated, block: B:65:0x033c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:66:0x033d  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:0x033d -> B:67:0x0343). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:731)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public java.lang.Object loadLinks(@org.jetbrains.annotations.NotNull java.lang.String r29, boolean r30, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.SubtitleFile, kotlin.Unit> r31, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.utils.ExtractorLink, kotlin.Unit> r32, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> r33) {
        /*
            Method dump skipped, instruction units count: 882
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.UltraCine.UltraCine.loadLinks(java.lang.String, boolean, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final List<Episode> parseEpisodes(Document doc) {
        String value;
        Integer intOrNull;
        Document document = doc;
        Iterable episodes = (List) new ArrayList();
        Elements seasons = document.select("ul.header-navigation li[data-season-id]");
        Iterable $this$forEach$iv = (Iterable) seasons;
        int $i$f$forEach = 0;
        Iterator it = $this$forEach$iv.iterator();
        while (it.hasNext()) {
            Object element$iv = it.next();
            Element season = (Element) element$iv;
            Integer intOrNull2 = StringsKt.toIntOrNull(season.attr("data-season-number"));
            final int seasonNumber = intOrNull2 != null ? intOrNull2.intValue() : 1;
            String seasonId = season.attr("data-season-id");
            for (Element ep : document.select("li[data-season-id='" + seasonId + "']")) {
                String epId = ep.attr("data-episode-id");
                if (!StringsKt.isBlank(epId)) {
                    Element elementSelectFirst = ep.selectFirst("a");
                    final String name = elementSelectFirst != null ? elementSelectFirst.text() : null;
                    if (name == null) {
                        name = "";
                    }
                    MatchResult matchResultFind$default = Regex.find$default(new Regex("\\d+"), name, 0, 2, (Object) null);
                    final int number = (matchResultFind$default == null || (value = matchResultFind$default.getValue()) == null || (intOrNull = StringsKt.toIntOrNull(value)) == null) ? 1 : intOrNull.intValue();
                    ((Collection) episodes).add(MainAPIKt.newEpisode(this, epId, new Function1() { // from class: com.UltraCine.UltraCine$$ExternalSyntheticLambda0
                        public final Object invoke(Object obj) {
                            return UltraCine.parseEpisodes$lambda$8$lambda$7$lambda$6(name, seasonNumber, number, (Episode) obj);
                        }
                    }));
                }
                seasons = seasons;
                $this$forEach$iv = $this$forEach$iv;
                $i$f$forEach = $i$f$forEach;
                it = it;
                element$iv = element$iv;
                season = season;
            }
            document = doc;
        }
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

    private final List<String> extractEmbedLinks(Document doc) {
        Iterable $this$mapNotNull$iv = doc.select("button[data-source]");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            Element it = (Element) element$iv$iv$iv;
            String strAttr = it.attr("data-source");
            if (strAttr != null) {
                destination$iv$iv.add(strAttr);
            }
        }
        List buttons = (List) destination$iv$iv;
        Iterable $this$mapNotNull$iv2 = doc.select("div#player iframe, div.play-overlay iframe");
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv$iv2 : $this$mapNotNull$iv2) {
            Element it2 = (Element) element$iv$iv$iv2;
            String strAttr2 = it2.attr("src");
            if (strAttr2 != null) {
                destination$iv$iv2.add(strAttr2);
            }
        }
        List iframes = (List) destination$iv$iv2;
        Iterable $this$filter$iv = CollectionsKt.distinct(CollectionsKt.plus(buttons, iframes));
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it3 = (String) element$iv$iv;
            if (!StringsKt.isBlank(it3)) {
                destination$iv$iv3.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv3;
    }

    private final List<Pair<Actor, ActorRole>> selectActors(Document $this$selectActors) {
        ArrayList arrayList;
        Iterable $this$map$iv = $this$selectActors.select("ul.cast-lst a");
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator it = $this$map$iv.iterator();
        while (true) {
            arrayList = null;
            if (!it.hasNext()) {
                break;
            }
            Object item$iv$iv = it.next();
            Element it2 = (Element) item$iv$iv;
            destination$iv$iv.add(TuplesKt.to(new Actor(it2.text(), it2.attr("href")), (Object) null));
        }
        List actors = (List) destination$iv$iv;
        List list = actors;
        if (!list.isEmpty()) {
            arrayList = list;
        }
        return arrayList;
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
}
