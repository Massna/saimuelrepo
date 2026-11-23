package com.Doramas

import android.content.Context
import com.Doramas.extractor.EmbedPlayUpnOne
import com.Doramas.extractor.EmbedPlayUpnsPro
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class DoramasProvider : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(Doramas())
        registerExtractorAPI(EmbedPlayUpnsPro())
        registerExtractorAPI(EmbedPlayUpnOne())
    }
}
