package sh.deut.recipeapp.model

import sh.deut.recipeapp.db.RecipeDAO
import sh.deut.recipeapp.db.daoToModel
import sh.deut.recipeapp.db.suspendTransaction

class PostgresRecipeRepository: RecipeRepository {
    override suspend fun recipeById(recipeId: Int): Recipe? = suspendTransaction {
        RecipeDAO.findById(recipeId)?.let { daoToModel(it) }
    }
}