package com.example.model

interface RecipeRepository {
    suspend fun recipeById(recipeId: Int): Recipe?
}