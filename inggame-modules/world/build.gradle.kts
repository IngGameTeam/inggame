dependencies {
    api(project(":inggame-modules:module-api"))
    api(project(":inggame-modules:scheduler"))
    api(project(":inggame-modules:utils"))

    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.1.2")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.1.2") { isTransitive = false }
}
