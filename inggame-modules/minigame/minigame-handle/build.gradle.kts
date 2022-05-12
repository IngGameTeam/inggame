dependencies {
    listOf(
        project(":inggame-modules:minigame:minigame-api"),
    ).forEach {
        compileOnly(it)
        testCompileOnly(it)
    }
}
