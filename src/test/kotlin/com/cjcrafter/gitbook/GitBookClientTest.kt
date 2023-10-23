package com.cjcrafter.gitbook

import com.cjcrafter.gitbook.search.AskRequest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GitBookClientTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var gitBookClient: GitBookClient

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val testBaseUrl = mockWebServer.url("/").toString()
        gitBookClient = GitBookClient("API_KEY", testBaseUrl) // This is a dummy call, therefore it doesn't need a real API key
    }

    @AfterEach
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test ask request success`() {
        val responseBody = """
        {
            "answer": {
                "text": "Yes, it is possible to invert shooting and zooming. In the provided context, it states that due to Minecraft limitations, using left click to shoot does not work. Instead, you can use right click to shoot. Therefore, you can configure the shooting action to be triggered by the right mouse button (RMB) and the zooming action to be triggered by the left mouse button (LMB).",
                "followupQuestions": ["How do I configure the shooting and zooming actions for a weapon?", "Can I customize the keybindings for shooting and zooming?"],
                "pages": [
                ]
            }
        }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        // Make the API call
        val request = AskRequest(query = "Hi! Is it possible to invert shooting and zooming? Example: LMB = shoot, RMB = zoom")
        val response = gitBookClient.ask(request)

        // Assertions
        assertEquals("Yes, it is possible to invert shooting and zooming. In the provided context, it states that due to Minecraft limitations, using left click to shoot does not work. Instead, you can use right click to shoot. Therefore, you can configure the shooting action to be triggered by the right mouse button (RMB) and the zooming action to be triggered by the left mouse button (LMB).", response.getOrNull()?.answer?.text)
    }

    @Test
    fun `test ask request not enough info`() {
        val responseBody = """
        {
            "answer": null
        }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        // Make the API call
        val request = AskRequest(query = "Hi! Is it possible to invert shooting and zooming? Example: LMB = shoot, RMB = zoom")
        val response = gitBookClient.ask(request)

        // Assertions
        assertEquals(null, response.getOrNull()?.answer)
    }
}
