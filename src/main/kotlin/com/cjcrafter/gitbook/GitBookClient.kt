package com.cjcrafter.gitbook

import com.cjcrafter.gitbook.gson.SourceTypeAdapter
import com.cjcrafter.gitbook.search.*
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class GitBookClient(
    private val apiKey: String,
    private val endpoint: String = GitBookApi.ENDPOINT,
    private val client: OkHttpClient = OkHttpClient(),
): GitBookApi {

    private val gson = GsonBuilder()
        .registerTypeAdapter(AskResponse.Answer.Source::class.java, SourceTypeAdapter())
        .create()

    override fun search(search: SearchRequest, location: SearchLocation): Result<SearchResponse> {
        val urlBuilder = StringBuilder(location.searchEndpoint(endpoint))
            .append("?query=${search.query}")
        search.limit?.let { urlBuilder.append("&limit=$it") }
        search.page?.let { urlBuilder.append("&page=$it") }

        val request = Request.Builder()
            .url(urlBuilder.toString())
            .header("Authorization", "Bearer $apiKey")
            .build()

        return executeCall(request, SearchResponse::class.java)
    }

    override fun ask(ask: AskRequest, location: SearchLocation): Result<AskResponse> {
        val request = buildRequest(ask, location.askEndpoint(endpoint))
        return executeCall(request, AskResponse::class.java)
    }

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
}
