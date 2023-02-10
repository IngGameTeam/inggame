package io.github.inggameteam.inggame.mongodb

data class DatabaseString(val dbName: String) {
    override fun toString() = dbName
}