package com.cjcrafter.gitbook

data class AskRequest(
    val query: String,
    val previousQueries: List<String> = listOf(),
)
