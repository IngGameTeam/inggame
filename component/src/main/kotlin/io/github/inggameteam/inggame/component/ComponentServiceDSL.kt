package io.github.inggameteam.inggame.component

class ComponentServiceDSL(
    val name: String,
    val parents: ArrayList<String>,
    val registry: ArrayList<ComponentServiceDSL>,
    val isLayer: Boolean = false,
    var key: String? = null
) {

    private fun findComponentServiceDSL(name: String): ComponentServiceDSL {
        return registry.firstOrNull { it.name == name }
            ?: ComponentServiceDSL(name, ArrayList(), registry).apply(registry::add)
    }

    infix fun String.key(key: String): String = this.apply {
        findComponentServiceDSL(this).key = key
    }

    infix fun String.csc(block: ComponentServiceDSL.() -> Unit): ComponentServiceDSL {
        val inst = findComponentServiceDSL(this)
        this@ComponentServiceDSL.parents.add(this)
        inst.apply(block)
        return inst
    }

    infix fun ComponentServiceDSL.csc(block: ComponentServiceDSL.() -> Unit): ComponentServiceDSL {
        this.apply(block)
        return this
    }

    infix fun String.cs(parent: String): ComponentServiceDSL {
        val base = findComponentServiceDSL(this)
        this@ComponentServiceDSL.parents.add(this)
        val oParent = findComponentServiceDSL(parent)
        base.parents.add(parent)
        return oParent
    }

    infix fun ComponentServiceDSL.cs(parent: String): ComponentServiceDSL {
        this.parents.add(parent)
        return findComponentServiceDSL(parent)
    }

    override fun toString(): String {
        return "Component{name=$name, parents$parents${if (key === null) "" else ", key=$key"}}"
    }

}
