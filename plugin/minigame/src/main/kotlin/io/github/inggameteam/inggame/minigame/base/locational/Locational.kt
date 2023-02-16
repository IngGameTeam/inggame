package io.github.inggameteam.inggame.minigame.base.locational

import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import org.bukkit.Location

val LocationNotFound = Throwable("Location is not exist")
interface Locational : Wrapper {

    val locations: HashMap<String, LocationModel>
    fun getLocation(name: String): Location = getLocationOrNull(name)?: throw LocationNotFound
    fun getLocationOrNull(name: String): Location?

}

class LocationalImp(wrapper: Wrapper) : Locational, SimpleWrapper(wrapper) {
    override val locations: HashMap<String, LocationModel> by nonNull
    override fun getLocationOrNull(name: String) = getOrNull(name, LocationModel::class)?.toLocation()

}