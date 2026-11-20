package com.DonghuaNoSekai;

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
import com.lagradost.cloudstream3.SearchResponse;
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.util.ArrayList;
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
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: DonghuaNoSekai.kt */
/* JADX INFO: loaded from: /tmp/cs3/DonghuaNoSekai/classes.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 M2\u00020\u0001:\u0001MB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010 \u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0096@¢\u0006\u0002\u0010)J\u000e\u0010*\u001a\u0004\u0018\u00010+*\u00020,H\u0002J\u001c\u0010-\u001a\b\u0012\u0004\u0012\u00020+0\u001e2\u0006\u0010.\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010/J\u0016\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010/JF\u00103\u001a\u00020\u000e2\u0006\u00104\u001a\u00020\u00052\u0006\u00105\u001a\u00020\u000e2\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u000209072\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020907H\u0096@¢\u0006\u0002\u0010<J\u0012\u0010=\u001a\u0004\u0018\u00010\u00052\u0006\u0010>\u001a\u00020?H\u0002J\u0017\u0010@\u001a\u0004\u0018\u00010&2\u0006\u0010>\u001a\u00020?H\u0002¢\u0006\u0002\u0010AJ\u0017\u0010B\u001a\u0004\u0018\u00010&2\u0006\u0010>\u001a\u00020?H\u0002¢\u0006\u0002\u0010AJ\u0016\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00050D2\u0006\u0010>\u001a\u00020?H\u0002J\u0012\u0010E\u001a\u0004\u0018\u00010\u00052\u0006\u0010>\u001a\u00020?H\u0002J$\u0010F\u001a\b\u0012\u0004\u0012\u00020G0\u001e2\u0006\u0010>\u001a\u00020?2\u0006\u0010H\u001a\u00020\u0005H\u0082@¢\u0006\u0002\u0010IJ\u0010\u0010J\u001a\u00020&2\u0006\u0010K\u001a\u00020\u0005H\u0002J\u0016\u0010L\u001a\b\u0012\u0004\u0012\u00020&0\u001e2\u0006\u0010K\u001a\u00020\u0005H\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0014\u0010\r\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0007\"\u0004\b\u0013\u0010\tR\u0014\u0010\u0014\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0014\u0010\u0016\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050#X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006N"}, d2 = {"Lcom/DonghuaNoSekai/DonghuaNoSekai;", "Lcom/lagradost/cloudstream3/MainAPI;", "<init>", "()V", "mainUrl", "", "getMainUrl", "()Ljava/lang/String;", "setMainUrl", "(Ljava/lang/String;)V", "name", "getName", "setName", "hasMainPage", "", "getHasMainPage", "()Z", "lang", "getLang", "setLang", "hasDownloadSupport", "getHasDownloadSupport", "hasQuickSearch", "getHasQuickSearch", "supportedTypes", "", "Lcom/lagradost/cloudstream3/TvType;", "getSupportedTypes", "()Ljava/util/Set;", "mainPage", "", "Lcom/lagradost/cloudstream3/MainPageData;", "getMainPage", "()Ljava/util/List;", "genreFilters", "", "Lcom/lagradost/cloudstream3/HomePageResponse;", "page", "", "request", "Lcom/lagradost/cloudstream3/MainPageRequest;", "(ILcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toSearchResult", "Lcom/lagradost/cloudstream3/SearchResponse;", "Lorg/jsoup/nodes/Element;", "search", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "Lcom/lagradost/cloudstream3/LoadResponse;", "url", "loadLinks", "data", "isCasting", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractDescription", "document", "Lorg/jsoup/nodes/Document;", "extractYear", "(Lorg/jsoup/nodes/Document;)Ljava/lang/Integer;", "extractDuration", "extractGenres", "", "extractPoster", "loadEpisodesFromPage", "Lcom/lagradost/cloudstream3/Episode;", "baseUrl", "(Lorg/jsoup/nodes/Document;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractSeasonNumber", "text", "extractEpisodeNumbers", "Companion", "DonghuaNoSekai_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDonghuaNoSekai.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DonghuaNoSekai.kt\ncom/DonghuaNoSekai/DonghuaNoSekai\n+ 2 NiceResponse.kt\ncom/lagradost/nicehttp/NiceResponse\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,296:1\n96#2,5:297\n1611#3,9:302\n1863#3:311\n1864#3:313\n1620#3:314\n1611#3,9:315\n1863#3:324\n1864#3:326\n1620#3:327\n1557#3:328\n1628#3,3:329\n774#3:332\n865#3,2:333\n1557#3:336\n1628#3,3:337\n774#3:340\n865#3,2:341\n1611#3,9:343\n1863#3:352\n1611#3,9:353\n1863#3:362\n1864#3:364\n1620#3:365\n1864#3:367\n1620#3:368\n1#4:312\n1#4:325\n1#4:335\n1#4:363\n1#4:366\n*S KotlinDebug\n*F\n+ 1 DonghuaNoSekai.kt\ncom/DonghuaNoSekai/DonghuaNoSekai\n*L\n87#1:297,5\n92#1:302,9\n92#1:311\n92#1:313\n92#1:314\n126#1:315,9\n126#1:324\n126#1:326\n126#1:327\n179#1:328\n179#1:329,3\n180#1:332\n180#1:333,2\n214#1:336\n214#1:337,3\n215#1:340\n215#1:341,2\n230#1:343,9\n230#1:352\n238#1:353,9\n238#1:362\n238#1:364\n238#1:365\n230#1:367\n230#1:368\n92#1:312\n126#1:325\n238#1:363\n230#1:366\n*E\n"})
public final class DonghuaNoSekai extends MainAPI {

    @NotNull
    private static final Regex SEASON_REGEX = new Regex("(\\d+)[aª]?\\s*Temporada");

    @NotNull
    private static final Regex EPISODE_RANGE_REGEX = new Regex("Episódio\\s*(\\d+)\\s*(?:e\\s*(\\d+)|a\\s*(\\d+))?");

    @NotNull
    private static final Regex EPISODE_SIMPLE_REGEX = new Regex("Episódio\\s*(\\d+)");

    @NotNull
    private static final Regex YEAR_REGEX = new Regex("Ano:\\s*(\\d{4})");

    @NotNull
    private static final Regex DURATION_HOURS_REGEX = new Regex("(\\d+)min");

    @NotNull
    private static final Regex DURATION_MINUTES_REGEX = new Regex("(\\d+)\\s*min");

    @NotNull
    private static final Regex POSTER_REGEX = new Regex("url\\(([^)]+)\\)");

    @NotNull
    private String mainUrl = "https://donghuanosekai.com";

    @NotNull
    private String name = "DonghuaNoSekai";
    private final boolean hasMainPage = true;

    @NotNull
    private String lang = "pt-br";
    private final boolean hasDownloadSupport = true;
    private final boolean hasQuickSearch = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(new TvType[]{TvType.Anime, TvType.AnimeMovie});

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to("acao", "Ação"), TuplesKt.to("artes-marciais", "Artes Marciais"), TuplesKt.to("comedia", "Comédia"), TuplesKt.to("fantasia", "Fantasia"), TuplesKt.to("harem", "Hárem"), TuplesKt.to("magia", "Magia"), TuplesKt.to("misterio", "Mistério"), TuplesKt.to("romance", "Romance")});

    @NotNull
    private final Map<String, String> genreFilters = MapsKt.mapOf(new Pair[]{TuplesKt.to("acao", "2"), TuplesKt.to("artes-marciais", "54"), TuplesKt.to("comedia", "16"), TuplesKt.to("fantasia", "6"), TuplesKt.to("harem", "111"), TuplesKt.to("magia", "17"), TuplesKt.to("misterio", "149"), TuplesKt.to("romance", "12")});

    /* JADX INFO: renamed from: com.DonghuaNoSekai.DonghuaNoSekai$getMainPage$1, reason: invalid class name */
    /* JADX INFO: compiled from: DonghuaNoSekai.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.DonghuaNoSekai.DonghuaNoSekai", f = "DonghuaNoSekai.kt", i = {0, 0}, l = {75}, m = "getMainPage", n = {"this", "request"}, s = {"L$0", "L$1"})
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
            return DonghuaNoSekai.this.getMainPage(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.DonghuaNoSekai.DonghuaNoSekai$load$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DonghuaNoSekai.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.DonghuaNoSekai.DonghuaNoSekai", f = "DonghuaNoSekai.kt", i = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {130, 148, 149, 158}, m = "load", n = {"this", "url", "this", "url", "title", "description", "year", "duration", "genres", "poster", "type"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8"})
    static final class C00001 extends ContinuationImpl {
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

        C00001(Continuation<? super C00001> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DonghuaNoSekai.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.DonghuaNoSekai.DonghuaNoSekai$load$2, reason: invalid class name */
    /* JADX INFO: compiled from: DonghuaNoSekai.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/AnimeLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.DonghuaNoSekai.DonghuaNoSekai$load$2", f = "DonghuaNoSekai.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<AnimeLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $description;
        final /* synthetic */ Integer $duration;
        final /* synthetic */ List<Episode> $episodes;
        final /* synthetic */ List<String> $genres;
        final /* synthetic */ String $poster;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, String str2, Integer num, Integer num2, List<String> list, List<Episode> list2, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$poster = str;
            this.$description = str2;
            this.$year = num;
            this.$duration = num2;
            this.$genres = list;
            this.$episodes = list2;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass2 = new AnonymousClass2(this.$poster, this.$description, this.$year, this.$duration, this.$genres, this.$episodes, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                    loadResponse.setPosterUrl(this.$poster);
                    loadResponse.setPlot(this.$description);
                    loadResponse.setYear(this.$year);
                    if (this.$duration != null) {
                        LoadResponse.Companion.addDuration(loadResponse, this.$duration.toString());
                    }
                    loadResponse.setTags(this.$genres);
                    MainAPIKt.addEpisodes(loadResponse, DubStatus.Subbed, this.$episodes);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.DonghuaNoSekai.DonghuaNoSekai$load$3, reason: invalid class name */
    /* JADX INFO: compiled from: DonghuaNoSekai.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/MovieLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.DonghuaNoSekai.DonghuaNoSekai$load$3", f = "DonghuaNoSekai.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $description;
        final /* synthetic */ Integer $duration;
        final /* synthetic */ List<String> $genres;
        final /* synthetic */ String $poster;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(String str, String str2, Integer num, Integer num2, List<String> list, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$poster = str;
            this.$description = str2;
            this.$year = num;
            this.$duration = num2;
            this.$genres = list;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass3 = new AnonymousClass3(this.$poster, this.$description, this.$year, this.$duration, this.$genres, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
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
                    loadResponse.setPosterUrl(this.$poster);
                    loadResponse.setPlot(this.$description);
                    loadResponse.setYear(this.$year);
                    if (this.$duration != null) {
                        LoadResponse.Companion.addDuration(loadResponse, this.$duration.toString());
                    }
                    loadResponse.setTags(this.$genres);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.DonghuaNoSekai.DonghuaNoSekai$search$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DonghuaNoSekai.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.DonghuaNoSekai.DonghuaNoSekai", f = "DonghuaNoSekai.kt", i = {0}, l = {123}, m = "search", n = {"this"}, s = {"L$0"})
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
            return DonghuaNoSekai.this.search(null, (Continuation) this);
        }
    }

    private final String extractDescription(Document document) {
        Iterable iterableSelect = document.select("div.context p");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableSelect, 10));
        Iterator it = iterableSelect.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt.trim(((Element) it.next()).text()).toString());
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList2.iterator();
        while (true) {
            boolean z = false;
            if (!it2.hasNext()) {
                break;
            }
            Object next = it2.next();
            String str = (String) next;
            if ((str.length() > 0) && !StringsKt.contains(str, "PATREON", true)) {
                z = true;
            }
            if (z) {
                arrayList3.add(next);
            }
        }
        String strJoinToString$default = CollectionsKt.joinToString$default(arrayList3, "\n\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
        if (strJoinToString$default.length() > 0) {
            return strJoinToString$default;
        }
        return null;
    }

    private final Integer extractDuration(Document document) {
        int iIntValue;
        Object next;
        String strText;
        MatchResult matchResultFind$default;
        List groupValues;
        String str;
        List groupValues2;
        String str2;
        Integer intOrNull;
        Iterator it = document.select("ul.b_flex.b_wrap.b_space_between li").iterator();
        do {
            iIntValue = 0;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!StringsKt.contains$default(((Element) next).text(), "Duração:", false, 2, (Object) null));
        Element element = (Element) next;
        if (element == null || (strText = element.text()) == null) {
            return null;
        }
        if (!StringsKt.contains$default(strText, "1h", false, 2, (Object) null)) {
            if (!StringsKt.contains$default(strText, "min", false, 2, (Object) null) || (matchResultFind$default = Regex.find$default(DURATION_MINUTES_REGEX, strText, 0, 2, (Object) null)) == null || (groupValues = matchResultFind$default.getGroupValues()) == null || (str = (String) groupValues.get(1)) == null) {
                return null;
            }
            return StringsKt.toIntOrNull(str);
        }
        MatchResult matchResultFind$default2 = Regex.find$default(DURATION_HOURS_REGEX, strText, 0, 2, (Object) null);
        if (matchResultFind$default2 != null && (groupValues2 = matchResultFind$default2.getGroupValues()) != null && (str2 = (String) groupValues2.get(1)) != null && (intOrNull = StringsKt.toIntOrNull(str2)) != null) {
            iIntValue = intOrNull.intValue();
        }
        return Integer.valueOf(iIntValue + 60);
    }

    private final List<Integer> extractEpisodeNumbers(String text) {
        List groupValues;
        String str;
        Integer intOrNull;
        int i;
        int iIntValue;
        ArrayList arrayList = new ArrayList();
        int iIntValue2 = 0;
        MatchResult matchResultFind$default = Regex.find$default(EPISODE_RANGE_REGEX, text, 0, 2, (Object) null);
        if (matchResultFind$default != null) {
            Integer intOrNull2 = StringsKt.toIntOrNull((String) matchResultFind$default.getGroupValues().get(1));
            int iIntValue3 = intOrNull2 != null ? intOrNull2.intValue() : 0;
            arrayList.add(Integer.valueOf(iIntValue3));
            Integer intOrNull3 = StringsKt.toIntOrNull((String) matchResultFind$default.getGroupValues().get(2));
            if (intOrNull3 != null) {
                arrayList.add(intOrNull3);
            }
            Integer intOrNull4 = StringsKt.toIntOrNull((String) matchResultFind$default.getGroupValues().get(3));
            if (intOrNull4 != null && intOrNull4.intValue() > iIntValue3 && (i = iIntValue3 + 1) <= (iIntValue = intOrNull4.intValue())) {
                while (true) {
                    arrayList.add(Integer.valueOf(i));
                    if (i == iIntValue) {
                        break;
                    }
                    i++;
                }
            }
        } else {
            MatchResult matchResultFind$default2 = Regex.find$default(EPISODE_SIMPLE_REGEX, text, 0, 2, (Object) null);
            if (matchResultFind$default2 != null && (groupValues = matchResultFind$default2.getGroupValues()) != null && (str = (String) groupValues.get(1)) != null && (intOrNull = StringsKt.toIntOrNull(str)) != null) {
                iIntValue2 = intOrNull.intValue();
            }
            int i2 = iIntValue2;
            if (i2 > 0) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        return CollectionsKt.sorted(CollectionsKt.distinct(arrayList));
    }

    private final List<String> extractGenres(Document document) {
        Iterable iterableSelect = document.select("div.genresL a");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableSelect, 10));
        Iterator it = iterableSelect.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt.trim(((Element) it.next()).text()).toString());
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (((String) obj).length() > 0) {
                arrayList3.add(obj);
            }
        }
        return CollectionsKt.toMutableList(CollectionsKt.distinct(arrayList3));
    }

    private final String extractPoster(Document document) {
        String strAttr;
        MatchResult matchResultFind$default;
        List groupValues;
        Element elementSelectFirst = document.selectFirst("div.mew");
        if (elementSelectFirst == null || (strAttr = elementSelectFirst.attr("style")) == null || (matchResultFind$default = Regex.find$default(POSTER_REGEX, strAttr, 0, 2, (Object) null)) == null || (groupValues = matchResultFind$default.getGroupValues()) == null) {
            return null;
        }
        return (String) groupValues.get(1);
    }

    private final int extractSeasonNumber(String text) {
        List groupValues;
        String str;
        Integer intOrNull;
        MatchResult matchResultFind$default = Regex.find$default(SEASON_REGEX, text, 0, 2, (Object) null);
        if (matchResultFind$default == null || (groupValues = matchResultFind$default.getGroupValues()) == null || (str = (String) groupValues.get(1)) == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) {
            return 1;
        }
        return intOrNull.intValue();
    }

    private final Integer extractYear(Document document) {
        Object next;
        String strText;
        MatchResult matchResultFind$default;
        List groupValues;
        String str;
        Iterator it = document.select("ul.b_flex.b_wrap.b_space_between li").iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!StringsKt.contains$default(((Element) next).text(), "Ano:", false, 2, (Object) null));
        Element element = (Element) next;
        if (element == null || (strText = element.text()) == null || (matchResultFind$default = Regex.find$default(YEAR_REGEX, strText, 0, 2, (Object) null)) == null || (groupValues = matchResultFind$default.getGroupValues()) == null || (str = (String) groupValues.get(1)) == null) {
            return null;
        }
        return StringsKt.toIntOrNull(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object loadEpisodesFromPage(Document document, String str, Continuation<? super List<Episode>> continuation) {
        Iterable iterableSelect = document.select("div.episode_list div.item");
        boolean z = false;
        ArrayList arrayList = new ArrayList();
        Iterable<Element> iterable = iterableSelect;
        boolean z2 = false;
        for (Element element : iterable) {
            Iterable iterable2 = iterableSelect;
            String strFixUrl = MainAPIKt.fixUrl(this, element.select("a").attr("href"));
            String string = StringsKt.trim(element.select("div.dados div.title h3 span.anime").text()).toString();
            final String string2 = StringsKt.trim(element.select("div.dados div.title h3 span.episode").text()).toString();
            List<Integer> listExtractEpisodeNumbers = extractEpisodeNumbers(string2);
            boolean z3 = z;
            final int iExtractSeasonNumber = extractSeasonNumber(string);
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = listExtractEpisodeNumbers.iterator();
            while (it.hasNext()) {
                Iterable iterable3 = iterable;
                final int iIntValue = ((Number) it.next()).intValue();
                Episode episodeNewEpisode = iIntValue > 0 ? MainAPIKt.newEpisode(this, strFixUrl, new Function1() { // from class: com.DonghuaNoSekai.DonghuaNoSekai$$ExternalSyntheticLambda0
                    public final Object invoke(Object obj) {
                        return DonghuaNoSekai.loadEpisodesFromPage$lambda$17$lambda$16$lambda$15(string2, iIntValue, iExtractSeasonNumber, (Episode) obj);
                    }
                }) : null;
                if (episodeNewEpisode != null) {
                    arrayList2.add(episodeNewEpisode);
                }
                this = this;
                iterable = iterable3;
                z2 = z2;
            }
            arrayList.add(arrayList2);
            iterableSelect = iterable2;
            z = z3;
        }
        return CollectionsKt.sortedWith(CollectionsKt.flatten(arrayList), ComparisonsKt.compareBy(new Function1[]{new Function1() { // from class: com.DonghuaNoSekai.DonghuaNoSekai$$ExternalSyntheticLambda1
            public final Object invoke(Object obj) {
                return DonghuaNoSekai.loadEpisodesFromPage$lambda$18((Episode) obj);
            }
        }, new Function1() { // from class: com.DonghuaNoSekai.DonghuaNoSekai$$ExternalSyntheticLambda2
            public final Object invoke(Object obj) {
                return DonghuaNoSekai.loadEpisodesFromPage$lambda$19((Episode) obj);
            }
        }}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadEpisodesFromPage$lambda$17$lambda$16$lambda$15(String str, int i, int i2, Episode episode) {
        episode.setName(str);
        episode.setEpisode(Integer.valueOf(i));
        episode.setSeason(Integer.valueOf(i2));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable loadEpisodesFromPage$lambda$18(Episode episode) {
        return episode.getSeason();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable loadEpisodesFromPage$lambda$19(Episode episode) {
        return episode.getEpisode();
    }

    private final SearchResponse toSearchResult(Element element) {
        String string = StringsKt.trim(element.select("div.dados div.title h3 span.sxcd").text()).toString();
        String strFixUrl = MainAPIKt.fixUrl(this, element.select("a").attr("href"));
        final String strAttr = element.select("div.thumb img").attr("src");
        StringsKt.trim(element.select("div.status").text()).toString();
        String strAttr2 = element.select("div.selos").attr("data-ona");
        return MainAPIKt.newAnimeSearchResponse$default(this, string, strFixUrl, Intrinsics.areEqual(strAttr2, "Movie") || Intrinsics.areEqual(strAttr2, "Special") ? TvType.AnimeMovie : TvType.Anime, false, new Function1() { // from class: com.DonghuaNoSekai.DonghuaNoSekai$$ExternalSyntheticLambda3
            public final Object invoke(Object obj) {
                return DonghuaNoSekai.toSearchResult$lambda$2(strAttr, (AnimeSearchResponse) obj);
            }
        }, 8, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResult$lambda$2(String str, AnimeSearchResponse animeSearchResponse) {
        animeSearchResponse.setPosterUrl(str);
        return Unit.INSTANCE;
    }

    public boolean getHasDownloadSupport() {
        return this.hasDownloadSupport;
    }

    public boolean getHasMainPage() {
        return this.hasMainPage;
    }

    public boolean getHasQuickSearch() {
        return this.hasQuickSearch;
    }

    @NotNull
    public String getLang() {
        return this.lang;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object getMainPage(int i, @NotNull MainPageRequest mainPageRequest, @NotNull Continuation<? super HomePageResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        MainPageRequest mainPageRequest2;
        DonghuaNoSekai donghuaNoSekai;
        Object safe;
        List<String> results;
        String strJoinToString$default;
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
        AnonymousClass1 anonymousClass2 = anonymousClass1;
        Object objPost$default = anonymousClass2.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (anonymousClass2.label) {
            case 0:
                ResultKt.throwOnFailure(objPost$default);
                String str = this.genreFilters.get(mainPageRequest.getData());
                if (str == null) {
                    str = "2";
                }
                StringBuilder sb = new StringBuilder();
                sb.append("%7B%22filter_data%22%3A%22filter_letter%3D0%26type_url%3DONA%26filter_status%3Dall%26filter_animation%3Dall%26filter_audio%3Dundefined%26filter_order%3Dname%22%2C%22filter_genre_add%22%3A%5B%22");
                sb.append(str);
                sb.append("%22%5D%2C%22filter_genre_del%22%3A%5B%5D%7D");
                Map mapMapOf = MapsKt.mapOf(new Pair[]{TuplesKt.to("action", "getListFilter"), TuplesKt.to("token", "d01c3e1cc8"), TuplesKt.to("pagina", String.valueOf(i)), TuplesKt.to("search", "0"), TuplesKt.to("limit", "30"), TuplesKt.to("type", "lista"), TuplesKt.to("filters", sb.toString())});
                Requests app = MainActivityKt.getApp();
                String str2 = getMainUrl() + "/wp-admin/admin-ajax.php";
                Map mapMapOf2 = MapsKt.mapOf(new Pair[]{TuplesKt.to("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"), TuplesKt.to("X-Requested-With", "XMLHttpRequest"), TuplesKt.to("Accept", "application/json, text/javascript, */*; q=0.01"), TuplesKt.to("Origin", getMainUrl()), TuplesKt.to("Referer", getMainUrl() + "/donghuas")});
                anonymousClass2.L$0 = this;
                anonymousClass2.L$1 = mainPageRequest;
                anonymousClass2.label = 1;
                mainPageRequest2 = mainPageRequest;
                objPost$default = Requests.post$default(app, str2, mapMapOf2, (String) null, (Map) null, (Map) null, mapMapOf, (List) null, (Object) null, (RequestBody) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, anonymousClass2, 65500, (Object) null);
                if (objPost$default == coroutine_suspended) {
                    return coroutine_suspended;
                }
                donghuaNoSekai = this;
                break;
            case 1:
                MainPageRequest mainPageRequest3 = (MainPageRequest) anonymousClass2.L$1;
                donghuaNoSekai = (DonghuaNoSekai) anonymousClass2.L$0;
                ResultKt.throwOnFailure(objPost$default);
                mainPageRequest2 = mainPageRequest3;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        NiceResponse niceResponse = (NiceResponse) objPost$default;
        try {
            ResponseParser parser = niceResponse.getParser();
            Intrinsics.checkNotNull(parser);
            safe = parser.parseSafe(niceResponse.getText(), Reflection.getOrCreateKotlinClass(AjaxResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
            safe = null;
        }
        AjaxResponse ajaxResponse = (AjaxResponse) safe;
        String str3 = "";
        if (ajaxResponse != null && (results = ajaxResponse.getResults()) != null && (strJoinToString$default = CollectionsKt.joinToString$default(results, "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)) != null) {
            str3 = strJoinToString$default;
        }
        Iterable iterableSelect = Jsoup.parse(str3).select("div.itemE.capa");
        ArrayList arrayList = new ArrayList();
        Iterator it = iterableSelect.iterator();
        while (it.hasNext()) {
            SearchResponse searchResult = donghuaNoSekai.toSearchResult((Element) it.next());
            if (searchResult != null) {
                arrayList.add(searchResult);
            }
        }
        return MainAPIKt.newHomePageResponse(new HomePageList(mainPageRequest2.getName(), arrayList, false), Boxing.boxBoolean((ajaxResponse != null ? ajaxResponse.getPage() : 0) < (ajaxResponse != null ? ajaxResponse.getTotal_page() : 0)));
    }

    @NotNull
    public List<MainPageData> getMainPage() {
        return this.mainPage;
    }

    @NotNull
    public String getMainUrl() {
        return this.mainUrl;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public Set<TvType> getSupportedTypes() {
        return this.supportedTypes;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:30:0x0122  */
    /* JADX WARN: Code duplicated, block: B:33:0x0148 A[LOOP:0: B:28:0x011c->B:33:0x0148, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:40:0x0168  */
    /* JADX WARN: Code duplicated, block: B:43:0x017a  */
    /* JADX WARN: Code duplicated, block: B:45:0x0191  */
    /* JADX WARN: Code duplicated, block: B:46:0x0193  */
    /* JADX WARN: Code duplicated, block: B:49:0x019d A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:52:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:55:0x01a6  */
    /* JADX WARN: Code duplicated, block: B:56:0x01a9  */
    /* JADX WARN: Code duplicated, block: B:59:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:61:0x01cf A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:62:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:65:0x0222 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:67:0x0226  */
    /* JADX WARN: Code duplicated, block: B:69:0x0259 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:72:0x014f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:0x0152 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object load(@NotNull String str, @NotNull Continuation<? super LoadResponse> continuation) {
        C00001 c00001;
        Object obj;
        String str2;
        DonghuaNoSekai donghuaNoSekai;
        Document document;
        Element elementSelectFirst;
        String string;
        String str3;
        String strExtractDescription;
        Integer numExtractYear;
        Integer numExtractDuration;
        List<String> listExtractGenres;
        String strExtractPoster;
        Iterator it;
        String str4;
        Object obj2;
        Element element;
        String string2;
        Document document2;
        Object obj3;
        boolean z;
        boolean z2;
        TvType tvType;
        TvType tvType2;
        Object obj4;
        Object objLoadEpisodesFromPage;
        Object obj5;
        Integer num;
        Integer num2;
        String str5;
        String str6;
        DonghuaNoSekai donghuaNoSekai2;
        String str7;
        Object obj6;
        String str8;
        List<String> list;
        String strText;
        Object next;
        Iterator it2;
        String strText2;
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
        Object objNewMovieLoadResponse = c00001.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00001.label) {
            case 0:
                ResultKt.throwOnFailure(objNewMovieLoadResponse);
                Requests app = MainActivityKt.getApp();
                c00001.L$0 = this;
                c00001.L$1 = str;
                c00001.label = 1;
                objNewMovieLoadResponse = Requests.get$default(app, str, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00001, 4094, (Object) null);
                obj = coroutine_suspended;
                if (objNewMovieLoadResponse == obj) {
                    return obj;
                }
                str2 = str;
                donghuaNoSekai = this;
                document = ((NiceResponse) objNewMovieLoadResponse).getDocument();
                elementSelectFirst = document.selectFirst("div.dados h1");
                if (elementSelectFirst != null || (strText2 = elementSelectFirst.text()) == null || (string = StringsKt.trim(strText2).toString()) == null) {
                    string = "";
                }
                str3 = string;
                strExtractDescription = donghuaNoSekai.extractDescription(document);
                numExtractYear = donghuaNoSekai.extractYear(document);
                numExtractDuration = donghuaNoSekai.extractDuration(document);
                listExtractGenres = donghuaNoSekai.extractGenres(document);
                strExtractPoster = donghuaNoSekai.extractPoster(document);
                it = document.select("ul.b_flex.b_wrap.b_space_between li").iterator();
                while (true) {
                    if (it.hasNext()) {
                        next = it.next();
                        it2 = it;
                        obj2 = next;
                        str4 = null;
                        if (StringsKt.contains$default(((Element) next).text(), "Tipo:", false, 2, (Object) null)) {
                            it = it2;
                        }
                    } else {
                        str4 = null;
                        obj2 = null;
                    }
                }
                element = (Element) obj2;
                if (element != null || (strText = element.text()) == null) {
                    string2 = str4;
                } else {
                    string2 = StringsKt.trim(strText).toString();
                }
                boolean z3 = !document.select("div.episode_list").isEmpty();
                if (string2 != null) {
                    document2 = document;
                    obj3 = obj;
                    z = StringsKt.contains$default(string2, "Movie", false, 2, str4);
                    if (z && z3) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z2) {
                        tvType = TvType.AnimeMovie;
                    } else {
                        tvType = TvType.Anime;
                    }
                    tvType2 = tvType;
                    if (tvType2 == TvType.Anime) {
                        obj4 = obj3;
                        String str9 = str4;
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3(strExtractPoster, strExtractDescription, numExtractYear, numExtractDuration, listExtractGenres, null);
                        c00001.L$0 = str9;
                        c00001.L$1 = str9;
                        c00001.label = 4;
                        objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(donghuaNoSekai, str3, str2, tvType2, str2, anonymousClass3, c00001);
                        if (objNewMovieLoadResponse == obj4) {
                            return obj4;
                        }
                        return (LoadResponse) objNewMovieLoadResponse;
                    }
                    c00001.L$0 = donghuaNoSekai;
                    c00001.L$1 = str2;
                    c00001.L$2 = str3;
                    c00001.L$3 = strExtractDescription;
                    c00001.L$4 = numExtractYear;
                    c00001.L$5 = numExtractDuration;
                    c00001.L$6 = listExtractGenres;
                    c00001.L$7 = strExtractPoster;
                    c00001.L$8 = tvType2;
                    c00001.label = 2;
                    objLoadEpisodesFromPage = donghuaNoSekai.loadEpisodesFromPage(document2, str2, c00001);
                    obj5 = obj3;
                    if (objLoadEpisodesFromPage == obj5) {
                        return obj5;
                    }
                    num = numExtractDuration;
                    num2 = numExtractYear;
                    str5 = strExtractDescription;
                    str6 = str2;
                    donghuaNoSekai2 = donghuaNoSekai;
                    str7 = str3;
                    obj6 = objLoadEpisodesFromPage;
                    str8 = strExtractPoster;
                    list = listExtractGenres;
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(str8, str5, num2, num, list, (List) obj6, null);
                    c00001.L$0 = str4;
                    c00001.L$1 = str4;
                    c00001.L$2 = str4;
                    c00001.L$3 = str4;
                    c00001.L$4 = str4;
                    c00001.L$5 = str4;
                    c00001.L$6 = str4;
                    c00001.L$7 = str4;
                    c00001.L$8 = str4;
                    c00001.label = 3;
                    objNewMovieLoadResponse = MainAPIKt.newAnimeLoadResponse$default(donghuaNoSekai2, str7, str6, tvType2, false, anonymousClass2, c00001, 8, (Object) null);
                    if (objNewMovieLoadResponse == obj5) {
                        return obj5;
                    }
                    return (LoadResponse) objNewMovieLoadResponse;
                }
                document2 = document;
                obj3 = obj;
                if (z) {
                    z2 = true;
                } else {
                    z2 = true;
                }
                if (z2) {
                    tvType = TvType.AnimeMovie;
                } else {
                    tvType = TvType.Anime;
                }
                tvType2 = tvType;
                if (tvType2 == TvType.Anime) {
                    obj4 = obj3;
                    String str10 = str4;
                    AnonymousClass3 anonymousClass4 = new AnonymousClass3(strExtractPoster, strExtractDescription, numExtractYear, numExtractDuration, listExtractGenres, null);
                    c00001.L$0 = str10;
                    c00001.L$1 = str10;
                    c00001.label = 4;
                    objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(donghuaNoSekai, str3, str2, tvType2, str2, anonymousClass4, c00001);
                    if (objNewMovieLoadResponse == obj4) {
                        return obj4;
                    }
                    return (LoadResponse) objNewMovieLoadResponse;
                }
                c00001.L$0 = donghuaNoSekai;
                c00001.L$1 = str2;
                c00001.L$2 = str3;
                c00001.L$3 = strExtractDescription;
                c00001.L$4 = numExtractYear;
                c00001.L$5 = numExtractDuration;
                c00001.L$6 = listExtractGenres;
                c00001.L$7 = strExtractPoster;
                c00001.L$8 = tvType2;
                c00001.label = 2;
                objLoadEpisodesFromPage = donghuaNoSekai.loadEpisodesFromPage(document2, str2, c00001);
                obj5 = obj3;
                if (objLoadEpisodesFromPage == obj5) {
                    return obj5;
                }
                num = numExtractDuration;
                num2 = numExtractYear;
                str5 = strExtractDescription;
                str6 = str2;
                donghuaNoSekai2 = donghuaNoSekai;
                str7 = str3;
                obj6 = objLoadEpisodesFromPage;
                str8 = strExtractPoster;
                list = listExtractGenres;
                AnonymousClass2 anonymousClass5 = new AnonymousClass2(str8, str5, num2, num, list, (List) obj6, null);
                c00001.L$0 = str4;
                c00001.L$1 = str4;
                c00001.L$2 = str4;
                c00001.L$3 = str4;
                c00001.L$4 = str4;
                c00001.L$5 = str4;
                c00001.L$6 = str4;
                c00001.L$7 = str4;
                c00001.L$8 = str4;
                c00001.label = 3;
                objNewMovieLoadResponse = MainAPIKt.newAnimeLoadResponse$default(donghuaNoSekai2, str7, str6, tvType2, false, anonymousClass5, c00001, 8, (Object) null);
                if (objNewMovieLoadResponse == obj5) {
                    return obj5;
                }
                return (LoadResponse) objNewMovieLoadResponse;
            case 1:
                String str11 = (String) c00001.L$1;
                DonghuaNoSekai donghuaNoSekai3 = (DonghuaNoSekai) c00001.L$0;
                ResultKt.throwOnFailure(objNewMovieLoadResponse);
                str2 = str11;
                donghuaNoSekai = donghuaNoSekai3;
                obj = coroutine_suspended;
                document = ((NiceResponse) objNewMovieLoadResponse).getDocument();
                elementSelectFirst = document.selectFirst("div.dados h1");
                if (elementSelectFirst != null) {
                    string = "";
                } else {
                    string = "";
                }
                str3 = string;
                strExtractDescription = donghuaNoSekai.extractDescription(document);
                numExtractYear = donghuaNoSekai.extractYear(document);
                numExtractDuration = donghuaNoSekai.extractDuration(document);
                listExtractGenres = donghuaNoSekai.extractGenres(document);
                strExtractPoster = donghuaNoSekai.extractPoster(document);
                it = document.select("ul.b_flex.b_wrap.b_space_between li").iterator();
                while (true) {
                    if (it.hasNext()) {
                        next = it.next();
                        it2 = it;
                        obj2 = next;
                        str4 = null;
                        if (StringsKt.contains$default(((Element) next).text(), "Tipo:", false, 2, (Object) null)) {
                            it = it2;
                        }
                    } else {
                        str4 = null;
                        obj2 = null;
                    }
                }
                element = (Element) obj2;
                if (element != null) {
                    string2 = str4;
                } else {
                    string2 = str4;
                }
                boolean z4 = !document.select("div.episode_list").isEmpty();
                if (string2 != null) {
                    document2 = document;
                    obj3 = obj;
                    if (StringsKt.contains$default(string2, "Movie", false, 2, str4)) {
                    }
                    if (z) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                    if (z2) {
                        tvType = TvType.AnimeMovie;
                    } else {
                        tvType = TvType.Anime;
                    }
                    tvType2 = tvType;
                    if (tvType2 == TvType.Anime) {
                        obj4 = obj3;
                        String str12 = str4;
                        AnonymousClass3 anonymousClass6 = new AnonymousClass3(strExtractPoster, strExtractDescription, numExtractYear, numExtractDuration, listExtractGenres, null);
                        c00001.L$0 = str12;
                        c00001.L$1 = str12;
                        c00001.label = 4;
                        objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(donghuaNoSekai, str3, str2, tvType2, str2, anonymousClass6, c00001);
                        if (objNewMovieLoadResponse == obj4) {
                            return obj4;
                        }
                        return (LoadResponse) objNewMovieLoadResponse;
                    }
                    c00001.L$0 = donghuaNoSekai;
                    c00001.L$1 = str2;
                    c00001.L$2 = str3;
                    c00001.L$3 = strExtractDescription;
                    c00001.L$4 = numExtractYear;
                    c00001.L$5 = numExtractDuration;
                    c00001.L$6 = listExtractGenres;
                    c00001.L$7 = strExtractPoster;
                    c00001.L$8 = tvType2;
                    c00001.label = 2;
                    objLoadEpisodesFromPage = donghuaNoSekai.loadEpisodesFromPage(document2, str2, c00001);
                    obj5 = obj3;
                    if (objLoadEpisodesFromPage == obj5) {
                        return obj5;
                    }
                    num = numExtractDuration;
                    num2 = numExtractYear;
                    str5 = strExtractDescription;
                    str6 = str2;
                    donghuaNoSekai2 = donghuaNoSekai;
                    str7 = str3;
                    obj6 = objLoadEpisodesFromPage;
                    str8 = strExtractPoster;
                    list = listExtractGenres;
                    AnonymousClass2 anonymousClass7 = new AnonymousClass2(str8, str5, num2, num, list, (List) obj6, null);
                    c00001.L$0 = str4;
                    c00001.L$1 = str4;
                    c00001.L$2 = str4;
                    c00001.L$3 = str4;
                    c00001.L$4 = str4;
                    c00001.L$5 = str4;
                    c00001.L$6 = str4;
                    c00001.L$7 = str4;
                    c00001.L$8 = str4;
                    c00001.label = 3;
                    objNewMovieLoadResponse = MainAPIKt.newAnimeLoadResponse$default(donghuaNoSekai2, str7, str6, tvType2, false, anonymousClass7, c00001, 8, (Object) null);
                    if (objNewMovieLoadResponse == obj5) {
                        return obj5;
                    }
                    return (LoadResponse) objNewMovieLoadResponse;
                }
                document2 = document;
                obj3 = obj;
                if (z) {
                    z2 = true;
                } else {
                    z2 = true;
                }
                if (z2) {
                    tvType = TvType.AnimeMovie;
                } else {
                    tvType = TvType.Anime;
                }
                tvType2 = tvType;
                if (tvType2 == TvType.Anime) {
                    obj4 = obj3;
                    String str13 = str4;
                    AnonymousClass3 anonymousClass8 = new AnonymousClass3(strExtractPoster, strExtractDescription, numExtractYear, numExtractDuration, listExtractGenres, null);
                    c00001.L$0 = str13;
                    c00001.L$1 = str13;
                    c00001.label = 4;
                    objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(donghuaNoSekai, str3, str2, tvType2, str2, anonymousClass8, c00001);
                    if (objNewMovieLoadResponse == obj4) {
                        return obj4;
                    }
                    return (LoadResponse) objNewMovieLoadResponse;
                }
                c00001.L$0 = donghuaNoSekai;
                c00001.L$1 = str2;
                c00001.L$2 = str3;
                c00001.L$3 = strExtractDescription;
                c00001.L$4 = numExtractYear;
                c00001.L$5 = numExtractDuration;
                c00001.L$6 = listExtractGenres;
                c00001.L$7 = strExtractPoster;
                c00001.L$8 = tvType2;
                c00001.label = 2;
                objLoadEpisodesFromPage = donghuaNoSekai.loadEpisodesFromPage(document2, str2, c00001);
                obj5 = obj3;
                if (objLoadEpisodesFromPage == obj5) {
                    return obj5;
                }
                num = numExtractDuration;
                num2 = numExtractYear;
                str5 = strExtractDescription;
                str6 = str2;
                donghuaNoSekai2 = donghuaNoSekai;
                str7 = str3;
                obj6 = objLoadEpisodesFromPage;
                str8 = strExtractPoster;
                list = listExtractGenres;
                AnonymousClass2 anonymousClass9 = new AnonymousClass2(str8, str5, num2, num, list, (List) obj6, null);
                c00001.L$0 = str4;
                c00001.L$1 = str4;
                c00001.L$2 = str4;
                c00001.L$3 = str4;
                c00001.L$4 = str4;
                c00001.L$5 = str4;
                c00001.L$6 = str4;
                c00001.L$7 = str4;
                c00001.L$8 = str4;
                c00001.label = 3;
                objNewMovieLoadResponse = MainAPIKt.newAnimeLoadResponse$default(donghuaNoSekai2, str7, str6, tvType2, false, anonymousClass9, c00001, 8, (Object) null);
                if (objNewMovieLoadResponse == obj5) {
                    return obj5;
                }
                return (LoadResponse) objNewMovieLoadResponse;
            case 2:
                TvType tvType3 = (TvType) c00001.L$8;
                String str14 = (String) c00001.L$7;
                List<String> list2 = (List) c00001.L$6;
                Integer num3 = (Integer) c00001.L$5;
                Integer num4 = (Integer) c00001.L$4;
                String str15 = (String) c00001.L$3;
                String str16 = (String) c00001.L$2;
                String str17 = (String) c00001.L$1;
                DonghuaNoSekai donghuaNoSekai4 = (DonghuaNoSekai) c00001.L$0;
                ResultKt.throwOnFailure(objNewMovieLoadResponse);
                tvType2 = tvType3;
                num = num3;
                num2 = num4;
                str5 = str15;
                str7 = str16;
                str6 = str17;
                donghuaNoSekai2 = donghuaNoSekai4;
                obj5 = coroutine_suspended;
                obj6 = objNewMovieLoadResponse;
                list = list2;
                str4 = null;
                str8 = str14;
                AnonymousClass2 anonymousClass10 = new AnonymousClass2(str8, str5, num2, num, list, (List) obj6, null);
                c00001.L$0 = str4;
                c00001.L$1 = str4;
                c00001.L$2 = str4;
                c00001.L$3 = str4;
                c00001.L$4 = str4;
                c00001.L$5 = str4;
                c00001.L$6 = str4;
                c00001.L$7 = str4;
                c00001.L$8 = str4;
                c00001.label = 3;
                objNewMovieLoadResponse = MainAPIKt.newAnimeLoadResponse$default(donghuaNoSekai2, str7, str6, tvType2, false, anonymousClass10, c00001, 8, (Object) null);
                if (objNewMovieLoadResponse == obj5) {
                    return obj5;
                }
                return (LoadResponse) objNewMovieLoadResponse;
            case 3:
                ResultKt.throwOnFailure(objNewMovieLoadResponse);
                return (LoadResponse) objNewMovieLoadResponse;
            case 4:
                ResultKt.throwOnFailure(objNewMovieLoadResponse);
                return (LoadResponse) objNewMovieLoadResponse;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @Nullable
    public Object loadLinks(@NotNull String str, boolean z, @NotNull Function1<? super SubtitleFile, Unit> function1, @NotNull Function1<? super ExtractorLink, Unit> function2, @NotNull Continuation<? super Boolean> continuation) {
        return DonghuaNoSekaiExtractor.INSTANCE.extractVideoLinks(str, getMainUrl(), getName(), function2, continuation);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object search(@NotNull String str, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        C00011 c00011;
        DonghuaNoSekai donghuaNoSekai;
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
        Object obj = c00011.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00011.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                String str2 = getMainUrl() + "/?s=" + StringsKt.replace$default(str, " ", "+", false, 4, (Object) null);
                Requests app = MainActivityKt.getApp();
                c00011.L$0 = this;
                c00011.label = 1;
                obj = Requests.get$default(app, str2, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00011, 4094, (Object) null);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                donghuaNoSekai = this;
                break;
            case 1:
                donghuaNoSekai = (DonghuaNoSekai) c00011.L$0;
                ResultKt.throwOnFailure(obj);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Iterable iterableSelect = ((NiceResponse) obj).getDocument().select("div.b_flex.b_wrap div.itemE.capa");
        ArrayList arrayList = new ArrayList();
        Iterator it = iterableSelect.iterator();
        while (it.hasNext()) {
            SearchResponse searchResult = donghuaNoSekai.toSearchResult((Element) it.next());
            if (searchResult != null) {
                arrayList.add(searchResult);
            }
        }
        return arrayList;
    }

    public void setLang(@NotNull String str) {
        this.lang = str;
    }

    public void setMainUrl(@NotNull String str) {
        this.mainUrl = str;
    }

    public void setName(@NotNull String str) {
        this.name = str;
    }
}
