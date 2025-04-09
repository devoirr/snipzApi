package me.snipz.api.listener

import me.snipz.api.APIPlugin
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

open class QuickListener: Listener {

    fun register() {
        Bukkit.getPluginManager().registerEvents(this, APIPlugin.instance)
    }

    fun unregister() {
        HandlerList.unregisterAll(this)
    }

}