package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.componentservice.*
import io.github.inggameteam.inggame.component.helper.AddToSaveRegistry
import io.github.inggameteam.inggame.component.loader.ComponentServiceType.*
import io.github.inggameteam.inggame.mongodb.createFileRepo
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

class ComponentServiceDSL private constructor(
    val name: String,
    val registry: ArrayList<ComponentServiceDSL> = ArrayList(),
    var root: String? = null,
    var key: String? = null,
    var type: ComponentServiceType = RESOURCE,
    var isSavable: Boolean = false,
    var loadedSemaphore: Boolean = false
) {


    internal val parents: ArrayList<String> = if (name == "default") arrayListOf() else arrayListOf("default")

    private fun addParent(name: String) {
        if (parents.contains("default")) parents.clear()
        parents.add(name)
    }

    companion object {
        fun newRoot() = ComponentServiceDSL("null", type = MULTI)
            .apply { cs("root", type = MULTI) csc {} }
            .apply { cs("default") }
    }

    fun findComponentServiceDSL(name: String): ComponentServiceDSL {
        return registry.firstOrNull { it.name == name }
            ?: ComponentServiceDSL(name, registry).apply(registry::add)
    }

    infix fun ComponentServiceDSL.isSavable(isSavable: Boolean): ComponentServiceDSL = this.apply {
        this.isSavable = isSavable
    }

    infix fun String.isSavable(isSavable: Boolean): String = this.apply {
        findComponentServiceDSL(this).isSavable = isSavable
    }

    fun cs(name: String,
              root: String? = null,
              key: String? = null,
              isSavable: Boolean = false,
           type: ComponentServiceType? = null
    ) = findComponentServiceDSL(name).apply {
            type?.also { this.type = type }
            this.root = root
            this.key = key
            this.isSavable = isSavable
    }.also { this.addParent(name) }

    infix fun ComponentServiceDSL.root(root: String): ComponentServiceDSL = this.apply {
        this.root = root
    }

    infix fun String.root(root: String): ComponentServiceDSL = findComponentServiceDSL(this).apply {
        this.root = root
    }

    infix fun ComponentServiceDSL.key(key: String): ComponentServiceDSL = this.apply {
        this.key = key
    }

    infix fun String.key(key: String) = findComponentServiceDSL(this).apply {
        this.key = key
    }

    infix fun String.csc(block: ComponentServiceDSL.() -> Unit): ComponentServiceDSL {
        val inst = findComponentServiceDSL(this)
        this@ComponentServiceDSL.addParent(this)
        inst.apply(block)
        return inst
    }

    infix fun ComponentServiceDSL.csc(block: ComponentServiceDSL.() -> Unit): ComponentServiceDSL {
        this.apply(block)
        return this
    }

    infix fun String.cs(parent: String): ComponentServiceDSL {
        val base = findComponentServiceDSL(this)
        this@ComponentServiceDSL.addParent(this)
        val oParent = findComponentServiceDSL(parent)
        base.addParent(parent)
        return oParent
    }

    infix fun ComponentServiceDSL.cs(parent: String): ComponentServiceDSL {
        this.addParent(parent)
        return findComponentServiceDSL(parent)
    }

    override fun toString(): String {
        return "Component{name=$name, parents$parents${if (key === null) "" else ", key=$key"}${if (root === null) "" else ", root=$root"}}"
    }

}

fun ComponentServiceDSL.createComponentModule(): Module = this.let { cs ->
    module {
        if (cs.loadedSemaphore) return@module
        includes(createFileRepo(cs.name, cs.name))
        cs.loadedSemaphore = true
        single(named(cs.name)) {
            (if (cs.parents.isEmpty()) EmptyComponentServiceImp(cs.name)
            else if (cs.type == MULTI || cs.key !== null && cs.type !== LAYER) {
                val root by lazy { get<ComponentService>(named(cs.root ?: "root is not exists")) }
                MultiParentsComponentService(
                    cs.name,
                    { root },
                    cs.parents.map { get(named(it)) },
                    cs.key
                )
            } else if (cs.type == MASKED) LayerMaskComponentService(
                get(named(cs.name)),
                get(),
                get(named(cs.parents.first())),
                cs.name
            )
            else if (cs.type == LAYER) LayeredComponentServiceImp(
                get(named(cs.name)),
                get(),
                get(named(cs.parents.first())),
                cs.name
            )
            else if (cs.type == LINKED) LinkedComponentService(
                get(named(cs.parents.first()))
            )
            else ResourceComponentServiceImp(
                get(named(cs.name)),
                get(),
                get(named(cs.parents.first())),
                cs.name
            ))
                .apply { if (cs.isSavable) AddToSaveRegistry(this, get()) }
        } bind ComponentService::class
    }
}