package me.snipz.api

import com.mojang.brigadier.Command
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.snipz.api.cooldown.CooldownService
import me.snipz.api.gui.GUIListener
import me.snipz.api.gui.example.ExampleMenu
import me.snipz.api.service.ServiceManager
import net.milkbowl.vault.permission.Permission
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class APIPlugin: JavaPlugin() {

    companion object {
        lateinit var instance: APIPlugin
    }

    lateinit var permission: Permission

    lateinit var serviceManager: ServiceManager

    override fun onEnable() {
        instance = this
        serviceManager = ServiceManager()

        serviceManager.register(CooldownService::class, CooldownService())

        GUIListener().register()

        permission = server.servicesManager.getRegistration(Permission::class.java)!!.provider

        this.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            commands.registrar().register(Commands.literal("test").executes { ctx ->
                ExampleMenu(ctx.source.sender as Player).open()
                return@executes Command.SINGLE_SUCCESS
            }.build())
        }
    }

}