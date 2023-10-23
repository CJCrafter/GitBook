package com.cjcrafter.gitbook.search

data class SearchRequest(
    val limit: Int? = null,
    val page: String? = null,
    val query: String,
)