package com.AnimesCloud;

import com.lagradost.cloudstream3.Actor;
import com.lagradost.cloudstream3.AnimeLoadResponse;
import com.lagradost.cloudstream3.AnimeSearchResponse;
import com.lagradost.cloudstream3.DubStatus;
import com.lagradost.cloudstream3.Episode;
import com.lagradost.cloudstream3.LoadResponse;
import com.lagradost.cloudstream3.MainAPI;
import com.lagradost.cloudstream3.MainAPIKt;
import com.lagradost.cloudstream3.MainActivityKt;
import com.lagradost.cloudstream3.MainPageData;
import com.lagradost.cloudstream3.MovieLoadResponse;
import com.lagradost.cloudstream3.Score;
import com.lagradost.cloudstream3.SearchQuality;
import com.lagradost.cloudstream3.SearchResponse;
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.network.CloudflareKiller;
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
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* JADX INFO: compiled from: AnimesCloud.kt */
/* JADX INFO: loaded from: /tmp/cs3/AnimesCloud/classes.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 J2\u00020\u0001:\u0001JB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0005H\u0082@¢\u0006\u0002\u0010'J\u000e\u0010(\u001a\u0004\u0018\u00010\u0005*\u00020)H\u0002J\u001e\u0010-\u001a\u00020/2\u0006\u00100\u001a\u0002012\u0006\u0010$\u001a\u000202H\u0096@¢\u0006\u0002\u00103J\u0014\u00104\u001a\u0004\u0018\u000105*\u00020)H\u0082@¢\u0006\u0002\u00106J\u001c\u00107\u001a\b\u0012\u0004\u0012\u0002050+2\u0006\u00108\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010'J\u0016\u00109\u001a\u00020:2\u0006\u0010&\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010'J*\u0010;\u001a\u0014\u0012\u0004\u0012\u00020<\u0012\n\u0012\b\u0012\u0004\u0012\u00020=0+0!2\u0006\u0010>\u001a\u00020%2\u0006\u0010?\u001a\u00020\u0005H\u0002JF\u0010@\u001a\u00020\u000e2\u0006\u0010A\u001a\u00020\u00052\u0006\u0010B\u001a\u00020\u000e2\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020E\u0012\u0004\u0012\u00020F0D2\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020H\u0012\u0004\u0012\u00020F0DH\u0096@¢\u0006\u0002\u0010IR\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0014\u0010\r\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0007\"\u0004\b\u0013\u0010\tR\u0014\u0010\u0014\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0014\u0010\u0016\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050!8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u001a\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.¨\u0006K"}, d2 = {"Lcom/AnimesCloud/AnimesCloud;", "Lcom/lagradost/cloudstream3/MainAPI;", "<init>", "()V", "mainUrl", "", "getMainUrl", "()Ljava/lang/String;", "setMainUrl", "(Ljava/lang/String;)V", "name", "getName", "setName", "hasMainPage", "", "getHasMainPage", "()Z", "lang", "getLang", "setLang", "hasDownloadSupport", "getHasDownloadSupport", "hasQuickSearch", "getHasQuickSearch", "supportedTypes", "", "Lcom/lagradost/cloudstream3/TvType;", "getSupportedTypes", "()Ljava/util/Set;", "cloudflareInterceptor", "Lcom/lagradost/cloudstream3/network/CloudflareKiller;", "persistedCookies", "defaultHeaders", "", "getDefaultHeaders", "()Ljava/util/Map;", "request", "Lorg/jsoup/nodes/Document;", "url", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractPoster", "Lorg/jsoup/nodes/Element;", "mainPage", "", "Lcom/lagradost/cloudstream3/MainPageData;", "getMainPage", "()Ljava/util/List;", "Lcom/lagradost/cloudstream3/HomePageResponse;", "page", "", "Lcom/lagradost/cloudstream3/MainPageRequest;", "(ILcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toSearchResult", "Lcom/lagradost/cloudstream3/SearchResponse;", "(Lorg/jsoup/nodes/Element;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "query", "load", "Lcom/lagradost/cloudstream3/LoadResponse;", "loadEpisodesFromPage", "Lcom/lagradost/cloudstream3/DubStatus;", "Lcom/lagradost/cloudstream3/Episode;", "document", "baseUrl", "loadLinks", "data", "isCasting", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "AnimesCloud_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAnimesCloud.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnimesCloud.kt\ncom/AnimesCloud/AnimesCloud\n+ 2 Mutex.kt\nkotlinx/coroutines/sync/MutexKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,193:1\n116#2,8:194\n125#2,2:205\n1863#3,2:202\n1611#3,9:207\n1863#3:216\n1864#3:218\n1620#3:219\n1611#3,9:220\n1863#3:229\n1864#3:231\n1620#3:232\n1611#3,9:233\n1863#3:242\n1864#3:244\n1620#3:245\n1557#3:246\n1628#3,3:247\n1#4:204\n1#4:217\n1#4:230\n1#4:243\n*S KotlinDebug\n*F\n+ 1 AnimesCloud.kt\ncom/AnimesCloud/AnimesCloud\n*L\n42#1:194,8\n42#1:205,2\n53#1:202,2\n80#1:207,9\n80#1:216\n80#1:218\n80#1:219\n99#1:220,9\n99#1:229\n99#1:231\n99#1:232\n127#1:233,9\n127#1:242\n127#1:244\n127#1:245\n169#1:246\n169#1:247,3\n80#1:217\n99#1:230\n127#1:243\n*E\n"})
public final class AnimesCloud extends MainAPI {

    @NotNull
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36";
    private static boolean isInitialized;

    @Nullable
    private String persistedCookies;

    @NotNull
    private static final Mutex locker = MutexKt.Mutex$default(false, 1, (Object) null);

    @NotNull
    private String mainUrl = "https://animesdrive.cloud/";

    @NotNull
    private String name = "AnimesCloud";
    private final boolean hasMainPage = true;

    @NotNull
    private String lang = "pt-br";
    private final boolean hasDownloadSupport = true;
    private final boolean hasQuickSearch = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Anime, TvType.AnimeMovie});

    @NotNull
    private final CloudflareKiller cloudflareInterceptor = new CloudflareKiller();

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to("tipo/dublado", "Dublados"), TuplesKt.to("tipo/legendado", "Legendados")});

    /* JADX INFO: renamed from: com.AnimesCloud.AnimesCloud$getMainPage$1, reason: invalid class name */
    /* JADX INFO: compiled from: AnimesCloud.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesCloud.AnimesCloud", f = "AnimesCloud.kt", i = {0, 0, 1, 1, 1}, l = {78, 80}, m = "getMainPage", n = {"this", "request", "this", "request", "destination$iv$iv"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesCloud.this.getMainPage(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesCloud.AnimesCloud$load$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesCloud.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesCloud.AnimesCloud", f = "AnimesCloud.kt", i = {0, 0}, l = {116, 142, 151}, m = "load", n = {"this", "url"}, s = {"L$0", "L$1"})
    static final class C00001 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C00001(Continuation<? super C00001> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesCloud.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesCloud.AnimesCloud$request$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesCloud.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesCloud.AnimesCloud", f = "AnimesCloud.kt", i = {0, 0, 0, 1, 1, 1}, l = {199, 46, 64}, m = "request", n = {"this", "url", "$this$withLock_u24default$iv", "this", "url", "$this$withLock_u24default$iv"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2"})
    static final class C00011 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C00011(Continuation<? super C00011> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesCloud.this.request(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesCloud.AnimesCloud$search$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesCloud.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesCloud.AnimesCloud", f = "AnimesCloud.kt", i = {0}, l = {98}, m = "search", n = {"this"}, s = {"L$0"})
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
            return AnimesCloud.this.search(null, (Continuation) this);
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

    private final Map<String, String> getDefaultHeaders() {
        Pair[] pairArr = new Pair[3];
        pairArr[0] = TuplesKt.to("User-Agent", USER_AGENT);
        pairArr[1] = TuplesKt.to("Referer", getMainUrl() + "/");
        String str = this.persistedCookies;
        if (str == null) {
            str = "";
        }
        pairArr[2] = TuplesKt.to("Cookie", str);
        return MapsKt.mapOf(pairArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:35:0x010a A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:36:0x010b  */
    /* JADX WARN: Code duplicated, block: B:39:0x0122 A[Catch: all -> 0x01ad, Exception -> 0x01b0, TryCatch #0 {Exception -> 0x01b0, blocks: (B:37:0x0117, B:39:0x0122, B:40:0x013a, B:42:0x0140, B:43:0x016c, B:45:0x017d, B:46:0x0186), top: B:73:0x0117 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x0140 A[Catch: all -> 0x01ad, Exception -> 0x01b0, LOOP:0: B:40:0x013a->B:42:0x0140, LOOP_END, TryCatch #0 {Exception -> 0x01b0, blocks: (B:37:0x0117, B:39:0x0122, B:40:0x013a, B:42:0x0140, B:43:0x016c, B:45:0x017d, B:46:0x0186), top: B:73:0x0117 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x017d A[Catch: all -> 0x01ad, Exception -> 0x01b0, TryCatch #0 {Exception -> 0x01b0, blocks: (B:37:0x0117, B:39:0x0122, B:40:0x013a, B:42:0x0140, B:43:0x016c, B:45:0x017d, B:46:0x0186), top: B:73:0x0117 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x01e6  */
    /* JADX WARN: Code duplicated, block: B:70:0x0250 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:75:0x00af A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object request(String url, Continuation<? super Document> continuation) throws Throwable {
        C00011 c00011;
        AnimesCloud animesCloud;
        C00011 c00012;
        String url2;
        AnimesCloud animesCloud2;
        String url3;
        Mutex $this$withLock_u24default$iv;
        Object owner$iv;
        int $i$f$withLock;
        Object owner$iv2;
        Mutex $this$withLock_u24default$iv2;
        int i;
        AnimesCloud animesCloud3;
        String url4;
        String url5;
        AnimesCloud animesCloud4;
        Mutex $this$withLock_u24default$iv3;
        boolean z;
        Object obj;
        NiceResponse resMain;
        List cookieList;
        String it;
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
        C00011 c00013 = c00011;
        Object $result = c00013.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00013.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                animesCloud = this;
                if (!isInitialized) {
                    Mutex $this$withLock_u24default$iv4 = locker;
                    c00013.L$0 = animesCloud;
                    c00013.L$1 = url;
                    c00013.L$2 = $this$withLock_u24default$iv4;
                    c00013.label = 1;
                    if ($this$withLock_u24default$iv4.lock((Object) null, c00013) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    animesCloud2 = animesCloud;
                    url3 = url;
                    $this$withLock_u24default$iv = $this$withLock_u24default$iv4;
                    owner$iv = null;
                    $i$f$withLock = 0;
                    try {
                        if (isInitialized) {
                            c00012 = c00013;
                            owner$iv2 = owner$iv;
                            animesCloud = animesCloud2;
                            url4 = url3;
                            $this$withLock_u24default$iv2 = $this$withLock_u24default$iv;
                        } else {
                            try {
                                Requests app = MainActivityKt.getApp();
                                String mainUrl = animesCloud2.getMainUrl();
                                Map mapMapOf = MapsKt.mapOf(TuplesKt.to("User-Agent", USER_AGENT));
                                Interceptor interceptor = animesCloud2.cloudflareInterceptor;
                                c00013.L$0 = animesCloud2;
                                c00013.L$1 = url3;
                                c00013.L$2 = $this$withLock_u24default$iv;
                                c00013.label = 2;
                                url5 = url3;
                                animesCloud4 = animesCloud2;
                                $this$withLock_u24default$iv3 = $this$withLock_u24default$iv;
                                z = true;
                                c00012 = c00013;
                                try {
                                    $result = Requests.get$default(app, mainUrl, mapMapOf, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 60L, interceptor, false, (ResponseParser) null, c00012, 3324, (Object) null);
                                    coroutine_suspended = coroutine_suspended;
                                    if ($result == coroutine_suspended) {
                                        return coroutine_suspended;
                                    }
                                    owner$iv2 = owner$iv;
                                    i = 0;
                                    animesCloud3 = animesCloud4;
                                    url4 = url5;
                                    $this$withLock_u24default$iv2 = $this$withLock_u24default$iv3;
                                    try {
                                        try {
                                            resMain = (NiceResponse) $result;
                                            if (resMain.getCode() == 200) {
                                                cookieList = new ArrayList();
                                                Iterable $this$forEach$iv = resMain.getOkhttpResponse().headers("Set-Cookie");
                                                for (Object element$iv : $this$forEach$iv) {
                                                    cookieList.add(StringsKt.split$default((String) element$iv, new String[]{";"}, false, 0, 6, (Object) null).get(0));
                                                }
                                                it = resMain.getOkhttpResponse().request().header("Cookie");
                                                if (it != null) {
                                                    Boxing.boxBoolean(cookieList.add(it));
                                                }
                                                animesCloud3.persistedCookies = CollectionsKt.joinToString$default(CollectionsKt.distinct(cookieList), "; ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                                                isInitialized = z;
                                            }
                                            animesCloud = animesCloud3;
                                        } catch (Exception e) {
                                            animesCloud = animesCloud3;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        $this$withLock_u24default$iv2.unlock(owner$iv2);
                                        throw th;
                                    }
                                } catch (Exception e2) {
                                    coroutine_suspended = coroutine_suspended;
                                    owner$iv2 = owner$iv;
                                    i = 0;
                                    animesCloud3 = animesCloud4;
                                    url4 = url5;
                                    $this$withLock_u24default$iv2 = $this$withLock_u24default$iv3;
                                    animesCloud = animesCloud3;
                                    Unit unit = Unit.INSTANCE;
                                    $this$withLock_u24default$iv2.unlock(owner$iv2);
                                    url2 = url4;
                                    Requests app2 = MainActivityKt.getApp();
                                    Map<String, String> defaultHeaders = animesCloud.getDefaultHeaders();
                                    Interceptor interceptor2 = animesCloud.cloudflareInterceptor;
                                    C00011 c00014 = c00012;
                                    c00014.L$0 = null;
                                    c00014.L$1 = null;
                                    c00014.L$2 = null;
                                    c00014.label = 3;
                                    obj = coroutine_suspended;
                                    $result = Requests.get$default(app2, url2, defaultHeaders, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, interceptor2, false, (ResponseParser) null, c00014, 3580, (Object) null);
                                    if ($result == obj) {
                                        return obj;
                                    }
                                    return ((NiceResponse) $result).getDocument();
                                } catch (Throwable th2) {
                                    th = th2;
                                    owner$iv2 = owner$iv;
                                    $this$withLock_u24default$iv2 = $this$withLock_u24default$iv3;
                                    $this$withLock_u24default$iv2.unlock(owner$iv2);
                                    throw th;
                                }
                            } catch (Exception e3) {
                                c00012 = c00013;
                                owner$iv2 = owner$iv;
                                i = 0;
                                animesCloud3 = animesCloud2;
                                url4 = url3;
                                $this$withLock_u24default$iv2 = $this$withLock_u24default$iv;
                            }
                        }
                        Unit unit2 = Unit.INSTANCE;
                        $this$withLock_u24default$iv2.unlock(owner$iv2);
                        url2 = url4;
                    } catch (Throwable th3) {
                        th = th3;
                        owner$iv2 = owner$iv;
                        $this$withLock_u24default$iv2 = $this$withLock_u24default$iv;
                    }
                    break;
                } else {
                    c00012 = c00013;
                    url2 = url;
                }
                Requests app3 = MainActivityKt.getApp();
                Map<String, String> defaultHeaders2 = animesCloud.getDefaultHeaders();
                Interceptor interceptor3 = animesCloud.cloudflareInterceptor;
                C00011 c00015 = c00012;
                c00015.L$0 = null;
                c00015.L$1 = null;
                c00015.L$2 = null;
                c00015.label = 3;
                obj = coroutine_suspended;
                $result = Requests.get$default(app3, url2, defaultHeaders2, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, interceptor3, false, (ResponseParser) null, c00015, 3580, (Object) null);
                if ($result == obj) {
                    return obj;
                }
                return ((NiceResponse) $result).getDocument();
            case 1:
                Mutex $this$withLock_u24default$iv5 = (Mutex) c00013.L$2;
                String url6 = (String) c00013.L$1;
                AnimesCloud animesCloud5 = (AnimesCloud) c00013.L$0;
                ResultKt.throwOnFailure($result);
                $i$f$withLock = 0;
                owner$iv = null;
                $this$withLock_u24default$iv = $this$withLock_u24default$iv5;
                url3 = url6;
                animesCloud2 = animesCloud5;
                if (isInitialized) {
                    Requests app4 = MainActivityKt.getApp();
                    String mainUrl2 = animesCloud2.getMainUrl();
                    Map mapMapOf2 = MapsKt.mapOf(TuplesKt.to("User-Agent", USER_AGENT));
                    Interceptor interceptor4 = animesCloud2.cloudflareInterceptor;
                    c00013.L$0 = animesCloud2;
                    c00013.L$1 = url3;
                    c00013.L$2 = $this$withLock_u24default$iv;
                    c00013.label = 2;
                    url5 = url3;
                    animesCloud4 = animesCloud2;
                    $this$withLock_u24default$iv3 = $this$withLock_u24default$iv;
                    z = true;
                    c00012 = c00013;
                    $result = Requests.get$default(app4, mainUrl2, mapMapOf2, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 60L, interceptor4, false, (ResponseParser) null, c00012, 3324, (Object) null);
                    coroutine_suspended = coroutine_suspended;
                    if ($result == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    owner$iv2 = owner$iv;
                    i = 0;
                    animesCloud3 = animesCloud4;
                    url4 = url5;
                    $this$withLock_u24default$iv2 = $this$withLock_u24default$iv3;
                    resMain = (NiceResponse) $result;
                    if (resMain.getCode() == 200) {
                        cookieList = new ArrayList();
                        Iterable $this$forEach$iv2 = resMain.getOkhttpResponse().headers("Set-Cookie");
                        while (r13.hasNext()) {
                            cookieList.add(StringsKt.split$default((String) element$iv, new String[]{";"}, false, 0, 6, (Object) null).get(0));
                        }
                        it = resMain.getOkhttpResponse().request().header("Cookie");
                        if (it != null) {
                            Boxing.boxBoolean(cookieList.add(it));
                        }
                        animesCloud3.persistedCookies = CollectionsKt.joinToString$default(CollectionsKt.distinct(cookieList), "; ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                        isInitialized = z;
                    }
                    animesCloud = animesCloud3;
                    break;
                } else {
                    c00012 = c00013;
                    owner$iv2 = owner$iv;
                    animesCloud = animesCloud2;
                    url4 = url3;
                    $this$withLock_u24default$iv2 = $this$withLock_u24default$iv;
                }
                Unit unit3 = Unit.INSTANCE;
                $this$withLock_u24default$iv2.unlock(owner$iv2);
                url2 = url4;
                Requests app5 = MainActivityKt.getApp();
                Map<String, String> defaultHeaders3 = animesCloud.getDefaultHeaders();
                Interceptor interceptor5 = animesCloud.cloudflareInterceptor;
                C00011 c00016 = c00012;
                c00016.L$0 = null;
                c00016.L$1 = null;
                c00016.L$2 = null;
                c00016.label = 3;
                obj = coroutine_suspended;
                $result = Requests.get$default(app5, url2, defaultHeaders3, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, interceptor5, false, (ResponseParser) null, c00016, 3580, (Object) null);
                if ($result == obj) {
                    return obj;
                }
                return ((NiceResponse) $result).getDocument();
            case 2:
                i = 0;
                owner$iv2 = null;
                $this$withLock_u24default$iv2 = (Mutex) c00013.L$2;
                url4 = (String) c00013.L$1;
                animesCloud3 = (AnimesCloud) c00013.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    c00012 = c00013;
                    z = true;
                    resMain = (NiceResponse) $result;
                    if (resMain.getCode() == 200) {
                        cookieList = new ArrayList();
                        Iterable $this$forEach$iv3 = resMain.getOkhttpResponse().headers("Set-Cookie");
                        while (r13.hasNext()) {
                            cookieList.add(StringsKt.split$default((String) element$iv, new String[]{";"}, false, 0, 6, (Object) null).get(0));
                        }
                        it = resMain.getOkhttpResponse().request().header("Cookie");
                        if (it != null) {
                            Boxing.boxBoolean(cookieList.add(it));
                        }
                        animesCloud3.persistedCookies = CollectionsKt.joinToString$default(CollectionsKt.distinct(cookieList), "; ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                        isInitialized = z;
                    }
                    animesCloud = animesCloud3;
                    break;
                } catch (Exception e4) {
                    c00012 = c00013;
                    animesCloud = animesCloud3;
                    Unit unit4 = Unit.INSTANCE;
                    $this$withLock_u24default$iv2.unlock(owner$iv2);
                    url2 = url4;
                    Requests app6 = MainActivityKt.getApp();
                    Map<String, String> defaultHeaders4 = animesCloud.getDefaultHeaders();
                    Interceptor interceptor6 = animesCloud.cloudflareInterceptor;
                    C00011 c00017 = c00012;
                    c00017.L$0 = null;
                    c00017.L$1 = null;
                    c00017.L$2 = null;
                    c00017.label = 3;
                    obj = coroutine_suspended;
                    $result = Requests.get$default(app6, url2, defaultHeaders4, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, interceptor6, false, (ResponseParser) null, c00017, 3580, (Object) null);
                    if ($result == obj) {
                        return obj;
                    }
                    return ((NiceResponse) $result).getDocument();
                } catch (Throwable th4) {
                    th = th4;
                    $this$withLock_u24default$iv2.unlock(owner$iv2);
                    throw th;
                }
                Unit unit5 = Unit.INSTANCE;
                $this$withLock_u24default$iv2.unlock(owner$iv2);
                url2 = url4;
                Requests app7 = MainActivityKt.getApp();
                Map<String, String> defaultHeaders5 = animesCloud.getDefaultHeaders();
                Interceptor interceptor7 = animesCloud.cloudflareInterceptor;
                C00011 c00018 = c00012;
                c00018.L$0 = null;
                c00018.L$1 = null;
                c00018.L$2 = null;
                c00018.label = 3;
                obj = coroutine_suspended;
                $result = Requests.get$default(app7, url2, defaultHeaders5, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, interceptor7, false, (ResponseParser) null, c00018, 3580, (Object) null);
                if ($result == obj) {
                    return obj;
                }
                return ((NiceResponse) $result).getDocument();
            case 3:
                ResultKt.throwOnFailure($result);
                return ((NiceResponse) $result).getDocument();
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    private final String extractPoster(Element $this$extractPoster) {
        Element img = $this$extractPoster.selectFirst("img");
        if (img == null) {
            return null;
        }
        String it = img.attr("data-src");
        if (StringsKt.isBlank(it)) {
            it = null;
        }
        if (it == null) {
            it = img.attr("src");
            boolean z = false;
            if (!StringsKt.isBlank(it) && !StringsKt.contains$default(it, "placeholder", false, 2, (Object) null)) {
                z = true;
            }
            if (!z) {
                it = null;
            }
            if (it == null) {
                it = img.attr("data-lazy-src");
                if (StringsKt.isBlank(it)) {
                    it = null;
                }
            }
        }
        if (it == null) {
            return null;
        }
        return MainAPIKt.fixUrl(this, it);
    }

    @NotNull
    public List<MainPageData> getMainPage() {
        return this.mainPage;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:22:0x00d3 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:23:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:26:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x00d4 -> B:24:0x00dd). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at java.base/java.util.BitSet.get(BitSet.java:628)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:744)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getMainPage(int r18, @org.jetbrains.annotations.NotNull com.lagradost.cloudstream3.MainPageRequest r19, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super com.lagradost.cloudstream3.HomePageResponse> r20) {
        /*
            Method dump skipped, instruction units count: 272
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.AnimesCloud.AnimesCloud.getMainPage(int, com.lagradost.cloudstream3.MainPageRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object toSearchResult(Element $this$toSearchResult, Continuation<? super SearchResponse> continuation) {
        String strText;
        String title;
        String strAttr;
        String strText2;
        Element elementSelectFirst = $this$toSearchResult.selectFirst("div.data h3 a");
        Double doubleOrNull = null;
        if (elementSelectFirst == null || (strText = elementSelectFirst.text()) == null || (title = StringsKt.trim(strText).toString()) == null) {
            return null;
        }
        AnimesCloud animesCloud = this;
        Element elementSelectFirst2 = $this$toSearchResult.selectFirst("div.data h3 a");
        if (elementSelectFirst2 == null || (strAttr = elementSelectFirst2.attr("href")) == null) {
            return null;
        }
        String href = MainAPIKt.fixUrl(animesCloud, strAttr);
        Element elementSelectFirst3 = $this$toSearchResult.selectFirst("div.rating");
        if (elementSelectFirst3 != null && (strText2 = elementSelectFirst3.text()) != null) {
            doubleOrNull = StringsKt.toDoubleOrNull(strText2);
        }
        final Double scoreValue = doubleOrNull;
        final String poster = extractPoster($this$toSearchResult);
        return MainAPIKt.newAnimeSearchResponse$default(this, title, href, TvType.Anime, false, new Function1() { // from class: com.AnimesCloud.AnimesCloud$$ExternalSyntheticLambda0
            public final Object invoke(Object obj) {
                return AnimesCloud.toSearchResult$lambda$9(poster, scoreValue, (AnimeSearchResponse) obj);
            }
        }, 8, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResult$lambda$9(String $poster, Double $scoreValue, AnimeSearchResponse $this$newAnimeSearchResponse) {
        $this$newAnimeSearchResponse.setPosterUrl($poster);
        $this$newAnimeSearchResponse.setQuality(SearchQuality.HD);
        $this$newAnimeSearchResponse.setScore(Score.Companion.from10($scoreValue));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) throws Throwable {
        C00021 c00021;
        Object objRequest;
        AnimesCloud animesCloud;
        AnimeSearchResponse animeSearchResponseNewAnimeSearchResponse$default;
        String strText;
        String title;
        String strAttr;
        String strText2;
        String string;
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
                String str = getMainUrl() + "/?s=" + StringsKt.replace$default(query, " ", "+", false, 4, (Object) null);
                c00021.L$0 = this;
                c00021.label = 1;
                objRequest = request(str, c00021);
                if (objRequest == coroutine_suspended) {
                    return coroutine_suspended;
                }
                animesCloud = this;
                break;
                break;
            case 1:
                animesCloud = (AnimesCloud) c00021.L$0;
                ResultKt.throwOnFailure($result);
                objRequest = $result;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document document = (Document) objRequest;
        Iterable $this$forEach$iv$iv$iv = document.select("div.search-page div.result-item article");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element item = (Element) element$iv$iv;
            Element elementSelectFirst = item.selectFirst("div.details div.title a");
            if (elementSelectFirst == null || (strText = elementSelectFirst.text()) == null || (title = StringsKt.trim(strText).toString()) == null) {
                animeSearchResponseNewAnimeSearchResponse$default = null;
            } else {
                AnimesCloud animesCloud2 = animesCloud;
                Element elementSelectFirst2 = item.selectFirst("div.details div.title a");
                if (elementSelectFirst2 == null || (strAttr = elementSelectFirst2.attr("href")) == null) {
                    animeSearchResponseNewAnimeSearchResponse$default = null;
                } else {
                    String href = MainAPIKt.fixUrl(animesCloud2, strAttr);
                    Element elementSelectFirst3 = item.selectFirst("div.meta span.year");
                    final Integer yearVal = (elementSelectFirst3 == null || (strText2 = elementSelectFirst3.text()) == null || (string = StringsKt.trim(strText2).toString()) == null) ? null : StringsKt.toIntOrNull(string);
                    Element elementSelectFirst4 = item.selectFirst("div.image div.thumbnail a img");
                    final String poster = elementSelectFirst4 != null ? animesCloud.extractPoster(elementSelectFirst4) : null;
                    animeSearchResponseNewAnimeSearchResponse$default = MainAPIKt.newAnimeSearchResponse$default(animesCloud, title, href, TvType.Anime, false, new Function1() { // from class: com.AnimesCloud.AnimesCloud$$ExternalSyntheticLambda2
                        public final Object invoke(Object obj) {
                            return AnimesCloud.search$lambda$11$lambda$10(poster, yearVal, (AnimeSearchResponse) obj);
                        }
                    }, 8, (Object) null);
                }
            }
            if (animeSearchResponseNewAnimeSearchResponse$default != null) {
                destination$iv$iv.add(animeSearchResponseNewAnimeSearchResponse$default);
            }
        }
        return (List) destination$iv$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit search$lambda$11$lambda$10(String $poster, Integer $yearVal, AnimeSearchResponse $this$newAnimeSearchResponse) {
        $this$newAnimeSearchResponse.setPosterUrl($poster);
        $this$newAnimeSearchResponse.setYear($yearVal);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0251 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:104:0x00cd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:108:0x0155 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:25:0x0083  */
    /* JADX WARN: Code duplicated, block: B:29:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:36:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:39:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:43:0x0104  */
    /* JADX WARN: Code duplicated, block: B:45:0x0116  */
    /* JADX WARN: Code duplicated, block: B:58:0x014b A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:61:0x0150  */
    /* JADX WARN: Code duplicated, block: B:65:0x0174  */
    /* JADX WARN: Code duplicated, block: B:69:0x0183  */
    /* JADX WARN: Code duplicated, block: B:72:0x0187  */
    /* JADX WARN: Code duplicated, block: B:73:0x018a  */
    /* JADX WARN: Code duplicated, block: B:76:0x0195  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Code duplicated, block: B:82:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:84:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:87:0x01c6  */
    /* JADX WARN: Code duplicated, block: B:90:0x01d2  */
    /* JADX WARN: Code duplicated, block: B:93:0x01e7  */
    /* JADX WARN: Code duplicated, block: B:95:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:97:0x020d A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:99:0x0212  */
    @Nullable
    public Object load(@NotNull String url, @NotNull Continuation<? super LoadResponse> continuation) throws Throwable {
        C00001 c00001;
        Object objRequest;
        String url2;
        AnimesCloud animesCloud;
        Document document;
        Element elementSelectFirst;
        String title;
        Double scoreValue;
        Iterator it;
        Object next;
        Element element;
        String durationText;
        Collection destination$iv$iv;
        List actors;
        boolean z;
        boolean isMovie;
        TvType tvType;
        TvType type;
        Element elementSelectFirst2;
        Element elementSelectFirst3;
        String plotText;
        String strText;
        Element elementSelectFirst4;
        String finalPoster;
        Object objNewMovieLoadResponse;
        String strAttr;
        String strText2;
        String string;
        Element elementSelectFirst5;
        Pair pair;
        String name;
        String role;
        Element elementSelectFirst6;
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
                c00001.L$0 = this;
                c00001.L$1 = url;
                c00001.label = 1;
                objRequest = request(url, c00001);
                if (objRequest == coroutine_suspended) {
                    return coroutine_suspended;
                }
                url2 = url;
                animesCloud = this;
                document = (Document) objRequest;
                elementSelectFirst = document.selectFirst("h1");
                if (elementSelectFirst != null || (strText3 = elementSelectFirst.text()) == null || (title = StringsKt.trim(strText3).toString()) == null) {
                    title = "";
                }
                scoreValue = StringsKt.toDoubleOrNull(document.select("div.custom_fields:contains(Pontuação) span.valor strong").text());
                it = document.select("div.custom_fields").iterator();
                do {
                    if (it.hasNext()) {
                        next = it.next();
                    } else {
                        next = null;
                    }
                    element = (Element) next;
                    if (element != null || (elementSelectFirst6 = element.selectFirst("span.valor")) == null) {
                        durationText = null;
                    } else {
                        durationText = elementSelectFirst6.text();
                    }
                    Iterable $this$forEach$iv$iv$iv = document.select("div.person");
                    destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
                        Element it2 = (Element) element$iv$iv;
                        elementSelectFirst5 = it2.selectFirst("div.data div.name a");
                        if (elementSelectFirst5 != null || (name = elementSelectFirst5.text()) == null) {
                            pair = null;
                        } else {
                            Element elementSelectFirst7 = it2.selectFirst("div.data div.caracter");
                            if (elementSelectFirst7 == null || (role = elementSelectFirst7.text()) == null) {
                                role = "";
                            }
                            Element elementSelectFirst8 = it2.selectFirst("div.img img");
                            String img = elementSelectFirst8 != null ? elementSelectFirst8.attr("src") : null;
                            pair = new Pair(new Actor(name, img), role);
                        }
                        if (pair != null) {
                            destination$iv$iv.add(pair);
                        }
                    }
                    actors = (List) destination$iv$iv;
                    if (!StringsKt.contains$default(url2, "/filme/", false, 2, (Object) null) || document.select("div#seasons").isEmpty()) {
                        z = true;
                    } else {
                        z = false;
                    }
                    isMovie = z;
                    if (isMovie) {
                        tvType = TvType.AnimeMovie;
                    } else {
                        tvType = TvType.Anime;
                    }
                    type = tvType;
                    elementSelectFirst2 = document.selectFirst("div.wp-content p:first-child");
                    if (elementSelectFirst2 != null || (strText2 = elementSelectFirst2.text()) == null || (string = StringsKt.trim(strText2).toString()) == null) {
                        elementSelectFirst3 = document.selectFirst("div.wp-content");
                        if (elementSelectFirst3 != null || (strText = elementSelectFirst3.text()) == null) {
                            plotText = null;
                        } else {
                            plotText = StringsKt.trim(strText).toString();
                        }
                    } else {
                        plotText = string;
                    }
                    elementSelectFirst4 = document.selectFirst("div.g-item a");
                    if (elementSelectFirst4 != null || (strAttr = elementSelectFirst4.attr("href")) == null) {
                        finalPoster = null;
                    } else {
                        finalPoster = StringsKt.trim(strAttr).toString();
                    }
                    if (isMovie) {
                        AnonymousClass2 anonymousClass2 = new AnonymousClass2(finalPoster, plotText, scoreValue, durationText, actors, null);
                        c00001.L$0 = null;
                        c00001.L$1 = null;
                        c00001.label = 2;
                        objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(animesCloud, title, url2, type, url2, anonymousClass2, c00001);
                        if (objNewMovieLoadResponse == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        return (LoadResponse) objNewMovieLoadResponse;
                    }
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3(finalPoster, plotText, scoreValue, durationText, actors, animesCloud.loadEpisodesFromPage(document, url2), null);
                    c00001.L$0 = null;
                    c00001.L$1 = null;
                    c00001.label = 3;
                    $result = MainAPIKt.newAnimeLoadResponse$default(animesCloud, title, url2, type, false, anonymousClass3, c00001, 8, (Object) null);
                    if ($result == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return (LoadResponse) $result;
                } while (!StringsKt.contains$default(((Element) next).text(), "Duração", false, 2, (Object) null));
                element = (Element) next;
                if (element != null) {
                    durationText = null;
                } else {
                    durationText = null;
                }
                Iterable $this$forEach$iv$iv$iv2 = document.select("div.person");
                destination$iv$iv = new ArrayList();
                while (r17.hasNext()) {
                    Element it3 = (Element) element$iv$iv;
                    elementSelectFirst5 = it3.selectFirst("div.data div.name a");
                    if (elementSelectFirst5 != null) {
                    }
                    pair = null;
                    if (pair != null) {
                        destination$iv$iv.add(pair);
                    }
                }
                actors = (List) destination$iv$iv;
                if (StringsKt.contains$default(url2, "/filme/", false, 2, (Object) null)) {
                    z = true;
                } else {
                    z = true;
                }
                isMovie = z;
                if (isMovie) {
                    tvType = TvType.AnimeMovie;
                } else {
                    tvType = TvType.Anime;
                }
                type = tvType;
                elementSelectFirst2 = document.selectFirst("div.wp-content p:first-child");
                if (elementSelectFirst2 != null) {
                    elementSelectFirst3 = document.selectFirst("div.wp-content");
                    if (elementSelectFirst3 != null) {
                        plotText = null;
                    } else {
                        plotText = null;
                    }
                } else {
                    elementSelectFirst3 = document.selectFirst("div.wp-content");
                    if (elementSelectFirst3 != null) {
                        plotText = null;
                    } else {
                        plotText = null;
                    }
                }
                elementSelectFirst4 = document.selectFirst("div.g-item a");
                if (elementSelectFirst4 != null) {
                    finalPoster = null;
                } else {
                    finalPoster = null;
                }
                if (isMovie) {
                    AnonymousClass2 anonymousClass4 = new AnonymousClass2(finalPoster, plotText, scoreValue, durationText, actors, null);
                    c00001.L$0 = null;
                    c00001.L$1 = null;
                    c00001.label = 2;
                    objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(animesCloud, title, url2, type, url2, anonymousClass4, c00001);
                    if (objNewMovieLoadResponse == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return (LoadResponse) objNewMovieLoadResponse;
                }
                AnonymousClass3 anonymousClass5 = new AnonymousClass3(finalPoster, plotText, scoreValue, durationText, actors, animesCloud.loadEpisodesFromPage(document, url2), null);
                c00001.L$0 = null;
                c00001.L$1 = null;
                c00001.label = 3;
                $result = MainAPIKt.newAnimeLoadResponse$default(animesCloud, title, url2, type, false, anonymousClass5, c00001, 8, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return (LoadResponse) $result;
            case 1:
                String url3 = (String) c00001.L$1;
                AnimesCloud animesCloud2 = (AnimesCloud) c00001.L$0;
                ResultKt.throwOnFailure($result);
                url2 = url3;
                animesCloud = animesCloud2;
                objRequest = $result;
                document = (Document) objRequest;
                elementSelectFirst = document.selectFirst("h1");
                if (elementSelectFirst != null) {
                    title = "";
                } else {
                    title = "";
                }
                scoreValue = StringsKt.toDoubleOrNull(document.select("div.custom_fields:contains(Pontuação) span.valor strong").text());
                it = document.select("div.custom_fields").iterator();
                do {
                    if (it.hasNext()) {
                        next = it.next();
                    } else {
                        next = null;
                    }
                    element = (Element) next;
                    if (element != null) {
                        durationText = null;
                    } else {
                        durationText = null;
                    }
                    Iterable $this$forEach$iv$iv$iv3 = document.select("div.person");
                    destination$iv$iv = new ArrayList();
                    while (r17.hasNext()) {
                        Element it4 = (Element) element$iv$iv;
                        elementSelectFirst5 = it4.selectFirst("div.data div.name a");
                        if (elementSelectFirst5 != null) {
                            break;
                        }
                        pair = null;
                        if (pair != null) {
                            destination$iv$iv.add(pair);
                        }
                    }
                    actors = (List) destination$iv$iv;
                    if (StringsKt.contains$default(url2, "/filme/", false, 2, (Object) null)) {
                        z = true;
                    } else {
                        z = true;
                    }
                    isMovie = z;
                    if (isMovie) {
                        tvType = TvType.AnimeMovie;
                    } else {
                        tvType = TvType.Anime;
                    }
                    type = tvType;
                    elementSelectFirst2 = document.selectFirst("div.wp-content p:first-child");
                    if (elementSelectFirst2 != null) {
                        elementSelectFirst3 = document.selectFirst("div.wp-content");
                        if (elementSelectFirst3 != null) {
                            plotText = null;
                        } else {
                            plotText = null;
                        }
                    } else {
                        elementSelectFirst3 = document.selectFirst("div.wp-content");
                        if (elementSelectFirst3 != null) {
                            plotText = null;
                        } else {
                            plotText = null;
                        }
                    }
                    elementSelectFirst4 = document.selectFirst("div.g-item a");
                    if (elementSelectFirst4 != null) {
                        finalPoster = null;
                    } else {
                        finalPoster = null;
                    }
                    if (isMovie) {
                        AnonymousClass2 anonymousClass6 = new AnonymousClass2(finalPoster, plotText, scoreValue, durationText, actors, null);
                        c00001.L$0 = null;
                        c00001.L$1 = null;
                        c00001.label = 2;
                        objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(animesCloud, title, url2, type, url2, anonymousClass6, c00001);
                        if (objNewMovieLoadResponse == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        return (LoadResponse) objNewMovieLoadResponse;
                    }
                    AnonymousClass3 anonymousClass7 = new AnonymousClass3(finalPoster, plotText, scoreValue, durationText, actors, animesCloud.loadEpisodesFromPage(document, url2), null);
                    c00001.L$0 = null;
                    c00001.L$1 = null;
                    c00001.label = 3;
                    $result = MainAPIKt.newAnimeLoadResponse$default(animesCloud, title, url2, type, false, anonymousClass7, c00001, 8, (Object) null);
                    if ($result == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return (LoadResponse) $result;
                } while (!StringsKt.contains$default(((Element) next).text(), "Duração", false, 2, (Object) null));
                element = (Element) next;
                if (element != null) {
                    durationText = null;
                } else {
                    durationText = null;
                }
                Iterable $this$forEach$iv$iv$iv4 = document.select("div.person");
                destination$iv$iv = new ArrayList();
                while (r17.hasNext()) {
                    Element it5 = (Element) element$iv$iv;
                    elementSelectFirst5 = it5.selectFirst("div.data div.name a");
                    if (elementSelectFirst5 != null) {
                        break;
                    }
                    pair = null;
                    if (pair != null) {
                        destination$iv$iv.add(pair);
                    }
                }
                actors = (List) destination$iv$iv;
                if (StringsKt.contains$default(url2, "/filme/", false, 2, (Object) null)) {
                    z = true;
                } else {
                    z = true;
                }
                isMovie = z;
                if (isMovie) {
                    tvType = TvType.AnimeMovie;
                } else {
                    tvType = TvType.Anime;
                }
                type = tvType;
                elementSelectFirst2 = document.selectFirst("div.wp-content p:first-child");
                if (elementSelectFirst2 != null) {
                    elementSelectFirst3 = document.selectFirst("div.wp-content");
                    if (elementSelectFirst3 != null) {
                        plotText = null;
                    } else {
                        plotText = null;
                    }
                } else {
                    elementSelectFirst3 = document.selectFirst("div.wp-content");
                    if (elementSelectFirst3 != null) {
                        plotText = null;
                    } else {
                        plotText = null;
                    }
                }
                elementSelectFirst4 = document.selectFirst("div.g-item a");
                if (elementSelectFirst4 != null) {
                    finalPoster = null;
                } else {
                    finalPoster = null;
                }
                if (isMovie) {
                    AnonymousClass2 anonymousClass8 = new AnonymousClass2(finalPoster, plotText, scoreValue, durationText, actors, null);
                    c00001.L$0 = null;
                    c00001.L$1 = null;
                    c00001.label = 2;
                    objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(animesCloud, title, url2, type, url2, anonymousClass8, c00001);
                    if (objNewMovieLoadResponse == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return (LoadResponse) objNewMovieLoadResponse;
                }
                AnonymousClass3 anonymousClass9 = new AnonymousClass3(finalPoster, plotText, scoreValue, durationText, actors, animesCloud.loadEpisodesFromPage(document, url2), null);
                c00001.L$0 = null;
                c00001.L$1 = null;
                c00001.label = 3;
                $result = MainAPIKt.newAnimeLoadResponse$default(animesCloud, title, url2, type, false, anonymousClass9, c00001, 8, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return (LoadResponse) $result;
            case 2:
                ResultKt.throwOnFailure($result);
                objNewMovieLoadResponse = $result;
                return (LoadResponse) objNewMovieLoadResponse;
            case 3:
                ResultKt.throwOnFailure($result);
                return (LoadResponse) $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: renamed from: com.AnimesCloud.AnimesCloud$load$2, reason: invalid class name */
    /* JADX INFO: compiled from: AnimesCloud.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/MovieLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesCloud.AnimesCloud$load$2", f = "AnimesCloud.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<Pair<Actor, String>> $actors;
        final /* synthetic */ String $durationText;
        final /* synthetic */ String $finalPoster;
        final /* synthetic */ String $plotText;
        final /* synthetic */ Double $scoreValue;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, String str2, Double d, String str3, List<Pair<Actor, String>> list, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$finalPoster = str;
            this.$plotText = str2;
            this.$scoreValue = d;
            this.$durationText = str3;
            this.$actors = list;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass2 = new AnonymousClass2(this.$finalPoster, this.$plotText, this.$scoreValue, this.$durationText, this.$actors, continuation);
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
                    LoadResponse loadResponse = (MovieLoadResponse) this.L$0;
                    loadResponse.setPosterUrl(this.$finalPoster);
                    loadResponse.setPlot(this.$plotText);
                    loadResponse.setScore(Score.Companion.from10(this.$scoreValue));
                    LoadResponse.Companion.addDuration(loadResponse, this.$durationText);
                    LoadResponse.Companion.addActors(loadResponse, this.$actors);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.AnimesCloud.AnimesCloud$load$3, reason: invalid class name */
    /* JADX INFO: compiled from: AnimesCloud.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/AnimeLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesCloud.AnimesCloud$load$3", f = "AnimesCloud.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function2<AnimeLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<Pair<Actor, String>> $actors;
        final /* synthetic */ String $durationText;
        final /* synthetic */ Map<DubStatus, List<Episode>> $episodes;
        final /* synthetic */ String $finalPoster;
        final /* synthetic */ String $plotText;
        final /* synthetic */ Double $scoreValue;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass3(String str, String str2, Double d, String str3, List<Pair<Actor, String>> list, Map<DubStatus, ? extends List<Episode>> map, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$finalPoster = str;
            this.$plotText = str2;
            this.$scoreValue = d;
            this.$durationText = str3;
            this.$actors = list;
            this.$episodes = map;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass3 = new AnonymousClass3(this.$finalPoster, this.$plotText, this.$scoreValue, this.$durationText, this.$actors, this.$episodes, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        public final Object invoke(AnimeLoadResponse animeLoadResponse, Continuation<? super Unit> continuation) {
            return create(animeLoadResponse, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    LoadResponse loadResponse = (AnimeLoadResponse) this.L$0;
                    loadResponse.setPosterUrl(this.$finalPoster);
                    loadResponse.setPlot(this.$plotText);
                    loadResponse.setScore(Score.Companion.from10(this.$scoreValue));
                    LoadResponse.Companion.addDuration(loadResponse, this.$durationText);
                    LoadResponse.Companion.addActors(loadResponse, this.$actors);
                    DubStatus dubStatus = DubStatus.Subbed;
                    List<Episode> listEmptyList = this.$episodes.get(DubStatus.Subbed);
                    if (listEmptyList == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    MainAPIKt.addEpisodes(loadResponse, dubStatus, listEmptyList);
                    DubStatus dubStatus2 = DubStatus.Dubbed;
                    List<Episode> listEmptyList2 = this.$episodes.get(DubStatus.Dubbed);
                    if (listEmptyList2 == null) {
                        listEmptyList2 = CollectionsKt.emptyList();
                    }
                    MainAPIKt.addEpisodes(loadResponse, dubStatus2, listEmptyList2);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final Map<DubStatus, List<Episode>> loadEpisodesFromPage(Document document, String baseUrl) {
        Elements elements = document.select("div#seasons div.episode-card");
        Iterable $this$map$iv = (Iterable) elements;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            final Element episodeElement = (Element) item$iv$iv;
            String episodeUrl = MainAPIKt.fixUrl(this, episodeElement.select("h3.episode-title a").attr("href"));
            String numText = episodeElement.select("a.episode-mini-number").text();
            Elements elements2 = elements;
            final MatchResult match = Regex.find$default(new Regex("^(\\d+\\s*-\\s*)?(\\d+)$"), numText, 0, 2, (Object) null);
            destination$iv$iv.add(MainAPIKt.newEpisode(this, episodeUrl, new Function1() { // from class: com.AnimesCloud.AnimesCloud$$ExternalSyntheticLambda1
                public final Object invoke(Object obj) {
                    return AnimesCloud.loadEpisodesFromPage$lambda$15$lambda$14(episodeElement, match, (Episode) obj);
                }
            }));
            elements = elements2;
        }
        List episodeList = (List) destination$iv$iv;
        return MapsKt.mapOf(TuplesKt.to(StringsKt.contains$default(baseUrl, "dublado", false, 2, (Object) null) ? DubStatus.Dubbed : DubStatus.Subbed, episodeList));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadEpisodesFromPage$lambda$15$lambda$14(Element $episodeElement, MatchResult $match, Episode $this$newEpisode) {
        Integer intOrNull;
        List groupValues;
        String str;
        Integer intOrNull2;
        List groupValues2;
        String str2;
        $this$newEpisode.setName(StringsKt.trim($episodeElement.select("h3.episode-title a").text()).toString());
        Integer num = 1;
        if ($match == null || (groupValues2 = $match.getGroupValues()) == null || (str2 = (String) groupValues2.get(2)) == null || (intOrNull = StringsKt.toIntOrNull(str2)) == null) {
            intOrNull = num;
        }
        $this$newEpisode.setEpisode(intOrNull);
        if ($match != null && (groupValues = $match.getGroupValues()) != null && (str = (String) groupValues.get(1)) != null && (intOrNull2 = StringsKt.toIntOrNull(str)) != null) {
            num = intOrNull2;
        }
        $this$newEpisode.setSeason(num);
        return Unit.INSTANCE;
    }

    @Nullable
    public Object loadLinks(@NotNull String data, boolean isCasting, @NotNull Function1<? super SubtitleFile, Unit> function1, @NotNull Function1<? super ExtractorLink, Unit> function2, @NotNull Continuation<? super Boolean> continuation) {
        return AnimesCloudExtractor.INSTANCE.extractVideoLinks(data, getMainUrl(), getName(), function2, continuation);
    }
}
