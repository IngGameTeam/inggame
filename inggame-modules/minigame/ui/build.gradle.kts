dependencies {
    val api = project(":inggame-modules:minigame:api")
    compileOnly(api)
    testCompileOnly(api)
}
