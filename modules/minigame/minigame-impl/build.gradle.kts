dependencies {
    listOf(
        project(":modules:minigame:minigame-api"),
        project(":modules:minigame:minigame-ui"),
        project(":modules:minigame:minigame-base"),
    ).forEach {
        api(it)
        testApi(it)
    }
}
