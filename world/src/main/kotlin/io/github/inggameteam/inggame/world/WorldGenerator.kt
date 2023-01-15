package io.github.inggameteam.inggame.world

import org.bukkit.*

object WorldGenerator {
    fun generateWorld(name: String, onGenerate: () -> Unit) {
        if (name.isEmpty()) return
        if (Bukkit.getWorld(name) === null) {
            val worldCreator = WorldCreator(name)
            worldCreator.environment(World.Environment.NORMAL)
            worldCreator.type(WorldType.FLAT)
            worldCreator.generatorSettings(
                "{\"structures\": {\"structures\": {}}, " +
                        "\"layers\": [" +
                        "{\"block\": \"air\", \"height\": 1}, " +
                        "{\"block\": \"grass\", \"height\": 0}], " +
                        "\"biome\":\"the_void\"}"
            )
            val world = worldCreator.createWorld()
            if (world != null && world.getGameRuleValue(GameRule.DO_MOB_SPAWNING) != false) {
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
                world.isAutoSave = false
                onGenerate()
                world.save()
                Bukkit.unloadWorld(world, true)
                worldCreator.createWorld()
                world.isAutoSave = false
            }
        }
    }

}