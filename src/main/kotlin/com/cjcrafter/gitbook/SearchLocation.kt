package com.cjcrafter.gitbook

/**
 * Search location
 *
 * https://developer.gitbook.com/gitbook-api/reference/search
 *
 * @constructor Create empty Search location
 */
abstract class SearchLocation {

    abstract fun getSearchEndpoint(): String

    open fun getAskEndpoint() = getSearchEndpoint() + "/ask"

    companion object {

        val BASE = object : SearchLocation() {
            override fun getSearchEndpoint() = GitBook.ENDPOINT + "/v1/search"
        }

        @JvmStatic
        fun forOrganization(organizationId: String): SearchLocation {
            return object : SearchLocation() {
                override fun getSearchEndpoint() = GitBook.ENDPOINT + "/v1/orgs/$organizationId/search"

                override fun getAskEndpoint() = throw IllegalStateException("Cannot use ask for organizations")
            }
        }

        @JvmStatic
        fun forSpace(spaceId: String): SearchLocation {
            return object : SearchLocation() {
                override fun getSearchEndpoint() = GitBook.ENDPOINT + "/v1/spaces/$spaceId/search"
            }
        }
    }
}