package com.example.model

import com.example.db.RecipeDAO
import com.example.db.RecipeTable
import com.example.db.daoToModel
import com.example.db.suspendTransaction
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class PostgresSubcategoryRepository : SubcategoryRepository {
    override suspend fun recipesBySubcategoryId(id: Int): List<PartialRecipe> = suspendTransaction {
         RecipeDAO.find { RecipeTable.subcategoryId eq id }.map{
             PartialRecipe(
                 id = it.id.value,
                 name = it.name,
                 imgUrl = it.imgUrl,
                 cookTime = it.readyIn.toDuration(DurationUnit.MINUTES),
                 subcategoryId = it.subcategoryId.id.value,
             )
         }
    }


}