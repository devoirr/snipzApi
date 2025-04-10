package me.snipz.api.gui.pagination

class Pagination<T>(items: List<T>, pageSize: Int) {

    private val pages = mutableListOf<List<T>>()

    init {
        var list = mutableListOf<T>()

        for (item in items) {
            list.add(item)

            if (list.count() == pageSize) {
                pages.add(list)
                list = mutableListOf()
            }
        }

        if (list.isNotEmpty()) {
            pages.add(list)
        }
    }

    fun getPage(page: Int): List<T> {
        if (pages.count() <= page) {
            println("Page $page out of range!")
            return emptyList()
        }

        return pages[page]
    }

    fun count() = pages.count()

}