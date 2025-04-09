package me.snipz.api.panel

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.InventoryHolder

class Panel {

    interface PanelInventoryHolder: InventoryHolder {
        fun handleClick(event: InventoryClickEvent)
        fun handleClose(event: InventoryCloseEvent)
    }

}