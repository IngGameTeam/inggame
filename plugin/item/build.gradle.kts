projectDependencies(
    component_common,
    utils
)

repositories {
    mavenCentral()
}

dependencies {
    "io.github.bruce0203:nbt-api:4".let {
        compileOnly(it)
        testCompileOnly(it)
    }
}