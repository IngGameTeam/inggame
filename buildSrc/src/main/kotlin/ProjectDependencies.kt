import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.provideDelegate
import kotlin.reflect.KProperty

val Project.command                                   by ProjectDependencies
val Project.component                                 by ProjectDependencies
val Project.component_view                            by ProjectDependencies
val Project.guiapi                                    by ProjectDependencies
val Project.minigame                                  by ProjectDependencies
val Project.mongodb                                   by ProjectDependencies
val Project.party                                     by ProjectDependencies
val Project.player                                    by ProjectDependencies
val Project.plugin                                    by ProjectDependencies
val Project.plugman                                   by ProjectDependencies
val Project.updateman                                 by ProjectDependencies
val Project.utils                                     by ProjectDependencies
val Project.world                                     by ProjectDependencies
val Project.item                                      by ProjectDependencies

fun Project.projectDependencies(vararg project: ProjectDependency, includeJar: Boolean = false) {
    dependencies {
        for (p in project) {
            add("compileOnly", p)
            add("testCompileOnly", p)
            if (includeJar) {
                add("api", p)
                add("testApi", p)
            }
        }
    }
}

object ProjectDependencies {
    operator fun getValue(thisRef: Project, property: KProperty<*>) = run {
        thisRef.dependencies.project(":${property.name.replace("_", "-")}")
    }
}