dependencies {
    listOf(
        ":utils",
        ":mongodb",
    ).forEach { compileOnly(project(it)); testCompileOnly(project(it)) }
}