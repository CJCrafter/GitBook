package com.cjcrafter.gitbook.search

data class SearchResponse(
    val next: Next,
    val count: Int,
    val items: List<Item>,
) {
    data class Next(
        val page: String
    )

    data class Item(
        val id: String,
        val title: String,
        val pages: List<Page>,
    ) {
        data class Page(
            val id: String,
            val title: String,
            val path: String,
            val sections: List<Section>,
            val urls: Urls,
        ) {
            data class Section(
                val id: String,
                val title: String,
                val path: String,
                val body: String,
                val urls: Urls
            )

            data class Urls(
                val app: String,
            )
        }
    }
}