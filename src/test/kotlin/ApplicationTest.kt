package sh.deut.recipeapp

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import sh.deut.recipeapp.fake.FakeCategoryRepository
import sh.deut.recipeapp.fake.FakeRecipeRepository
import sh.deut.recipeapp.fake.FakeSubcategoryRepository
import sh.deut.recipeapp.model.Category
import sh.deut.recipeapp.model.PartialRecipe
import sh.deut.recipeapp.model.Recipe
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

    @Test
    fun testCategoriesIDReturnsSpecificCategory() = testApplication {
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
        val response = client.get("/categories/1")
        val results = response.body<Category>()

        assertEquals(HttpStatusCode.OK, response.status)

        val expectedName = "Kids"
        val actualName = results.name
        assertEquals(expectedName, actualName)
    }

    @Test
    fun testSubcategoriesRecipeReturnsListOfRecipes() = testApplication {
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
        val response = client.get("/subcategories/13/recipes")
        val results = response.body<List<PartialRecipe>>()

        assertEquals(HttpStatusCode.OK, response.status)

        val expectedName = "Pumpkin Pie"
        val actualName = results[0].name
        assertEquals(expectedName, actualName)

        assertEquals(results.size, 1)
    }

    @Test
    fun testRecipesIDReturnsSpecificRecipe() = testApplication {
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
        val response = client.get("/recipes/22")
        val results = response.body<Recipe>()

        assertEquals(HttpStatusCode.OK, response.status)

        val expectedName = "Chocolate Pancakes"
        val actualName = results.name
        assertEquals(expectedName, actualName)
    }
}
