package me.snipz.api.gui

import me.snipz.api.gui.api.GUIBase
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.persistence.PersistentDataType
import java.util.*

@Suppress("unused")
class GUI : GUIBase() {

    private val menuID = UUID.randomUUID()

    private val handlers = mutableListOf<(InventoryClickEvent) -> Unit>()

    fun handler(handler: (InventoryClickEvent) -> Unit): GUI {
        handlers.add(handler)
        return this
    }

    fun runHandlers(event: InventoryClickEvent) {
        handlers.forEach { it(event) }
    }

    override fun openFor(player: Player) {

        val inventory = Bukkit.createInventory(player, size, title)

        for (slot in items.keys.filter { it < size }) {

            items[slot]!!.forEachIndexed { index, guiItem ->
                guiItem.item(player)?.let {
                    it.editMeta { meta ->
                        meta.persistentDataContainer.set(NamespacedKey.minecraft("action"), PersistentDataType.INTEGER, index)
                    }
                    inventory.setItem(slot, it)
                }
            }

        }

        val holder = GUIHolder(menuID, inventory, items)
        player.openInventory(inventory)

        GUIManager.addHolderForPlayer(player, holder)

    }

    fun register(): GUI {
        GUIManager.register(this, menuID)
        return this
    }

    fun unregister(): GUI {
        GUIManager.unregister(menuID)
        return this
    }

}