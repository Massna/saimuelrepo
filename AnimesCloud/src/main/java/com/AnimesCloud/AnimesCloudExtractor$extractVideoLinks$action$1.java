package com.AnimesCloud;

import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import com.lagradost.cloudstream3.utils.ExtractorLinkType;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: AnimesCloudExtractor.kt */
/* JADX INFO: loaded from: /tmp/cs3/AnimesCloud/classes.dex */
@Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\n"}, d2 = {"<anonymous>", ""}, k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "com.AnimesCloud.AnimesCloudExtractor$extractVideoLinks$action$1", f = "AnimesCloudExtractor.kt", i = {}, l = {74, 86, 91}, m = "invokeSuspend", n = {}, s = {})
final class AnimesCloudExtractor$extractVideoLinks$action$1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    final /* synthetic */ Function1<? super ExtractorLink, Unit> $callback;
    final /* synthetic */ String $embedUrl;
    final /* synthetic */ Ref.BooleanRef $hasValidLinks;
    final /* synthetic */ String $mainUrl;
    final /* synthetic */ String $title;
    Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AnimesCloudExtractor$extractVideoLinks$action$1(String str, String str2, String str3, Function1<? super ExtractorLink, Unit> function1, Ref.BooleanRef booleanRef, Continuation<?> continuation) {
        super(1, (Continuation) continuation);
        this.$embedUrl = str;
        this.$title = str2;
        this.$mainUrl = str3;
        this.$callback = function1;
        this.$hasValidLinks = booleanRef;
    }

    public final Continuation<Unit> create(Continuation<?> continuation) {
        return (Continuation<Unit>) (Object) new AnimesCloudExtractor$extractVideoLinks$action$1(this.$embedUrl, this.$title, this.$mainUrl, this.$callback, this.$hasValidLinks, continuation);
    }

    public final Object invoke(Continuation<? super Unit> continuation) {
        return ((AnimesCloudExtractor$extractVideoLinks$action$1) (Object) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object $result2;
        Object $result3;
        List groupValues;
        String it;
        Object $result4;
        Function1<? super ExtractorLink, Unit> function1;
        Ref.BooleanRef booleanRef;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                if (StringsKt.contains(this.$embedUrl, "source=", false) && (StringsKt.contains(this.$embedUrl, ".mp4", false) || StringsKt.contains(this.$embedUrl, ".m3u8", false))) {
                    MatchResult matchResultFind$default = new Regex("source=([^&]+)").find(this.$embedUrl, 0);
                    if (matchResultFind$default != null && (groupValues = matchResultFind$default.getGroupValues()) != null && (it = (String) groupValues.get(1)) != null) {
                        Function1<? super ExtractorLink, Unit> function2 = this.$callback;
                        String str = this.$title;
                        Ref.BooleanRef booleanRef2 = this.$hasValidLinks;
                        String str2 = this.$mainUrl;
                        String strDecode = URLDecoder.decode(it, java.nio.charset.StandardCharsets.UTF_8);
                        ExtractorLinkType extractorLinkType = ExtractorLinkType.VIDEO;
                        AnimesCloudExtractor$extractVideoLinks$action$1$1$1 animesCloudExtractor$extractVideoLinks$action$1$1$1 = new AnimesCloudExtractor$extractVideoLinks$action$1$1$1(str2, null);
                        this.L$0 = booleanRef2;
                        this.L$1 = function2;
                        this.label = 1;
                        $result4 = ExtractorApiKt.newExtractorLink("AnimesCloud " + str, "AnimesCloud " + str, strDecode, extractorLinkType, animesCloudExtractor$extractVideoLinks$action$1$1$1, this);
                        if ($result4 == coroutine_suspended) {
                            return (Continuation<Unit>) (Object) coroutine_suspended;
                        }
                        function1 = function2;
                        booleanRef = booleanRef2;
                        function1.invoke((ExtractorLink) $result4);
                        booleanRef.element = true;
                    }
                } else if (StringsKt.contains(this.$embedUrl, "animeshd.cloud", false)) {
                    AnimesHD extractor = new AnimesHD();
                    extractor.setName("AnimesCloud " + this.$title);
                    this.label = 2;
                    if (extractor.getUrl(this.$embedUrl, this.$mainUrl, new Function1() { // from class: com.AnimesCloud.AnimesCloudExtractor$extractVideoLinks$action$1$$ExternalSyntheticLambda0
                        public final Object invoke(Object obj) {
                            return AnimesCloudExtractor$extractVideoLinks$action$1.invokeSuspend$lambda$1((SubtitleFile) obj);
                        }
                    }, this.$callback, (Continuation) this) == coroutine_suspended) {
                        return (Continuation<Unit>) (Object) coroutine_suspended;
                    }
                    $result3 = $result;
                    this.$hasValidLinks.element = true;
                } else if (StringsKt.contains((CharSequence) this.$embedUrl, "animes.strp2p.com", false)) {
                    AnimesSTRP extractor2 = new AnimesSTRP();
                    extractor2.setName("AnimesCloud " + this.$title);
                    this.label = 3;
                    if (extractor2.getUrl(this.$embedUrl, this.$mainUrl, new Function1() { // from class: com.AnimesCloud.AnimesCloudExtractor$extractVideoLinks$action$1$$ExternalSyntheticLambda1
                        public final Object invoke(Object obj) {
                            return AnimesCloudExtractor$extractVideoLinks$action$1.invokeSuspend$lambda$2((SubtitleFile) obj);
                        }
                    }, this.$callback, (Continuation) this) == coroutine_suspended) {
                        return (Continuation<Unit>) (Object) coroutine_suspended;
                    }
                    $result2 = $result;
                    this.$hasValidLinks.element = true;
                }
                return Unit.INSTANCE;
            case 1:
                $result4 = $result;
                function1 = (Function1) this.L$1;
                booleanRef = (Ref.BooleanRef) this.L$0;
                ResultKt.throwOnFailure($result4);
                function1.invoke((ExtractorLink) $result4);
                booleanRef.element = true;
                return Unit.INSTANCE;
            case 2:
                $result3 = $result;
                ResultKt.throwOnFailure($result3);
                this.$hasValidLinks.element = true;
                return Unit.INSTANCE;
            case 3:
                $result2 = $result;
                ResultKt.throwOnFailure($result2);
                this.$hasValidLinks.element = true;
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$1(SubtitleFile it) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$2(SubtitleFile it) {
        return Unit.INSTANCE;
    }
}
