package io.github.inggameteam.world

import io.github.inggameteam.utils.Intvector
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.io.File

interface Fawe {

    fun paste(location: Location, file: File)

    companion object {



        @JvmStatic
        val LOADED = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null
        val fawe  = if (LOADED) FaweImpl() else EmptyFawe()
        const val SCHEMATIC_DIR = "schematics"
        const val DEFAULT = "default"
    }
}
