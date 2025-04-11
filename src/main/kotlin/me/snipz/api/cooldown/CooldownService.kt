package me.snipz.api.cooldown

import me.snipz.api.service.Service

class CooldownService: Service {

    private val cooldowns = mutableMapOf<String, Cooldown>()

    fun register(key: String, cooldown: Cooldown) {
        cooldowns[key] = cooldown
    }

    fun unregister(key: String) {
        cooldowns.remove(key)
    }

    fun getCooldown(key: String): Cooldown? {
        return cooldowns[key]
    }

    override fun onEnable() { }

    override fun onDisable() { }
}