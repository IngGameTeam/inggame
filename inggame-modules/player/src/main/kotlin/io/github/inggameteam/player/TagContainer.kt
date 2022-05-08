package io.github.inggameteam.player

import io.github.inggameteam.utils.listWithToString
import java.util.*
import kotlin.collections.HashSet

interface TagContainer {
    var tags: HashSet<String>

    fun addTag(tag: Enum<*>) = addTag(tag.name)

    fun addTag(tag: String) {
        tags.add(tag)
    }

    fun removeTag(tag: Enum<*>) = removeTag(tag.name)
    fun removeTag(tag: String) {
        tags.remove(tag)
    }

    fun hasTag(tag: Enum<*>) = hasTag(tag.name)
    fun hasTag(tag: String): Boolean = tags.contains(tag)

    fun clearTags() {
        tags = HashSet()
    }

}


fun <T : TagContainer> Collection<T>.hasTags(vararg pTags: String) = filter {
    pTags.isEmpty() || Arrays.stream(pTags).allMatch { tag -> it.hasTag(tag!!) }
}.listWithToString()
fun <T : TagContainer> Collection<T>.hasNoTags(vararg pTags: String) = filter {
    pTags.isEmpty() || Arrays.stream(pTags).noneMatch { tag -> it.hasTag(tag!!) }
}.listWithToString()
fun <T : TagContainer> Collection<T>.hasNoTags(vararg pTags: Enum<*>) = hasNoTags(*pTags.map(Enum<*>::name).toTypedArray())
fun <T : TagContainer> Collection<T>.hasTags(vararg pTags: Enum<*>) = hasTags(*pTags.map(Enum<*>::name).toTypedArray())