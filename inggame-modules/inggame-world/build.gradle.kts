dependencies {
    implementation(project(":inggame-modules:inggame-scheduler"))
    implementation(project(":inggame-modules:inggame-utils"))

    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.1.2")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.1.2") { isTransitive = false }
}
