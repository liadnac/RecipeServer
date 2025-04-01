package sh.deut.recipeapp

import sh.deut.recipeapp.model.CategoryRepository
import sh.deut.recipeapp.model.RecipeRepository
import sh.deut.recipeapp.model.SubcategoryRepository
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

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }

}
