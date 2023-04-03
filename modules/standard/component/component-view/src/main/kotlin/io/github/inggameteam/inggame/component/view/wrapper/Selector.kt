package io.github.inggameteam.inggame.component.view.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface Selector : Wrapper {

    val VIEW_CANNOT_EDIT: String
    val VIEW_CANCEL_EDIT: String
    val VIEW_CLICK_ITEM_TO_EDIT: String
    val VIEW_ADD_BUTTON: String
    val VIEW_REMOVE_BUTTON: String
    val VIEW_PARENT_BUTTON: String
    val VIEW_RENAME_BUTTON: String
    val VIEW_TITLE: String
    val VIEW_SEARCH_BUTTON: String
    val VIEW_CLEAR_SEARCH_BUTTON: String
    val VIEW_PREV_PAGE: String
    val VIEW_NEXT_PAGE: String
    val VIEW_BACK_PAGE: String

}