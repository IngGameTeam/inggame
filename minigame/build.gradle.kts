dependencies {
    listOf(
        ":utils",
        ":component",
        ":mongodb",
        ":player",
        ":world",
    ).forEach { compileOnly(project(it)); testCompileOnly(project(it)) }
}