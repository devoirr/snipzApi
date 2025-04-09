package me.snipz.api.locale

import com.google.gson.Gson
import java.io.File

class LocaleLoader(file: File) {

    private val messages: Messages

    init {
        val json = file.readText()
        val map =
            (Gson().fromJson(json, Map::class.java) as Map<*, *>).map { it.key.toString() to it.value.toString() }
                .toMap()

        messages = Messages(map)
    }

    fun toMessages(): Messages = messages

}