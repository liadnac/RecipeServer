package sh.deut.recipeapp

import io.ktor.server.application.*
import sh.deut.recipeapp.model.PostgresCategoryRepository
import sh.deut.recipeapp.model.PostgresRecipeRepository
import sh.deut.recipeapp.model.PostgresSubcategoryRepository

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases(environment.config)
    configureRouting(
        categoryRepository = PostgresCategoryRepository(),
        subcategoryRepository = PostgresSubcategoryRepository(),
        recipeRepository = PostgresRecipeRepository()
    )
}
