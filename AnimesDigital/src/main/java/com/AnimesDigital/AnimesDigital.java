package com.AnimesDigital;

import com.lagradost.cloudstream3.AnimeLoadResponse;
import com.lagradost.cloudstream3.AnimeSearchResponse;
import com.lagradost.cloudstream3.DubStatus;
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
import com.lagradost.cloudstream3.SearchResponse;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.cloudstream3.utils.ExtractorLinkType;
import com.lagradost.cloudstream3.utils.Qualities;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: AnimesDigital.kt */
/* JADX INFO: loaded from: /tmp/cs3/AnimesDigital/classes.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u001e\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0096@¢\u0006\u0002\u0010%J\u0016\u0010&\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0082@¢\u0006\u0002\u0010'J\u0018\u0010(\u001a\u0004\u0018\u00010\u00052\u0006\u0010)\u001a\u00020\u0005H\u0082@¢\u0006\u0002\u0010*J\u001e\u0010+\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0082@¢\u0006\u0002\u0010%J(\u0010,\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\"2\u0006\u0010.\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\u0005H\u0002J4\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005012\u0006\u0010!\u001a\u00020\"2\u0006\u0010.\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u0005H\u0002J\u001c\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005012\u0006\u00104\u001a\u00020\u0005H\u0002J\u0016\u00105\u001a\b\u0012\u0004\u0012\u0002060\u001c2\u0006\u00107\u001a\u00020\u0005H\u0002J\u0010\u00108\u001a\u00020\"2\u0006\u00107\u001a\u00020\u0005H\u0002J\u0016\u00109\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0082@¢\u0006\u0002\u0010'J\u001c\u0010:\u001a\b\u0012\u0004\u0012\u0002060\u001c2\u0006\u0010;\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010*J\u0018\u0010<\u001a\u0004\u0018\u00010=2\u0006\u0010)\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010*J\u0015\u0010>\u001a\u0004\u0018\u00010\"*\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0002\u0010?J \u0010@\u001a\u0004\u0018\u00010=2\u0006\u0010)\u001a\u00020\u00052\u0006\u0010A\u001a\u00020BH\u0082@¢\u0006\u0002\u0010CJ\u001a\u0010D\u001a\u0004\u0018\u00010\u00052\u0006\u0010A\u001a\u00020B2\u0006\u0010)\u001a\u00020\u0005H\u0002J\u0012\u0010E\u001a\u0004\u0018\u00010\u00052\u0006\u0010)\u001a\u00020\u0005H\u0002J\u0018\u0010F\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u00052\u0006\u0010G\u001a\u00020\u0005H\u0002J \u0010H\u001a\u0004\u0018\u00010=2\u0006\u0010)\u001a\u00020\u00052\u0006\u0010A\u001a\u00020BH\u0082@¢\u0006\u0002\u0010CJ$\u0010I\u001a\b\u0012\u0004\u0012\u00020J0\u001c2\u0006\u0010)\u001a\u00020\u00052\u0006\u0010A\u001a\u00020BH\u0082@¢\u0006\u0002\u0010CJ\u0018\u0010K\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\"H\u0002J\u0016\u0010L\u001a\b\u0012\u0004\u0012\u00020J0\u001c2\u0006\u0010A\u001a\u00020BH\u0002J\u0018\u0010M\u001a\u00020\"2\u0006\u0010G\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0002J\u000e\u0010N\u001a\u0004\u0018\u000106*\u00020OH\u0002J\u000e\u0010P\u001a\u0004\u0018\u000106*\u00020OH\u0002JF\u0010Q\u001a\u00020\u000e2\u0006\u0010R\u001a\u00020\u00052\u0006\u0010S\u001a\u00020\u000e2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020V\u0012\u0004\u0012\u00020W0U2\u0012\u0010X\u001a\u000e\u0012\u0004\u0012\u00020Y\u0012\u0004\u0012\u00020W0UH\u0096@¢\u0006\u0002\u0010ZJ\u0012\u0010[\u001a\u0004\u0018\u00010\u00052\u0006\u0010\\\u001a\u00020\u0005H\u0002J\u0012\u0010]\u001a\u0004\u0018\u00010\u00052\u0006\u0010\\\u001a\u00020\u0005H\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0014\u0010\r\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0007\"\u0004\b\u0013\u0010\tR\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006^"}, d2 = {"Lcom/AnimesDigital/AnimesDigital;", "Lcom/lagradost/cloudstream3/MainAPI;", "<init>", "()V", "mainUrl", "", "getMainUrl", "()Ljava/lang/String;", "setMainUrl", "(Ljava/lang/String;)V", "name", "getName", "setName", "hasMainPage", "", "getHasMainPage", "()Z", "lang", "getLang", "setLang", "supportedTypes", "", "Lcom/lagradost/cloudstream3/TvType;", "getSupportedTypes", "()Ljava/util/Set;", "hasDownloadSupport", "getHasDownloadSupport", "mainPage", "", "Lcom/lagradost/cloudstream3/MainPageData;", "getMainPage", "()Ljava/util/List;", "Lcom/lagradost/cloudstream3/HomePageResponse;", "page", "", "request", "Lcom/lagradost/cloudstream3/MainPageRequest;", "(ILcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHomePage", "(Lcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSecurityToken", "url", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAnimesFromAPI", "buildReferrerUrl", "base", "typeUrl", "filterAudio", "buildPostData", "", "token", "apiHeaders", "ref", "parseApiResponse", "Lcom/lagradost/cloudstream3/SearchResponse;", "json", "extractTotalPage", "getFallbackPage", "search", "query", "load", "Lcom/lagradost/cloudstream3/LoadResponse;", "toStatus", "(Ljava/lang/String;)Ljava/lang/Integer;", "loadEpisode", "doc", "Lorg/jsoup/nodes/Document;", "(Ljava/lang/String;Lorg/jsoup/nodes/Document;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractAnimeMainPageUrl", "extractAnimeSlugFromUrl", "extractCurrentEpisodeNumber", "title", "loadAnime", "loadAllEpisodes", "Lcom/lagradost/cloudstream3/Episode;", "buildNextPageUrl", "extractEpisodesFromPage", "extractEpisodeNumber", "toSearchResult", "Lorg/jsoup/nodes/Element;", "toSearchResultAlternative", "loadLinks", "data", "isCasting", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractM3u8Url", "src", "decodeAnimesDigitalUrl", "AnimesDigital_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAnimesDigital.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnimesDigital.kt\ncom/AnimesDigital/AnimesDigital\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,351:1\n1611#2,9:352\n1863#2:361\n1864#2:363\n1620#2:364\n1611#2,9:365\n1863#2:374\n1864#2:376\n1620#2:377\n1611#2,9:378\n1863#2:387\n1864#2:389\n1620#2:390\n1557#2:392\n1628#2,3:393\n774#2:396\n865#2:397\n2632#2,3:398\n866#2:401\n1062#2:402\n1611#2,9:403\n1863#2:412\n1864#2:414\n1620#2:415\n1557#2:416\n1628#2,3:417\n1863#2:420\n1864#2:422\n1863#2,2:429\n1863#2,2:431\n1863#2,2:433\n1#3:362\n1#3:375\n1#3:388\n1#3:391\n1#3:413\n1#3:421\n434#4:423\n507#4,5:424\n*S KotlinDebug\n*F\n+ 1 AnimesDigital.kt\ncom/AnimesDigital/AnimesDigital\n*L\n38#1:352,9\n38#1:361\n38#1:363\n38#1:364\n121#1:365,9\n121#1:374\n121#1:376\n121#1:377\n129#1:378,9\n129#1:387\n129#1:389\n129#1:390\n195#1:392\n195#1:393,3\n226#1:396\n226#1:397\n226#1:398,3\n226#1:401\n232#1:402\n238#1:403,9\n238#1:412\n238#1:414\n238#1:415\n249#1:416\n249#1:417,3\n250#1:420\n250#1:422\n299#1:429,2\n318#1:431,2\n345#1:433,2\n38#1:362\n121#1:375\n129#1:388\n238#1:413\n261#1:423\n261#1:424,5\n*E\n"})
public final class AnimesDigital extends MainAPI {

    @NotNull
    private String mainUrl = "https://animesdigital.org";

    @NotNull
    private String name = "AnimesDigital";
    private final boolean hasMainPage = true;

    @NotNull
    private String lang = "pt-br";

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(TvType.Anime);
    private final boolean hasDownloadSupport = true;

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to(getMainUrl() + "/home", "Animes - Últimos Episódios"), TuplesKt.to(getMainUrl() + "/animes-legendados-online001", "Animes - Legendados"), TuplesKt.to(getMainUrl() + "/animes-dublado001", "Animes - Dublados"), TuplesKt.to(getMainUrl() + "/filmes001", "Animes - Filmes"), TuplesKt.to(getMainUrl() + "/desenhos-online001", "Desenhos Animados")});

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$getAnimesFromAPI$1, reason: invalid class name */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {0, 0, 0, 0, 0, 1, 1, 1}, l = {59, 64, 68}, m = "getAnimesFromAPI", n = {"this", "request", "typeUrl", "filterAudio", "page", "this", "request", "page"}, s = {"L$0", "L$1", "L$2", "L$3", "I$0", "L$0", "L$1", "I$0"})
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesDigital.this.getAnimesFromAPI(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$getFallbackPage$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {0, 0}, l = {120}, m = "getFallbackPage", n = {"this", "request"}, s = {"L$0", "L$1"})
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
            return AnimesDigital.this.getFallbackPage(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$getHomePage$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {0, 0}, l = {37}, m = "getHomePage", n = {"this", "request"}, s = {"L$0", "L$1"})
    final class C00011 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C00011(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            try {
                return AnimesDigital.this.getHomePage(null, (Continuation) this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$getSecurityToken$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {}, l = {47}, m = "getSecurityToken", n = {}, s = {})
    final class C00021 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C00021(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesDigital.this.getSecurityToken(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$load$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {0, 0}, l = {134, 135, 135}, m = "load", n = {"this", "url"}, s = {"L$0", "L$1"})
    final class C00031 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C00031(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesDigital.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$loadAllEpisodes$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {0, 0, 0, 0, 0}, l = {229}, m = "loadAllEpisodes", n = {"this", "url", "episodes", "page", "hasNext"}, s = {"L$0", "L$1", "L$2", "I$0", "I$1"})
    final class C00041 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C00041(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesDigital.this.loadAllEpisodes(null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$loadAnime$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {200, 207, 208}, m = "loadAnime", n = {"this", "url", "title", "poster", "description", "tagsList", "year", "tvType", "dubStatus"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8"})
    final class C00051 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        int label;
        /* synthetic */ Object result;

        C00051(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesDigital.this.loadAnime(null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$loadLinks$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6}, l = {288, 304, 312, 323, 331, 333, 338}, m = "loadLinks", n = {"this", "subtitleCallback", "callback", "found", "realUrl", "epNum", "this", "subtitleCallback", "callback", "found", "realUrl", "fhdLinks", "otherLinks", "this", "subtitleCallback", "callback", "found", "realUrl", "fhdLinks", "otherLinks", "this", "subtitleCallback", "callback", "found", "realUrl", "fhdLinks", "otherLinks", "epNum", "this", "subtitleCallback", "callback", "found", "realUrl", "fhdLinks", "otherLinks", "url", "epNum", "this", "subtitleCallback", "callback", "found", "realUrl", "fhdLinks", "otherLinks", "epNum", "this", "subtitleCallback", "callback", "found", "realUrl", "fhdLinks", "otherLinks", "epNum"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "I$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "I$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$8", "I$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "I$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "I$0"})
    final class C00071 extends ContinuationImpl {
        int I$0;
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

        C00071(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesDigital.this.loadLinks(null, false, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$search$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital", f = "AnimesDigital.kt", i = {0}, l = {128}, m = "search", n = {"this"}, s = {"L$0"})
    final class C00081 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C00081(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesDigital.this.search(null, (Continuation) this);
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

    @NotNull
    public Set<TvType> getSupportedTypes() {
        return this.supportedTypes;
    }

    public boolean getHasDownloadSupport() {
        return this.hasDownloadSupport;
    }

    @NotNull
    public List<MainPageData> getMainPage() {
        return this.mainPage;
    }

    @Nullable
    public Object getMainPage(int page, @NotNull MainPageRequest request, @NotNull Continuation<? super HomePageResponse> continuation) {
        try {
            return StringsKt.contains(request.getData(), "home", false) ? getHomePage(request, continuation) : getAnimesFromAPI(page, request, continuation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object getHomePage(MainPageRequest request, Continuation<? super HomePageResponse> continuation) throws IOException {
        C00011 c00011;
        MainPageRequest request2;
        AnimesDigital animesDigital;
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
                String data = request.getData();
                c00011.L$0 = this;
                c00011.L$1 = request;
                c00011.label = 1;
                $result = app.get(data, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00011);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                request2 = request;
                animesDigital = this;
                break;
            case 1:
                request2 = (MainPageRequest) c00011.L$1;
                animesDigital = (AnimesDigital) c00011.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document document = ((NiceResponse) $result).getDocument();
        Iterable $this$forEach$iv$iv$iv = document.select(".itemE, .itemA");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element it = (Element) element$iv$iv;
            SearchResponse searchResult = animesDigital.toSearchResult(it);
            if (searchResult != null) {
                destination$iv$iv.add(searchResult);
            }
        }
        List home = (List) destination$iv$iv;
        return MainAPIKt.newHomePageResponse(new HomePageList(request2.getName(), home, true), Boxing.boxBoolean(false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:24:0x0082 A[Catch: Exception -> 0x008b, TRY_LEAVE, TryCatch #2 {Exception -> 0x008b, blocks: (B:22:0x0074, B:24:0x0082, B:19:0x006d), top: B:35:0x006d }] */
    /* JADX WARN: Code duplicated, block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object getSecurityToken(String url, Continuation<? super String> continuation) {
        C00021 c00021;
        Element elementSelectFirst;
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
        C00021 c00022 = c00021;
        Object $result = c00022.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00022.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                try {
                    Requests app = MainActivityKt.getApp();
                    c00022.label = 1;
                    try {
                        $result = app.get(url, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00022);
                        if ($result == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        elementSelectFirst = ((NiceResponse) $result).getDocument().selectFirst(".menu_filter_box");
                        if (elementSelectFirst != null) {
                            return elementSelectFirst.attr("data-secury");
                        }
                        return null;
                    } catch (Exception e) {
                        return null;
                    }
                } catch (Exception e2) {
                    return null;
                }
            case 1:
                try {
                    ResultKt.throwOnFailure($result);
                    elementSelectFirst = ((NiceResponse) $result).getDocument().selectFirst(".menu_filter_box");
                    if (elementSelectFirst != null) {
                        return elementSelectFirst.attr("data-secury");
                    }
                    return null;
                } catch (Exception e3) {
                    return null;
                }
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:38:0x0112  */
    /* JADX WARN: Code duplicated, block: B:45:0x0190 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:46:0x0191  */
    /* JADX WARN: Code duplicated, block: B:49:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:50:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:53:0x01bc  */
    /* JADX WARN: Code duplicated, block: B:54:0x01bd  */
    /* JADX WARN: Code duplicated, block: B:65:0x01f6 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object getAnimesFromAPI(int page, MainPageRequest request, Continuation<? super HomePageResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        Pair pair;
        Object securityToken;
        AnimesDigital animesDigital;
        int page2;
        String typeUrl;
        MainPageRequest request2;
        String filterAudio;
        String str;
        Object obj;
        AnonymousClass1 anonymousClass2;
        AnimesDigital animesDigital2;
        MainPageRequest request3;
        AnimesDigital animesDigital3;
        boolean z = false;
        MainPageRequest request4;
        NiceResponse response;
        int page3;
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
        AnonymousClass1 anonymousClass3 = anonymousClass1;
        Object $result = anonymousClass3.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (anonymousClass3.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                if (StringsKt.contains(request.getData(), "animes-dublado", false)) {
                    pair = TuplesKt.to("animes", "dublado");
                } else if (StringsKt.contains(request.getData(), "animes-legendados", false)) {
                    pair = TuplesKt.to("animes", "legendado");
                } else if (StringsKt.contains(request.getData(), "filmes", false)) {
                    pair = TuplesKt.to("filmes", "0");
                } else {
                    pair = StringsKt.contains(request.getData(), "desenhos", false) ? TuplesKt.to("desenhos", "0") : TuplesKt.to("animes", "animes");
                }
                String typeUrl2 = (String) pair.component1();
                String filterAudio2 = (String) pair.component2();
                String data = request.getData();
                anonymousClass3.L$0 = this;
                anonymousClass3.L$1 = request;
                anonymousClass3.L$2 = typeUrl2;
                anonymousClass3.L$3 = filterAudio2;
                anonymousClass3.I$0 = page;
                anonymousClass3.label = 1;
                securityToken = getSecurityToken(data, anonymousClass3);
                if (securityToken == coroutine_suspended) {
                    return coroutine_suspended;
                }
                animesDigital = this;
                page2 = page;
                typeUrl = typeUrl2;
                request2 = request;
                filterAudio = filterAudio2;
                str = (String) securityToken;
                if (str == null) {
                    str = "c1deb78cd4";
                }
                String token = str;
                String refUrl = animesDigital.buildReferrerUrl(request2.getData(), page2, typeUrl, filterAudio);
                Map<String, String> mapBuildPostData = animesDigital.buildPostData(page2, typeUrl, filterAudio, token);
                try {
                    Requests app = MainActivityKt.getApp();
                    String str2 = animesDigital.getMainUrl() + "/func/listanime";
                    Map<String, String> mapApiHeaders = animesDigital.apiHeaders(refUrl);
                    anonymousClass3.L$0 = animesDigital;
                    anonymousClass3.L$1 = request2;
                    anonymousClass3.L$2 = null;
                    anonymousClass3.L$3 = null;
                    anonymousClass3.I$0 = page2;
                    anonymousClass3.label = 2;
                    animesDigital3 = animesDigital;
                    z = true;
                    request4 = request2;
                    anonymousClass2 = anonymousClass3;
                    try {
                        $result = app.post(str2, mapApiHeaders, (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), mapBuildPostData, (List) null, (Object) null, (RequestBody) null, true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), anonymousClass2);
                        obj = coroutine_suspended;
                        if ($result == obj) {
                            return obj;
                        }
                        animesDigital2 = animesDigital3;
                        request3 = request4;
                        try {
                            response = (NiceResponse) $result;
                            List<SearchResponse> apiResponse = animesDigital2.parseApiResponse(response.getText());
                            if (page2 < animesDigital2.extractTotalPage(response.getText())) {
                                page3 = 1;
                            } else {
                                page3 = 0;
                            }
                            HomePageList homePageList = new HomePageList(request3.getName(), apiResponse, false);
                            if (page3 == 0) {
                                z = false;
                            }
                            return MainAPIKt.newHomePageResponse(homePageList, Boxing.boxBoolean(z));
                        } catch (Exception e) {
                            AnonymousClass1 anonymousClass4 = anonymousClass2;
                            anonymousClass4.L$0 = null;
                            anonymousClass4.L$1 = null;
                            anonymousClass4.L$2 = null;
                            anonymousClass4.L$3 = null;
                            anonymousClass4.label = 3;
                            $result = animesDigital2.getFallbackPage(request3, anonymousClass4);
                            if ($result == obj) {
                                return obj;
                            }
                            return (HomePageResponse) $result;
                        }
                    } catch (Exception e2) {
                        obj = coroutine_suspended;
                        animesDigital2 = animesDigital3;
                        request3 = request4;
                        AnonymousClass1 anonymousClass5 = anonymousClass2;
                        anonymousClass5.L$0 = null;
                        anonymousClass5.L$1 = null;
                        anonymousClass5.L$2 = null;
                        anonymousClass5.L$3 = null;
                        anonymousClass5.label = 3;
                        $result = animesDigital2.getFallbackPage(request3, anonymousClass5);
                        if ($result == obj) {
                            return obj;
                        }
                        return (HomePageResponse) $result;
                    }
                } catch (Exception e3) {
                    MainPageRequest mainPageRequest = request2;
                    obj = coroutine_suspended;
                    anonymousClass2 = anonymousClass3;
                    animesDigital2 = animesDigital;
                    request3 = mainPageRequest;
                }
                break;
            case 1:
                page2 = anonymousClass3.I$0;
                filterAudio = (String) anonymousClass3.L$3;
                typeUrl = (String) anonymousClass3.L$2;
                MainPageRequest request5 = (MainPageRequest) anonymousClass3.L$1;
                AnimesDigital animesDigital4 = (AnimesDigital) anonymousClass3.L$0;
                ResultKt.throwOnFailure($result);
                request2 = request5;
                animesDigital = animesDigital4;
                securityToken = $result;
                str = (String) securityToken;
                if (str == null) {
                    str = "c1deb78cd4";
                }
                String token2 = str;
                String refUrl2 = animesDigital.buildReferrerUrl(request2.getData(), page2, typeUrl, filterAudio);
                Map<String, String> mapBuildPostData2 = animesDigital.buildPostData(page2, typeUrl, filterAudio, token2);
                Requests app2 = MainActivityKt.getApp();
                String str3 = animesDigital.getMainUrl() + "/func/listanime";
                Map<String, String> mapApiHeaders2 = animesDigital.apiHeaders(refUrl2);
                anonymousClass3.L$0 = animesDigital;
                anonymousClass3.L$1 = request2;
                anonymousClass3.L$2 = null;
                anonymousClass3.L$3 = null;
                anonymousClass3.I$0 = page2;
                anonymousClass3.label = 2;
                animesDigital3 = animesDigital;
                z = true;
                request4 = request2;
                anonymousClass2 = anonymousClass3;
                $result = app2.post(str3, mapApiHeaders2, (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), mapBuildPostData2, (List) null, (Object) null, (RequestBody) null, true, app2.getDefaultCacheTime(), app2.getDefaultCacheTimeUnit(), app2.getDefaultTimeOut(), (Interceptor) null, true, app2.getResponseParser(), anonymousClass2);
                obj = coroutine_suspended;
                if ($result == obj) {
                    return obj;
                }
                animesDigital2 = animesDigital3;
                request3 = request4;
                response = (NiceResponse) $result;
                List<SearchResponse> apiResponse2 = animesDigital2.parseApiResponse(response.getText());
                if (page2 < animesDigital2.extractTotalPage(response.getText())) {
                    page3 = 1;
                } else {
                    page3 = 0;
                }
                HomePageList homePageList2 = new HomePageList(request3.getName(), apiResponse2, false);
                if (page3 == 0) {
                    z = false;
                }
                return MainAPIKt.newHomePageResponse(homePageList2, Boxing.boxBoolean(z));
            case 2:
                page2 = anonymousClass3.I$0;
                request3 = (MainPageRequest) anonymousClass3.L$1;
                animesDigital2 = (AnimesDigital) anonymousClass3.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    obj = coroutine_suspended;
                    anonymousClass2 = anonymousClass3;
                    z = true;
                    response = (NiceResponse) $result;
                    List<SearchResponse> apiResponse3 = animesDigital2.parseApiResponse(response.getText());
                    if (page2 < animesDigital2.extractTotalPage(response.getText())) {
                        page3 = 1;
                    } else {
                        page3 = 0;
                    }
                    HomePageList homePageList3 = new HomePageList(request3.getName(), apiResponse3, false);
                    if (page3 == 0) {
                        z = false;
                    }
                    return MainAPIKt.newHomePageResponse(homePageList3, Boxing.boxBoolean(z));
                } catch (Exception e4) {
                    obj = coroutine_suspended;
                    anonymousClass2 = anonymousClass3;
                    AnonymousClass1 anonymousClass6 = anonymousClass2;
                    anonymousClass6.L$0 = null;
                    anonymousClass6.L$1 = null;
                    anonymousClass6.L$2 = null;
                    anonymousClass6.L$3 = null;
                    anonymousClass6.label = 3;
                    $result = animesDigital2.getFallbackPage(request3, anonymousClass6);
                    if ($result == obj) {
                        return obj;
                    }
                    return (HomePageResponse) $result;
                }
            case 3:
                ResultKt.throwOnFailure($result);
                return (HomePageResponse) $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return null;
    }

    private final String buildReferrerUrl(String base, int page, String typeUrl, String filterAudio) {
        StringBuilder sbAppend;
        char c;
        String query = CollectionsKt.joinToString(CollectionsKt.listOf(new String[]{"filter_letter=0", "type_url=" + typeUrl, "filter_audio=" + filterAudio, "filter_order=name", "filter_genre_add=", "filter_genre_del=", "pagina=" + page, "search=0", "limit=30"}), "&", "", "", -1, "...", (Function1) null);
        if (StringsKt.contains(base, "?", false)) {
            sbAppend = new StringBuilder().append(base);
            c = '&';
        } else {
            sbAppend = new StringBuilder().append(base);
            c = '?';
        }
        return sbAppend.append(c).append(query).toString();
    }

    private final Map<String, String> buildPostData(int page, String typeUrl, String filterAudio, String token) {
        return MapsKt.mapOf(new Pair[]{TuplesKt.to("token", token), TuplesKt.to("pagina", String.valueOf(page)), TuplesKt.to("search", "0"), TuplesKt.to("limit", "30"), TuplesKt.to("type", "lista"), TuplesKt.to("filters", "{\"filter_data\":\"filter_letter=0&type_url=" + typeUrl + "&filter_audio=" + filterAudio + "&filter_order=name\",\"filter_genre_add\":[],\"filter_genre_del\":[]}")});
    }

    private final Map<String, String> apiHeaders(String ref) {
        return MapsKt.mapOf(new Pair[]{TuplesKt.to("accept", "application/json, text/javascript, */*; q=0.01"), TuplesKt.to("content-type", "application/x-www-form-urlencoded; charset=UTF-8"), TuplesKt.to("x-requested-with", "XMLHttpRequest"), TuplesKt.to("referer", ref)});
    }

    private final List<SearchResponse> parseApiResponse(String json) {
        List list = new ArrayList();
        try {
            try {
                JSONArray arr = new JSONObject(json).optJSONArray("results");
                if (arr == null) {
                    return CollectionsKt.emptyList();
                }
                int length = arr.length();
                for (int i = 0; i < length; i++) {
                    String html = StringsKt.replace(StringsKt.replace(arr.optString(i), "\\\"", "\"", false), "\\/", "/", false);
                    Element elementSelectFirst = Jsoup.parseBodyFragment(html).selectFirst(".itemA");
                    if (elementSelectFirst != null) {
                        try {
                            SearchResponse sr = toSearchResultAlternative(elementSelectFirst);
                            if (sr != null) {
                                list.add(sr);
                            } else {
                                continue;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                return list;
            } catch (Exception e2) {
            }
        } catch (Exception e3) {
        }
        return null;
    }

    private final int extractTotalPage(String json) {
        try {
            return new JSONObject(json).optInt("total_page", 1);
        } catch (Exception e) {
            return 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object getFallbackPage(MainPageRequest request, Continuation<? super HomePageResponse> continuation) {
        C00001 c00001;
        MainPageRequest request2;
        AnimesDigital animesDigital;
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
                String data = request.getData();
                c00001.L$0 = this;
                c00001.L$1 = request;
                c00001.label = 1;
                $result = app.get(data, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00001);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                request2 = request;
                animesDigital = this;
                break;
            case 1:
                request2 = (MainPageRequest) c00001.L$1;
                animesDigital = (AnimesDigital) c00001.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document document = ((NiceResponse) $result).getDocument();
        Iterable $this$forEach$iv$iv$iv = document.select(".itemA, .anime-item, .item, .post, [class*='anime']");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element it = (Element) element$iv$iv;
            SearchResponse searchResultAlternative = animesDigital.toSearchResultAlternative(it);
            if (searchResultAlternative != null) {
                destination$iv$iv.add(searchResultAlternative);
            }
        }
        List home = (List) destination$iv$iv;
        boolean hasNext = !document.select(".pagination a.current + a, .pagination a:contains(Próximo)").isEmpty();
        return MainAPIKt.newHomePageResponse(new HomePageList(request2.getName(), home, false), Boxing.boxBoolean(hasNext));
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object search(@NotNull String query, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        C00081 c00081;
        AnimesDigital animesDigital;
        if (continuation instanceof C00081) {
            c00081 = (C00081) continuation;
            if ((c00081.label & Integer.MIN_VALUE) != 0) {
                c00081.label -= Integer.MIN_VALUE;
            } else {
                c00081 = new C00081(continuation);
            }
        } else {
            c00081 = new C00081(continuation);
        }
        Object $result = c00081.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00081.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Requests app = MainActivityKt.getApp();
                String str = getMainUrl() + "/?s=" + query;
                c00081.L$0 = this;
                c00081.label = 1;
                $result = app.get(str, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00081);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                animesDigital = this;
                break;
            case 1:
                animesDigital = (AnimesDigital) c00081.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document document = ((NiceResponse) $result).getDocument();
        Iterable $this$forEach$iv$iv$iv = document.select("div.itemE, div.itemA");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$forEach$iv$iv$iv) {
            Element it = (Element) element$iv$iv;
            SearchResponse searchResultAlternative = animesDigital.toSearchResultAlternative(it);
            if (searchResultAlternative != null) {
                destination$iv$iv.add(searchResultAlternative);
            }
        }
        return (List) destination$iv$iv;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:23:0x00b6 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x00b7 A[PHI: r15
      0x00b7: PHI (r15v7 '$result' java.lang.Object) = (r15v6 '$result' java.lang.Object), (r15v0 '$result' java.lang.Object) binds: [B:22:0x00b4, B:13:0x0039] A[DONT_GENERATE, DONT_INLINE], RETURN] */
    /* JADX WARN: Code duplicated, block: B:25:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:27:0x00c1 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:28:0x00c2 A[PHI: r15
      0x00c2: PHI (r15v8 '$result' java.lang.Object) = (r15v5 '$result' java.lang.Object), (r15v0 '$result' java.lang.Object) binds: [B:26:0x00bf, B:12:0x0032] A[DONT_GENERATE, DONT_INLINE], RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object load(@NotNull String url, @NotNull Continuation<? super LoadResponse> continuation) {
        C00031 c00031;
        Object obj;
        AnimesDigital animesDigital;
        String url2;
        Document document;
        boolean zContains$default;
        if (continuation instanceof C00031) {
            c00031 = (C00031) continuation;
            if ((c00031.label & Integer.MIN_VALUE) != 0) {
                c00031.label -= Integer.MIN_VALUE;
            } else {
                c00031 = new C00031(continuation);
            }
        } else {
            c00031 = new C00031(continuation);
        }
        Object $result = c00031.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00031.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Requests app = MainActivityKt.getApp();
                c00031.L$0 = this;
                c00031.L$1 = url;
                c00031.label = 1;
                $result = app.get(url, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00031);
                obj = coroutine_suspended;
                if ($result == obj) {
                    return obj;
                }
                animesDigital = this;
                url2 = url;
                document = ((NiceResponse) $result).getDocument();
                zContains$default = StringsKt.contains(url2, "/video/a/", false);
                c00031.L$0 = null;
                c00031.L$1 = null;
                if (zContains$default) {
                    c00031.label = 2;
                    $result = animesDigital.loadEpisode(url2, document, c00031);
                    if ($result == obj) {
                        return obj;
                    }
                    return $result;
                }
                c00031.label = 3;
                $result = animesDigital.loadAnime(url2, document, c00031);
                if ($result == obj) {
                    return obj;
                }
                return $result;
            case 1:
                String url3 = (String) c00031.L$1;
                AnimesDigital animesDigital2 = (AnimesDigital) c00031.L$0;
                ResultKt.throwOnFailure($result);
                animesDigital = animesDigital2;
                url2 = url3;
                obj = coroutine_suspended;
                document = ((NiceResponse) $result).getDocument();
                zContains$default = StringsKt.contains(url2, "/video/a/", false);
                c00031.L$0 = null;
                c00031.L$1 = null;
                if (zContains$default) {
                    c00031.label = 2;
                    $result = animesDigital.loadEpisode(url2, document, c00031);
                    if ($result == obj) {
                        return obj;
                    }
                    return $result;
                }
                c00031.label = 3;
                $result = animesDigital.loadAnime(url2, document, c00031);
                if ($result == obj) {
                    return obj;
                }
                return $result;
            case 2:
                ResultKt.throwOnFailure($result);
                return $result;
            case 3:
                ResultKt.throwOnFailure($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    private final Integer toStatus(String $this$toStatus) {
        if ($this$toStatus == null) {
            return null;
        }
        String it = $this$toStatus.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(it, "toLowerCase(...)");
        if (it != null) {
            if (StringsKt.contains(it, "completo", false)) {
                return 2;
            }
            return StringsKt.contains(it, "em lançamento", false) ? 1 : null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:23:0x0069  */
    public final Object loadEpisode(String url, Document doc, Continuation<? super LoadResponse> continuation) {
        String title;
        String animeTitle;
        String strText = null;
        String strText2;
        Element elementSelectFirst = doc.selectFirst("meta[property=og:title]");
        if (elementSelectFirst == null || (title = elementSelectFirst.attr("content")) == null) {
            return null;
        }
        AnimesDigital animesDigital = this;
        Element elementSelectFirst2 = doc.selectFirst("meta[property=og:image]");
        String poster = MainAPIKt.fixUrlNull(animesDigital, elementSelectFirst2 != null ? elementSelectFirst2.attr("content") : null);
        Element elementSelectFirst3 = doc.selectFirst(".info span:contains(Anime) + span");
        if (elementSelectFirst3 == null || (strText2 = elementSelectFirst3.text()) == null) {
            Element elementSelectFirst4 = doc.selectFirst("#anime_title");
            if (elementSelectFirst4 == null || (strText = elementSelectFirst4.text()) == null) {
                animeTitle = title;
            } else {
                String strReplace = new Regex(" Episódio \\d+").replace(strText, "");
                if (strReplace != null) {
                    animeTitle = StringsKt.trim(strReplace).toString();
                } else {
                    animeTitle = title;
                }
            }
        } else {
            animeTitle = strText2;
        }
        final int episodeNum = extractCurrentEpisodeNumber(url, title);
        Episode currentEpisode = MainAPIKt.newEpisode(this, url + "|#|" + episodeNum, new Function1() { // from class: com.AnimesDigital.AnimesDigital$$ExternalSyntheticLambda5
            public final Object invoke(Object obj) {
                return AnimesDigital.loadEpisode$lambda$5(episodeNum, (Episode) obj);
            }
        });
        String animeUrl = extractAnimeMainPageUrl(doc, url);
        return MainAPIKt.newAnimeLoadResponse(this, animeTitle, url, TvType.Anime, false, new C00062(poster, doc, currentEpisode, animeUrl, this, null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadEpisode$lambda$5(int $episodeNum, Episode $this$newEpisode) {
        $this$newEpisode.setName("Episódio " + $episodeNum);
        $this$newEpisode.setEpisode(Integer.valueOf($episodeNum));
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$loadEpisode$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/AnimeLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital$loadEpisode$2", f = "AnimesDigital.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C00062 extends SuspendLambda implements Function2<AnimeLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $animeUrl;
        final /* synthetic */ Episode $currentEpisode;
        final /* synthetic */ Document $doc;
        final /* synthetic */ String $poster;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ AnimesDigital this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00062(String str, Document document, Episode episode, String str2, AnimesDigital animesDigital, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$doc = document;
            this.$currentEpisode = episode;
            this.$animeUrl = str2;
            this.this$0 = animesDigital;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00062 c00062 = new C00062(this.$poster, this.$doc, this.$currentEpisode, this.$animeUrl, this.this$0, continuation);
            c00062.L$0 = obj;
            return (Continuation<Unit>) (Object) c00062;
        }

        public final Object invoke(AnimeLoadResponse animeLoadResponse, Continuation<? super Unit> continuation) {
            return ((C00062) (Object) create(animeLoadResponse, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    AnimeLoadResponse $this$newAnimeLoadResponse = (AnimeLoadResponse) this.L$0;
                    $this$newAnimeLoadResponse.setPosterUrl(this.$poster);
                    Element elementSelectFirst = this.$doc.selectFirst("meta[property=og:description]");
                    $this$newAnimeLoadResponse.setPlot(elementSelectFirst != null ? elementSelectFirst.attr("content") : null);
                    MainAPIKt.addEpisodes($this$newAnimeLoadResponse, DubStatus.Subbed, CollectionsKt.listOf(this.$currentEpisode));
                    if (this.$animeUrl != null) {
                        AnimesDigital animesDigital = this.this$0;
                        String strFixUrl = MainAPIKt.fixUrl(this.this$0, this.$animeUrl);
                        TvType tvType = TvType.Anime;
                        final String str = this.$poster;
                        $this$newAnimeLoadResponse.setRecommendations(CollectionsKt.listOf(MainAPIKt.newAnimeSearchResponse(animesDigital, "Ver todos os episódios", strFixUrl, tvType, false, new Function1() { // from class: com.AnimesDigital.AnimesDigital$loadEpisode$2$$ExternalSyntheticLambda0
                            public final Object invoke(Object obj2) {
                                return AnimesDigital.C00062.invokeSuspend$lambda$0(str, (AnimeSearchResponse) obj2);
                            }
                        })));
                    }
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(String $poster, AnimeSearchResponse $this$newAnimeSearchResponse) {
            $this$newAnimeSearchResponse.setPosterUrl($poster);
            return Unit.INSTANCE;
        }
    }

    private final String extractAnimeMainPageUrl(Document doc, String url) {
        String strAttr;
        Element elementSelectFirst = doc.selectFirst(".epsL a[href]");
        if (elementSelectFirst != null && (strAttr = elementSelectFirst.attr("href")) != null) {
            return strAttr;
        }
        Element elementSelectFirst2 = doc.selectFirst("a[href*='/anime/a/']");
        String strAttr2 = elementSelectFirst2 != null ? elementSelectFirst2.attr("href") : null;
        if (strAttr2 != null) {
            return strAttr2;
        }
        String it = extractAnimeSlugFromUrl(url);
        if (it != null) {
            return getMainUrl() + "/anime/a/" + it;
        }
        return null;
    }

    private final String extractAnimeSlugFromUrl(String url) {
        List groupValues;
        MatchResult matchResultFind$default = new Regex("/video/a/([^/]+)/").find(url, 0);
        if (matchResultFind$default == null || (groupValues = matchResultFind$default.getGroupValues()) == null) {
            return null;
        }
        return (String) groupValues.get(1);
    }

    private final int extractCurrentEpisodeNumber(String url, String title) {
        List groupValues;
        String str;
        List groupValues2;
        String str2;
        Integer intOrNull;
        Integer intOrNull2 = null;
        MatchResult matchResultFind$default = new Regex("Epis[oó]dio\\s*(\\d+)", RegexOption.IGNORE_CASE).find(title, 0);
        if (matchResultFind$default != null && (groupValues2 = matchResultFind$default.getGroupValues()) != null && (str2 = (String) groupValues2.get(1)) != null && (intOrNull = StringsKt.toIntOrNull(str2)) != null) {
            return intOrNull.intValue();
        }
        MatchResult matchResultFind$default2 = new Regex("\\b(\\d+)\\b").find(title, 0);
        if (matchResultFind$default2 != null && (groupValues = matchResultFind$default2.getGroupValues()) != null && (str = (String) groupValues.get(1)) != null) {
            intOrNull2 = StringsKt.toIntOrNull(str);
        }
        if (intOrNull2 != null) {
            return intOrNull2.intValue();
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object loadAnime(String url, Document doc, Continuation<? super LoadResponse> continuation) {
        C00051 c00051;
        String strText = null;
        String strAttr;
        String strSubstringBefore$default;
        String strAttr2;
        String strAttr3;
        Object objLoadAllEpisodes;
        String url2;
        String poster;
        AnimesDigital animesDigital;
        String title;
        List tagsList;
        String title2;
        TvType tvType;
        DubStatus dubStatus;
        Integer year;
        String strText2;
        String strReplace$default;
        String string;
        String strText3;
        String strText4;
        if (continuation instanceof C00051) {
            c00051 = (C00051) continuation;
            if ((c00051.label & Integer.MIN_VALUE) != 0) {
                c00051.label -= Integer.MIN_VALUE;
            } else {
                c00051 = new C00051(continuation);
            }
        } else {
            c00051 = new C00051(continuation);
        }
        Object $result = c00051.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00051.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Element info = doc.selectFirst(".single_anime, .single-content, .dados");
                if (info == null) {
                    info = (Element) doc;
                }
                Element elementSelectFirst = info.selectFirst(".dados h1, h1.single-title, h1");
                if (elementSelectFirst == null || (strText4 = elementSelectFirst.text()) == null || (strText = StringsKt.trim(strText4).toString()) == null) {
                    Element elementSelectFirst2 = doc.selectFirst("meta[property=og:title]");
                    if (elementSelectFirst2 == null || (strAttr = elementSelectFirst2.attr("content")) == null || (strSubstringBefore$default = StringsKt.substringBefore(strAttr, " - Animes Online", strAttr)) == null) {
                        Element elementSelectFirst3 = doc.selectFirst("h1, h2");
                        strText = elementSelectFirst3 != null ? elementSelectFirst3.text() : null;
                        if (strText == null) {
                            return null;
                        }
                    } else {
                        strText = StringsKt.trim(strSubstringBefore$default).toString();
                    }
                }
                String title3 = strText;
                AnimesDigital animesDigital2 = this;
                Element elementSelectFirst4 = info.selectFirst(".foto img");
                if (elementSelectFirst4 == null || (strAttr2 = elementSelectFirst4.attr("src")) == null) {
                    Element elementSelectFirst5 = doc.selectFirst("img[src*=/uploads/]");
                    strAttr2 = elementSelectFirst5 != null ? elementSelectFirst5.attr("src") : null;
                    if (strAttr2 == null) {
                        Element elementSelectFirst6 = doc.selectFirst("meta[property=og:image]");
                        strAttr2 = elementSelectFirst6 != null ? elementSelectFirst6.attr("content") : null;
                    }
                }
                String poster2 = MainAPIKt.fixUrlNull(animesDigital2, strAttr2);
                Element elementSelectFirst7 = info.selectFirst(".dados .sinopse, .sinopse p");
                if (elementSelectFirst7 == null || (strText3 = elementSelectFirst7.text()) == null || (strAttr3 = StringsKt.trim(strText3).toString()) == null) {
                    Element elementSelectFirst8 = doc.selectFirst("meta[property=og:description]");
                    strAttr3 = elementSelectFirst8 != null ? elementSelectFirst8.attr("content") : null;
                }
                String description = strAttr3;
                Iterable $this$map$iv = info.select(".dados .genres a, .generos a, .single-meta a[href*='genero']");
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    Element it = (Element) item$iv$iv;
                    destination$iv$iv.add(StringsKt.trim(it.text()).toString());
                }
                List tagsList2 = (List) destination$iv$iv;
                Element info2 = info.selectFirst(".dados .info:contains(Ano)");
                Integer year2 = (info2 == null || (strText2 = info2.text()) == null || (strReplace$default = StringsKt.replace(strText2, "Ano", "", false)) == null || (string = StringsKt.trim(strReplace$default).toString()) == null) ? null : StringsKt.toIntOrNull(string);
                TvType tvType2 = StringsKt.contains(url, "/filme/", true) ? TvType.Movie : TvType.Anime;
                DubStatus dubStatus2 = (StringsKt.contains(url, "dublado", true) || StringsKt.contains(url, "desenhos", true)) ? DubStatus.Dubbed : DubStatus.Subbed;
                if (tvType2 == TvType.Movie) {
                    TvType tvType3 = TvType.Movie;
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(poster2, description, tagsList2, year2, null);
                    c00051.label = 1;
                    Object objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(this, title3, url, tvType3, url, anonymousClass2, c00051);
                    return objNewMovieLoadResponse == coroutine_suspended ? coroutine_suspended : objNewMovieLoadResponse;
                }
                c00051.L$0 = this;
                c00051.L$1 = url;
                c00051.L$2 = title3;
                c00051.L$3 = poster2;
                c00051.L$4 = description;
                c00051.L$5 = tagsList2;
                c00051.L$6 = year2;
                c00051.L$7 = tvType2;
                c00051.L$8 = dubStatus2;
                c00051.label = 2;
                objLoadAllEpisodes = loadAllEpisodes(url, doc, c00051);
                if (objLoadAllEpisodes == coroutine_suspended) {
                    return coroutine_suspended;
                }
                url2 = url;
                poster = poster2;
                animesDigital = this;
                title = title3;
                tagsList = tagsList2;
                title2 = description;
                tvType = tvType2;
                dubStatus = dubStatus2;
                year = year2;
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                return $result;
            case 2:
                DubStatus dubStatus3 = (DubStatus) c00051.L$8;
                TvType tvType4 = (TvType) c00051.L$7;
                Integer year3 = (Integer) c00051.L$6;
                List tagsList3 = (List) c00051.L$5;
                String description2 = (String) c00051.L$4;
                String poster3 = (String) c00051.L$3;
                String title4 = (String) c00051.L$2;
                String url3 = (String) c00051.L$1;
                AnimesDigital animesDigital3 = (AnimesDigital) c00051.L$0;
                ResultKt.throwOnFailure($result);
                dubStatus = dubStatus3;
                tvType = tvType4;
                title2 = description2;
                poster = poster3;
                title = title4;
                url2 = url3;
                objLoadAllEpisodes = $result;
                animesDigital = animesDigital3;
                year = year3;
                tagsList = tagsList3;
                break;
            case 3:
                ResultKt.throwOnFailure($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        List episodes = (List) objLoadAllEpisodes;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(poster, title2, tagsList, year, episodes, dubStatus, null);
        c00051.L$0 = null;
        c00051.L$1 = null;
        c00051.L$2 = null;
        c00051.L$3 = null;
        c00051.L$4 = null;
        c00051.L$5 = null;
        c00051.L$6 = null;
        c00051.L$7 = null;
        c00051.L$8 = null;
        c00051.label = 3;
        Object objNewAnimeLoadResponse$default = MainAPIKt.newAnimeLoadResponse(animesDigital, title, url2, tvType, false, anonymousClass3, c00051);
        return objNewAnimeLoadResponse$default == coroutine_suspended ? coroutine_suspended : objNewAnimeLoadResponse$default;
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$loadAnime$2, reason: invalid class name */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/MovieLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital$loadAnime$2", f = "AnimesDigital.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $description;
        final /* synthetic */ String $poster;
        final /* synthetic */ List<String> $tagsList;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, String str2, List<String> list, Integer num, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$description = str2;
            this.$tagsList = list;
            this.$year = num;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$poster, this.$description, this.$tagsList, this.$year, continuation);
            anonymousClass2.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass2;
        }

        public final Object invoke(MovieLoadResponse movieLoadResponse, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) (Object) create(movieLoadResponse, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    MovieLoadResponse $this$newMovieLoadResponse = (MovieLoadResponse) this.L$0;
                    $this$newMovieLoadResponse.setPosterUrl(this.$poster);
                    $this$newMovieLoadResponse.setPlot(this.$description);
                    $this$newMovieLoadResponse.setTags(CollectionsKt.toMutableList(this.$tagsList));
                    $this$newMovieLoadResponse.setYear(this.$year);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.AnimesDigital.AnimesDigital$loadAnime$3, reason: invalid class name */
    /* JADX INFO: compiled from: AnimesDigital.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/AnimeLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesDigital.AnimesDigital$loadAnime$3", f = "AnimesDigital.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function2<AnimeLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $description;
        final /* synthetic */ DubStatus $dubStatus;
        final /* synthetic */ List<Episode> $episodes;
        final /* synthetic */ String $poster;
        final /* synthetic */ List<String> $tagsList;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(String str, String str2, List<String> list, Integer num, List<Episode> list2, DubStatus dubStatus, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$description = str2;
            this.$tagsList = list;
            this.$year = num;
            this.$episodes = list2;
            this.$dubStatus = dubStatus;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$poster, this.$description, this.$tagsList, this.$year, this.$episodes, this.$dubStatus, continuation);
            anonymousClass3.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass3;
        }

        public final Object invoke(AnimeLoadResponse animeLoadResponse, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) (Object) create(animeLoadResponse, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    AnimeLoadResponse $this$newAnimeLoadResponse = (AnimeLoadResponse) this.L$0;
                    $this$newAnimeLoadResponse.setPosterUrl(this.$poster);
                    $this$newAnimeLoadResponse.setPlot(this.$description);
                    $this$newAnimeLoadResponse.setTags(CollectionsKt.toMutableList(this.$tagsList));
                    $this$newAnimeLoadResponse.setYear(this.$year);
                    if (!this.$episodes.isEmpty()) {
                        MainAPIKt.addEpisodes($this$newAnimeLoadResponse, this.$dubStatus, this.$episodes);
                    }
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x0146 -> B:47:0x0151). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:14:0x0068
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public final java.lang.Object loadAllEpisodes(java.lang.String r27, org.jsoup.nodes.Document r28, kotlin.coroutines.Continuation<? super java.util.List<com.lagradost.cloudstream3.Episode>> r29) {
        C00041 c00041;
        AnimesDigital animesDigital;
        String url;
        List episodes;
        int page;
        int hasNext;
        Document doc;
        if (r29 instanceof C00041) {
            c00041 = (C00041) r29;
            if ((c00041.label & Integer.MIN_VALUE) != 0) {
                c00041.label -= Integer.MIN_VALUE;
            } else {
                c00041 = new C00041(r29);
            }
        } else {
            c00041 = new C00041(r29);
        }
        Object $result = c00041.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00041.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                animesDigital = this;
                url = r27;
                doc = r28;
                episodes = (List) new ArrayList();
                page = 1;
                hasNext = 1;
                break;
            case 1:
                hasNext = c00041.I$1;
                page = c00041.I$0;
                episodes = (List) c00041.L$2;
                url = (String) c00041.L$1;
                animesDigital = (AnimesDigital) c00041.L$0;
                ResultKt.throwOnFailure($result);
                doc = ((NiceResponse) $result).getDocument();
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (hasNext != 0 && page <= 20) {
            List pageEpisodes = animesDigital.extractEpisodesFromPage(doc);
            Collection newEpisodes = new ArrayList();
            for (Object obj : (Iterable) pageEpisodes) {
                Episode ep = (Episode) obj;
                boolean none$iv = true;
                Iterable $this$none$iv = (Iterable) episodes;
                if (!($this$none$iv instanceof Collection && ((Collection) $this$none$iv).isEmpty())) {
                    for (Object it2 : $this$none$iv) {
                        Episode it = (Episode) it2;
                        if (Intrinsics.areEqual(it.getEpisode(), ep.getEpisode())) {
                            none$iv = false;
                            break;
                        }
                    }
                }
                if (none$iv) {
                    newEpisodes.add(ep);
                }
            }
            List newEpisodesList = (List) newEpisodes;
            int continueFlag = ((Collection) newEpisodesList).isEmpty() ? 0 : hasNext;
            if (!((Collection) newEpisodesList).isEmpty()) {
                episodes.addAll((Collection) newEpisodesList);
            }
            int nextPage = page + 1;
            if (continueFlag != 0) {
                Requests app = MainActivityKt.getApp();
                String nextUrl = animesDigital.buildNextPageUrl(url, nextPage);
                c00041.L$0 = animesDigital;
                c00041.L$1 = url;
                c00041.L$2 = episodes;
                c00041.I$0 = nextPage;
                c00041.I$1 = continueFlag;
                c00041.label = 1;
                $result = app.get(nextUrl, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00041);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                hasNext = continueFlag;
                page = nextPage;
                doc = ((NiceResponse) $result).getDocument();
            } else {
                hasNext = continueFlag;
                page = nextPage;
            }
        }
        return CollectionsKt.sortedWith((Iterable) episodes, (Comparator) new Comparator<Episode>() {
            @Override
            public int compare(Episode a, Episode b) {
                return ComparisonsKt.compareValues((Comparable) b.getEpisode(), (Comparable) a.getEpisode());
            }
        });
    }

    private final String buildNextPageUrl(String base, int page) {
        if (!StringsKt.contains(base, "/page/", false)) {
            return StringsKt.removeSuffix(base, "/") + "/page/" + page + '/';
        }
        return new Regex("/page/\\d+/").replace(base, "/page/" + page + '/');
    }

    private final List<Episode> extractEpisodesFromPage(Document doc) {
        String strReplace$default;
        String strAttr;
        Episode episodeNewEpisode;
        String strText = null;
        Iterable $this$mapNotNull$iv = doc.select(".item_ep a");
        int $i$f$mapNotNull = 0;
        Collection destination$iv$iv = new ArrayList();
        Iterable $this$mapNotNullTo$iv$iv = $this$mapNotNull$iv;
        for (Object element$iv$iv$iv : $this$mapNotNullTo$iv$iv) {
            Element it = (Element) element$iv$iv$iv;
            String href = it.attr("href");
            if (StringsKt.isBlank(href)) {
                href = null;
            }
            if (href == null) {
                episodeNewEpisode = null;
            } else {
                final Ref.ObjectRef title = new Ref.ObjectRef();
                Element elementSelectFirst = it.selectFirst("div.title_anime");
                if (elementSelectFirst == null || (strText = elementSelectFirst.text()) == null || (strReplace$default = StringsKt.trim(strText).toString()) == null) {
                    Element elementSelectFirst2 = it.selectFirst("img");
                    strReplace$default = (elementSelectFirst2 == null || (strAttr = elementSelectFirst2.attr("title")) == null) ? "Título Desconhecido" : StringsKt.replace(strAttr, "Assistir ", "", false);
                }
                title.element = strReplace$default;
                title.element = StringsKt.replace((String) title.element, "Episodio ", "Episódio", false);
                final int num = extractEpisodeNumber((String) title.element, href);
                episodeNewEpisode = MainAPIKt.newEpisode(this, href + "|#|" + num, new Function1() { // from class: com.AnimesDigital.AnimesDigital$$ExternalSyntheticLambda6
                    public final Object invoke(Object obj) {
                        return AnimesDigital.extractEpisodesFromPage$lambda$12$lambda$11(title, num, (Episode) obj);
                    }
                });
            }
            if (episodeNewEpisode != null) {
                destination$iv$iv.add(episodeNewEpisode);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv;
            $i$f$mapNotNull = $i$f$mapNotNull;
            $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv;
        }
        return (List) destination$iv$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit extractEpisodesFromPage$lambda$12$lambda$11(Ref.ObjectRef $title, int $num, Episode $this$newEpisode) {
        $this$newEpisode.setName((String) $title.element);
        $this$newEpisode.setEpisode(Integer.valueOf($num));
        return Unit.INSTANCE;
    }

    private final int extractEpisodeNumber(String title, String url) {
        List groupValues;
        String str;
        Integer intOrNull;
        List groupValues2;
        String str2;
        Integer intOrNull2;
        Iterable $this$map$iv = CollectionsKt.listOf(new String[]{"Epis[oó]dio\\s*(\\d+)", "Cap\\.?\\s*(\\d+)", "\\b(\\d+)\\b"});
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            destination$iv$iv.add(new Regex(it, RegexOption.IGNORE_CASE));
        }
        Iterable patterns = (List) destination$iv$iv;
        Iterable $this$forEach$iv = patterns;
        for (Object element$iv : $this$forEach$iv) {
            Regex it2 = (Regex) element$iv;
            MatchResult matchResultFind$default = it2.find(title, 0);
            if (matchResultFind$default != null && (groupValues2 = matchResultFind$default.getGroupValues()) != null && (str2 = (String) groupValues2.get(1)) != null && (intOrNull2 = StringsKt.toIntOrNull(str2)) != null) {
                int it3 = intOrNull2.intValue();
                return it3;
            }
        }
        MatchResult matchResultFind$default2 = new Regex("[\\/\\-](\\d+)[\\/\\-]?").find(url, 0);
        if (matchResultFind$default2 == null || (groupValues = matchResultFind$default2.getGroupValues()) == null || (str = (String) groupValues.get(1)) == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) {
            return 0;
        }
        return intOrNull.intValue();
    }

    private final SearchResponse toSearchResult(Element $this$toSearchResult) throws IOException {
        String strText = null;
        String animeTitle;
        Element elementSelectFirst;
        String strText2;
        String episodeText;
        Element titleEl = $this$toSearchResult.selectFirst("a");
        if (titleEl == null) {
            return null;
        }
        String href = titleEl.attr("href");
        Element elementSelectFirst2 = $this$toSearchResult.selectFirst(".title_anime");
        if (elementSelectFirst2 == null || (strText = elementSelectFirst2.text()) == null || (animeTitle = StringsKt.trim(strText).toString()) == null || (elementSelectFirst = $this$toSearchResult.selectFirst(".number")) == null || (strText2 = elementSelectFirst.text()) == null || (episodeText = StringsKt.trim(strText2).toString()) == null) {
            return null;
        }
        Element elementSelectFirst3 = $this$toSearchResult.selectFirst("img");
        final String poster = elementSelectFirst3 != null ? elementSelectFirst3.attr("src") : null;
        String $this$filterTo$iv$iv = episodeText;
        Appendable destination$iv$iv = new StringBuilder();
        int length = $this$filterTo$iv$iv.length();
        for (int index$iv$iv = 0; index$iv$iv < length; index$iv$iv++) {
            char element$iv$iv = $this$filterTo$iv$iv.charAt(index$iv$iv);
            if (Character.isDigit(element$iv$iv)) {
                destination$iv$iv.append(element$iv$iv);
            }
        }
        String $this$filter$iv = ((StringBuilder) destination$iv$iv).toString();
        Integer intOrNull = StringsKt.toIntOrNull($this$filter$iv);
        boolean z = true;
        final int episodeNum = intOrNull != null ? intOrNull.intValue() : 1;
        if (!StringsKt.contains(animeTitle, "dublado", true) && !StringsKt.contains(href, "dublado", true) && !StringsKt.contains(episodeText, "dublado", true)) {
            z = false;
        }
        final boolean isDub = z;
        return MainAPIKt.newAnimeSearchResponse(this, animeTitle + " - " + episodeText, href, TvType.Anime, false, new Function1() { // from class: com.AnimesDigital.AnimesDigital$$ExternalSyntheticLambda7
            public final Object invoke(Object obj) {
                return AnimesDigital.toSearchResult$lambda$17(AnimesDigital.this, poster, isDub, episodeNum, (AnimeSearchResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResult$lambda$17(AnimesDigital this$0, String $poster, boolean $isDub, int $episodeNum, AnimeSearchResponse $this$newAnimeSearchResponse) {
        $this$newAnimeSearchResponse.setPosterUrl(MainAPIKt.fixUrlNull(this$0, $poster));
        MainAPIKt.addDubStatus($this$newAnimeSearchResponse, $isDub, Integer.valueOf($episodeNum));
        return Unit.INSTANCE;
    }

    private final SearchResponse toSearchResultAlternative(Element $this$toSearchResultAlternative) {
        String strAttr;
        Element titleEl = $this$toSearchResultAlternative.selectFirst("a");
        if (titleEl == null && (titleEl = $this$toSearchResultAlternative.selectFirst(".title, .name, h1, h2, h3")) == null) {
            return null;
        }
        String href = titleEl.attr("href");
        String title = StringsKt.trim(titleEl.text()).toString();
        Element elementSelectFirst = $this$toSearchResultAlternative.selectFirst("img");
        if (elementSelectFirst == null || (strAttr = elementSelectFirst.attr("src")) == null) {
            Element elementSelectFirst2 = $this$toSearchResultAlternative.selectFirst("img");
            strAttr = elementSelectFirst2 != null ? elementSelectFirst2.attr("data-src") : null;
        }
        final String poster = strAttr;
        if (!(title.length() > 0)) {
            return null;
        }
        if (!(href.length() > 0)) {
            return null;
        }
        if (StringsKt.contains(href, "/filme/", true) || StringsKt.contains(title, "filme", true)) {
            return MainAPIKt.newMovieSearchResponse(this, title, href, TvType.Movie, false, new Function1() { // from class: com.AnimesDigital.AnimesDigital$$ExternalSyntheticLambda0
                public final Object invoke(Object obj) {
                    return AnimesDigital.toSearchResultAlternative$lambda$18(AnimesDigital.this, poster, (MovieSearchResponse) obj);
                }
            });
        }
        return MainAPIKt.newAnimeSearchResponse(this, title, href, TvType.Anime, false, new Function1() { // from class: com.AnimesDigital.AnimesDigital$$ExternalSyntheticLambda1
            public final Object invoke(Object obj) {
                return AnimesDigital.toSearchResultAlternative$lambda$19(AnimesDigital.this, poster, (AnimeSearchResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResultAlternative$lambda$18(AnimesDigital this$0, String $poster, MovieSearchResponse $this$newMovieSearchResponse) {
        $this$newMovieSearchResponse.setPosterUrl(MainAPIKt.fixUrlNull(this$0, $poster));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResultAlternative$lambda$19(AnimesDigital this$0, String $poster, AnimeSearchResponse $this$newAnimeSearchResponse) {
        $this$newAnimeSearchResponse.setPosterUrl(MainAPIKt.fixUrlNull(this$0, $poster));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x02c2  */
    /* JADX WARN: Code duplicated, block: B:36:0x02d2  */
    /* JADX WARN: Code duplicated, block: B:37:0x02d6  */
    /* JADX WARN: Code duplicated, block: B:39:0x02ed  */
    /* JADX WARN: Code duplicated, block: B:51:0x03aa A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:52:0x03ab  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:109:0x062e -> B:110:0x0642). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x02d2 -> B:55:0x03d0). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x0372 -> B:55:0x03d0). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x03ab -> B:53:0x03bc). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:62:0x0416 -> B:112:0x065a). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:731)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public java.lang.Object loadLinks(@org.jetbrains.annotations.NotNull java.lang.String r48, boolean r49, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.SubtitleFile, kotlin.Unit> r50, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.utils.ExtractorLink, kotlin.Unit> r51, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> r52) {
        C00071 c00071;
        AnimesDigital animesDigital = null;
        String realUrl = null;
        String url = null;
        int epNum = 0;
        Function1 subtitleCallback = null;
        Function1 callback = null;
        Ref.BooleanRef found = null;
        List fhdLinks = null;
        List otherLinks = null;
        Iterator it = null;
        int step = 0;
        if (r52 instanceof C00071) {
            c00071 = (C00071) r52;
            if ((c00071.label & Integer.MIN_VALUE) != 0) {
                c00071.label -= Integer.MIN_VALUE;
            } else {
                c00071 = new C00071(r52);
            }
        } else {
            c00071 = new C00071(r52);
        }
        Object $result = c00071.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        boolean runFilm = false;
        switch (c00071.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                animesDigital = this;
                subtitleCallback = (Function1) r50;
                callback = (Function1) r51;
                found = new Ref.BooleanRef();
                List parts = StringsKt.split((CharSequence) r48, new String[]{"|#|"}, false, 0);
                realUrl = (String) parts.get(0);
                String epStr = (String) CollectionsKt.getOrNull(parts, 1);
                Integer epNumOrNull = epStr == null ? null : StringsKt.toIntOrNull(epStr);
                epNum = epNumOrNull != null ? epNumOrNull.intValue() : 1;
                c00071.L$0 = animesDigital;
                c00071.L$1 = subtitleCallback;
                c00071.L$2 = callback;
                c00071.L$3 = found;
                c00071.L$4 = realUrl;
                c00071.I$0 = epNum;
                c00071.label = 1;
                $result = MainActivityKt.getApp().get(realUrl, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00071);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                step = 0;
                break;
            }
            case 1: {
                epNum = c00071.I$0;
                realUrl = (String) c00071.L$4;
                found = (Ref.BooleanRef) c00071.L$3;
                callback = (Function1) c00071.L$2;
                subtitleCallback = (Function1) c00071.L$1;
                animesDigital = (AnimesDigital) c00071.L$0;
                ResultKt.throwOnFailure($result);
                step = 0;
                break;
            }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7: {
                step = c00071.label;
                url = c00071.L$8 instanceof String ? (String) c00071.L$8 : null;
                it = (Iterator) c00071.L$7;
                otherLinks = (List) c00071.L$6;
                fhdLinks = (List) c00071.L$5;
                realUrl = (String) c00071.L$4;
                found = (Ref.BooleanRef) c00071.L$3;
                callback = (Function1) c00071.L$2;
                subtitleCallback = (Function1) c00071.L$1;
                animesDigital = (AnimesDigital) c00071.L$0;
                epNum = c00071.I$0;
                ResultKt.throwOnFailure($result);
                break;
            }
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        if (step <= 1) {
            Document doc = ((NiceResponse) $result).getDocument();
            runFilm = StringsKt.contains((CharSequence) realUrl, (CharSequence) "/filme/", true);
            fhdLinks = (List) new ArrayList();
            otherLinks = (List) new ArrayList();
            it = (runFilm ? doc.select("iframe[src]") : doc.select(".tab-video iframe[src]")).iterator();
        } else {
            runFilm = step == 2 || step == 3;
        }
        if (runFilm) {
            while (true) {
                if (step == 2) {
                    loadLinks$addLink(fhdLinks, otherLinks, (ExtractorLink) $result, true);
                    found.element = true;
                    step = 0;
                } else if (step == 3) {
                    found.element = true;
                    step = 0;
                } else {
                    if (!it.hasNext()) {
                        break;
                    }
                    String src = ((Element) it.next()).attr("src");
                    if (src == null) {
                        continue;
                    }
                    if (StringsKt.contains((CharSequence) src, (CharSequence) "anivideo.net", false) && StringsKt.contains((CharSequence) src, (CharSequence) "m3u8", false)) {
                        String m3u8 = animesDigital.extractM3u8Url(src);
                        if (m3u8 != null) {
                            final String referer = realUrl;
                            c00071.L$0 = animesDigital;
                            c00071.L$1 = subtitleCallback;
                            c00071.L$2 = callback;
                            c00071.L$3 = found;
                            c00071.L$4 = realUrl;
                            c00071.L$5 = fhdLinks;
                            c00071.L$6 = otherLinks;
                            c00071.L$7 = it;
                            c00071.L$8 = otherLinks;
                            c00071.L$9 = fhdLinks;
                            c00071.label = 2;
                            Object obj = ExtractorApiKt.newExtractorLink(animesDigital.getName(), "Player FHD", m3u8, ExtractorLinkType.M3U8, new Function2<ExtractorLink, Continuation<? super Unit>, Object>() { // from class: com.AnimesDigital.AnimesDigital$loadLinks$2$1$1
                                public final Object invoke(ExtractorLink $this$newExtractorLink, Continuation<? super Unit> continuation) {
                                    $this$newExtractorLink.setReferer(referer);
                                    $this$newExtractorLink.setQuality(Qualities.Unknown.getValue());
                                    return Unit.INSTANCE;
                                }
                            }, c00071);
                            if (obj == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            $result = obj;
                            step = 2;
                            continue;
                        }
                        continue;
                    }
                    final List $fhdLinks = fhdLinks;
                    final List $otherLinks = otherLinks;
                    c00071.L$0 = animesDigital;
                    c00071.L$1 = subtitleCallback;
                    c00071.L$2 = callback;
                    c00071.L$3 = found;
                    c00071.L$4 = realUrl;
                    c00071.L$5 = fhdLinks;
                    c00071.L$6 = otherLinks;
                    c00071.L$7 = it;
                    c00071.L$8 = null;
                    c00071.L$9 = null;
                    c00071.label = 3;
                    Object obj = ExtractorApiKt.loadExtractor(src, realUrl, subtitleCallback, new Function1<ExtractorLink, Unit>() { // from class: com.AnimesDigital.AnimesDigital$$ExternalSyntheticLambda2
                        public final Unit invoke(ExtractorLink ex) {
                            return AnimesDigital.loadLinks$lambda$22$lambda$21($fhdLinks, $otherLinks, ex);
                        }
                    }, c00071);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    $result = obj;
                    step = 3;
                }
            }
        } else {
            while (true) {
                if (step == 4) {
                    loadLinks$addLink(fhdLinks, otherLinks, (ExtractorLink) $result, true);
                    found.element = true;
                    step = 0;
                } else if (step == 5) {
                    Document blogDoc = ((NiceResponse) $result).getDocument();
                    List frames = (List) blogDoc.select(".post-body iframe[src]");
                    Element frame = (Element) CollectionsKt.getOrNull(frames, epNum - 1);
                    String embed = frame == null ? null : frame.attr("src");
                    if (embed != null && !StringsKt.isBlank((CharSequence) embed)) {
                        final List $fhdLinks2 = fhdLinks;
                        final List $otherLinks2 = otherLinks;
                        c00071.L$0 = animesDigital;
                        c00071.L$1 = subtitleCallback;
                        c00071.L$2 = callback;
                        c00071.L$3 = found;
                        c00071.L$4 = realUrl;
                        c00071.L$5 = fhdLinks;
                        c00071.L$6 = otherLinks;
                        c00071.L$7 = it;
                        c00071.L$8 = null;
                        c00071.I$0 = epNum;
                        c00071.label = 6;
                        Object obj = ExtractorApiKt.loadExtractor(embed, url, subtitleCallback, new Function1<ExtractorLink, Unit>() { // from class: com.AnimesDigital.AnimesDigital$$ExternalSyntheticLambda3
                            public final Unit invoke(ExtractorLink ex) {
                                return AnimesDigital.loadLinks$lambda$29$lambda$27$lambda$26$lambda$25($fhdLinks2, $otherLinks2, ex);
                            }
                        }, c00071);
                        if (obj == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        found.element = true;
                    }
                    step = 0;
                } else if (step == 6 || step == 7) {
                    found.element = true;
                    step = 0;
                } else {
                    if (!it.hasNext()) {
                        break;
                    }
                    String src = ((Element) it.next()).attr("src");
                    if (src == null) {
                        continue;
                    }
                    if (StringsKt.contains((CharSequence) src, (CharSequence) "anivideo.net", false) && StringsKt.contains((CharSequence) src, (CharSequence) "m3u8", false)) {
                        String m3u8 = animesDigital.extractM3u8Url(src);
                        if (m3u8 != null) {
                            final String referer = realUrl;
                            c00071.L$0 = animesDigital;
                            c00071.L$1 = subtitleCallback;
                            c00071.L$2 = callback;
                            c00071.L$3 = found;
                            c00071.L$4 = realUrl;
                            c00071.L$5 = fhdLinks;
                            c00071.L$6 = otherLinks;
                            c00071.L$7 = it;
                            c00071.L$8 = otherLinks;
                            c00071.L$9 = fhdLinks;
                            c00071.I$0 = epNum;
                            c00071.label = 4;
                            Object obj = ExtractorApiKt.newExtractorLink(animesDigital.getName(), "Player FHD", m3u8, ExtractorLinkType.M3U8, new Function2<ExtractorLink, Continuation<? super Unit>, Object>() { // from class: com.AnimesDigital.AnimesDigital$loadLinks$3$1$1
                                public final Object invoke(ExtractorLink $this$newExtractorLink, Continuation<? super Unit> continuation) {
                                    $this$newExtractorLink.setReferer(referer);
                                    $this$newExtractorLink.setQuality(Qualities.Unknown.getValue());
                                    return Unit.INSTANCE;
                                }
                            }, c00071);
                            if (obj == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            $result = obj;
                            step = 4;
                            continue;
                        }
                        continue;
                    }
                    if (StringsKt.contains((CharSequence) src, (CharSequence) "animesdigital.org/aHR0", false)) {
                        String decoded = animesDigital.decodeAnimesDigitalUrl(src);
                        if (decoded != null) {
                            c00071.L$0 = animesDigital;
                            c00071.L$1 = subtitleCallback;
                            c00071.L$2 = callback;
                            c00071.L$3 = found;
                            c00071.L$4 = realUrl;
                            c00071.L$5 = fhdLinks;
                            c00071.L$6 = otherLinks;
                            c00071.L$7 = it;
                            c00071.L$8 = decoded;
                            c00071.L$9 = null;
                            c00071.I$0 = epNum;
                            c00071.label = 5;
                            Object obj = MainActivityKt.getApp().get(decoded, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00071);
                            if (obj == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            url = decoded;
                            $result = obj;
                            step = 5;
                            continue;
                        }
                        continue;
                    }
                    final List $fhdLinks3 = fhdLinks;
                    final List $otherLinks3 = otherLinks;
                    c00071.L$0 = animesDigital;
                    c00071.L$1 = subtitleCallback;
                    c00071.L$2 = callback;
                    c00071.L$3 = found;
                    c00071.L$4 = realUrl;
                    c00071.L$5 = fhdLinks;
                    c00071.L$6 = otherLinks;
                    c00071.L$7 = it;
                    c00071.L$8 = null;
                    c00071.L$9 = null;
                    c00071.I$0 = epNum;
                    c00071.label = 7;
                    Object obj = ExtractorApiKt.loadExtractor(src, realUrl, subtitleCallback, new Function1<ExtractorLink, Unit>() { // from class: com.AnimesDigital.AnimesDigital$$ExternalSyntheticLambda4
                        public final Unit invoke(ExtractorLink ex) {
                            return AnimesDigital.loadLinks$lambda$29$lambda$28($fhdLinks3, $otherLinks3, ex);
                        }
                    }, c00071);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    $result = obj;
                    step = 7;
                }
            }
        }
        for (Object exObj : CollectionsKt.plus((Collection) fhdLinks, (Iterable) otherLinks)) {
            callback.invoke((ExtractorLink) exObj);
        }
        return Boxing.boxBoolean(found.element);
    }

    private static final void loadLinks$addLink(List<ExtractorLink> list, List<ExtractorLink> list2, ExtractorLink link, boolean isFHD) {
        if (isFHD) {
            list.add(link);
        } else {
            list2.add(link);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadLinks$lambda$22$lambda$21(List $fhdLinks, List $otherLinks, ExtractorLink ex) {
        loadLinks$addLink($fhdLinks, $otherLinks, ex, false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadLinks$lambda$29$lambda$27$lambda$26$lambda$25(List $fhdLinks, List $otherLinks, ExtractorLink ex) {
        loadLinks$addLink($fhdLinks, $otherLinks, ex, false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadLinks$lambda$29$lambda$28(List $fhdLinks, List $otherLinks, ExtractorLink ex) {
        loadLinks$addLink($fhdLinks, $otherLinks, ex, false);
        return Unit.INSTANCE;
    }

    private final String extractM3u8Url(String src) {
        Object next;
        String it;
        try {
            Iterator it2 = StringsKt.split((CharSequence) CollectionsKt.last(StringsKt.split(src, new String[]{"?"}, false, 0)), new String[]{"&"}, false, 0).iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!StringsKt.startsWith((String) next, "d=", false));
            String str = (String) next;
            if (str == null || (it = StringsKt.substringAfter(str, "=", str)) == null) {
                return null;
            }
            return URLDecoder.decode(it, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    private final String decodeAnimesDigitalUrl(String src) {
        try {
            byte[] it = Base64.getDecoder().decode(StringsKt.substringBefore(StringsKt.substringAfter(src, "animesdigital.org/", src), "/", StringsKt.substringAfter(src, "animesdigital.org/", src)));
            return new String(it, Charsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}
