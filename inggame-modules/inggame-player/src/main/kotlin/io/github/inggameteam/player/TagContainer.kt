package io.github.inggameteam.player

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
