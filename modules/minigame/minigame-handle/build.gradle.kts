dependencies {
    listOf(
        project(":modules:minigame:minigame-api"),
        project(":modules:minigame:minigame-impl"),
        project(":modules:mongodb"),
    ).forEach {
        api(it)
        testApi(it)
    }
}
