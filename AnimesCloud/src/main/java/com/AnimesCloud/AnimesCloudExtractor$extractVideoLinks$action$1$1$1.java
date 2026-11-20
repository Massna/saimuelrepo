package com.AnimesCloud;

import com.lagradost.cloudstream3.utils.ExtractorLink;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: AnimesCloudExtractor.kt */
/* JADX INFO: loaded from: /tmp/cs3/AnimesCloud/classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lcom/lagradost/cloudstream3/utils/ExtractorLink;"}, k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "com.AnimesCloud.AnimesCloudExtractor$extractVideoLinks$action$1$1$1", f = "AnimesCloudExtractor.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class AnimesCloudExtractor$extractVideoLinks$action$1$1$1 extends SuspendLambda implements Function2<ExtractorLink, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $mainUrl;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AnimesCloudExtractor$extractVideoLinks$action$1$1$1(String str, Continuation<? super AnimesCloudExtractor$extractVideoLinks$action$1$1$1> continuation) {
        super(2, continuation);
        this.$mainUrl = str;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Continuation<Unit> animesCloudExtractor$extractVideoLinks$action$1$1$1 = new AnimesCloudExtractor$extractVideoLinks$action$1$1$1(this.$mainUrl, continuation);
        animesCloudExtractor$extractVideoLinks$action$1$1$1.L$0 = obj;
        return animesCloudExtractor$extractVideoLinks$action$1$1$1;
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
                $this$newExtractorLink.setReferer(this.$mainUrl);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
