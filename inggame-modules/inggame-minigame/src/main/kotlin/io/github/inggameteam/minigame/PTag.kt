package io.github.inggameteam.minigame

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import java.util.*
import java.util.stream.Collectors

enum class PTag {
    PLAY, DEAD,
    BLUE, RED;
}

fun ArrayList<GPlayer>.playerHasTags(vararg pTags: PTag): GPlayerList {
    return GPlayerList(stream()
        .filter { gPlayer ->
            pTags.isEmpty() || Arrays.stream(pTags).allMatch { tag -> gPlayer.hasTag(tag!!) }
        }.collect(Collectors.toSet()))
}

fun ArrayList<GPlayer>.playerHasNoTags(vararg pTags: PTag): GPlayerList {
    return GPlayerList(stream()
        .filter { gPlayer ->
            pTags.isEmpty() || Arrays.stream(pTags).noneMatch { tag -> gPlayer.hasTag(tag!!) }
        }.collect(Collectors.toSet()))
}
