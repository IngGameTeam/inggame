package io.github.inggameteam.inggame.component.view.wrapper

import io.github.inggameteam.inggame.component.delegate.Wrapper

class SelectorImp(wrapper: Wrapper) : Wrapper by wrapper, Selector {
    override val VIEW_CANNOT_EDIT: String by nonNull
    override val VIEW_CANCEL_EDIT: String by nonNull
    override val VIEW_CLICK_ITEM_TO_EDIT: String by nonNull
    override val VIEW_ADD_BUTTON: String by nonNull
    override val VIEW_REMOVE_BUTTON: String by nonNull
    override val VIEW_TITLE: String by nonNull
    override val VIEW_PREV_PAGE: String by nonNull
    override val VIEW_NEXT_PAGE: String by nonNull
    override val VIEW_BACK_PAGE: String by nonNull


}