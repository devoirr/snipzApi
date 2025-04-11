package me.snipz.api.gui

import me.snipz.api.listener.QuickListener
import me.snipz.api.runnable.QuickRunnable
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.*

class GUIListener : QuickListener() {

    companion object {
        val allowedActions =
            mutableSetOf(
                InventoryAction.PICKUP_ALL,
                InventoryAction.PICKUP_ONE,
                InventoryAction.PICKUP_HALF,
                InventoryAction.PICKUP_SOME,
                InventoryAction.PLACE_ALL,
//                InventoryAction.PLACE_ONE,
//                InventoryAction.PLACE_SOME,
//                InventoryAction.MOVE_TO_OTHER_INVENTORY
            )
    }

    @EventHandler(ignoreCancelled = true)
    fun onClick(event: InventoryClickEvent) {

        val player = event.whoClicked as? Player ?: return
        val topInventory = player.openInventory.topInventory
        val holder = topInventory.getHolder(false)

        if (holder !is GUI)
            return

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

        val clickedInventory = event.clickedInventory ?: return

        if (event.isShiftClick && holder.inventory != clickedInventory) {
            event.isCancelled = true
        }

        if (clickedInventory != player.openInventory.topInventory) {
            return
        }

        if (event.action !in allowedActions) {
            event.isCancelled = true
            return
        }
        
        event.isCancelled = true
        holder.click(event)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.inventory.getHolder(false) !is GUI)
            return

        (event.inventory.getHolder(false) as GUI).close(event)
    }

    @EventHandler
    fun onDrag(event: InventoryDragEvent) {
        val player = (event.whoClicked as? Player) ?: return
        val inventory = event.inventory

        if (inventory != player.openInventory.topInventory)
            return

        if (player.openInventory.topInventory !is GUI)
            return

        event.isCancelled = true
    }

}