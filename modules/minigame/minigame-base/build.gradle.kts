dependencies {
    listOf(
        project(":modules:minigame:minigame-api"),
        project(":modules:minigame:minigame-ui"),
    ).forEach {
        api(it)
        testApi(it)
    }
}
