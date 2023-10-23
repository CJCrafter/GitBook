package com.cjcrafter.gitbook.search

data class AskRequest(
    val query: String,
    val previousQueries: List<String> = listOf(),
)
