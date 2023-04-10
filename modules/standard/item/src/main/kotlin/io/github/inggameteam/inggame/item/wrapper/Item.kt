package io.github.inggameteam.inggame.item.wrapper

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface Item : Wrapper

class ItemImp(wrapper: Wrapper) : Item, SimpleWrapper(wrapper)
