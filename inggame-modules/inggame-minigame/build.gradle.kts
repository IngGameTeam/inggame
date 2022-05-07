subprojects {
    dependencies {
        api(project(":inggame-modules:inggame-api"))
        api(project(":inggame-modules:inggame-scheduler"))
        api(project(":inggame-modules:inggame-utils"))
        api(project(":inggame-modules:inggame-alert"))
        api(project(":inggame-modules:inggame-party"))
        api(project(":inggame-modules:inggame-player"))
        api(project(":inggame-modules:inggame-world"))
    }
}