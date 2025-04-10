package me.snipz.api.gui

import me.snipz.api.gui.item.GUIButton
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

abstract class GUI(protected val player: Player, size: Int, title: Component): InventoryHolder {

    protected var drawn = false

    private val closeHandlers = mutableListOf<(InventoryCloseEvent) -> Unit>()
    private val clickHandlers = mutableListOf<(InventoryClickEvent) -> Unit>()

    private val buttons = mutableMapOf<Int, (InventoryClickEvent) -> Unit>()

    private val inventory = Bukkit.createInventory(this, size, title)

    open fun open() {
        this.redraw()
        this.drawn = true

        player.openInventory(this.inventory)
    }

    abstract fun redraw()

    fun isFirstDraw(): Boolean {
        return !drawn
    }

    fun bindCloseHandler(handler: (InventoryCloseEvent) -> Unit) {
        closeHandlers.add(handler)
    }

    fun bindClickHandler(handler: (InventoryClickEvent) -> Unit) {
        clickHandlers.add(handler)
    }

    fun close(event: InventoryCloseEvent) {
        closeHandlers.forEach { it.invoke(event) }
    }

    fun click(event: InventoryClickEvent) {
        buttons[event.slot]?.invoke(event)
        clickHandlers.forEach { it(event) }
    }

    fun addButton(button: GUIButton) {
        for (slot in button.slots.filter { it < inventory.size }) {
            inventory.setItem(slot, button.itemStack)
            buttons[slot] = button.action
        }
    }

    fun removeButton(slot: Int) {
        buttons.remove(slot)
        inventory.setItem(slot, null)
    }

    override fun getInventory(): Inventory {
        return inventory
    }
}