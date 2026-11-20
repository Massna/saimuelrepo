package com.PobreFlix;

import com.lagradost.cloudstream3.MainActivityKt;
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: PobreFlixExtractor.kt */
/* JADX INFO: loaded from: /tmp/cs3/PobreFlix/classes.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J>\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00052\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u00112\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00130\u0011H\u0086@¢\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00182\u0006\u0010\u000f\u001a\u00020\u0005H\u0002J\u0018\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0005H\u0002J\u0010\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u0005H\u0002JF\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u00052\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u00112\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00130\u0011H\u0082@¢\u0006\u0002\u0010 R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/PobreFlix/PobreFlixExtractor;", "", "<init>", "()V", "BASE_PLAYER", "", "TAG", "videoRegex", "Lkotlin/text/Regex;", "serverRegex", "serverPriority", "", "", "getLinks", "", "data", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseData", "Lkotlin/Pair;", "buildPlayerUrl", "type", "url", "extractPriority", "onClick", "processItem", "referer", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "PobreFlix_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPobreFlixExtractor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PobreFlixExtractor.kt\ncom/PobreFlix/PobreFlixExtractor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,93:1\n1053#2:94\n1863#2,2:95\n*S KotlinDebug\n*F\n+ 1 PobreFlixExtractor.kt\ncom/PobreFlix/PobreFlixExtractor\n*L\n40#1:94\n41#1:95,2\n*E\n"})
public final class PobreFlixExtractor {

    @NotNull
    private static final String BASE_PLAYER = "https://www.pobreflixtv.club/e/getplay.php";

    @NotNull
    private static final String TAG = "PobreFlix";

    @NotNull
    public static final PobreFlixExtractor INSTANCE = new PobreFlixExtractor();

    @NotNull
    private static final Regex videoRegex = new Regex("C_Video\\('(\\d+)','(.*?)'\\)");

    @NotNull
    private static final Regex serverRegex = new Regex("'([^']*)'\\)");

    @NotNull
    private static final Map<String, Integer> serverPriority = MapsKt.mapOf(new Pair[]{TuplesKt.to("streamtape", 1), TuplesKt.to("filemoon", 2), TuplesKt.to("doodstream", 3), TuplesKt.to("mixdrop", 4)});

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlixExtractor$getLinks$1, reason: invalid class name */
    /* JADX INFO: compiled from: PobreFlixExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.PobreFlix.PobreFlixExtractor", f = "PobreFlixExtractor.kt", i = {0, 0, 0, 0, 1, 1, 1, 1}, l = {33, 43}, m = "getLinks", n = {"subtitleCallback", "callback", "url", "$this$getLinks_u24lambda_u242", "subtitleCallback", "callback", "url", "$this$getLinks_u24lambda_u242"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlixExtractor.this.getLinks(null, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlixExtractor$processItem$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PobreFlixExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.PobreFlix.PobreFlixExtractor", f = "PobreFlixExtractor.kt", i = {0, 0, 0, 0}, l = {80, 89}, m = "processItem", n = {"referer", "subtitleCallback", "callback", "playUrl"}, s = {"L$0", "L$1", "L$2", "L$3"})
    static final class C00041 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C00041(Continuation<? super C00041> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlixExtractor.this.processItem(null, null, null, null, (Continuation) this);
        }
    }

    private PobreFlixExtractor() {
    }

    /* JADX WARN: Code duplicated, block: B:31:0x012b A[Catch: all -> 0x016f, TryCatch #3 {all -> 0x016f, blocks: (B:29:0x0125, B:31:0x012b, B:36:0x0160), top: B:55:0x0125 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x0157 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:34:0x0158  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0158 -> B:35:0x015e). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:731)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getLinks(@org.jetbrains.annotations.NotNull java.lang.String r30, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.SubtitleFile, kotlin.Unit> r31, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.utils.ExtractorLink, kotlin.Unit> r32, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> r33) {
        /*
            Method dump skipped, instruction units count: 418
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.PobreFlix.PobreFlixExtractor.getLinks(java.lang.String, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Pair<String, String> parseData(String data) {
        List parts = StringsKt.split$default(data, new String[]{"|"}, false, 2, 2, (Object) null);
        String str = (String) CollectionsKt.getOrNull(parts, 0);
        if (str == null) {
            str = "";
        }
        String str2 = (String) CollectionsKt.getOrNull(parts, 1);
        if (str2 == null) {
            str2 = data;
        }
        return new Pair<>(str, str2);
    }

    private final String buildPlayerUrl(String type, String url) {
        StringBuilder sbAppend;
        String str;
        if (!Intrinsics.areEqual(type, "movie")) {
            return url;
        }
        if (StringsKt.contains$default(url, "?", false, 2, (Object) null)) {
            sbAppend = new StringBuilder().append(url);
            str = "&area=online";
        } else {
            sbAppend = new StringBuilder().append(url);
            str = "/?area=online";
        }
        return sbAppend.append(str).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int extractPriority(String onClick) {
        List groupValues;
        String str;
        String lowerCase = null;
        MatchResult matchResultFind$default = Regex.find$default(serverRegex, onClick, 0, 2, (Object) null);
        if (matchResultFind$default != null && (groupValues = matchResultFind$default.getGroupValues()) != null && (str = (String) groupValues.get(1)) != null) {
            lowerCase = str.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        }
        if (lowerCase == null) {
            lowerCase = "";
        }
        String server = lowerCase;
        Integer num = serverPriority.get(server);
        if (num != null) {
            return num.intValue();
        }
        return Integer.MAX_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:33:0x0134  */
    /* JADX WARN: Code duplicated, block: B:34:0x0135  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object processItem(String onClick, String referer, Function1<? super SubtitleFile, Unit> function1, Function1<? super ExtractorLink, Unit> function2, Continuation<? super Unit> continuation) {
        C00041 c00041;
        boolean z;
        C00041 c00042;
        int i;
        Function1<? super SubtitleFile, Unit> function3;
        Function1<? super ExtractorLink, Unit> function4;
        String referer2;
        String playUrl;
        String finalUrl;
        if (continuation instanceof C00041) {
            c00041 = (C00041) continuation;
            if ((c00041.label & Integer.MIN_VALUE) != 0) {
                c00041.label -= Integer.MIN_VALUE;
            } else {
                c00041 = new C00041(continuation);
            }
        } else {
            c00041 = new C00041(continuation);
        }
        C00041 c00043 = c00041;
        Object $result = c00043.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        try {
            switch (c00043.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    MatchResult match = Regex.find$default(videoRegex, onClick, 0, 2, (Object) null);
                    if (match == null) {
                        return Unit.INSTANCE;
                    }
                    String id = (String) match.getGroupValues().get(1);
                    String server = ((String) match.getGroupValues().get(2)).toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(server, "toLowerCase(...)");
                    String playUrl2 = "https://www.pobreflixtv.club/e/getplay.php?id=" + id + "&sv=" + server;
                    try {
                        Result.Companion companion = Result.Companion;
                        PobreFlixExtractor pobreFlixExtractor = this;
                        Requests app = MainActivityKt.getApp();
                        Map mapMapOf = MapsKt.mapOf(TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"));
                        c00043.L$0 = referer;
                        c00043.L$1 = function1;
                        c00043.L$2 = function2;
                        c00043.L$3 = playUrl2;
                        c00043.label = 1;
                        z = true;
                        c00042 = c00043;
                        try {
                            $result = Requests.get$default(app, playUrl2, mapMapOf, referer, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00042, 4088, (Object) null);
                            if ($result == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            i = 0;
                            function3 = function1;
                            function4 = function2;
                            referer2 = referer;
                            playUrl = playUrl2;
                            NiceResponse response = (NiceResponse) $result;
                            finalUrl = response.getUrl();
                            if (finalUrl.length() > 0) {
                                z = false;
                            }
                            if (z || Intrinsics.areEqual(finalUrl, playUrl) || StringsKt.contains$default(finalUrl, "pobreflixtv", false, 2, (Object) null)) {
                                Result.constructor-impl(Unit.INSTANCE);
                                return Unit.INSTANCE;
                            }
                            C00041 c00044 = c00042;
                            try {
                                c00044.L$0 = null;
                                c00044.L$1 = null;
                                c00044.L$2 = null;
                                c00044.L$3 = null;
                                c00044.label = 2;
                                if (ExtractorApiKt.loadExtractor(finalUrl, referer2, function3, function4, c00044) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                                Result.constructor-impl(Unit.INSTANCE);
                                return Unit.INSTANCE;
                            } catch (Throwable th) {
                                th = th;
                                Result.Companion companion2 = Result.Companion;
                                Result.constructor-impl(ResultKt.createFailure(th));
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            Result.Companion companion3 = Result.Companion;
                            Result.constructor-impl(ResultKt.createFailure(th));
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        Result.Companion companion4 = Result.Companion;
                        Result.constructor-impl(ResultKt.createFailure(th));
                    }
                    break;
                case 1:
                    i = 0;
                    playUrl = (String) c00043.L$3;
                    function4 = (Function1) c00043.L$2;
                    function3 = (Function1) c00043.L$1;
                    referer2 = (String) c00043.L$0;
                    ResultKt.throwOnFailure($result);
                    c00042 = c00043;
                    z = true;
                    NiceResponse response2 = (NiceResponse) $result;
                    finalUrl = response2.getUrl();
                    if (finalUrl.length() > 0) {
                        z = false;
                    }
                    if (z) {
                        break;
                    }
                    Result.constructor-impl(Unit.INSTANCE);
                    return Unit.INSTANCE;
                case 2:
                    ResultKt.throwOnFailure($result);
                    Result.constructor-impl(Unit.INSTANCE);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }
}
