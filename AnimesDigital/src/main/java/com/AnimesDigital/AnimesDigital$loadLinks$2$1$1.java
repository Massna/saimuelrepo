package com.AnimesDigital;

import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.cloudstream3.utils.Qualities;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: AnimesDigital.kt */
/* JADX INFO: loaded from: /tmp/cs3/AnimesDigital/classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;"}, k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "com.AnimesDigital.AnimesDigital$loadLinks$2$1$1", f = "AnimesDigital.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class AnimesDigital$loadLinks$2$1$1 extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $realUrl;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AnimesDigital$loadLinks$2$1$1(String str, Continuation<?> continuation) {
        super(2, (Continuation) continuation);
        this.$realUrl = str;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        AnimesDigital$loadLinks$2$1$1 animesDigital$loadLinks$2$1$1 = new AnimesDigital$loadLinks$2$1$1(this.$realUrl, continuation);
        animesDigital$loadLinks$2$1$1.L$0 = obj;
        return (Continuation<Unit>) (Object) animesDigital$loadLinks$2$1$1;
    }

    public final Object invoke(ExtractorLink extractorLink, Continuation<? super Unit> continuation) {
        return ((AnimesDigital$loadLinks$2$1$1) (Object) create(extractorLink, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                ExtractorLink $this$newExtractorLink = (ExtractorLink) this.L$0;
                $this$newExtractorLink.setReferer(this.$realUrl);
                $this$newExtractorLink.setQuality(Qualities.Unknown.getValue());
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
