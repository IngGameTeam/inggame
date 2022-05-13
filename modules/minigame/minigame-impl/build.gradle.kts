dependencies {
    listOf(
        project(":modules:minigame:minigame-api"),
        project(":modules:minigame:minigame-handle"),
        project(":modules:minigame:minigame-ui"),
    ).forEach {
        compileOnly(it)
        testCompileOnly(it)
    }
}
