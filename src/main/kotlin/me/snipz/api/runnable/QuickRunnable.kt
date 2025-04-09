@file:Suppress("unused")

package me.snipz.api.runnable

import me.snipz.api.APIPlugin
import org.bukkit.scheduler.BukkitRunnable

class QuickRunnable {

    companion object {
        fun runSync(runnable: Runnable) {
            object : BukkitRunnable() {
                override fun run() {
                    runnable.run()
                }
            }.runTask(APIPlugin.instance)
        }

        fun runAsync(runnable: Runnable) {
            object : BukkitRunnable() {
                override fun run() {
                    runnable.run()
                }
            }.runTaskAsynchronously(APIPlugin.instance)
        }

        fun runLaterSync(runnable: Runnable, time: Long) {
            object : BukkitRunnable() {
                override fun run() {
                    runnable.run()
                }
            }.runTaskLater(APIPlugin.instance, time)
        }

        fun runLaterAsync(runnable: Runnable, time: Long) {
            object : BukkitRunnable() {
                override fun run() {
                    runnable.run()
                }
            }.runTaskLaterAsynchronously(APIPlugin.instance, time)
        }

        fun runTimerSync(runnable: Runnable, delay: Long, period: Long) {
            object : BukkitRunnable() {
                override fun run() {
                    runnable.run()
                }
            }.runTaskTimer(APIPlugin.instance, delay, period)
        }

        fun runTimerAsync(runnable: Runnable, delay: Long, period: Long) {
            object : BukkitRunnable() {
                override fun run() {
                    runnable.run()
                }
            }.runTaskTimerAsynchronously(APIPlugin.instance, delay, period)
        }
    }

}