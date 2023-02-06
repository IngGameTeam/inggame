package io.github.inggameteam.inggame.minigame.base.locational

import io.github.inggameteam.inggame.component.delegate.SimpleWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.LocationModel

class LocationalImp(wrapper: Wrapper) : Locational, SimpleWrapper(wrapper) {
    override val locations: HashMap<String, LocationModel> by nonNull
    override fun getLocationOrNull(name: String) = getOrNull(name, LocationModel::class)?.toLocation()

}