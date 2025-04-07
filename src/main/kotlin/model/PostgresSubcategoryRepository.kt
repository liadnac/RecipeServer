package sh.deut.recipeapp.model

import sh.deut.recipeapp.db.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class PostgresSubcategoryRepository : SubcategoryRepository {
    override suspend fun recipesBySubcategoryId(id: Int): List<PartialRecipe> = suspendTransaction {
        RecipeDAO.find { RecipeTable.subcategoryId eq id }.map {
            PartialRecipe(
                id = it.id.value,
                name = it.name,
                imgUrl = it.imgUrl,
                cookTime = it.readyIn.toDuration(DurationUnit.MINUTES),
                subcategoryId = it.subcategoryId.id.value,
            )
        }
    }

    override suspend fun addSubcategory(subcategory: SubCategory, categoryId: Int): Unit = suspendTransaction {
        val category = CategoryDAO.findById(categoryId)
            ?: throw IllegalArgumentException("Category with id $categoryId not found.")

        SubcategoryDAO.new {
            name = subcategory.name
            this.categoryId = category
        }
    }


}