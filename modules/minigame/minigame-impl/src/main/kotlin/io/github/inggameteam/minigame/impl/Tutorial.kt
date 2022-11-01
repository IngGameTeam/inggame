package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.SectionalImpl
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.SpawnPlayer
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.scheduler.delay
import io.github.inggameteam.utils.ColorUtil.color
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Witch

class Tutorial(plugin: GamePlugin) : SectionalImpl(plugin), SpawnPlayer, SimpleGame {
    override val name get() = TUTORIAL_NAME
    override val recommendedStartPlayersAmount get() = 1
    private val entityMap = HashMap<String, Entity>()
    private val symbolicTable = HashMap<String, (List<String>) -> Unit>()

    companion object {
        const val TUTORIAL_NAME = "tutorial"
    }

    var count = 1
    var stopTick = 0


    override fun beginGame() {
        super.beginGame()
        symbolicTable["shakeWitch"] = { args ->
            val witch = entityMap[args[0]] as? Witch
            witch?.swingMainHand()
            witch?.swingOffHand()
        }
        symbolicTable["entityMessage"] = { args ->
            entityMap[args[0]]?.sendMessage(args.subList(1, args.size).joinToString(" "))
        }
        symbolicTable["title"] = { args ->
            val fadeIn = args[0].toInt()
            val stay = args[1].toInt()
            val fadeOut = args[2].toInt()
            (args.subList(3, args.size).joinToString(" ")).split("-").apply {
                val subTitle = if (size != 1) get(1) else "&f".color
                val title = get(0).ifEmpty { " " }
                joined.forEach { player ->
                    player.sendTitle(title, subTitle, fadeIn, stay, fadeOut)
                }
            }
        }
        symbolicTable["spawnEntity"] = { args ->
            world.spawn(
                getLocation(args[2]),
                EntityType.valueOf(args[0]).entityClass!!,
            )?.apply {
                isInvulnerable = if (args[4] == "true") true else false
                addTask({
                    this.remove()
                }.delay(plugin, args[3].toLong()))
                entityMap[args[1]] = this
            }
        }
        symbolicTable["playSound"] = { args ->
            joined.forEach { player ->
                player.playSound(
                    Location(point.world, 0.0, 0.0, 0.0),
                    args[0],
                    Float.MAX_VALUE,
                    1f
                )
            }
        }
        symbolicTable["stopSound"] = { args ->
            joined.forEach { player ->
                player.stopSound(args[0])
            }
        }

        stopTick = Integer.parseInt(comp.string("stop", plugin.defaultLanguage))
        addTask(ITask.repeat(plugin, 1, 1, {
            if (count >= stopTick) {
                joined[0].addTag(PTag.DEAD)
                leftGame(joined[0], LeftType.GAME_STOP)
                return@repeat
            }
            symbolicTable
                .map {
                    Pair(comp.stringOrNull("${it.key}$count", plugin.defaultLanguage)?.split(" "), it.value) }
                .forEach { it.second.invoke(it.first?: return@forEach) }

            count++
        }))
    }


}