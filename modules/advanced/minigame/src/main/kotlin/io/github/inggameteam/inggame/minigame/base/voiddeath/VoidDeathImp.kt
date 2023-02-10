package io.github.inggameteam.inggame.minigame.base.voiddeath

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper

class VoidDeathImp(wrapper: Wrapper) : VoidDeath, SimpleWrapper(wrapper) {

    override val voidDeath: Int by nonNull

}