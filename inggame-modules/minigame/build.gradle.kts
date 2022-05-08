allprojects {
    dependencies {
        val prefix = ":inggame-modules"
        listOf(
            "$prefix:inggame-api",
            "$prefix:scheduler",
            "$prefix:utils",
            "$prefix:alert",
            "$prefix:party",
            "$prefix:player",
            "$prefix:world",
        ).forEach {
            implementation(project(it))
            testImplementation(project(it))
        }
    }
}