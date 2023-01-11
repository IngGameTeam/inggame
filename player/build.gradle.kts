dependencies {
    listOf(
        ":utils",
        ":component",
    ).forEach { compileOnly(project(it)) }
}