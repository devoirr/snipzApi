package me.snipz.api.locale

data class Messages(private val messages: Map<String, String>) {
    fun getMessage(key: String) = messages[key]
}