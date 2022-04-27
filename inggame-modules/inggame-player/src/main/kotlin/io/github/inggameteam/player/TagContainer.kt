package io.github.inggameteam.player

interface TagContainer {
    var tags: HashSet<PTag>

    fun addTag(tag: PTag) {
        tags.add(tag)
    }

    fun removeTag(tag: PTag) {
        tags.remove(tag)
    }

    fun hasTag(tag: PTag): Boolean = tags.contains(tag)

    fun clearTags() {
        tags = HashSet()
    }

}
