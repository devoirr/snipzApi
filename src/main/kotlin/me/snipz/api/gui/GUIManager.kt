package me.snipz.api.gui

import org.bukkit.entity.Player
import java.util.*

object GUIManager {

    private val menus = mutableMapOf<UUID, GUI>()
    private val opened = mutableMapOf<UUID, GUIHolder>()

    fun addHolderForPlayer(player: Player, holder: GUIHolder) {
        opened[player.uniqueId] = holder
    }

    fun closeHolderForPlayer(player: Player) {
        opened.remove(player.uniqueId)
    }

    fun getPlayerHolder(player: Player) = opened[player.uniqueId]

    fun getGUI(uuid: UUID): GUI? {
        return menus[uuid]
    }

    fun register(menu: GUI, uuid: UUID) {
        menus[uuid] = menu
    }

    fun unregister(uuid: UUID) {
        menus.remove(uuid)
    }

}