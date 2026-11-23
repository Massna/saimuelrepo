plugins {
    id("com.android.library")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.PobreFlix"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        buildConfigField("String", "TMDB_API_KEY", "\"${System.getenv("TMDB_API_KEY") ?: "dummy_api_key"}\"")
        buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"${System.getenv("TMDB_ACCESS_TOKEN") ?: "dummy_access_token"}\"")
    }
}

cloudstream {
    version = 9
    description = "PobreFlix - FHD e HD."
    language = "pt-br"
    authors = listOf("saimuelbr")
    status = 1
    tvTypes = listOf("Movie", "TvSeries", "Anime", "AnimeMovie")
    iconUrl = "https://www.pobreflixtv.club/uploads/set_resources_14/a5bd102ac3b4e1362ac25aed38707f87_favicon.ico"

}
