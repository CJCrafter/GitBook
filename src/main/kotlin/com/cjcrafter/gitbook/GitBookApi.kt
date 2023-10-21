package com.cjcrafter.gitbook

import okhttp3.OkHttpClient

interface GitBookApi {

    /**
     * Shorthand for searching in [SearchLocation.Base].
     *
     * @param search The search request.
     * @return The search response, or an exception wrapped in [Result].
     */
    fun search(search: SearchRequest) = search(search, SearchLocation.Base)

    /**
     * Searches for occurrences of a query in a wiki using
     * [GitBook's search api](https://developer.gitbook.com/gitbook-api/reference/search).
     *
     * @param search The search query.
     * @param location Which wiki(s) to search.
     * @return The search response, or an exception wrapped in [Result].
     */
    fun search(search: SearchRequest, location: SearchLocation): Result<SearchResponse>

    /**
     * Shorthand for asking in [SearchLocation.Base].
     *
     * @param ask The ask request.
     * @return The ask response, or an exception wrapped in [Result].
     */
    fun ask(ask: AskRequest) = ask(ask, SearchLocation.Base)

    /**
     * Asks a question in natural language using
     * [GitBook's ask api](https://developer.gitbook.com/gitbook-api/reference/ask).
     * If the wiki does not have enough information to answer the question, the
     * resulting [AskResponse] will have a `null` answer. See [AskResponse.hasAnswer].
     *
     * @param ask The ask request.
     * @param location Which wiki(s) to ask.
     * @return The ask response, or an exception wrapped in [Result].
     */
    fun ask(ask: AskRequest, location: SearchLocation): Result<AskResponse>

    /**
     * Builder for instantiating a basic [GitBookClient].
     */
    class Builder {
        private lateinit var apiKey: String
        private var endpoint: String = ENDPOINT
        private var client: OkHttpClient = OkHttpClient()

        fun apiKey(apiKey: String) = apply {
            this.apiKey = apiKey
        }

        fun endpoint(endpoint: String) = apply {
            this.endpoint = endpoint
        }

        fun client(client: OkHttpClient) = apply {
            this.client = client
        }

        fun build(): GitBookClient {
            return GitBookClient(apiKey, endpoint, client)
        }
    }

    companion object {

        const val ENDPOINT = "https://api.gitbook.com"

        /**
         * Returns a new builder instance for creating a basic [GitBookClient].
         */
        @JvmStatic
        fun builder() = Builder()
    }
}