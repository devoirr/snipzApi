package me.snipz.api.gui.item

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class GUIButton {
    var slots: List<Int> = emptyList()
    var itemStack: ItemStack = ItemStack(Material.AIR)
    var action: (InventoryClickEvent) -> Unit = { }
}