package io.github.inggameteam.item.impl

import io.github.inggameteam.item.api.ItemComponentGetter

enum class ItemType {
    VOLUME, TOGGLE, LIMITED, SELECT;
}
fun ItemComponentGetter.toItemType(name: String) = ItemType.valueOf(itemComp.string(name, plugin.defaultLanguage))
