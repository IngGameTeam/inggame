package io.github.inggameteam.world

import org.bukkit.*
import java.io.File

object WorldGenerator {
    fun generateWorld(name: String, onGenerate: () -> Unit) {
        if (name.isEmpty()) return
        if (Bukkit.getWorld(name) === null) {
            File(Bukkit.getWorldContainer(), name).deleteOnExit()
            val worldCreator = WorldCreator(name)
            worldCreator.environment(World.Environment.NORMAL)
            worldCreator.type(WorldType.FLAT)
            worldCreator.generatorSettings(
                "{\"structures\": {\"structures\": {}}, " +
                        "\"layers\": [" +
                        "{\"block\": \"stone\", \"height\": 0}, " +
                        "{\"block\": \"grass\", \"height\": 0}], " +
                        "\"biome\":\"the_void\"}"
            )
            val world = worldCreator.createWorld()
            if (world != null) {
                world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
                world.setGameRule(GameRule.SPAWN_RADIUS, 0)
                world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)
                world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
                world.setGameRule(GameRule.DO_PATROL_SPAWNING, false)
                world.setGameRule(GameRule.DO_TRADER_SPAWNING, false)
                world.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, true)
                world.setGameRule(GameRule.KEEP_INVENTORY, true)
                world.setGameRule(GameRule.NATURAL_REGENERATION, false)
                world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, true)
                world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, -1)
                world.setGameRule(GameRule.UNIVERSAL_ANGER, false)
                world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false)
                try { world.isAutoSave = false }
                catch (e: Exception) { e.printStackTrace() }
                onGenerate()
                world.save()
            }
        }
    }

}