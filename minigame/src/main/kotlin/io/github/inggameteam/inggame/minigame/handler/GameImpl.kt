//package io.github.inggameteam.inggame.minigame.handler
//
//import io.github.inggameteam.inggame.minigame.Sector
//import io.github.inggameteam.inggame.utils.HandleListener
//import org.bukkit.Bukkit
//import org.bukkit.Particle
//import org.bukkit.event.EventHandler
//import org.bukkit.event.EventPriority
//import org.bukkit.event.player.PlayerQuitEvent
//import org.bukkit.plugin.Plugin
//
//abstract class GameImpl(
//    val plugin: Plugin,
//) : HandleListener(plugin) {
//
//    override fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean {
//        if (joined.contains(gPlayer)) {
//            if (sendMessage) comp.send(ALREADY_JOINED, gPlayer, displayName(gPlayer))
//        } else if (gameState !== GameState.WAIT && joinType === JoinType.PLAY) {
//            if (sendMessage) comp.send(CANNOT_JOIN_DUE_TO_STARTED, gPlayer, displayName(gPlayer))
//        } else if (playerLimitAmount > 0 && joined.hasTags(PTag.PLAY).size >= playerLimitAmount && joinType === JoinType.PLAY) {
//            if (sendMessage) comp.send(CANNOT_JOIN_PLAYER_LIMITED, gPlayer, displayName(gPlayer))
//        } else {
//            return true
//        }
//        return false
//    }
//
//    override fun joinGame(gPlayer: GPlayer, joinType: JoinType): Boolean {
//        if (requestJoin(gPlayer, joinType, true)) {
//            joined.add(gPlayer)
//            playerData[gPlayer] = HashMap()
//            comp.send(JOIN, gPlayer, displayName(gPlayer))
//            if (joinType === JoinType.PLAY) gPlayer.addTag(PTag.PLAY)
//            else comp.send(START_SPECTATING, gPlayer, displayName(gPlayer))
//            Bukkit.getPluginManager().callEvent(GameJoinEvent(gPlayer, this, joinType))
//            if (gameTask === null
//                && gameState === GameState.WAIT
//                && 0 < startPlayersAmount
//                && joined.hasTags(PTag.PLAY).size >= startPlayersAmount)
//                start(false)
//            return true
//        }
//        return false
//    }
//
//    override fun requestLeft(gPlayer: GPlayer, leftType: LeftType) = joined.contains(gPlayer)
//
//    override fun leftGame(gPlayer: GPlayer, leftType: LeftType): Boolean {
//        if (!requestLeft(gPlayer, leftType)) return false
//        Bukkit.getPluginManager().callEvent(GameLeftEvent(gPlayer, this, leftType))
//        joined.remove(gPlayer)
//        playerData.remove(gPlayer)
//        gPlayer.clearTags()
//        if (leftType === LeftType.LEFT_SERVER) {
//            comp.send(LEFT_GAME_DUE_TO_SERVER_LEFT, gPlayer, displayName(gPlayer))
//        } else {
//            comp.send(LEFT, gPlayer, displayName(gPlayer))
//        }
//        val joinedSize = joined.hasTags(PTag.PLAY).size
//        if (gameState === GameState.WAIT && joinedSize < startPlayersAmount && gameTask != null) {
//            comp.send(START_CANCELLED_DUE_TO_PLAYERLESS, joined)
//            cancelGameTask()
//            gameTask = null
//        }
//        if (leftType.isJoinHub) plugin.gameRegister.join(gPlayer, plugin.gameRegister.hubName)
//        if (gameState != GameState.STOP && joinedSize <= if (gameState === GameState.PLAY) 1 else 0) {
//            stop(false)
//        }
//        return true
//    }
//
//    override fun start(force: Boolean) {
//        if (force) {
//            gameState = GameState.PLAY
//            afterParticle()
//            beginGame()
//            Bukkit.getPluginManager().callEvent(GameBeginEvent(this))
//        } else if (gameState === GameState.WAIT) {
//            val tick = startWaitingSecond
//            if (tick < 0) {
//                start(true)
//            } else {
//                val list = ArrayList<() -> Unit>()
//                for (i in tick downTo  1) list.add {
//                    joined.forEach { comp.send(GAME_START_COUNT_DOWN, it, displayName(it), i, comp.stringOrNull("$name-info", it.lang(plugin))?: "") }
//                }
//                list.add { gameTask = null; start(true) }
//                gameTask = ITask.repeat(plugin, 20, 20, *(list.toTypedArray()))
//            }
//        }
//    }
//
//    override fun stop(force: Boolean, leftType: LeftType) {
//        if (gameState !== GameState.STOP) {
//            gameState = GameState.STOP
//            finishGame()
//            ArrayList(joined).forEach { gPlayer ->
//                leftGame(gPlayer, leftType)
//            }
//            gameTask = null
//            Bukkit.getPluginManager().callEvent(GameUnloadEvent(this))
//            if (gameState === GameState.STOP)
//                gameTask = { if (plugin.gameRegister.contains(this)) gameTask = null; plugin.gameRegister.removeGame(this) }.delay(plugin, stopWaitingTick)
//        }
//    }
//
//    override fun finishGame() = Unit
//
//    open fun beginGame() {
//        joined.forEach { comp.send(GAME_START, it, displayName(it)) }
//    }
//
//    @EventHandler(priority = EventPriority.HIGH)
//    fun onPlayerQuit(event: PlayerQuitEvent) {
//        val gPlayer = plugin[event.player]
//        if (!isJoined(gPlayer)) return
//        if (requestLeft(gPlayer, LeftType.LEFT_SERVER)) leftGame(gPlayer, LeftType.LEFT_SERVER)
//    }
//
//    open fun afterParticle() {
//        joined.hasTags(PTag.PLAY).forEach {
//            it.world.spawnParticle(Particle.END_ROD, it.eyeLocation.clone(), 20)
//        }
//    }
//
//
//}