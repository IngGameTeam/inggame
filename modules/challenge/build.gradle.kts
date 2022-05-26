allprojects {
    dependencies {
        api(project(":modules:module-api"))
        api(project(":modules:utils"))
        api(project(":modules:player"))
        api(project(":modules:alert"))
        api(project(":modules:scheduler"))
        api(project(":modules:minigame:minigame-api"))
        api(project(":modules:mongodb"))
    }
}