package me.snipz.api.locale

import com.google.gson.Gson
import java.io.File

data class Messages(private val messages: Map<String, String>) {
    fun getMessage(key: String) = messages[key]

    companion object {
        fun load(file: File): Messages? {
            if (!file.exists()) return null

            val json = file.readText()
            val map =
                (Gson().fromJson(json, Map::class.java) as Map<*, *>).map { it.key.toString() to it.value.toString() }
                    .toMap()

            return Messages(map)
        }
    }
}