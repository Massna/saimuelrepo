package com.OverFlix;

import com.lagradost.cloudstream3.MainActivityKt;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.cloudstream3.utils.ExtractorLinkType;
import com.lagradost.cloudstream3.utils.JsUnpacker;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: OverFlixExtractor.kt */
/* JADX INFO: loaded from: /tmp/cs3/OverFlix/classes.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0011\u0018\u0000 *2\u00020\u0001:\u0001*B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J>\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000b0\tH\u0086@¢\u0006\u0002\u0010\u000eJ\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0006\u001a\u00020\u0007H\u0082@¢\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0002J(\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0007H\u0002J$\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u001a2\u0006\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0007H\u0082@¢\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u001a2\u0006\u0010\u001f\u001a\u00020\u0017H\u0002J0\u0010 \u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001b\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0007H\u0082@¢\u0006\u0002\u0010#J \u0010$\u001a\u0004\u0018\u00010\u00072\u0006\u0010%\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0007H\u0082@¢\u0006\u0002\u0010\u001dJ\u0010\u0010&\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J2\u0010'\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u00072\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000b0\tH\u0082@¢\u0006\u0002\u0010)¨\u0006+"}, d2 = {"Lcom/OverFlix/OverFlixExtractor;", "", "<init>", "()V", "extractLinks", "", "url", "", "subtitleCallback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/SubtitleFile;", "", "callback", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractIframe", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isTvContent", "pageUrl", "iframeUrl", "extractContentInfo", "isTv", "embedDoc", "Lorg/jsoup/nodes/Document;", "embedHtml", "fetchSeriesServerIds", "", "contentInfo", "referer", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchMovieServerIds", "doc", "fetchPlayerUrl", "contentType", "videoId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolveRedirect", "playerUrl", "isInternalPlayer", "extractInternalPlayer", "finalUrl", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "OverFlix_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nOverFlixExtractor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 OverFlixExtractor.kt\ncom/OverFlix/OverFlixExtractor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,217:1\n1863#2,2:218\n1611#2,9:221\n1863#2:230\n1864#2:232\n1620#2:233\n1#3:220\n1#3:231\n*S KotlinDebug\n*F\n+ 1 OverFlixExtractor.kt\ncom/OverFlix/OverFlixExtractor\n*L\n40#1:218,2\n123#1:221,9\n123#1:230\n123#1:232\n123#1:233\n123#1:231\n*E\n"})
public final class OverFlixExtractor {

    @NotNull
    private static final String OPTIONS_API = "https://fshd.link/api/options";

    @NotNull
    private static final String PLAYER_API = "https://fshd.link/api/players";

    @NotNull
    private static final Map<String, String> AJAX_HEADERS = MapsKt.mapOf(new Pair[]{TuplesKt.to("X-Requested-With", "XMLHttpRequest"), TuplesKt.to("Accept", "application/json")});

    /* JADX INFO: renamed from: com.OverFlix.OverFlixExtractor$extractIframe$1, reason: invalid class name */
    /* JADX INFO: compiled from: OverFlixExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlixExtractor", f = "OverFlixExtractor.kt", i = {}, l = {62}, m = "extractIframe", n = {}, s = {})
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OverFlixExtractor.this.extractIframe(null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlixExtractor$extractInternalPlayer$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: OverFlixExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlixExtractor", f = "OverFlixExtractor.kt", i = {0, 0}, l = {170, 206}, m = "extractInternalPlayer", n = {"finalUrl", "callback"}, s = {"L$0", "L$1"})
    static final class C00021 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C00021(Continuation<? super C00021> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OverFlixExtractor.this.extractInternalPlayer(null, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlixExtractor$extractLinks$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: OverFlixExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlixExtractor", f = "OverFlixExtractor.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6}, l = {24, 26, 35, 42, 49, 52, 54}, m = "extractLinks", n = {"this", "url", "subtitleCallback", "callback", "this", "url", "subtitleCallback", "callback", "iframeUrl", "this", "subtitleCallback", "callback", "iframeUrl", "contentType", "contentInfo", "this", "subtitleCallback", "callback", "iframeUrl", "contentType", "contentInfo", "$this$extractLinks_u24lambda_u241_u24lambda_u240", "this", "subtitleCallback", "callback", "iframeUrl", "contentType", "contentInfo", "$this$extractLinks_u24lambda_u241_u24lambda_u240", "playerUrl", "this", "subtitleCallback", "callback", "iframeUrl", "contentType", "contentInfo", "this", "subtitleCallback", "callback", "iframeUrl", "contentType", "contentInfo"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$4", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$7", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$7", "L$8", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5"})
    static final class C00031 extends ContinuationImpl {
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

        C00031(Continuation<? super C00031> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OverFlixExtractor.this.extractLinks(null, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlixExtractor$fetchPlayerUrl$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: OverFlixExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlixExtractor", f = "OverFlixExtractor.kt", i = {}, l = {132}, m = "fetchPlayerUrl", n = {}, s = {})
    static final class C00041 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C00041(Continuation<? super C00041> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OverFlixExtractor.this.fetchPlayerUrl(null, null, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlixExtractor$fetchSeriesServerIds$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: OverFlixExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlixExtractor", f = "OverFlixExtractor.kt", i = {}, l = {103}, m = "fetchSeriesServerIds", n = {}, s = {})
    static final class C00051 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C00051(Continuation<? super C00051> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OverFlixExtractor.this.fetchSeriesServerIds(null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlixExtractor$resolveRedirect$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: OverFlixExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlixExtractor", f = "OverFlixExtractor.kt", i = {}, l = {154}, m = "resolveRedirect", n = {}, s = {})
    static final class C00061 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C00061(Continuation<? super C00061> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OverFlixExtractor.this.resolveRedirect(null, null, (Continuation) this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0387 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:101:0x0388  */
    /* JADX WARN: Code duplicated, block: B:151:0x0317 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:60:0x026b  */
    /* JADX WARN: Code duplicated, block: B:67:0x02ac A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:68:0x02ad  */
    /* JADX WARN: Code duplicated, block: B:71:0x02c3  */
    /* JADX WARN: Code duplicated, block: B:72:0x02cf A[Catch: all -> 0x03c2, TRY_LEAVE, TryCatch #0 {all -> 0x03c2, blocks: (B:69:0x02bf, B:72:0x02cf), top: B:128:0x02bf }] */
    /* JADX WARN: Code duplicated, block: B:74:0x02ec A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:75:0x02ed  */
    /* JADX WARN: Code duplicated, block: B:78:0x02fe  */
    /* JADX WARN: Code duplicated, block: B:79:0x030b A[Catch: all -> 0x03b6, TRY_LEAVE, TryCatch #9 {all -> 0x03b6, blocks: (B:76:0x02fa, B:79:0x030b), top: B:145:0x02fa }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Code duplicated, block: B:85:0x0335 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:86:0x0336  */
    /* JADX WARN: Code duplicated, block: B:95:0x0369  */
    /* JADX WARN: Not initialized variable reg: 7, insn: 0x0084: MOVE (r12 I:??[OBJECT, ARRAY]) = (r7 I:??[OBJECT, ARRAY] A[D('contentInfo' java.lang.String)]), block:B:19:0x0084 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:101:0x0388 -> B:147:0x0393). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:123:0x03f8 -> B:124:0x0403). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:71:0x02c3 -> B:58:0x0265). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:78:0x02fe -> B:58:0x0265). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:88:0x0344 -> B:147:0x0393). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:731)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object extractLinks(@org.jetbrains.annotations.NotNull java.lang.String r30, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.SubtitleFile, kotlin.Unit> r31, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.utils.ExtractorLink, kotlin.Unit> r32, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> r33) {
        /*
            Method dump skipped, instruction units count: 1064
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.OverFlix.OverFlixExtractor.extractLinks(java.lang.String, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object extractIframe(String url, Continuation<? super String> continuation) {
        AnonymousClass1 anonymousClass1;
        String strAttr;
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
                anonymousClass1.label = 1;
                $result = Requests.get$default(app, url, (Map) null, (String) null, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, anonymousClass1, 4094, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Document doc = ((NiceResponse) $result).getDocument();
        Element elementSelectFirst = doc.selectFirst("iframe[src*=/filme/], iframe[src*=/v/], iframe[src*=/serie/]");
        if (elementSelectFirst == null || (strAttr = elementSelectFirst.attr("src")) == null) {
            Element elementSelectFirst2 = doc.selectFirst("div.aspect-video iframe");
            strAttr = elementSelectFirst2 != null ? elementSelectFirst2.attr("src") : null;
        }
        String src = strAttr;
        if (src != null) {
            return StringsKt.startsWith$default(src, "//", false, 2, (Object) null) ? "https:" + src : src;
        }
        return null;
    }

    private final boolean isTvContent(String pageUrl, String iframeUrl) {
        return StringsKt.contains$default(pageUrl, "/episodio/", false, 2, (Object) null) || StringsKt.contains$default(iframeUrl, "/serie/", false, 2, (Object) null);
    }

    private final String extractContentInfo(boolean isTv, String iframeUrl, Document embedDoc, String embedHtml) {
        String strAttr;
        List groupValues;
        List listSplit$default;
        List groupValues2;
        String str = null;
        if (isTv) {
            Element elementSelectFirst = embedDoc.selectFirst(".episodeOption.active");
            if (elementSelectFirst == null || (strAttr = elementSelectFirst.attr("data-contentid")) == null) {
                MatchResult matchResultFind$default = Regex.find$default(new Regex("var CONTENT_INFO = '(\\d+)';"), embedHtml, 0, 2, (Object) null);
                strAttr = (matchResultFind$default == null || (groupValues2 = matchResultFind$default.getGroupValues()) == null) ? null : (String) groupValues2.get(1);
            }
        } else {
            MatchResult matchResultFind$default2 = Regex.find$default(new Regex("var CONTENT_INFO = '(\\d+)';"), embedHtml, 0, 2, (Object) null);
            strAttr = (matchResultFind$default2 == null || (groupValues = matchResultFind$default2.getGroupValues()) == null) ? null : (String) groupValues.get(1);
        }
        if (strAttr != null) {
            return strAttr;
        }
        String str2 = (String) CollectionsKt.lastOrNull(StringsKt.split$default(iframeUrl, new String[]{"/"}, false, 0, 6, (Object) null));
        if (str2 != null && (listSplit$default = StringsKt.split$default(str2, new String[]{"?"}, false, 0, 6, (Object) null)) != null) {
            str = (String) CollectionsKt.firstOrNull(listSplit$default);
        }
        return str == null ? "" : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object fetchSeriesServerIds(String contentInfo, String str, Continuation<? super List<String>> continuation) {
        C00051 c00051;
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
                Requests app = MainActivityKt.getApp();
                Map<String, String> map = AJAX_HEADERS;
                Pair[] pairArr = new Pair[2];
                Integer intOrNull = StringsKt.toIntOrNull(contentInfo);
                pairArr[0] = TuplesKt.to("content_id", Boxing.boxInt(intOrNull != null ? intOrNull.intValue() : 0));
                pairArr[1] = TuplesKt.to("content_type", "2");
                Map mapMapOf = MapsKt.mapOf(pairArr);
                c00051.label = 1;
                $result = Requests.post$default(app, OPTIONS_API, map, str, (Map) null, (Map) null, (Map) null, (List) null, mapMapOf, (RequestBody) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00051, 65400, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        String response = ((NiceResponse) $result).getText();
        return SequencesKt.toList(SequencesKt.map(Regex.findAll$default(new Regex("[\"']ID[\"']\\s*:\\s*(\\d+)"), response, 0, 2, (Object) null), new Function1() { // from class: com.OverFlix.OverFlixExtractor$$ExternalSyntheticLambda0
            public final Object invoke(Object obj) {
                return OverFlixExtractor.fetchSeriesServerIds$lambda$3((MatchResult) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String fetchSeriesServerIds$lambda$3(MatchResult it) {
        return (String) it.getGroupValues().get(1);
    }

    private final List<String> fetchMovieServerIds(Document doc) {
        Iterable $this$mapNotNull$iv = doc.select("div.server-selector, .audio-selector");
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            Element it = (Element) element$iv$iv$iv;
            String p0 = it.attr("data-id");
            if (!(p0.length() > 0)) {
                p0 = null;
            }
            if (p0 != null) {
                destination$iv$iv.add(p0);
            }
        }
        return (List) destination$iv$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object fetchPlayerUrl(String contentInfo, String contentType, String videoId, String str, Continuation<? super String> continuation) {
        C00041 c00041;
        List groupValues;
        String str2;
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
        Object $result = c00041.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00041.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Requests app = MainActivityKt.getApp();
                Map mapMapOf = MapsKt.mapOf(TuplesKt.to("X-Requested-With", "XMLHttpRequest"));
                Pair[] pairArr = new Pair[3];
                Integer intOrNull = StringsKt.toIntOrNull(contentInfo);
                pairArr[0] = TuplesKt.to("content_info", Boxing.boxInt(intOrNull != null ? intOrNull.intValue() : 0));
                pairArr[1] = TuplesKt.to("content_type", contentType);
                Integer intOrNull2 = StringsKt.toIntOrNull(videoId);
                pairArr[2] = TuplesKt.to("video_id", Boxing.boxInt(intOrNull2 != null ? intOrNull2.intValue() : 0));
                Map mapMapOf2 = MapsKt.mapOf(pairArr);
                c00041.label = 1;
                $result = Requests.post$default(app, PLAYER_API, mapMapOf, str, (Map) null, (Map) null, (Map) null, (List) null, mapMapOf2, (RequestBody) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00041, 65400, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        String response = ((NiceResponse) $result).getText();
        MatchResult matchResultFind$default = Regex.find$default(new Regex("[\"']video_url[\"']\\s*:\\s*[\"'](.*?)[\"']"), response, 0, 2, (Object) null);
        if (matchResultFind$default == null || (groupValues = matchResultFind$default.getGroupValues()) == null || (str2 = (String) groupValues.get(1)) == null) {
            return null;
        }
        return StringsKt.replace$default(str2, "\\/", "/", false, 4, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object resolveRedirect(String playerUrl, String referer, Continuation<? super String> continuation) {
        C00061 c00061;
        List groupValues;
        if (continuation instanceof C00061) {
            c00061 = (C00061) continuation;
            if ((c00061.label & Integer.MIN_VALUE) != 0) {
                c00061.label -= Integer.MIN_VALUE;
            } else {
                c00061 = new C00061(continuation);
            }
        } else {
            c00061 = new C00061(continuation);
        }
        Object $result = c00061.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (c00061.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Requests app = MainActivityKt.getApp();
                c00061.label = 1;
                $result = Requests.get$default(app, playerUrl, (Map) null, referer, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00061, 4090, (Object) null);
                if ($result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        String page = ((NiceResponse) $result).getText();
        MatchResult matchResultFind$default = Regex.find$default(new Regex("window\\.location\\.href\\s*=\\s*\"([^\"]+)"), page, 0, 2, (Object) null);
        if (matchResultFind$default == null || (groupValues = matchResultFind$default.getGroupValues()) == null) {
            return null;
        }
        return (String) groupValues.get(1);
    }

    private final boolean isInternalPlayer(String url) {
        return StringsKt.contains$default(url, "112234152.xyz", false, 2, (Object) null) || StringsKt.contains$default(url, "/player/", false, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:28:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:34:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:36:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:39:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:40:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:42:0x0100  */
    /* JADX WARN: Code duplicated, block: B:44:0x0103  */
    /* JADX WARN: Code duplicated, block: B:47:0x010f  */
    /* JADX WARN: Code duplicated, block: B:50:0x011a  */
    /* JADX WARN: Code duplicated, block: B:51:0x011c  */
    /* JADX WARN: Code duplicated, block: B:53:0x011f  */
    /* JADX WARN: Code duplicated, block: B:55:0x0122  */
    /* JADX WARN: Code duplicated, block: B:62:0x0145  */
    /* JADX WARN: Code duplicated, block: B:64:0x0157  */
    /* JADX WARN: Code duplicated, block: B:67:0x0166  */
    /* JADX WARN: Code duplicated, block: B:75:0x0178  */
    /* JADX WARN: Code duplicated, block: B:77:0x017b  */
    /* JADX WARN: Code duplicated, block: B:79:0x0192  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Code duplicated, block: B:80:0x0194  */
    /* JADX WARN: Code duplicated, block: B:83:0x01dd A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:84:0x01de  */
    public final Object extractInternalPlayer(String finalUrl, String referer, Function1<? super ExtractorLink, Unit> function1, Continuation<? super Unit> continuation) {
        C00021 c00021;
        Object obj;
        Function1<? super ExtractorLink, Unit> function2;
        String finalUrl2;
        MatchResult matchResultFind$default;
        int i;
        String baseUrl;
        MatchResult matchResultFind$default2;
        String packedJs;
        boolean z;
        String packedJs2;
        boolean z2;
        MatchResult matchResultFind$default3;
        MatchResult matchResultFind$default4;
        String str;
        List groupValues;
        String m3u8Path;
        String str2;
        String m3u8Path2;
        String str3;
        Function1<? super ExtractorLink, Unit> function3;
        List groupValues2;
        List groupValues3;
        List groupValues4;
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
                c00021.L$0 = finalUrl;
                c00021.L$1 = function1;
                c00021.label = 1;
                $result = Requests.get$default(app, finalUrl, (Map) null, referer, (Map) null, (Map) null, false, 0, (TimeUnit) null, 0L, (Interceptor) null, false, (ResponseParser) null, c00021, 4090, (Object) null);
                obj = coroutine_suspended;
                if ($result == obj) {
                    return obj;
                }
                function2 = function1;
                finalUrl2 = finalUrl;
                String html = ((NiceResponse) $result).getText();
                matchResultFind$default = Regex.find$default(new Regex("var player_base_url\\s*=\\s*\"([^\"]+)"), html, 0, 2, (Object) null);
                if (matchResultFind$default != null || (groupValues4 = matchResultFind$default.getGroupValues()) == null) {
                    baseUrl = null;
                } else {
                    i = 1;
                    baseUrl = (String) groupValues4.get(1);
                }
                if (baseUrl == null) {
                    baseUrl = "";
                }
                matchResultFind$default2 = Regex.find$default(new Regex("eval\\s*\\((function\\(p,a,c,k,e,d\\).+?)\\)\\s*;?\\s*</script>", RegexOption.DOT_MATCHES_ALL), html, 0, 2, (Object) null);
                if (matchResultFind$default2 != null || (groupValues3 = matchResultFind$default2.getGroupValues()) == null) {
                    packedJs = null;
                } else {
                    packedJs = (String) groupValues3.get(i);
                }
                if (packedJs == null) {
                    packedJs = "";
                }
                if (packedJs.length() == 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return Unit.INSTANCE;
                }
                String strUnpack = new JsUnpacker(packedJs).unpack();
                packedJs2 = strUnpack != null ? strUnpack : "";
                if (packedJs2.length() == 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    return Unit.INSTANCE;
                }
                matchResultFind$default3 = Regex.find$default(new Regex("videoUrl\\s*[:=]\\s*[\"'](.*?)[\"']"), packedJs2, 0, 2, (Object) null);
                if (matchResultFind$default3 != null || (groupValues2 = matchResultFind$default3.getGroupValues()) == null || (str = (String) groupValues2.get(i)) == null) {
                    matchResultFind$default4 = Regex.find$default(new Regex("[\"']([^\"']+\\.(?:m3u8|hls|txt)[^\"']*)[\"']"), packedJs2, 0, 2, (Object) null);
                    if (matchResultFind$default4 != null || (groupValues = matchResultFind$default4.getGroupValues()) == null) {
                        str = null;
                    } else {
                        str = (String) groupValues.get(i);
                    }
                }
                m3u8Path = str;
                str2 = m3u8Path;
                if (str2 != null && str2.length() != 0) {
                    i = 0;
                }
                if (i != 0) {
                    return Unit.INSTANCE;
                }
                m3u8Path2 = StringsKt.replace$default(m3u8Path, "\\/", "/", false, 4, (Object) null);
                if (StringsKt.startsWith$default(m3u8Path2, "http", false, 2, (Object) null)) {
                    str3 = m3u8Path2;
                } else {
                    str3 = StringsKt.removeSuffix(baseUrl, "/") + '/' + StringsKt.removePrefix(m3u8Path2, "/");
                }
                ExtractorLinkType extractorLinkType = ExtractorLinkType.M3U8;
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(finalUrl2, null);
                c00021.L$0 = function2;
                c00021.L$1 = null;
                c00021.label = 2;
                $result = ExtractorApiKt.newExtractorLink("Fsplay", "Fsplay", str3, extractorLinkType, anonymousClass2, c00021);
                if ($result == obj) {
                    return obj;
                }
                function3 = function2;
                function3.invoke($result);
                return Unit.INSTANCE;
            case 1:
                Function1<? super ExtractorLink, Unit> function4 = (Function1) c00021.L$1;
                String finalUrl3 = (String) c00021.L$0;
                ResultKt.throwOnFailure($result);
                function2 = function4;
                finalUrl2 = finalUrl3;
                obj = coroutine_suspended;
                String html2 = ((NiceResponse) $result).getText();
                matchResultFind$default = Regex.find$default(new Regex("var player_base_url\\s*=\\s*\"([^\"]+)"), html2, 0, 2, (Object) null);
                i = matchResultFind$default != null ? 1 : 1;
                baseUrl = null;
                if (baseUrl == null) {
                    baseUrl = "";
                }
                matchResultFind$default2 = Regex.find$default(new Regex("eval\\s*\\((function\\(p,a,c,k,e,d\\).+?)\\)\\s*;?\\s*</script>", RegexOption.DOT_MATCHES_ALL), html2, 0, 2, (Object) null);
                if (matchResultFind$default2 != null) {
                    packedJs = null;
                } else {
                    packedJs = null;
                }
                if (packedJs == null) {
                    packedJs = "";
                }
                if (packedJs.length() == 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return Unit.INSTANCE;
                }
                String strUnpack2 = new JsUnpacker(packedJs).unpack();
                packedJs2 = strUnpack2 != null ? strUnpack2 : "";
                if (packedJs2.length() == 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    return Unit.INSTANCE;
                }
                matchResultFind$default3 = Regex.find$default(new Regex("videoUrl\\s*[:=]\\s*[\"'](.*?)[\"']"), packedJs2, 0, 2, (Object) null);
                if (matchResultFind$default3 != null) {
                    matchResultFind$default4 = Regex.find$default(new Regex("[\"']([^\"']+\\.(?:m3u8|hls|txt)[^\"']*)[\"']"), packedJs2, 0, 2, (Object) null);
                    if (matchResultFind$default4 != null) {
                        str = null;
                    } else {
                        str = null;
                    }
                } else {
                    matchResultFind$default4 = Regex.find$default(new Regex("[\"']([^\"']+\\.(?:m3u8|hls|txt)[^\"']*)[\"']"), packedJs2, 0, 2, (Object) null);
                    if (matchResultFind$default4 != null) {
                        str = null;
                    } else {
                        str = null;
                    }
                }
                m3u8Path = str;
                str2 = m3u8Path;
                if (str2 != null) {
                    i = 0;
                }
                if (i != 0) {
                    return Unit.INSTANCE;
                }
                m3u8Path2 = StringsKt.replace$default(m3u8Path, "\\/", "/", false, 4, (Object) null);
                if (StringsKt.startsWith$default(m3u8Path2, "http", false, 2, (Object) null)) {
                    str3 = m3u8Path2;
                } else {
                    str3 = StringsKt.removeSuffix(baseUrl, "/") + '/' + StringsKt.removePrefix(m3u8Path2, "/");
                }
                ExtractorLinkType extractorLinkType2 = ExtractorLinkType.M3U8;
                AnonymousClass2 anonymousClass3 = new AnonymousClass2(finalUrl2, null);
                c00021.L$0 = function2;
                c00021.L$1 = null;
                c00021.label = 2;
                $result = ExtractorApiKt.newExtractorLink("Fsplay", "Fsplay", str3, extractorLinkType2, anonymousClass3, c00021);
                if ($result == obj) {
                    return obj;
                }
                function3 = function2;
                function3.invoke($result);
                return Unit.INSTANCE;
            case 2:
                function3 = (Function1) c00021.L$0;
                ResultKt.throwOnFailure($result);
                function3.invoke($result);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: renamed from: com.OverFlix.OverFlixExtractor$extractInternalPlayer$2, reason: invalid class name */
    /* JADX INFO: compiled from: OverFlixExtractor.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.OverFlix.OverFlixExtractor$extractInternalPlayer$2", f = "OverFlixExtractor.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $finalUrl;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$finalUrl = str;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Continuation<Unit> anonymousClass2 = new AnonymousClass2(this.$finalUrl, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        public final Object invoke(ExtractorLink extractorLink, Continuation<? super Unit> continuation) {
            return create(extractorLink, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    ExtractorLink $this$newExtractorLink = (ExtractorLink) this.L$0;
                    $this$newExtractorLink.setReferer(this.$finalUrl);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }
}
