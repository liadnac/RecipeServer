package sh.deut.recipeapp

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import sh.deut.recipeapp.model.CategoryRepository
import sh.deut.recipeapp.model.Recipe
import sh.deut.recipeapp.model.RecipeRepository
import sh.deut.recipeapp.model.SubcategoryRepository
import java.io.File
import java.security.SecureRandom

fun generateSecureRandomPassword(length: Int = 16): String {
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val random = SecureRandom()
    return (1..length).map { characters[random.nextInt(characters.length)] }.joinToString("")
}

fun Application.configureRouting(
    categoryRepository: CategoryRepository,
    subcategoryRepository: SubcategoryRepository,
    recipeRepository: RecipeRepository
) {
    install(StatusPages) {
    }

    // Generate a secure random password
    val username = "admin"
    val password = generateSecureRandomPassword() // Generate a random password

    // Log the credentials
    environment.log.info("Generated credentials -> Username: $username, Password: $password")


    install(Authentication) {
        basic("auth-basic") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == username && credentials.password == password) {
                    UserIdPrincipal(credentials.name)
                } else null
            }
        }
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

        route("/subcategories/{subcategoryId}/recipes") {
            get() {
                val id = call.parameters["subcategoryId"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val recipes = subcategoryRepository.recipesBySubcategoryId(id.toInt())
                call.respond(recipes)
            }
            authenticate("auth-basic") {
                post() {
                    val id = call.parameters["subcategoryId"]
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest)
                        return@post
                    }
                    val requestBody = call.receive<Recipe>()
                    recipeRepository.addRecipe(requestBody, id.toInt())
                    call.respond(HttpStatusCode.Created)
                }
            }
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
