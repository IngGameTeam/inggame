package io.github.inggameteam.utils

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


object MojangUtil {
    fun getUUID(name: String): String {
        var uuid: String
        try {
            val `in` =
                BufferedReader(InputStreamReader(URL("https://api.mojang.com/users/profiles/minecraft/$name").openStream()))
            uuid = (JsonParser().parse(`in`) as JsonObject).get("id").toString().replace("\"", "")
            uuid = uuid.replace("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(), "$1-$2-$3-$4-$5")
            `in`.close()
        } catch (e: Exception) {
            println("Unable to get UUID of: $name!")
            uuid = "er"
        }
        return uuid
    }

}