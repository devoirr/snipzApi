package me.snipz.api

import org.bukkit.plugin.java.JavaPlugin

class APIPlugin: JavaPlugin() {

    companion object {
        lateinit var instance: APIPlugin
    }

    override fun onEnable() {
        instance = this
    }

}