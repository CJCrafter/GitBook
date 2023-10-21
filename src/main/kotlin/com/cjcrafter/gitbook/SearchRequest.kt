package com.cjcrafter.gitbook

data class SearchRequest(
    val limit: Int? = null,
    val page: String? = null,
    val query: String,
)