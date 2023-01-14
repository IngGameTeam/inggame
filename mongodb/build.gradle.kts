dependencies {
    listOf(
        ":utils",
    ).forEach { compileOnly(project(it)); testCompileOnly(project(it)) }
}