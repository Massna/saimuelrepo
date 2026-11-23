package com.OverFlix

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class OverFlixProvider : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(OverFlix())
    }
}
