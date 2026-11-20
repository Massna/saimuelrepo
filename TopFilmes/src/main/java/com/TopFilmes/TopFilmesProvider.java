package com.TopFilmes;

import com.lagradost.cloudstream3.plugins.BasePlugin;
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin;
import kotlin.Metadata;

/* JADX INFO: compiled from: TopFilmesProvider.kt */
/* JADX INFO: loaded from: /tmp/cs3/TopFilmes/classes.dex */
@CloudstreamPlugin
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lcom/TopFilmes/TopFilmesProvider;", "Lcom/lagradost/cloudstream3/plugins/BasePlugin;", "<init>", "()V", "load", "", "TopFilmes_debug"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class TopFilmesProvider extends BasePlugin {
    public void load() {
        registerMainAPI(new TopFilmes());
    }
}
