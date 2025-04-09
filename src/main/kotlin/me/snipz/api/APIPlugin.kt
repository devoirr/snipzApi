package me.snipz.api

import me.snipz.api.gui.GUIListener
import org.bukkit.plugin.java.JavaPlugin

class APIPlugin: JavaPlugin() {

    companion object {
        lateinit var instance: APIPlugin
    }

    override fun onEnable() {
        instance = this

        GUIListener().register()
    }

}