package com.NetCine

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class NetCineProvider : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(NetCine())
    }
}
