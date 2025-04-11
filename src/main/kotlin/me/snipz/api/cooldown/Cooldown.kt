package me.snipz.api.cooldown

import me.snipz.api.APIPlugin
import org.apache.commons.lang.time.DateFormatUtils
import org.bukkit.entity.Player
import java.util.UUID

class Cooldown(private val defaultTime: Int) {

    private val groups = mutableMapOf<String, Int>()
    private val bypass = mutableSetOf<String>()

    private val data = mutableMapOf<UUID, Long>()

    fun customTimeFor(group: String, time: Int) {
        groups[group] = time
    }

    fun bypassFor(group: String) {
        bypass.add(group)
    }

    fun apply(player: Player) {
        val group = APIPlugin.instance.permission.getPrimaryGroup(player)
        if (group in bypass)
            return

        val time = groups[group] ?: defaultTime
        data[player.uniqueId] = System.currentTimeMillis() + (time * 1000L)
    }

    fun getLeft(player: Player): String? {
        val timeLeft = data[player.uniqueId] ?: return null
        val secondsLeft = (timeLeft - System.currentTimeMillis()) / 1000L

        if (secondsLeft < 0) {
            data.remove(player.uniqueId)
            return null
        }

        return if (secondsLeft < 60) {
            "$secondsLeft сек."
        } else {
            DateFormatUtils.format(secondsLeft * 1000L, "HH ч. mm м. ss сек.")
        }
    }
}