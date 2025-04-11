package me.snipz.api

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.inventory.ItemStack

fun String.toComponent(): Component {
    val component = LegacyComponentSerializer.legacyAmpersand().deserialize(this)
        .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)

    return MiniMessage.miniMessage().deserialize(MiniMessage.miniMessage().serialize(component).replace("\\", ""))
}

fun Location.toString(block: Boolean): String {
    val stringBuilder = StringBuilder()
        .append(this.world!!.name).append(";")
        .append(if (block) this.blockX else this.x)
        .append(";")
        .append(if (block) this.blockY else this.y)
        .append(";")
        .append(if (block) this.blockZ else this.z)

    if (!block) {
        stringBuilder.append(";")
            .append(this.yaw)
            .append(";")
            .append(this.pitch)
    }

    return stringBuilder.toString()
}

fun String.toLocation(): Location {
    val args = this.split(";")
    val world = Bukkit.getWorld(args[0])

    val x = args[1].toDouble()
    val y = args[2].toDouble()
    val z = args[3].toDouble()

    val yaw = if (args.size > 4) args[4].toFloat() else 0f
    val pitch = if (args.size > 5) args[5].toFloat() else 0f

    return Location(world, x, y, z, yaw, pitch)
}

fun String.toIntegerList(): List<Int> {
    val list = mutableListOf<Int>()
    val args = this.replace(" ", "").split(",")

    for (item in args) {
        if (item.contains("-")) {
            val firstInRange = item.split("-")[0].toInt()
            val secondInRange = item.split("-")[1].toInt()

            for (i in firstInRange..secondInRange) {
                list.add(i)
            }
        } else {
            list.add(Integer.valueOf(item))
        }
    }

    return list
}

fun Double.twoDecimalPlaces(): Double {
    return String.format("%.2f", this).toDouble()
}

fun String.toItemStack(): ItemStack {
    val serializedObject = java.util.Base64.getDecoder().decode(this)
    return ItemStack.deserializeBytes(serializedObject)
}

fun ItemStack.toBase64(): String {
    val serializedObject = this.serializeAsBytes()
    return java.util.Base64.getEncoder().encodeToString(serializedObject)
}

fun ItemStack.hasMetaAndModelData() = this.hasItemMeta() && this.hasCustomModelData()

fun String.toGamemode(): GameMode? {
    return when (this) {
        "1", "creative", "c" -> GameMode.CREATIVE
        "0", "survival", "s" -> GameMode.SURVIVAL
        "3", "spectator", "sp" -> GameMode.SPECTATOR
        "2", "adventure", "ad" -> GameMode.ADVENTURE
        else -> null
    }
}

fun GameMode.displayName(): String {
    return when (this) {
        GameMode.CREATIVE -> "креатив"
        GameMode.SURVIVAL -> "выживание"
        GameMode.SPECTATOR -> "наблюдение"
        GameMode.ADVENTURE -> "приключенческий"
    }
}