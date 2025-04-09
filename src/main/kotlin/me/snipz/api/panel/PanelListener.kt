package me.snipz.api.panel

import me.snipz.api.listener.QuickListener
import me.snipz.api.runnable.QuickRunnable
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class PanelListener: QuickListener() {

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

    @EventHandler
    fun onClick(event: InventoryClickEvent) {

        val player = event.whoClicked as? Player ?: return
        val inventory = event.clickedInventory ?: return
        val holder = inventory.getHolder(false) ?: return

        if (holder !is Panel.PanelInventoryHolder) return

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
                QuickRunnable.runSync({
                    player.inventory.setItemInOffHand(itemInOffHand)
                    player.updateInventory()
                })
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

        event.isCancelled = true

        if (event.action !in allowedActions)
            return

        holder.handleClick(event)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        val inventory = event.inventory
        val holder = inventory.getHolder(false) ?: return
        if (holder !is Panel.PanelInventoryHolder) return

        holder.handleClose(event)
    }

}