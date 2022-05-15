dependencies {
    listOf(
        project(":modules:minigame:minigame-api"),
    ).forEach {
        api(it)
        testApi(it)
    }
}
