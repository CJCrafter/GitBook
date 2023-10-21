package com.cjcrafter.gitbook

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class GitBook(
    private val apiKey: String,
    private val client: OkHttpClient = OkHttpClient(),
) {
    private val gson = Gson()

    private fun buildRequest(request: Any, endpoint: String): Request {
        val requestJson = gson.toJson(request)
        val requestBody = requestJson.toRequestBody("application/json; charset=utf-8".toMediaType())
        return Request.Builder()
            .url(endpoint)
            .post(requestBody)
            .header("Authorization", "Bearer $apiKey")
            .build()
    }

    private inline fun <reified T> executeCall(request: Request, clazz: Class<T>): Result<T> {
        val response: Response = client.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.string()?.let {
                return Result.success(gson.fromJson(it, clazz))
            }
        }
        return Result.failure(Exception("Request failed with response: $response"))
    }

    fun search(search: SearchRequest, location: SearchLocation = SearchLocation.BASE): Result<SearchResponse> {
        val urlBuilder = StringBuilder(location.getSearchEndpoint())
            .append("?query=${search.query}")
        search.limit?.let { urlBuilder.append("&limit=$it") }
        search.page?.let { urlBuilder.append("&page=$it") }

        val request = Request.Builder()
            .url(urlBuilder.toString())
            .header("Authorization", "Bearer $apiKey")
            .build()

        return executeCall(request, SearchResponse::class.java)
    }

    fun ask(ask: AskRequest, location: SearchLocation = SearchLocation.BASE): Result<AskResponse> {
        val request = buildRequest(ask, location.getAskEndpoint())
        return executeCall(request, AskResponse::class.java)
    }

    class Builder {
        private lateinit var apiKey: String
        private var client: OkHttpClient = OkHttpClient()

        fun apiKey(apiKey: String) = apply {
            this.apiKey = apiKey
        }

        fun client(client: OkHttpClient) = apply {
            this.client = client
        }

        fun build(): GitBook {
            return GitBook(apiKey, client)
        }
    }

    companion object {
        const val ENDPOINT = "https://api.gitbook.com"


        @JvmStatic
        fun builder() = Builder()
    }
}
