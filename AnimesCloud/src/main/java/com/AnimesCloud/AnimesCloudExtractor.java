package com.AnimesCloud;

import com.lagradost.cloudstream3.network.CloudflareKiller;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AnimesCloudExtractor.kt */
/* JADX INFO: loaded from: /tmp/cs3/AnimesCloud/classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J:\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eH\u0086@¢\u0006\u0002\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/AnimesCloud/AnimesCloudExtractor;", "", "<init>", "()V", "cfKiller", "Lcom/lagradost/cloudstream3/network/CloudflareKiller;", "USER_AGENT", "", "extractVideoLinks", "", "url", "mainUrl", "name", "callback", "Lkotlin/Function1;", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "AnimesCloud_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAnimesCloudExtractor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnimesCloudExtractor.kt\ncom/AnimesCloud/AnimesCloudExtractor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,116:1\n1863#2,2:117\n1863#2,2:119\n*S KotlinDebug\n*F\n+ 1 AnimesCloudExtractor.kt\ncom/AnimesCloud/AnimesCloudExtractor\n*L\n108#1:117,2\n109#1:119,2\n*E\n"})
public final class AnimesCloudExtractor {

    @NotNull
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36";

    @NotNull
    public static final AnimesCloudExtractor INSTANCE = new AnimesCloudExtractor();

    @NotNull
    private static final CloudflareKiller cfKiller = new CloudflareKiller();

    /* JADX INFO: renamed from: com.AnimesCloud.AnimesCloudExtractor$extractVideoLinks$1, reason: invalid class name */
    /* JADX INFO: compiled from: AnimesCloudExtractor.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.AnimesCloud.AnimesCloudExtractor", f = "AnimesCloudExtractor.kt", i = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 3}, l = {36, 58, 108, 109}, m = "extractVideoLinks", n = {"url", "mainUrl", "callback", "url", "mainUrl", "callback", "hasValidLinks", "priorityLinks", "lateLinks", "title", "hasValidLinks", "lateLinks", "hasValidLinks"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$7", "L$0", "L$1", "L$0"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnimesCloudExtractor.this.extractVideoLinks(null, null, null, null, (Continuation) this);
        }
    }

    private AnimesCloudExtractor() {
    }

    /* JADX WARN: Code duplicated, block: B:115:0x0384 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:180:0x01ef A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x016a A[Catch: Exception -> 0x042b, TRY_LEAVE, TryCatch #2 {Exception -> 0x042b, blocks: (B:41:0x0164, B:43:0x016a), top: B:156:0x0164 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x01ad A[Catch: Exception -> 0x0425, TryCatch #9 {Exception -> 0x0425, blocks: (B:47:0x01a0, B:49:0x01ad, B:52:0x01c5, B:57:0x01d3, B:62:0x01e1, B:67:0x01ef, B:120:0x03ac), top: B:169:0x01a0 }] */
    /* JADX WARN: Code duplicated, block: B:54:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:55:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:57:0x01d3 A[Catch: Exception -> 0x0425, TryCatch #9 {Exception -> 0x0425, blocks: (B:47:0x01a0, B:49:0x01ad, B:52:0x01c5, B:57:0x01d3, B:62:0x01e1, B:67:0x01ef, B:120:0x03ac), top: B:169:0x01a0 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:60:0x01de  */
    /* JADX WARN: Code duplicated, block: B:62:0x01e1 A[Catch: Exception -> 0x0425, TryCatch #9 {Exception -> 0x0425, blocks: (B:47:0x01a0, B:49:0x01ad, B:52:0x01c5, B:57:0x01d3, B:62:0x01e1, B:67:0x01ef, B:120:0x03ac), top: B:169:0x01a0 }] */
    /* JADX WARN: Code duplicated, block: B:64:0x01ea  */
    /* JADX WARN: Code duplicated, block: B:65:0x01ec  */
    /* JADX WARN: Code duplicated, block: B:73:0x0272 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:74:0x0273  */
    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    /* JADX WARN: Code duplicated, block: B:82:0x02a3 A[Catch: Exception -> 0x0322, TryCatch #13 {Exception -> 0x0322, blocks: (B:80:0x029d, B:82:0x02a3, B:84:0x02ed), top: B:177:0x029d }] */
    /* JADX WARN: Code duplicated, block: B:84:0x02ed A[Catch: Exception -> 0x0322, TRY_LEAVE, TryCatch #13 {Exception -> 0x0322, blocks: (B:80:0x029d, B:82:0x02a3, B:84:0x02ed), top: B:177:0x029d }] */
    /* JADX WARN: Code duplicated, block: B:88:0x02fb  */
    /* JADX WARN: Code duplicated, block: B:89:0x02fc A[Catch: Exception -> 0x0305, TryCatch #10 {Exception -> 0x0305, blocks: (B:86:0x02f5, B:91:0x0301, B:89:0x02fc), top: B:171:0x02f5 }] */
    /* JADX WARN: Code duplicated, block: B:90:0x0300  */
    /* JADX WARN: Code duplicated, block: B:95:0x0307  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:113:0x0362 -> B:156:0x0164). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:127:0x03e5 -> B:128:0x03e6). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:135:0x041c -> B:136:0x041d). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:74:0x0273 -> B:165:0x0281). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at java.base/java.util.BitSet.set(BitSet.java:454)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:745)
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object extractVideoLinks(@org.jetbrains.annotations.NotNull java.lang.String r46, @org.jetbrains.annotations.NotNull java.lang.String r47, @org.jetbrains.annotations.NotNull java.lang.String r48, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.utils.ExtractorLink, kotlin.Unit> r49, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> r50) {
        /*
            Method dump skipped, instruction units count: 1104
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.AnimesCloud.AnimesCloudExtractor.extractVideoLinks(java.lang.String, java.lang.String, java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
