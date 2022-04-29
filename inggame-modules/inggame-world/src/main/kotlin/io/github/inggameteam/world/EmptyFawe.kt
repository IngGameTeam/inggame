package io.github.inggameteam.world

import org.bukkit.Location
import java.io.File

class EmptyFawe : Fawe {
    override fun paste(location: Location, file: File) = Unit

}
