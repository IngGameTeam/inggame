package io.github.inggameteam.inggame.utils

import java.util.*
import java.util.concurrent.CopyOnWriteArraySet

interface TagContainer {
    var tags: SafeSetWithToString<String>

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
        tags = SafeSetWithToString()
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