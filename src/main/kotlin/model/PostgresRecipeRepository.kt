package com.example.model

import com.example.db.RecipeDAO
import com.example.db.daoToModel
import com.example.db.suspendTransaction

class PostgresRecipeRepository: RecipeRepository {
    override suspend fun recipeById(recipeId: Int): Recipe? = suspendTransaction {
        RecipeDAO.findById(recipeId)?.let { daoToModel(it) }
    }
}