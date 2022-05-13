dependencies {
    api(project(":modules:module-api"))
    api(project(":modules:scheduler"))
    api(project(":modules:utils"))

    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.1.2")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.1.2") { isTransitive = false }
}
