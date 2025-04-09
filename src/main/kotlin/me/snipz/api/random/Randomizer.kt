package me.snipz.api.random

import kotlin.random.Random

interface ItemWithChance {
    fun getChance(): Double
}

class Randomizer<T : ItemWithChance>(private val list: List<T>) {

    fun chooseRandom(): T {
        if (list.size == 1)
            return list.first()

        val sortByChance = list.shuffled().sortedBy { it.getChance() }
        val sum = sortByChance.sumOf { it.getChance() }

        val chance = Random.nextDouble(0.0, sum + 1)
        for (t in sortByChance) {
            if (chance < t.getChance())
                return t
        }

        return sortByChance.last()
    }

    fun size() = list.size

}