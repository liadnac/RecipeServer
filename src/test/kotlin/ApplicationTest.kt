package com.example

import com.example.model.Category
import com.example.model.FakeCategoryRepository
import com.example.model.FakeRecipeRepository
import com.example.model.FakeSubcategoryRepository
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testCategoriesReturnsListOfCategories() = testApplication {
        application {
            val categoryRepository = FakeCategoryRepository()
            val subcategoryRepository = FakeSubcategoryRepository()
            val recipeRepository = FakeRecipeRepository()
            configureSerialization(categoryRepository, subcategoryRepository, recipeRepository)
            configureRouting()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.get("/categories")
        val results = response.body<List<Category>>()

        assertEquals(HttpStatusCode.OK, response.status)

        val expectedNames = listOf("Kids", "Meals")
        val actualNames = results.map { it.name }
        assertContentEquals(expectedNames, actualNames)

    }

}
