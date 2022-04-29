package io.github.inggameteam.player

import io.github.inggameteam.alert.api.Alert
import org.bukkit.entity.Player
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

open class GPlayerList(c: Collection<GPlayer> = Collections.emptyList()) : ArrayList<GPlayer>(c) {

    fun receiveAll(sender: UUID, alert: Alert<Player>, vararg args: Any) {
        forEach { gPlayer -> alert.send(sender, gPlayer.player, *args)}
    }

    fun playerHasTags(vararg pTags: PTag): GPlayerList {
        return GPlayerList(stream()
            .filter { gPlayer ->
                pTags.isEmpty() || Arrays.stream(pTags).allMatch { tag -> gPlayer.hasTag(tag!!) }
            }.collect(Collectors.toSet()))
    }

    fun playerHasNoTags(vararg pTags: PTag): GPlayerList {
        return GPlayerList(stream()
            .filter { gPlayer ->
                pTags.isEmpty() || Arrays.stream(pTags).noneMatch { tag -> gPlayer.hasTag(tag!!) }
            }.collect(Collectors.toSet()))
    }

    override fun toString() = if (isEmpty()) "empty" else joinToString(", ")
}
