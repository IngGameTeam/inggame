assert(JavaVersion.current() == JavaVersion.VERSION_17) { "Java 17 is required" }

rootProject.name = "inggame"

val core = "${rootProject.name}-core"
val abilities = "${rootProject.name}-modules"
val plugin = "${rootProject.name}-plugins"


listOf(core, abilities, plugin).forEach { sub ->
    include(sub)
    file(sub).listFiles()?.filter { it.isDirectory && it.name.startsWith("${rootProject.name}-") }?.forEach { file ->
        include(":${sub}:${file.name}")
    }
}

include("inggame-publish")
