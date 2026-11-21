package com.AnimesCloud;

import android.content.Context;
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin;
import com.lagradost.cloudstream3.plugins.Plugin;

@CloudstreamPlugin
public final class AnimesCloudProvider extends Plugin {
    private boolean loaded;

    @Override
    public void load() {
        if (loaded) return;
        loaded = true;
        registerMainAPI(new AnimesCloud());
    }

    @Override
    public void load(Context context) {
        load();
    }
}
