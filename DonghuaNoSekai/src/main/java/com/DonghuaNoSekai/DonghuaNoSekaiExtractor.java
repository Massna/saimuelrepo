package com.DonghuaNoSekai;

import com.lagradost.cloudstream3.MainActivityKt;
import com.lagradost.cloudstream3.network.WebViewResolver;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.cloudstream3.utils.M3u8Helper;
import com.lagradost.nicehttp.NiceResponse;
import com.lagradost.nicehttp.Requests;
import com.lagradost.nicehttp.ResponseParser;
import java.util.Iterator;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DonghuaNoSekaiExtractor.kt */
/* JADX INFO: loaded from: /tmp/cs3/DonghuaNoSekai/classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J:\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bH\u0086@¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Lcom/DonghuaNoSekai/DonghuaNoSekaiExtractor;", "", "<init>", "()V", "extractVideoLinks", "", "url", "", "mainUrl", "name", "callback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "DonghuaNoSekai_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDonghuaNoSekaiExtractor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DonghuaNoSekaiExtractor.kt\ncom/DonghuaNoSekai/DonghuaNoSekaiExtractor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,62:1\n1863#2,2:63\n*S KotlinDebug\n*F\n+ 1 DonghuaNoSekaiExtractor.kt\ncom/DonghuaNoSekai/DonghuaNoSekaiExtractor\n*L\n51#1:63,2\n*E\n"})
public final class DonghuaNoSekaiExtractor {

    @NotNull
    public static final DonghuaNoSekaiExtractor INSTANCE = new DonghuaNoSekaiExtractor();

    /* JADX INFO: renamed from: com.DonghuaNoSekai.DonghuaNoSekaiExtractor$extractVideoLinks$1, reason: invalid class name */
    /* JADX INFO: compiled from: DonghuaNoSekaiExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.DonghuaNoSekai.DonghuaNoSekaiExtractor", f = "DonghuaNoSekaiExtractor.kt", i = {0, 0, 0, 0, 1}, l = {25, 46}, m = "extractVideoLinks", n = {"url", "mainUrl", "name", "callback", "callback"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0"})
    final class AnonymousClass1 extends ContinuationImpl {
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
            return DonghuaNoSekaiExtractor.this.extractVideoLinks(null, null, null, null, (Continuation) this);
        }
    }

    private DonghuaNoSekaiExtractor() {
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:30:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:32:0x0102 A[Catch: Exception -> 0x01e9, TRY_LEAVE, TryCatch #1 {Exception -> 0x01e9, blocks: (B:27:0x00ee, B:32:0x0102, B:23:0x00dd), top: B:74:0x00dd }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0112 A[Catch: Exception -> 0x01e3, TryCatch #2 {Exception -> 0x01e3, blocks: (B:54:0x01cb, B:55:0x01d2, B:57:0x01d8, B:34:0x010c, B:36:0x0112, B:38:0x011f, B:40:0x012f, B:42:0x0135, B:50:0x0146), top: B:76:0x010c }] */
    /* JADX WARN: Code duplicated, block: B:38:0x011f A[Catch: Exception -> 0x01e3, TryCatch #2 {Exception -> 0x01e3, blocks: (B:54:0x01cb, B:55:0x01d2, B:57:0x01d8, B:34:0x010c, B:36:0x0112, B:38:0x011f, B:40:0x012f, B:42:0x0135, B:50:0x0146), top: B:76:0x010c }] */
    /* JADX WARN: Code duplicated, block: B:49:0x0145  */
    /* JADX WARN: Code duplicated, block: B:52:0x01c9 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:53:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:57:0x01d8 A[Catch: Exception -> 0x01e3, LOOP:0: B:55:0x01d2->B:57:0x01d8, LOOP_END, TRY_LEAVE, TryCatch #2 {Exception -> 0x01e3, blocks: (B:54:0x01cb, B:55:0x01d2, B:57:0x01d8, B:34:0x010c, B:36:0x0112, B:38:0x011f, B:40:0x012f, B:42:0x0135, B:50:0x0146), top: B:76:0x010c }] */
    /* JADX WARN: Code duplicated, block: B:62:0x01e5  */
    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    @Nullable
    public final Object extractVideoLinks(@NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull Function1<? super ExtractorLink, Unit> function1, @NotNull Continuation<? super Boolean> continuation) {
        AnonymousClass1 anonymousClass1;
        boolean z = false;
        Object obj;
        String str4;
        String str5;
        Function1<? super ExtractorLink, Unit> function2;
        String str6;
        String url;
        boolean z2;
        boolean z3 = false;
        char c;
        Object objGenerateM3u8$default;
        Function1<? super ExtractorLink, Unit> function3;
        MatchResult matchResultFind$default;
        List groupValues;
        Iterator it;
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
        Object obj2 = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        try {
            switch (anonymousClass1.label) {
                case 0:
                    ResultKt.throwOnFailure(obj2);
                    try {
                        Interceptor webViewResolver = new com.lagradost.cloudstream3.network.WebViewResolver(new Regex("m3u8"), CollectionsKt.listOf(new Regex("m3u8")), (String) null, false, (String) null, (Function1) null, 15000L);
                        Requests app = MainActivityKt.getApp();
                        Interceptor interceptor = webViewResolver;
                        anonymousClass1.L$0 = str;
                        anonymousClass1.L$1 = str2;
                        anonymousClass1.L$2 = str3;
                        anonymousClass1.L$3 = function1;
                        anonymousClass1.label = 1;
                        try {
                            obj2 = app.get(str, java.util.Collections.emptyMap(), (String) null, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), true, app.getDefaultCacheTime(), app.getDefaultCacheTimeUnit(), app.getDefaultTimeOut(), interceptor, true, app.getResponseParser(), anonymousClass1);
                            obj = coroutine_suspended;
                            if (obj2 == obj) {
                                return obj;
                            }
                            str4 = str3;
                            str5 = str;
                            function2 = function1;
                            str6 = str2;
                            url = ((NiceResponse) obj2).getUrl();
                            if (url.length() > 0) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (z2) {
                                try {
                                    if (StringsKt.contains(url, ".m3u8", false)) {
                                        if (StringsKt.contains(url, "player-nativov2.php?v=", false)) {
                                            matchResultFind$default = new Regex("v=([^&]+)").find(url, 0);
                                            if (matchResultFind$default == null && (groupValues = matchResultFind$default.getGroupValues()) != null) {
                                                c = 1;
                                                String str7 = (String) groupValues.get(1);
                                                if (str7 != null) {
                                                    url = str7;
                                                }
                                            }
                                        }
                                        Pair[] pairArr = new Pair[8];
                                        pairArr[0] = TuplesKt.to("Accept", "*/*");
                                        pairArr[1] = TuplesKt.to("Connection", "keep-alive");
                                        pairArr[2] = TuplesKt.to("Sec-Fetch-Dest", "empty");
                                        pairArr[3] = TuplesKt.to("Sec-Fetch-Mode", "cors");
                                        pairArr[4] = TuplesKt.to("Sec-Fetch-Site", "cross-site");
                                        pairArr[5] = TuplesKt.to("Referer", str5);
                                        pairArr[6] = TuplesKt.to("Origin", str6);
                                        pairArr[7] = TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                                        Map mapMapOf = MapsKt.mapOf(pairArr);
                                        M3u8Helper.Companion companion = M3u8Helper.Companion;
                                        anonymousClass1.L$0 = function2;
                                        anonymousClass1.L$1 = null;
                                        anonymousClass1.L$2 = null;
                                        anonymousClass1.L$3 = null;
                                        anonymousClass1.label = 2;
                                        objGenerateM3u8$default = companion.generateM3u8(str4, url, str6, (Integer) null, mapMapOf, (String) null, anonymousClass1);
                                        if (objGenerateM3u8$default == obj) {
                                            return obj;
                                        }
                                        function3 = function2;
                                        it = ((Iterable) objGenerateM3u8$default).iterator();
                                        while (it.hasNext()) {
                                            ((kotlin.jvm.functions.Function1) function3).invoke(it.next());
                                            break;
                                        }
                                        z3 = true;
                                    }
                                    z = z3;
                                } catch (Exception e) {
                                    z = false;
                                }
                                return Boxing.boxBoolean(z);
                            }
                            z3 = false;
                            z = z3;
                            return Boxing.boxBoolean(z);
                        } catch (Exception e2) {
                            z = false;
                        }
                    } catch (Exception e3) {
                        z = false;
                    }
                    break;
                case 1:
                    Function1<? super ExtractorLink, Unit> function4 = (Function1) anonymousClass1.L$3;
                    String str8 = (String) anonymousClass1.L$2;
                    String str9 = (String) anonymousClass1.L$1;
                    String str10 = (String) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    function2 = function4;
                    obj = coroutine_suspended;
                    str4 = str8;
                    str5 = str10;
                    str6 = str9;
                    url = ((NiceResponse) obj2).getUrl();
                    if (url.length() > 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        if (StringsKt.contains(url, ".m3u8", false)) {
                            if (StringsKt.contains(url, "player-nativov2.php?v=", false)) {
                                matchResultFind$default = new Regex("v=([^&]+)").find(url, 0);
                                c = 1;
                            }
                            Pair[] pairArr2 = new Pair[8];
                            pairArr2[0] = TuplesKt.to("Accept", "*/*");
                            pairArr2[1] = TuplesKt.to("Connection", "keep-alive");
                            pairArr2[2] = TuplesKt.to("Sec-Fetch-Dest", "empty");
                            pairArr2[3] = TuplesKt.to("Sec-Fetch-Mode", "cors");
                            pairArr2[4] = TuplesKt.to("Sec-Fetch-Site", "cross-site");
                            pairArr2[5] = TuplesKt.to("Referer", str5);
                            pairArr2[6] = TuplesKt.to("Origin", str6);
                            pairArr2[7] = TuplesKt.to("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                            Map mapMapOf2 = MapsKt.mapOf(pairArr2);
                            M3u8Helper.Companion companion2 = M3u8Helper.Companion;
                            anonymousClass1.L$0 = function2;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.L$2 = null;
                            anonymousClass1.L$3 = null;
                            anonymousClass1.label = 2;
                            objGenerateM3u8$default = companion2.generateM3u8(str4, url, str6, (Integer) null, mapMapOf2, (String) null, anonymousClass1);
                            if (objGenerateM3u8$default == obj) {
                                return obj;
                            }
                            function3 = function2;
                            it = ((Iterable) objGenerateM3u8$default).iterator();
                            while (it.hasNext()) {
                                ((kotlin.jvm.functions.Function1) function3).invoke(it.next());
                                break;
                            }
                            z3 = true;
                        }
                        z = z3;
                        return Boxing.boxBoolean(z);
                    }
                    z3 = false;
                    z = z3;
                    return Boxing.boxBoolean(z);
                case 2:
                    function3 = (Function1) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    objGenerateM3u8$default = obj2;
                    it = ((Iterable) objGenerateM3u8$default).iterator();
                    while (it.hasNext()) {
                        ((kotlin.jvm.functions.Function1) function3).invoke(it.next());
                        break;
                    }
                    z3 = true;
                    z = z3;
                    return Boxing.boxBoolean(z);
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Exception e4) {
        }
        return null;
    }
}
