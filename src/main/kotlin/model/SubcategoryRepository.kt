package com.example.model

interface SubcategoryRepository {
    suspend fun recipesBySubcategoryId(id: Int): List<PartialRecipe>
}