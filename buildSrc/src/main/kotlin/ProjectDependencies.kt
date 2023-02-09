import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.provideDelegate
import kotlin.reflect.KProperty


private val projectDependencies = ProjectDependencies()

val Project.command                                   by projectDependencies
val Project.component                                 by projectDependencies
val Project.component_view                            by projectDependencies
val Project.guiapi                                    by projectDependencies
val Project.minigame                                  by projectDependencies
val Project.mongodb                                   by projectDependencies
val Project.party                                     by projectDependencies
val Project.player                                    by projectDependencies
val Project.plugin                                    by projectDependencies
val Project.plugman                                   by projectDependencies
val Project.updateman                                 by projectDependencies
val Project.utils                                     by projectDependencies
val Project.world                                     by projectDependencies
val Project.item                                      by projectDependencies

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

class ProjectDependencies {
    operator fun getValue(thisRef: Project, property: KProperty<*>) = run {
        thisRef.dependencies.project(":${property.name.replace("_", "-")}")
    }

}