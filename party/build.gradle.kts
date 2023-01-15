dependencies {
    listOf(
        ":utils",
        ":component",
        ":mongodb",
        ":player",
    ).forEach { compileOnly(project(it)); testCompileOnly(project(it)) }
}