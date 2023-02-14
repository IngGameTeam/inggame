dependencies {
    compileOnly("org.eclipse.jgit:org.eclipse.jgit:6.4.0.202211300538-r")
    compileOnly("commons-io:commons-io:2.6")
}

projectDependencies(
    utils,
    command,
    plugman,
)
