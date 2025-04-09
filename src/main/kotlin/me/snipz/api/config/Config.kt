package me.snipz.api.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class Config(private val file: File) {

    private var handle: FileConfiguration

    init {
        if (!file.exists()) {
            file.mkdirs()
            file.createNewFile()
        }
        handle = YamlConfiguration.loadConfiguration(file)
    }

    fun get() = handle

    fun save() {
        handle.save(file)
    }

    fun reload() {
        handle = YamlConfiguration.loadConfiguration(file)
    }

}