package com.DonghuaNoSekai;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DonghuaNoSekai.kt */
/* JADX INFO: loaded from: /tmp/cs3/DonghuaNoSekai/classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t¢\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\tHÆ\u0003JA\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001e\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001f"}, d2 = {"Lcom/DonghuaNoSekai/AjaxResponse;", "", "page", "", "total_results", "mensagem", "", "total_page", "results", "", "<init>", "(IILjava/lang/String;ILjava/util/List;)V", "getPage", "()I", "getTotal_results", "getMensagem", "()Ljava/lang/String;", "getTotal_page", "getResults", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "DonghuaNoSekai_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AjaxResponse {

    @NotNull
    private final String mensagem;
    private final int page;

    @NotNull
    private final List<String> results;
    private final int total_page;
    private final int total_results;

    public AjaxResponse(int i, int i2, @NotNull String str, int i3, @NotNull List<String> list) {
        this.page = i;
        this.total_results = i2;
        this.mensagem = str;
        this.total_page = i3;
        this.results = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AjaxResponse copy$default(AjaxResponse ajaxResponse, int i, int i2, String str, int i3, List list, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = ajaxResponse.page;
        }
        if ((i4 & 2) != 0) {
            i2 = ajaxResponse.total_results;
        }
        int i5 = i2;
        if ((i4 & 4) != 0) {
            str = ajaxResponse.mensagem;
        }
        String str2 = str;
        if ((i4 & 8) != 0) {
            i3 = ajaxResponse.total_page;
        }
        int i6 = i3;
        if ((i4 & 16) != 0) {
            list = ajaxResponse.results;
        }
        return ajaxResponse.copy(i, i5, str2, i6, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getPage() {
        return this.page;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getTotal_results() {
        return this.total_results;
    }

    @NotNull
    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getMensagem() {
        return this.mensagem;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getTotal_page() {
        return this.total_page;
    }

    @NotNull
    public final List<String> component5() {
        return this.results;
    }

    @NotNull
    public final AjaxResponse copy(int page, int total_results, @NotNull String mensagem, int total_page, @NotNull List<String> results) {
        return new AjaxResponse(page, total_results, mensagem, total_page, results);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AjaxResponse)) {
            return false;
        }
        AjaxResponse ajaxResponse = (AjaxResponse) other;
        return this.page == ajaxResponse.page && this.total_results == ajaxResponse.total_results && Intrinsics.areEqual(this.mensagem, ajaxResponse.mensagem) && this.total_page == ajaxResponse.total_page && Intrinsics.areEqual(this.results, ajaxResponse.results);
    }

    @NotNull
    public final String getMensagem() {
        return this.mensagem;
    }

    public final int getPage() {
        return this.page;
    }

    @NotNull
    public final List<String> getResults() {
        return this.results;
    }

    public final int getTotal_page() {
        return this.total_page;
    }

    public final int getTotal_results() {
        return this.total_results;
    }

    public int hashCode() {
        return (((((((this.page * 31) + this.total_results) * 31) + this.mensagem.hashCode()) * 31) + this.total_page) * 31) + this.results.hashCode();
    }

    @NotNull
    public String toString() {
        return "AjaxResponse(page=" + this.page + ", total_results=" + this.total_results + ", mensagem=" + this.mensagem + ", total_page=" + this.total_page + ", results=" + this.results + ')';
    }
}
