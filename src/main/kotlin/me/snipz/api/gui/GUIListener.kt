package me.snipz.api.gui

import me.snipz.api.listener.QuickListener
import me.snipz.api.runnable.QuickRunnable
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.*
import org.bukkit.persistence.PersistentDataType

class GUIListener : QuickListener() {

    companion object {
        val allowedActions =
            mutableSetOf(
                InventoryAction.PICKUP_ALL,
                InventoryAction.PICKUP_ONE,
                InventoryAction.PICKUP_HALF,
                InventoryAction.PICKUP_SOME,
                InventoryAction.PLACE_ALL,
            )
    }

    @EventHandler(ignoreCancelled = true)
    fun onClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val holder = GUIManager.getPlayerHolder(player) ?: return

        if (player.isSleeping) {
            player.closeInventory()
            event.isCancelled = true
            return
        }

        if (event.action == InventoryAction.HOTBAR_SWAP) {
            event.isCancelled = true

            val itemInOffHand = player.inventory.itemInOffHand
            player.inventory.setItemInOffHand(null)

            try {
                QuickRunnable.runSync {
                    player.inventory.setItemInOffHand(itemInOffHand)
                    player.updateInventory()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (event.click == ClickType.DOUBLE_CLICK && event.hotbarButton != -1) {
            event.isCancelled = true
            return
        }

        if (event.isShiftClick && holder.inventory != event.clickedInventory) {
            event.isCancelled = true
        }

        val clickedInventory = event.clickedInventory ?: return
        if (clickedInventory != player.openInventory.topInventory) {
            return
        }

        if (event.action !in allowedActions) {
            event.isCancelled = true
            return
        }

        event.isCancelled = true

        val menu = GUIManager.getGUI(holder.menuId)
        menu?.runHandlers(event)

        val items = holder.items[event.slot] ?: return
        val index = event.currentItem?.persistentDataContainer?.get(
            NamespacedKey.minecraft("action"),
            PersistentDataType.INTEGER
        )?.toInt()

        index?.let {
            items[index].action(event, player)
        }
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        (event.player as? Player)?.let {
            GUIManager.getPlayerHolder(it)?.let { holder ->
                val menu = GUIManager.getGUI(holder.menuId)
                menu?.runOnClose(event)
            }

            GUIManager.closeHolderForPlayer(it)
        }
    }

    @EventHandler
    fun onDrag(event: InventoryDragEvent) {

        val player = (event.whoClicked as? Player) ?: return
        val inventory = event.inventory

        if (inventory != player.openInventory.topInventory)
            return

        GUIManager.getPlayerHolder(player) ?: return
        event.isCancelled = true
    }

}