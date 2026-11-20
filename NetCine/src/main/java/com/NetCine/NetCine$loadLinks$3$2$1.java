package com.NetCine;

import com.lagradost.cloudstream3.utils.ExtractorLink;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

/* JADX INFO: compiled from: NetCine.kt */
/* JADX INFO: loaded from: /tmp/cs3/NetCine/classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;"}, k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "com.NetCine.NetCine$loadLinks$3$2$1", f = "NetCine.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class NetCine$loadLinks$3$2$1 extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
    final /* synthetic */ Ref.ObjectRef<String> $ref;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NetCine this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NetCine$loadLinks$3$2$1(NetCine netCine, Ref.ObjectRef<String> objectRef, Continuation<? super NetCine$loadLinks$3$2$1> continuation) {
        super(2, continuation);
        this.this$0 = netCine;
        this.$ref = objectRef;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Continuation<Unit> netCine$loadLinks$3$2$1 = new NetCine$loadLinks$3$2$1(this.this$0, this.$ref, continuation);
        netCine$loadLinks$3$2$1.L$0 = obj;
        return netCine$loadLinks$3$2$1;
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
                $this$newExtractorLink.setReferer(this.this$0.getMainUrl() + '/');
                $this$newExtractorLink.setHeaders(MapsKt.mapOf(new Pair[]{TuplesKt.to("User-Agent", NetCine.USER_AGENT), TuplesKt.to("Referer", this.$ref.element)}));
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
