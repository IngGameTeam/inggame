package io.github.inggameteam.inggame.component.view.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper

class SelectorImp(wrapper: Wrapper) : Wrapper by wrapper, Selector {
    override val VIEW_CANNOT_EDIT: String by nonNull
    override val VIEW_CANCEL_EDIT: String by nonNull
    override val VIEW_CLICK_ITEM_TO_EDIT: String by nonNull
    override val VIEW_ADD_BUTTON: String by nonNull
    override val VIEW_REMOVE_BUTTON: String by nonNull
    override val VIEW_PARENT_BUTTON: String by nonNull
    override val VIEW_RENAME_BUTTON: String by nonNull
    override val VIEW_TITLE: String by nonNull
    override val VIEW_PREV_PAGE: String by nonNull
    override val VIEW_NEXT_PAGE: String by nonNull
    override val VIEW_BACK_PAGE: String by nonNull

}