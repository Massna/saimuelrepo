package com.TopFilmes;

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
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.TvType;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.cloudstream3.utils.ExtractorLinkType;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.DelayKt;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* JADX INFO: compiled from: TopFilmes.kt */
/* JADX INFO: loaded from: /tmp/cs3/TopFilmes/classes.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010 \u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0096@¢\u0006\u0002\u0010'J\u000e\u0010(\u001a\u0004\u0018\u00010)*\u00020*H\u0002J\u001c\u0010+\u001a\b\u0012\u0004\u0012\u00020)0\u001e2\u0006\u0010,\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010-J\u0016\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0005H\u0096@¢\u0006\u0002\u0010-J\u001e\u00101\u001a\b\u0012\u0004\u0012\u00020\u00050\u001e2\u0006\u00102\u001a\u0002032\u0006\u00100\u001a\u00020\u0005H\u0002JF\u00104\u001a\u00020\u000e2\u0006\u00105\u001a\u00020\u00052\u0006\u00106\u001a\u00020\u000e2\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020:082\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020:08H\u0096@¢\u0006\u0002\u0010=R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0014\u0010\r\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0007\"\u0004\b\u0013\u0010\tR\u0014\u0010\u0014\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0014\u0010\u0016\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006>"}, d2 = {"Lcom/TopFilmes/TopFilmes;", "Lcom/lagradost/cloudstream3/MainAPI;", "<init>", "()V", "mainUrl", "", "getMainUrl", "()Ljava/lang/String;", "setMainUrl", "(Ljava/lang/String;)V", "name", "getName", "setName", "hasMainPage", "", "getHasMainPage", "()Z", "lang", "getLang", "setLang", "hasDownloadSupport", "getHasDownloadSupport", "hasQuickSearch", "getHasQuickSearch", "supportedTypes", "", "Lcom/lagradost/cloudstream3/TvType;", "getSupportedTypes", "()Ljava/util/Set;", "mainPage", "", "Lcom/lagradost/cloudstream3/MainPageData;", "getMainPage", "()Ljava/util/List;", "Lcom/lagradost/cloudstream3/HomePageResponse;", "page", "", "request", "Lcom/lagradost/cloudstream3/MainPageRequest;", "(ILcom/lagradost/cloudstream3/MainPageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toSearchResult", "Lcom/lagradost/cloudstream3/SearchResponse;", "Lorg/jsoup/nodes/Element;", "search", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "Lcom/lagradost/cloudstream3/LoadResponse;", "url", "getMovieSources", "document", "Lorg/jsoup/nodes/Document;", "loadLinks", "data", "isCasting", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "TopFilmes_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTopFilmes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TopFilmes.kt\ncom/TopFilmes/TopFilmes\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,276:1\n1611#2,9:277\n1863#2:286\n1864#2:288\n1620#2:289\n1611#2,9:291\n1863#2:300\n1864#2:302\n1620#2:303\n1611#2,9:305\n1863#2:314\n1864#2:316\n1620#2:317\n295#2,2:318\n1#3:287\n1#3:290\n1#3:301\n1#3:304\n1#3:315\n*S KotlinDebug\n*F\n+ 1 TopFilmes.kt\ncom/TopFilmes/TopFilmes\n*L\n64#1:277,9\n64#1:286\n64#1:288\n64#1:289\n133#1:291,9\n133#1:300\n133#1:302\n133#1:303\n166#1:305,9\n166#1:314\n166#1:316\n166#1:317\n251#1:318,2\n64#1:287\n133#1:301\n166#1:315\n*E\n"})
public final class TopFilmes extends MainAPI {

    @NotNull
    private String mainUrl = TopFilmesKt.MAIN_URL;

    @NotNull
    private String name = "TopFilmes";
    private final boolean hasMainPage = true;

    @NotNull
    private String lang = "pt-br";
    private final boolean hasDownloadSupport = true;
    private final boolean hasQuickSearch = true;

    @NotNull
    private final Set<TvType> supportedTypes = SetsKt.setOf(TvType.Movie);

    @NotNull
    private final List<MainPageData> mainPage = MainAPIKt.mainPageOf(new Pair[]{TuplesKt.to("/genero/acao", "Ação"), TuplesKt.to("/genero/animacao", "Animação"), TuplesKt.to("/genero/comedia", "Comédia"), TuplesKt.to("/genero/drama", "Drama"), TuplesKt.to("/genero/documentario", "Documentário"), TuplesKt.to("/genero/ficcao-cientifica", "Ficção Científica"), TuplesKt.to("/genero/fantasia", "Fantasia"), TuplesKt.to("/genero/policial", "Policial"), TuplesKt.to("/genero/misterio", "Mistério"), TuplesKt.to("/genero/romance", "Romance"), TuplesKt.to("/genero/suspense", "Suspense"), TuplesKt.to("/genero/terror", "Terror")});

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$getMainPage$1, reason: invalid class name */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes", f = "TopFilmes.kt", i = {0, 0, 0, 1, 1}, l = {60, 62}, m = "getMainPage", n = {"this", "request", "url", "this", "request"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1"})
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TopFilmes.this.getMainPage(0, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$load$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes", f = "TopFilmes.kt", i = {0, 0}, l = {137, 153}, m = "load", n = {"this", "url"}, s = {"L$0", "L$1"})
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
            return TopFilmes.this.load(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$load$2, reason: invalid class name */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/MovieLoadResponse;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes$load$2", f = "TopFilmes.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<MovieLoadResponse, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<String> $genres;
        final /* synthetic */ String $plot;
        final /* synthetic */ String $poster;
        final /* synthetic */ Integer $year;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, Integer num, String str2, List<String> list, Continuation<?> continuation) {
            super(2, (Continuation) continuation);
            this.$poster = str;
            this.$year = num;
            this.$plot = str2;
            this.$genres = list;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$poster, this.$year, this.$plot, this.$genres, continuation);
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
                    MovieLoadResponse movieLoadResponse = (MovieLoadResponse) this.L$0;
                    movieLoadResponse.setPosterUrl(this.$poster);
                    movieLoadResponse.setYear(this.$year);
                    movieLoadResponse.setPlot(this.$plot);
                    movieLoadResponse.setTags(this.$genres);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$loadLinks$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes", f = "TopFilmes.kt", i = {0, 0}, l = {198, 206, 222, 239, 260}, m = "loadLinks", n = {"this", "callback"}, s = {"L$0", "L$1"})
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
            return TopFilmes.this.loadLinks(null, false, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$loadLinks$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes$loadLinks$2", f = "TopFilmes.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    final class C00022 extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C00022(Continuation<?> continuation) {
            super(2, (Continuation) continuation);
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00022 c00022 = TopFilmes.this.new C00022(continuation);
            c00022.L$0 = obj;
            return (Continuation<Unit>) (Object) c00022;
        }

        public final Object invoke(ExtractorLink extractorLink, Continuation<? super Unit> continuation) {
            return ((C00022) (Object) create(extractorLink, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    ((ExtractorLink) this.L$0).setReferer(TopFilmes.this.getMainUrl());
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$loadLinks$3, reason: invalid class name */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes$loadLinks$3", f = "TopFilmes.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    final class AnonymousClass3 extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass3(Continuation<?> continuation) {
            super(2, (Continuation) continuation);
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = TopFilmes.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass3;
        }

        public final Object invoke(ExtractorLink extractorLink, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) (Object) create(extractorLink, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    ((ExtractorLink) this.L$0).setReferer(TopFilmes.this.getMainUrl());
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$loadLinks$4, reason: invalid class name */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes$loadLinks$4", f = "TopFilmes.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    final class AnonymousClass4 extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass4(Continuation<?> continuation) {
            super(2, (Continuation) continuation);
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass4 anonymousClass4 = TopFilmes.this.new AnonymousClass4(continuation);
            anonymousClass4.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass4;
        }

        public final Object invoke(ExtractorLink extractorLink, Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) (Object) create(extractorLink, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    ((ExtractorLink) this.L$0).setReferer(TopFilmes.this.getMainUrl());
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$loadLinks$5, reason: invalid class name */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes$loadLinks$5", f = "TopFilmes.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    final class AnonymousClass5 extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass5(Continuation<?> continuation) {
            super(2, (Continuation) continuation);
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass5 anonymousClass5 = TopFilmes.this.new AnonymousClass5(continuation);
            anonymousClass5.L$0 = obj;
            return (Continuation<Unit>) (Object) anonymousClass5;
        }

        public final Object invoke(ExtractorLink extractorLink, Continuation<? super Unit> continuation) {
            return ((AnonymousClass5) (Object) create(extractorLink, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    ((ExtractorLink) this.L$0).setReferer(TopFilmes.this.getMainUrl());
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: renamed from: com.TopFilmes.TopFilmes$search$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TopFilmes.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.TopFilmes.TopFilmes", f = "TopFilmes.kt", i = {0, 0, 1}, l = {117, 119}, m = "search", n = {"this", "url", "this"}, s = {"L$0", "L$1", "L$0"})
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
            return TopFilmes.this.search(null, (Continuation) this);
        }
    }

    private final List<String> getMovieSources(Document document, String url) {
        Elements elements;
        Iterable iterable;
        boolean z = false;
        Elements elementsSelect = document.select("div.links_dub a");
        if (elementsSelect.isEmpty()) {
            document.select("a[href*='player']");
        }
        Iterable<Element> iterable2 = (Iterable) elementsSelect;
        boolean z2 = false;
        ArrayList arrayList = new ArrayList();
        for (Element element : iterable2) {
            String strAttr = element.attr("href");
            StringsKt.trim(element.text()).toString();
            String str = null;
            if (strAttr.length() > 0) {
                elements = elementsSelect;
                iterable = iterable2;
                z = z2;
                str = StringsKt.startsWith(strAttr, "//", false) ? "https:" + strAttr : strAttr;
            } else {
                elements = elementsSelect;
                iterable = iterable2;
                z = z2;
            }
            if (str != null) {
                arrayList.add(str);
            }
            elementsSelect = elements;
            iterable2 = iterable;
            z2 = z;
        }
        return arrayList;
    }

    private final SearchResponse toSearchResult(Element element) {
        String strAttr;
        String string;
        String strAttr2;
        String strAttr3;
        String strAttr4;
        String strText = null;
        String strText2;
        String str;
        Integer intOrNull;
        String strText3;
        String strText4;
        String strReplace$default;
        String strText5;
        String strText6;
        String strFixUrl = null;
        if (Intrinsics.areEqual(element.tagName(), "a")) {
            strAttr = element.attr("href");
        } else {
            Element elementSelectFirst = element.selectFirst("a");
            strAttr = elementSelectFirst != null ? elementSelectFirst.attr("href") : null;
        }
        Element elementSelectFirst2 = element.selectFirst("div.title");
        if (elementSelectFirst2 == null || (strText6 = elementSelectFirst2.text()) == null || (string = StringsKt.trim(strText6).toString()) == null) {
            Element elementSelectFirst3 = element.selectFirst("h2");
            if (elementSelectFirst3 == null || (strText2 = elementSelectFirst3.text()) == null) {
                Element elementSelectFirst4 = element.selectFirst("h3");
                string = (elementSelectFirst4 == null || (strText = elementSelectFirst4.text()) == null) ? null : StringsKt.trim(strText).toString();
                if (string == null) {
                    Element elementSelectFirst5 = element.selectFirst("a");
                    string = (elementSelectFirst5 == null || (strAttr4 = elementSelectFirst5.attr("title")) == null) ? null : StringsKt.trim(strAttr4).toString();
                    if (string == null) {
                        Element elementSelectFirst6 = element.selectFirst("img");
                        string = (elementSelectFirst6 == null || (strAttr3 = elementSelectFirst6.attr("alt")) == null) ? null : StringsKt.trim(strAttr3).toString();
                        if (string == null) {
                            string = (!Intrinsics.areEqual(element.tagName(), "a") || (strAttr2 = element.attr("title")) == null) ? null : StringsKt.trim(strAttr2).toString();
                        }
                    }
                }
            } else {
                string = StringsKt.trim(strText2).toString();
            }
        }
        String str2 = string;
        Element elementSelectFirst7 = element.selectFirst("img");
        if (elementSelectFirst7 != null) {
            String strAttr5 = elementSelectFirst7.attr("data-src");
            String strAttr6 = elementSelectFirst7.attr("src");
            String strAttr7 = elementSelectFirst7.attr("data-original");
            String strAttr8 = elementSelectFirst7.attr("data-ll-src");
            String str3 = strAttr5;
            if (str3 == null || StringsKt.isBlank(str3)) {
                String str4 = strAttr6;
                if (str4 == null || StringsKt.isBlank(str4)) {
                    String str5 = strAttr7;
                    if (str5 == null || StringsKt.isBlank(str5)) {
                        String str6 = strAttr8;
                        str = !(str6 == null || StringsKt.isBlank(str6)) ? strAttr8 : null;
                    } else {
                        str = strAttr7;
                    }
                } else {
                    str = strAttr6;
                }
            } else {
                str = strAttr5;
            }
        } else {
            str = null;
        }
        String str7 = str;
        Element elementSelectFirst8 = element.selectFirst("div.year");
        if (elementSelectFirst8 == null || (strText5 = elementSelectFirst8.text()) == null || (intOrNull = StringsKt.toIntOrNull(strText5)) == null) {
            Element elementSelectFirst9 = element.selectFirst("span.year");
            intOrNull = (elementSelectFirst9 == null || (strText4 = elementSelectFirst9.text()) == null) ? null : StringsKt.toIntOrNull(strText4);
            if (intOrNull == null) {
                Element elementSelectFirst10 = element.selectFirst(".year");
                intOrNull = (elementSelectFirst10 == null || (strText3 = elementSelectFirst10.text()) == null) ? null : StringsKt.toIntOrNull(strText3);
            }
        }
        final Integer num = intOrNull;
        if (strAttr == null || str2 == null) {
            return null;
        }
        if (str7 != null && (strReplace$default = StringsKt.replace(str7, "_filter(blur)", "", false)) != null) {
            strFixUrl = MainAPIKt.fixUrl(this, strReplace$default);
        }
        final String strFixUrlF = strFixUrl;
        return MainAPIKt.newMovieSearchResponse(this, str2, MainAPIKt.fixUrl(this, strAttr), TvType.Movie, false, new Function1() { // from class: com.TopFilmes.TopFilmes$$ExternalSyntheticLambda0
            public final Object invoke(Object obj) {
                return TopFilmes.toSearchResult$lambda$3(strFixUrlF, num, (MovieSearchResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSearchResult$lambda$3(String str, Integer num, MovieSearchResponse movieSearchResponse) {
        movieSearchResponse.setPosterUrl(str);
        movieSearchResponse.setYear(num);
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

    /* JADX WARN: Code duplicated, block: B:24:0x0105 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:25:0x0106  */
    /* JADX WARN: Code duplicated, block: B:29:0x012d  */
    /* JADX WARN: Code duplicated, block: B:35:0x0157  */
    /* JADX WARN: Code duplicated, block: B:36:0x0159  */
    /* JADX WARN: Code duplicated, block: B:39:0x0167  */
    /* JADX WARN: Code duplicated, block: B:43:0x013c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x0127 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object getMainPage(int i, @NotNull MainPageRequest mainPageRequest, @NotNull Continuation<? super HomePageResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        StringBuilder sb;
        StringBuilder sbAppend;
        TopFilmes topFilmes;
        MainPageRequest mainPageRequest2;
        String str;
        TopFilmes topFilmes2;
        MainPageRequest mainPageRequest3;
        TopFilmes topFilmes3;
        MainPageRequest mainPageRequest4;
        Document document;
        ArrayList arrayList;
        Iterator it;
        boolean z = false;
        SearchResponse searchResult;
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
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (anonymousClass1.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                if (i == 1) {
                    sb = new StringBuilder();
                    sbAppend = sb.append(getMainUrl()).append(mainPageRequest.getData());
                } else {
                    sb = new StringBuilder();
                    sbAppend = sb.append(getMainUrl()).append(mainPageRequest.getData()).append("/p/").append(i);
                }
                String string = sbAppend.toString();
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = mainPageRequest;
                anonymousClass1.L$2 = string;
                anonymousClass1.label = 1;
                if (DelayKt.delay(3000L, anonymousClass1) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                topFilmes = this;
                mainPageRequest2 = mainPageRequest;
                str = string;
                Map mapMapOf = MapsKt.mapOf(TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
                Requests app = MainActivityKt.getApp();
                String strFixUrl = MainAPIKt.fixUrl(topFilmes, str);
                anonymousClass1.L$0 = topFilmes;
                anonymousClass1.L$1 = mainPageRequest2;
                anonymousClass1.L$2 = null;
                anonymousClass1.label = 2;
                topFilmes2 = topFilmes;
                mainPageRequest3 = mainPageRequest2;
                obj = app.get(strFixUrl, mapMapOf, (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), anonymousClass1);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                topFilmes3 = topFilmes2;
                mainPageRequest4 = mainPageRequest3;
                document = ((NiceResponse) obj).getDocument();
                Iterable iterableSelect = document.select("div.filmes div.filme");
                arrayList = new ArrayList();
                it = iterableSelect.iterator();
                while (it.hasNext()) {
                    searchResult = topFilmes3.toSearchResult((Element) it.next());
                    if (searchResult != null) {
                        arrayList.add(searchResult);
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (document.select("ul.pagination li a[rel=next]").isEmpty()) {
                    z = false;
                } else {
                    z = true;
                }
                return MainAPIKt.newHomePageResponse(new HomePageList(mainPageRequest4.getName(), arrayList2, false), Boxing.boxBoolean(z));
            case 1:
                String str2 = (String) anonymousClass1.L$2;
                MainPageRequest mainPageRequest5 = (MainPageRequest) anonymousClass1.L$1;
                TopFilmes topFilmes4 = (TopFilmes) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
                str = str2;
                mainPageRequest2 = mainPageRequest5;
                topFilmes = topFilmes4;
                Map mapMapOf2 = MapsKt.mapOf(TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
                Requests app2 = MainActivityKt.getApp();
                String strFixUrl2 = MainAPIKt.fixUrl(topFilmes, str);
                anonymousClass1.L$0 = topFilmes;
                anonymousClass1.L$1 = mainPageRequest2;
                anonymousClass1.L$2 = null;
                anonymousClass1.label = 2;
                topFilmes2 = topFilmes;
                mainPageRequest3 = mainPageRequest2;
                obj = app2.get(strFixUrl2, mapMapOf2, (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app2.getDefaultCacheTime(), app2.getDefaultCacheTimeUnit(), app2.getDefaultTimeOut(), (Interceptor) null, true, app2.getResponseParser(), anonymousClass1);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                topFilmes3 = topFilmes2;
                mainPageRequest4 = mainPageRequest3;
                document = ((NiceResponse) obj).getDocument();
                Iterable iterableSelect2 = document.select("div.filmes div.filme");
                arrayList = new ArrayList();
                it = iterableSelect2.iterator();
                while (it.hasNext()) {
                    searchResult = topFilmes3.toSearchResult((Element) it.next());
                    if (searchResult != null) {
                        arrayList.add(searchResult);
                    }
                }
                ArrayList arrayList3 = arrayList;
                if (document.select("ul.pagination li a[rel=next]").isEmpty()) {
                    z = true;
                } else {
                    z = false;
                }
                return MainAPIKt.newHomePageResponse(new HomePageList(mainPageRequest4.getName(), arrayList3, false), Boxing.boxBoolean(z));
            case 2:
                mainPageRequest4 = (MainPageRequest) anonymousClass1.L$1;
                topFilmes3 = (TopFilmes) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
                document = ((NiceResponse) obj).getDocument();
                Iterable iterableSelect3 = document.select("div.filmes div.filme");
                arrayList = new ArrayList();
                it = iterableSelect3.iterator();
                while (it.hasNext()) {
                    searchResult = topFilmes3.toSearchResult((Element) it.next());
                    if (searchResult != null) {
                        arrayList.add(searchResult);
                    }
                }
                ArrayList arrayList4 = arrayList;
                if (document.select("ul.pagination li a[rel=next]").isEmpty()) {
                    z = true;
                } else {
                    z = false;
                }
                return MainAPIKt.newHomePageResponse(new HomePageList(mainPageRequest4.getName(), arrayList4, false), Boxing.boxBoolean(z));
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
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

    /* JADX WARN: Code duplicated, block: B:24:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:30:0x00de  */
    /* JADX WARN: Code duplicated, block: B:36:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:42:0x011d  */
    /* JADX WARN: Code duplicated, block: B:48:0x013e  */
    /* JADX WARN: Code duplicated, block: B:54:0x015a  */
    /* JADX WARN: Code duplicated, block: B:66:0x0184  */
    /* JADX WARN: Code duplicated, block: B:68:0x0188  */
    /* JADX WARN: Code duplicated, block: B:76:0x019a  */
    /* JADX WARN: Code duplicated, block: B:79:0x01d9 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Code duplicated, block: B:80:0x01da A[PHI: r15
      0x01da: PHI (r15v9 java.lang.Object) = (r15v7 java.lang.Object), (r15v0 java.lang.Object) binds: [B:78:0x01d7, B:12:0x0033] A[DONT_GENERATE, DONT_INLINE], RETURN] */
    @Nullable
    public Object load(@NotNull String str, @NotNull Continuation<? super LoadResponse> continuation) {
        C00001 c00001;
        Object obj;
        String str2;
        TopFilmes topFilmes;
        Element elementSelectFirst;
        String string;
        Element elementSelectFirst2;
        String strFixUrl;
        Element element;
        Integer intOrNull;
        boolean z = false;
        Element element2;
        String string2;
        Element element3;
        String string3;
        Element elementSelectFirst3;
        String string4;
        Element elementSelectFirst4;
        ArrayList arrayList;
        String str3;
        boolean z2;
        String str4;
        String strText = null;
        String strText2;
        String strText3;
        String strText4;
        String strText5;
        String strAttr;
        String strText6;
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
                String strFixUrl2 = MainAPIKt.fixUrl(this, str);
                Map mapMapOf = MapsKt.mapOf(TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
                c00001.L$0 = this;
                c00001.L$1 = str;
                c00001.label = 1;
                objNewMovieLoadResponse = app.get(strFixUrl2, mapMapOf, (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00001);
                obj = coroutine_suspended;
                if (objNewMovieLoadResponse == obj) {
                    return obj;
                }
                str2 = str;
                topFilmes = this;
                Document document = ((NiceResponse) objNewMovieLoadResponse).getDocument();
                elementSelectFirst = document.selectFirst("div.infos h2");
                if (elementSelectFirst != null || (strText6 = elementSelectFirst.text()) == null || (string = StringsKt.trim(strText6).toString()) == null) {
                    string = "Sem título";
                }
                elementSelectFirst2 = document.selectFirst("div.player img");
                if (elementSelectFirst2 != null || (strAttr = elementSelectFirst2.attr("src")) == null) {
                    strFixUrl = null;
                } else {
                    strFixUrl = MainAPIKt.fixUrl(topFilmes, strAttr);
                }
                element = (Element) CollectionsKt.getOrNull(document.select("div.infos div.info"), 0);
                if (element != null || (strText5 = element.text()) == null) {
                    intOrNull = null;
                } else {
                    intOrNull = StringsKt.toIntOrNull(strText5);
                }
                z = true;
                element2 = (Element) CollectionsKt.getOrNull(document.select("div.infos div.info"), 1);
                if (element2 != null || (strText4 = element2.text()) == null) {
                    string2 = null;
                } else {
                    string2 = StringsKt.trim(strText4).toString();
                }
                element3 = (Element) CollectionsKt.getOrNull(document.select("div.infos div.info"), 2);
                if (element3 != null || (strText3 = element3.text()) == null) {
                    string3 = null;
                } else {
                    string3 = StringsKt.trim(strText3).toString();
                }
                elementSelectFirst3 = document.selectFirst("div.infos div.sinopse");
                if (elementSelectFirst3 != null || (strText2 = elementSelectFirst3.text()) == null) {
                    string4 = null;
                } else {
                    string4 = StringsKt.trim(strText2).toString();
                }
                elementSelectFirst4 = document.selectFirst("div.infos div.imdb span");
                if (elementSelectFirst4 != null && (strText = elementSelectFirst4.text()) != null) {
                    StringsKt.toFloatOrNull(strText);
                }
                arrayList = new ArrayList();
                str3 = string3;
                if (str3 != null || StringsKt.isBlank(str3)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    arrayList.add(string3);
                }
                str4 = string2;
                if (str4 != null && !StringsKt.isBlank(str4)) {
                    z = false;
                }
                if (!z) {
                    arrayList.add("Duração: " + string2);
                }
                List<String> movieSources = topFilmes.getMovieSources(document, str2);
                TvType tvType = TvType.Movie;
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(strFixUrl, intOrNull, string4, arrayList, null);
                c00001.L$0 = null;
                c00001.L$1 = null;
                c00001.label = 2;
                objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(topFilmes, string, str2, tvType, movieSources, anonymousClass2, c00001);
                if (objNewMovieLoadResponse == obj) {
                    return obj;
                }
                return objNewMovieLoadResponse;
            case 1:
                String str5 = (String) c00001.L$1;
                TopFilmes topFilmes2 = (TopFilmes) c00001.L$0;
                ResultKt.throwOnFailure(objNewMovieLoadResponse);
                str2 = str5;
                topFilmes = topFilmes2;
                obj = coroutine_suspended;
                Document document2 = ((NiceResponse) objNewMovieLoadResponse).getDocument();
                elementSelectFirst = document2.selectFirst("div.infos h2");
                if (elementSelectFirst != null) {
                    string = "Sem título";
                } else {
                    string = "Sem título";
                }
                elementSelectFirst2 = document2.selectFirst("div.player img");
                if (elementSelectFirst2 != null) {
                    strFixUrl = null;
                } else {
                    strFixUrl = null;
                }
                element = (Element) CollectionsKt.getOrNull(document2.select("div.infos div.info"), 0);
                if (element != null) {
                    intOrNull = null;
                } else {
                    intOrNull = null;
                }
                z = true;
                element2 = (Element) CollectionsKt.getOrNull(document2.select("div.infos div.info"), 1);
                if (element2 != null) {
                    string2 = null;
                } else {
                    string2 = null;
                }
                element3 = (Element) CollectionsKt.getOrNull(document2.select("div.infos div.info"), 2);
                if (element3 != null) {
                    string3 = null;
                } else {
                    string3 = null;
                }
                elementSelectFirst3 = document2.selectFirst("div.infos div.sinopse");
                if (elementSelectFirst3 != null) {
                    string4 = null;
                } else {
                    string4 = null;
                }
                elementSelectFirst4 = document2.selectFirst("div.infos div.imdb span");
                if (elementSelectFirst4 != null) {
                    StringsKt.toFloatOrNull(strText);
                }
                arrayList = new ArrayList();
                str3 = string3;
                if (str3 != null) {
                    z2 = true;
                } else {
                    z2 = true;
                }
                if (!z2) {
                    arrayList.add(string3);
                }
                str4 = string2;
                if (str4 != null) {
                    z = false;
                }
                if (!z) {
                    arrayList.add("Duração: " + string2);
                }
                List<String> movieSources2 = topFilmes.getMovieSources(document2, str2);
                TvType tvType2 = TvType.Movie;
                AnonymousClass2 anonymousClass3 = new AnonymousClass2(strFixUrl, intOrNull, string4, arrayList, null);
                c00001.L$0 = null;
                c00001.L$1 = null;
                c00001.label = 2;
                objNewMovieLoadResponse = MainAPIKt.newMovieLoadResponse(topFilmes, string, str2, tvType2, movieSources2, anonymousClass3, c00001);
                if (objNewMovieLoadResponse == obj) {
                    return obj;
                }
                return objNewMovieLoadResponse;
            case 2:
                ResultKt.throwOnFailure(objNewMovieLoadResponse);
                return objNewMovieLoadResponse;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x023d  */
    /* JADX WARN: Code duplicated, block: B:103:0x0248 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:104:0x024d  */
    /* JADX WARN: Code duplicated, block: B:111:0x025d  */
    /* JADX WARN: Code duplicated, block: B:113:0x0260 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:115:0x0266 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:116:0x0279  */
    /* JADX WARN: Code duplicated, block: B:119:0x029b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:120:0x029c  */
    /* JADX WARN: Code duplicated, block: B:123:0x02a5 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:126:0x02b9 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:132:0x02dd  */
    /* JADX WARN: Code duplicated, block: B:137:0x02ea  */
    /* JADX WARN: Code duplicated, block: B:144:0x02f6 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:146:0x0302 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:147:0x0315  */
    /* JADX WARN: Code duplicated, block: B:150:0x0337 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:151:0x0338  */
    /* JADX WARN: Code duplicated, block: B:154:0x0341 A[Catch: Exception -> 0x01d3, TRY_LEAVE, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:168:0x02f0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:53:0x016c A[Catch: Exception -> 0x0346, TRY_ENTER, TryCatch #1 {Exception -> 0x0346, blocks: (B:50:0x0156, B:53:0x016c, B:55:0x0174, B:57:0x017b, B:59:0x0180, B:46:0x0149), top: B:164:0x0149 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x0174 A[Catch: Exception -> 0x0346, TryCatch #1 {Exception -> 0x0346, blocks: (B:50:0x0156, B:53:0x016c, B:55:0x0174, B:57:0x017b, B:59:0x0180, B:46:0x0149), top: B:164:0x0149 }] */
    /* JADX WARN: Code duplicated, block: B:56:0x017a  */
    /* JADX WARN: Code duplicated, block: B:63:0x0189  */
    /* JADX WARN: Code duplicated, block: B:65:0x018c  */
    /* JADX WARN: Code duplicated, block: B:68:0x0195 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:69:0x01a8  */
    /* JADX WARN: Code duplicated, block: B:72:0x01c9 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:73:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:78:0x01d6 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:7:0x001c  */
    /* JADX WARN: Code duplicated, block: B:80:0x01e0 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:81:0x01e5  */
    /* JADX WARN: Code duplicated, block: B:88:0x01f5  */
    /* JADX WARN: Code duplicated, block: B:90:0x01f8 A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:92:0x01fe A[Catch: Exception -> 0x01d3, TryCatch #3 {Exception -> 0x01d3, blocks: (B:152:0x0339, B:121:0x029d, B:98:0x0235, B:74:0x01cb, B:66:0x018f, B:68:0x0195, B:70:0x01a9, B:78:0x01d6, B:80:0x01e0, B:82:0x01e6, B:84:0x01ec, B:90:0x01f8, B:92:0x01fe, B:94:0x0212, B:101:0x0240, B:103:0x0248, B:105:0x024e, B:107:0x0254, B:113:0x0260, B:115:0x0266, B:117:0x027a, B:123:0x02a5, B:124:0x02b3, B:126:0x02b9, B:128:0x02d4, B:134:0x02e0, B:142:0x02f1, B:144:0x02f6, B:146:0x0302, B:148:0x0316, B:154:0x0341), top: B:166:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:93:0x0211  */
    /* JADX WARN: Code duplicated, block: B:96:0x0233 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:97:0x0234  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v5 */
    @Nullable
    public java.lang.Object loadLinks(@NotNull java.lang.String data, boolean isCasting, @NotNull Function1<? super SubtitleFile, Unit> subtitleCallback, @NotNull Function1<? super ExtractorLink, Unit> callback, @NotNull Continuation<? super Boolean> continuation) {
        String UA = "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36";
        boolean found = false;
        try {
            Document doc = Jsoup.connect(data).userAgent(UA).timeout(15000).execute().parse();
            List<String> playerUrls = new ArrayList<>();
            for (Element a : doc.select("a[href]")) {
                String href = a.attr("href");
                if (href.contains("player=")) {
                    String abs = href.startsWith("http") ? href : MainAPIKt.fixUrl(this, href);
                    if (!playerUrls.contains(abs)) {
                        playerUrls.add(abs);
                    }
                }
            }
            int idx = 0;
            for (String pu : playerUrls) {
                idx++;
                Document pd = Jsoup.connect(pu).userAgent(UA).timeout(15000).execute().parse();
                Element srcEl = pd.selectFirst("video source");
                if (srcEl == null) {
                    srcEl = pd.selectFirst("source");
                }
                if (srcEl == null) {
                    continue;
                }
                String url = srcEl.attr("src");
                if (url == null || url.length() == 0) {
                    continue;
                }
                if (url.startsWith("//")) {
                    url = "https:" + url;
                }
                ExtractorLink link = new ExtractorLink(getName(), "TopFilmes " + idx, url, getMainUrl() + "/", 0, ExtractorLinkType.VIDEO, null, null);
                callback.invoke(link);
                found = true;
            }
        } catch (Exception e) {
        }
        return Boxing.boxBoolean(found);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x00d5 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:21:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:24:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:27:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:30:0x0102  */
    /* JADX WARN: Code duplicated, block: B:32:0x0111  */
    /* JADX WARN: Code duplicated, block: B:36:0x012a  */
    /* JADX WARN: Code duplicated, block: B:43:0x0139 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x0124 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    @Nullable
    public Object search(@NotNull String str, @NotNull Continuation<? super List<? extends SearchResponse>> continuation) {
        C00031 c00031;
        TopFilmes topFilmes;
        String str2;
        TopFilmes topFilmes2;
        TopFilmes topFilmes3;
        Document document;
        Elements elementsSelect;
        ArrayList arrayList;
        Iterator it;
        SearchResponse searchResult;
        Elements elementsSelect2;
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
        Object obj = c00031.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00031.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                String str3 = getMainUrl() + "/busca?q=" + StringsKt.replace(str, " ", "+", false);
                c00031.L$0 = this;
                c00031.L$1 = str3;
                c00031.label = 1;
                if (DelayKt.delay(3000L, c00031) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                topFilmes = this;
                str2 = str3;
                Map mapMapOf = MapsKt.mapOf(TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
                Requests app = MainActivityKt.getApp();
                String strFixUrl = MainAPIKt.fixUrl(topFilmes, str2);
                c00031.L$0 = topFilmes;
                c00031.L$1 = null;
                c00031.label = 2;
                topFilmes2 = topFilmes;
                obj = app.get(strFixUrl, mapMapOf, (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), (Interceptor) null, true, app.getResponseParser(), c00031);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                topFilmes3 = topFilmes2;
                document = ((NiceResponse) obj).getDocument();
                elementsSelect = document.select("div.filmes div.filme");
                if (elementsSelect.isEmpty()) {
                    elementsSelect = document.select("div.filme");
                }
                if (elementsSelect.isEmpty()) {
                    elementsSelect = document.select("div.card");
                }
                if (elementsSelect.isEmpty()) {
                    elementsSelect2 = document.select("a[href*/assistir/]");
                    if (!((Collection) elementsSelect2).isEmpty()) {
                        elementsSelect = elementsSelect2;
                    }
                }
                arrayList = new ArrayList();
                it = ((Iterable) elementsSelect).iterator();
                while (it.hasNext()) {
                    searchResult = topFilmes3.toSearchResult((Element) it.next());
                    if (searchResult != null) {
                        arrayList.add(searchResult);
                    }
                }
                return arrayList;
            case 1:
                String str4 = (String) c00031.L$1;
                TopFilmes topFilmes4 = (TopFilmes) c00031.L$0;
                ResultKt.throwOnFailure(obj);
                str2 = str4;
                topFilmes = topFilmes4;
                Map mapMapOf2 = MapsKt.mapOf(TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
                Requests app2 = MainActivityKt.getApp();
                String strFixUrl2 = MainAPIKt.fixUrl(topFilmes, str2);
                c00031.L$0 = topFilmes;
                c00031.L$1 = null;
                c00031.label = 2;
                topFilmes2 = topFilmes;
                obj = app2.get(strFixUrl2, mapMapOf2, (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app2.getDefaultCacheTime(), app2.getDefaultCacheTimeUnit(), app2.getDefaultTimeOut(), (Interceptor) null, true, app2.getResponseParser(), c00031);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                topFilmes3 = topFilmes2;
                document = ((NiceResponse) obj).getDocument();
                elementsSelect = document.select("div.filmes div.filme");
                if (elementsSelect.isEmpty()) {
                    elementsSelect = document.select("div.filme");
                }
                if (elementsSelect.isEmpty()) {
                    elementsSelect = document.select("div.card");
                }
                if (elementsSelect.isEmpty()) {
                    elementsSelect2 = document.select("a[href*/assistir/]");
                    if (!((Collection) elementsSelect2).isEmpty()) {
                        elementsSelect = elementsSelect2;
                    }
                }
                arrayList = new ArrayList();
                it = ((Iterable) elementsSelect).iterator();
                while (it.hasNext()) {
                    searchResult = topFilmes3.toSearchResult((Element) it.next());
                    if (searchResult != null) {
                        arrayList.add(searchResult);
                    }
                }
                return arrayList;
            case 2:
                topFilmes3 = (TopFilmes) c00031.L$0;
                ResultKt.throwOnFailure(obj);
                document = ((NiceResponse) obj).getDocument();
                elementsSelect = document.select("div.filmes div.filme");
                if (elementsSelect.isEmpty()) {
                    elementsSelect = document.select("div.filme");
                }
                if (elementsSelect.isEmpty()) {
                    elementsSelect = document.select("div.card");
                }
                if (elementsSelect.isEmpty()) {
                    elementsSelect2 = document.select("a[href*/assistir/]");
                    if (!((Collection) elementsSelect2).isEmpty()) {
                        elementsSelect = elementsSelect2;
                    }
                }
                arrayList = new ArrayList();
                it = ((Iterable) elementsSelect).iterator();
                while (it.hasNext()) {
                    searchResult = topFilmes3.toSearchResult((Element) it.next());
                    if (searchResult != null) {
                        arrayList.add(searchResult);
                    }
                }
                return arrayList;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
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
