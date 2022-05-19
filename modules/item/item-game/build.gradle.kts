allprojects {
    dependencies {
        api(project(":modules:item:item-api"))
        api(project(":modules:item:item-impl"))
        api(project(":modules:minigame:minigame-api"))
        api(project(":modules:minigame:minigame-base"))
    }
}