package me.snipz.api.gui.api

import me.snipz.api.gui.GUIItem
import me.snipz.api.gui.GUIItemAction
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

open class GUIBase {

    protected var title: Component = Component.text("Menu").color(NamedTextColor.BLACK)
    protected var size: Int = 9

    // One could add more than one item to the same slot, and then the first visible for player will appear.
    protected val items = mutableMapOf<Int, MutableList<GUIItem>>()

    // Called when the GUI is closed.
    private val closeHandlers = mutableListOf<(InventoryCloseEvent) -> Unit>()

    open fun openFor(player: Player) {}

    fun title(component: Component): GUIBase {
        this.title = component
        return this
    }

    fun size(size: Int): GUIBase {
        this.size = size
        return this
    }

    fun item(
        itemStack: (Player) -> ItemStack?,
        action: (InventoryClickEvent, Player) -> Unit,
        slots: List<Int>
    ) {
        val guiItem = GUIItem(itemStack, object : GUIItemAction() {
            override fun invoke(p1: InventoryClickEvent, p2: Player) {
                action(p1, p2)
            }
        })

        for (slot in slots) {
            items.getOrPut(slot) { mutableListOf() }.add(guiItem)
        }
    }

    fun setOverridingItem(
        itemStack: (Player) -> ItemStack?,
        action: (InventoryClickEvent, Player) -> Unit,
        vararg slots: Int
    ) {
        val guiItem = GUIItem(itemStack, object : GUIItemAction() {
            override fun invoke(p1: InventoryClickEvent, p2: Player) {
                action(p1, p2)
            }
        })

        for (slot in slots) {
            items[slot] = mutableListOf(guiItem)
        }
    }

    fun onClose(handler: (InventoryCloseEvent) -> Unit) {
        closeHandlers.add(handler)
    }

    fun runOnClose(event: InventoryCloseEvent) {
        closeHandlers.forEach { it.invoke(event) }
    }
}