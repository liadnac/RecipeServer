package sh.deut.recipeapp

import sh.deut.recipeapp.model.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization(
        categoryRepository = PostgresCategoryRepository(),
        subcategoryRepository = PostgresSubcategoryRepository(),
        recipeRepository = PostgresRecipeRepository()
    )
    configureDatabases(environment.config)
    configureRouting()
}
