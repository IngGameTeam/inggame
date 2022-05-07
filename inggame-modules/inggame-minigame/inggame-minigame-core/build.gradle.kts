dependencies {
    val api = project(":inggame-modules:inggame-minigame:inggame-minigame-api")
    implementation(api)
    testImplementation(api)
}