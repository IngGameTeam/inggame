//package io.github.inggameteam.inggame.component.view.selector
//
//import io.github.inggameteam.inggame.component.NameSpace
//import io.github.inggameteam.inggame.component.PropertyRegistry
//import io.github.inggameteam.inggame.component.view.model.NameSpaceView
//import org.bukkit.event.inventory.InventoryClickEvent
//import org.bukkit.inventory.ItemStack
//
//class ElementForAddSelector(
//    nameSpaceView: NameSpaceView,
//    override val parentSelector: Selector<*>? = null
//) : NameSpaceView by nameSpaceView, Selector<String> {
//    override val elements: Collection<String> get() = app.get<PropertyRegistry>().getAllProp().filter {  }
//
//
//    override fun select(t: NameSpace, event: InventoryClickEvent) {
//        TODO("Not yet implemented")
//    }
//
//    override fun transform(t: NameSpace): ItemStack {
//        TODO("Not yet implemented")
//    }
//}