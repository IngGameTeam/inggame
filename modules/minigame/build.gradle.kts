allprojects {
    dependencies {
        val prefix = ":modules"
        listOf(
            "$prefix:module-api",
            "$prefix:scheduler",
            "$prefix:utils",
            "$prefix:alert",
            "$prefix:party",
            "$prefix:player",
            "$prefix:world",
            "$prefix:bossbar",
            "$prefix:command",
            "$prefix:downloader",
        ).forEach {
            api(project(it))
            testApi(project(it))
        }
    }
}