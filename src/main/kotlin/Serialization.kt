package com.example

import com.example.model.CategoryRepository
import com.example.model.RecipeRepository
import com.example.model.SubcategoryRepository
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.DriverManager
import org.jetbrains.exposed.sql.*
import java.io.File

fun Application.configureSerialization(
    categoryRepository: CategoryRepository,
    subcategoryRepository: SubcategoryRepository,
    recipeRepository: RecipeRepository
) {
    install(ContentNegotiation) {
        json()
    }
    routing {
        staticFiles("/static", File("static"))
        route("/categories") {
            get {
                val categories = categoryRepository.allCategories()
                call.respond(categories)
            }
            get("/{id}") {
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val category = categoryRepository.categoryById(id.toInt())
                if (category == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(category)
            }
        }

        get("/subcategories/{subcategoryId}/recipes") {
            val id = call.parameters["subcategoryId"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val recipes = subcategoryRepository.recipesBySubcategoryId(id.toInt())
            call.respond(recipes)
        }

        get("/recipes/{recipeId}") {
            val id = call.parameters["recipeId"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val recipe = recipeRepository.recipeById(id.toInt())
            if (recipe == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(recipe)
        }
    }
}
