dependencies {
    listOf(
        project(":modules:minigame:minigame-api"),
        project(":modules:mongodb"),
    ).forEach {
        api(it)
        testApi(it)
    }
}
