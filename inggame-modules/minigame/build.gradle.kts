allprojects {
    dependencies {
        val prefix = ":inggame-modules"
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
        ).forEach {
            implementation(project(it))
            testImplementation(project(it))
        }
    }
}