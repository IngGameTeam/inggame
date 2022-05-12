package io.github.inggameteam.party

import com.eatthepath.uuid.FastUUID
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.utils.fastToString
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object PartyCacheSerializer {

    const val PARTY = "party"

    fun serialize(plugin: PartyPlugin) {
        val file = getFile(plugin)
        file.delete()
        file.createNewFile()
        val conf = file.toYaml()
        try {
            val partys = plugin.partyRegister
            partys.forEach { it ->
                val leader = it.leader.uniqueId.fastToString()
                conf.set("$PARTY.$leader.joined", it.joined.map { it.uniqueId.fastToString() })
                conf.set("$PARTY.$leader.banList", it.banList.map { it.fastToString() })
                if (it.renamed) conf.set("$PARTY.$leader.name", it.name)
                if (it.opened) conf.set("$PARTY.$leader.opened", it.opened)
            }
        } catch (_: Exception) {}
        conf.save(file)
    }

    fun deserialize(plugin: PartyPlugin): DeserializeResult {
        val partyRegister = plugin.partyRegister
        val file = getFile(plugin)
        if (!file.exists()) return DeserializeResult()
        val conf = file.toYaml()
        conf.getConfigurationSection(PARTY)?.getKeys(false)?.forEach { it ->
            val uniqueId = FastUUID.parseUUID(it)
            val player = Bukkit.getPlayer(uniqueId)
            if (player !== null) {
                val joined = conf.getStringList("$PARTY.$it.joined")
                    .mapNotNull { Bukkit.getPlayer(FastUUID.parseUUID(it)) }
                    .map { plugin[it] }.toTypedArray()
                val banList = ArrayList(conf.getStringList("$PARTY.$it.banList").map { FastUUID.parseUUID(it)!! }.toList())
                val opened = conf.isSet("$PARTY.$it.opened")
                val renamed = conf.isSet("$PARTY.$it.name")
                val party =
                    Party(
                        plugin,
                        joined = GPlayerList(joined.toList()),
                        opened = opened,
                        renamed = renamed,
                        banList = banList
                    ).apply { if (renamed) {
                        name = conf.getString("$PARTY.$it.name")!!
                        this.renamed = true
                    } }
                partyRegister.add(party)
            }
        }
        return DeserializeResult()
    }

    fun getFile(plugin: PartyPlugin) = File(plugin.dataFolder, "last-party.yml")

    fun File.toYaml() = YamlConfiguration.loadConfiguration(this)

}

class DeserializeResult
