package me.snipz.api.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

abstract class GUIItemAction : (InventoryClickEvent, Player) -> Unit
data class GUIItem(val item: (Player) -> ItemStack?, val action: GUIItemAction)

data class GUIHolder(
    val menuId: UUID,
    val inventory: Inventory,
    val items: Map<Int, MutableList<GUIItem>>
)