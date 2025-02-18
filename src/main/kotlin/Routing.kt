package com.example

import com.example.model.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        staticFiles("/static", File("static"))

        route("/categories") {
            get {
                val categories = CategoryRepository.allCategories()
                call.respond(categories)
            }
            get("/{id}") {
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val category = CategoryRepository.categoryById(id.toInt())
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
            val recipes = SubcategoryRepository.recipesBySubcategoryId(id.toInt())
            call.respond(recipes)
        }

        get("/recipes/{recipeId}") {
            val id = call.parameters["recipeId"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val recipe = RecipeRepository.recipeById(id.toInt())
            if (recipe == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(recipe)
        }
    }
}
