dependencies {
    listOf(
        project(":modules:minigame:minigame-api"),
        project(":modules:minigame:minigame-impl"),
        project(":modules:mongodb"),
        project(":modules:challenge"),
    ).forEach {
        api(it)
        testApi(it)
    }
}
