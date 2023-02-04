package io.github.inggameteam.inggame.minigame.base.voiddeath

import io.github.inggameteam.inggame.component.delegate.SimpleWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper

class VoidDeathImp(wrapper: Wrapper) : VoidDeath, SimpleWrapper(wrapper) {

    override val voidDeath: Int by nonNull

}