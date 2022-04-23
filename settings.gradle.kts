rootProject.name = "inggame"

val core = "${rootProject.name}-core"
val abilities = "${rootProject.name}-modules"

include(core, abilities)

file(abilities).listFiles()?.filter { it.isDirectory && it.name.startsWith("${rootProject.name}-") }?.forEach { file ->
    include(":${rootProject.name}-modules:${file.name}")
}
