package com.cjcrafter.gitbook

sealed class SearchLocation() {

    abstract fun searchEndpoint(baseEndpoint: String): String

    open fun askEndpoint(baseEndpoint: String) = searchEndpoint(baseEndpoint) + "/ask"

    /**
     * Searches everywhere that your API key has access to.
     */
    object Base : SearchLocation() {
        override fun searchEndpoint(baseEndpoint: String) = "$baseEndpoint/v1/search"
    }

    /**
     * Searches in a specific organization.
     *
     * @property organizationId Your organization's id.
     */
    data class Organization(val organizationId: String) : SearchLocation() {
        override fun searchEndpoint(baseEndpoint: String) = "$baseEndpoint/v1/orgs/$organizationId/search"

        // Ask does not support organizations, so we throw an exception to fail fast
        override fun askEndpoint(baseEndpoint: String) = throw IllegalStateException("Cannot use ask in organizations")
    }

    data class Space(val spaceId: String) : SearchLocation() {
        override fun searchEndpoint(baseEndpoint: String) = "$baseEndpoint/v1/spaces/$spaceId/search"
    }
}
