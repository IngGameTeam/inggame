package io.github.inggameteam.inggame.utils

import org.burningwave.core.assembler.ComponentContainer
import org.burningwave.core.assembler.ComponentSupplier
import org.burningwave.core.classes.ClassCriteria
import org.burningwave.core.classes.SearchConfig
import java.util.function.Function

object Finder {
    fun find(filter: Function<Class<*>, Boolean>): Collection<Class<*>> {
        val componentSupplier: ComponentSupplier = ComponentContainer.getInstance()
        val pathHelper = componentSupplier.pathHelper
        val classHunter = componentSupplier.classHunter
        println(pathHelper.mainClassPaths)
        println(pathHelper.mainClassPaths)
        println(pathHelper.mainClassPaths)
        println(pathHelper.mainClassPaths)
        println(pathHelper.mainClassPaths)
        val searchConfig = SearchConfig.forPaths(
            pathHelper.mainClassPaths
        ).by(
            ClassCriteria.create().allThoseThatMatch { t: Class<*> -> filter.apply(t) }
        )
        classHunter.findBy(searchConfig).use { searchResult -> return searchResult.classes }
    }
}