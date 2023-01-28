dependencies {
    listOf(
        ":utils",
        ":mongodb",
        ":guiapi",
        ":command",
        ":player",
        ":component"
    ).forEach { compileOnly(project(it)); testCompileOnly(project(it)) }
}