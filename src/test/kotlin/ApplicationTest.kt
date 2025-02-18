package com.example

import com.example.model.Category
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    @Test
    fun testCategoryCanBeFoundById() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.get("/categories/1")
        val results = response.body<Category>()
            assertEquals(HttpStatusCode.OK, response.status)

        val expectedCategoryName = "Kids"
        val actualCategoryName = results.name
            assertEquals(expectedCategoryName, actualCategoryName)
    }

    @Test
    fun unusedCategoryIdProduces404() = testApplication {
        application {
            module()
        }
        val response = client.get("/categories/5")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}
