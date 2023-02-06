package io.github.inggameteam.inggame.component

class ComponentServiceDSL(
    val name: String,
    val parents: ArrayList<String>,
    val registry: ArrayList<ComponentServiceDSL>,
    var root: String? = null,
    var isLayer: Boolean = false,
    var key: String? = null,
    var isMulti: Boolean = false,
    var isSavable: Boolean = false,
    var isMask: Boolean = false,
) {

    fun findComponentServiceDSL(name: String): ComponentServiceDSL {
        return registry.firstOrNull { it.name == name }
            ?: ComponentServiceDSL(name, ArrayList(), registry).apply(registry::add)
    }

    infix fun ComponentServiceDSL.isSavable(isSavable: Boolean): ComponentServiceDSL = this.apply {
        this.isSavable = isSavable
    }

    infix fun String.isSavable(isSavable: Boolean): String = this.apply {
        findComponentServiceDSL(this).isSavable = isSavable
    }

    infix fun ComponentServiceDSL.isMask(isMask: Boolean): ComponentServiceDSL = this.apply {
        this.isMask = isMask
    }

    infix fun String.isMask(isMask: Boolean): String = this.apply {
        findComponentServiceDSL(this).isMask = isMask
    }

    fun cs(name: String,
              isLayer: Boolean = false,
              isMask: Boolean = false,
              root: String? = null,
              key: String? = null,
              isSavable: Boolean = false,
              isMulti: Boolean = false
    ) = apply {
        this.parents.add(name)
        this.isMask = isMask
        this.root = root
        this.key = key
        this.isSavable = isSavable
        this.isMulti = isMulti
        this.isLayer = isLayer
    }

    infix fun ComponentServiceDSL.isLayer(isLayer: Boolean): ComponentServiceDSL = this.apply {
        this.isLayer = isLayer
    }

    infix fun String.isLayer(isLayer: Boolean): String = this.apply {
        findComponentServiceDSL(this).isLayer = isLayer
    }

    infix fun ComponentServiceDSL.root(root: String): ComponentServiceDSL = this.apply {
        this.root = root
    }

    infix fun String.root(root: String): ComponentServiceDSL  = findComponentServiceDSL(this).apply {
        this.root = root
    }

    infix fun ComponentServiceDSL.key(key: String): ComponentServiceDSL = this.apply {
        this.key = key
    }

    infix fun String.key(key: String) = findComponentServiceDSL(this).apply {
        this.key = key
    }

    infix fun ComponentServiceDSL.isMulti(isMulti: Boolean): ComponentServiceDSL = this.apply {
        this.isMulti = isMulti
    }

    infix fun String.isMulti(isMulti: Boolean): String = this.apply {
        findComponentServiceDSL(this).isMulti = isMulti
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
        return "Component{name=$name, parents$parents${if (key === null) "" else ", key=$key"}${if (root === null) "" else ", root=$root"}}"
    }

}
