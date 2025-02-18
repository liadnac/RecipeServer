package com.example

import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*


class ApplicationJsonPathTest {
    @Test
    fun categoriesCanBeFound() = testApplication {
        application {
            module()
        }
        val jsonDoc = client.getAsJsonPath("/categories")

        val result: List<String> = jsonDoc.read("$[*].name")
        assertEquals("Kids", result[0])
        assertEquals("Meals", result[1])
    }

    @Test
    fun categoryCanBeFoundById() = testApplication {
        application {
            module()
        }
        val id = 1
        val jsonDoc = client.getAsJsonPath("/categories/$id")

        val result: List<String> =
            jsonDoc.read("$[?(@.id == '$id')].name")
        assertEquals(1, result.size)

        assertEquals("Kids", result[0])
    }

    suspend fun HttpClient.getAsJsonPath(url: String): DocumentContext {
        val response = this.get(url) {
            accept(ContentType.Application.Json)
        }
        return JsonPath.parse(response.bodyAsText())
    }
}