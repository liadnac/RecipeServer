package sh.deut.recipeapp.model

import sh.deut.recipeapp.db.RecipeDAO
import sh.deut.recipeapp.db.RecipeTable
import sh.deut.recipeapp.db.daoToModel
import sh.deut.recipeapp.db.suspendTransaction
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