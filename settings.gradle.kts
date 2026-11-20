pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://repo1.maven.org/maven2/") }
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://repo1.maven.org/maven2/")
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

rootProject.name = "CloudstreamPlugins"

File(rootDir, ".").listFiles()?.filter {
    it.isDirectory && File(it, "build.gradle.kts").exists()
}?.forEach {
    include(":${'$'}{it.name}")
}
