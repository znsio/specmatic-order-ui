package controllers

import org.json.JSONObject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import `in`.specmatic.stub.ContractStub
import `in`.specmatic.stub.createStub
import org.json.JSONArray
import java.net.URI
import kotlin.test.assertEquals

class APITests {
    @Test
    fun `search for available products of type gadget`() {
        val apiClient = RestTemplate()
        val response = apiClient.getForEntity(URI.create("http://localhost:8080/findAvailableProducts?type=gadget"), String::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        val gadgets = JSONArray(response.body)

        val firstGadget = gadgets.getJSONObject(0)

        assertEquals(10, firstGadget.getInt("id"))
        assertEquals("XYZ Laptop", firstGadget.getString("name"))
    }

    @Test
    fun `create order`() {
        val requestBody = """{"productid": 10, "count": 1}"""

        val apiClient = RestTemplate()
        val url = URI.create("http://localhost:8080/addOrder")
        val requestEntity = authenticatedJsonRequest(url, requestBody)
        val responseEntity = apiClient.postForEntity<String>(url, requestEntity)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        val jsonResponse = JSONObject(responseEntity.body)
        assertEquals("success", jsonResponse.getString("status"))
        assertEquals(10, jsonResponse.getInt("id"))
    }

    private fun authenticatedJsonRequest(url: URI, requestBody: String): RequestEntity<String> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return RequestEntity(requestBody, headers, HttpMethod.PUT, url)
    }

    companion object {
        private var service: ConfigurableApplicationContext? = null

        private lateinit var stub: ContractStub

        @BeforeAll
        @JvmStatic
        fun setUp() {
            stub = createStub()
            service = SpringApplication.run(Application::class.java)
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            service?.stop()
            stub.close()
        }
    }
}
