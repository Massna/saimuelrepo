package com.PobreFlix;

import android.content.Context;
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin;
import com.lagradost.cloudstream3.plugins.Plugin;

@CloudstreamPlugin
public final class PobreFlixProvider extends Plugin {
    private boolean loaded;

    @Override
    public void load() {
        if (loaded) return;
        loaded = true;
        registerMainAPI(new PobreFlix());
    }

    @Override
    public void load(Context context) {
        load();
    }
}
