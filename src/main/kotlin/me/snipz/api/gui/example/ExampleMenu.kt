package me.snipz.api.gui.example

import me.snipz.api.gui.GUI
import me.snipz.api.gui.item.GUIButton
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Open with ExampleMenu(player).open()
 */
class ExampleMenu(player: Player): GUI(player, 9, Component.text("Example")) {

    private var diamonds = 64
    private var emeralds = 64

    /**
     * Called when the GUI is opened for the first time.
     * Can be called mannualy in code.
     */
    override fun redraw() {
        // When opening for the first time, create a close-handler.
        if (isFirstDraw()) {
            bindCloseHandler { event ->
                // Player will recieve a message after closing the GUI.
                player.sendMessage("You closed the menu!")
                player.sendMessage("Reason: ${event.reason.name}")
            }
        }

        // Add the diamonds
        addButton(GUIButton().apply {
            this.slots = listOf(0) // Slot: 0
            this.itemStack = ItemStack(Material.DIAMOND, diamonds) // ItemStack
            this.action = { // Will be called on click...
                diamonds-- // decrease diamonds amount in the gui
                player.inventory.addItem(ItemStack(Material.DIAMOND)) // give one diamond to the player

                redraw() // redraw the gui
            }
        })

        addButton(GUIButton().apply {
            this.slots = listOf(1)
            this.itemStack = ItemStack(Material.EMERALD, emeralds)
            this.action = {
                emeralds--
                player.inventory.addItem(ItemStack(Material.EMERALD))

                redraw()
            }
        })
    }
}