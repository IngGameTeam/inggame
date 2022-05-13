dependencies {
    listOf(
        project(":modules:minigame:minigame-api"),
    ).forEach {
        compileOnly(it)
        testCompileOnly(it)
    }
}
