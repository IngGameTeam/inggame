dependencies {
    listOf(
        project(":inggame-modules:minigame:api"),
    ).forEach {
        compileOnly(it)
        testCompileOnly(it)
    }
}
