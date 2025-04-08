package sh.deut.recipeapp.model

import sh.deut.recipeapp.db.*
import kotlin.time.DurationUnit

class PostgresRecipeRepository : RecipeRepository {
    override suspend fun recipeById(recipeId: Int): Recipe? = suspendTransaction {
        RecipeDAO.findById(recipeId)?.let { daoToModel(it) }
    }

    override suspend fun addRecipe(recipe: Recipe, subcategoryId: Int) = suspendTransaction {
        val subcategory = SubcategoryDAO.findById(subcategoryId)
            ?: throw IllegalArgumentException("Subcategory with id $subcategoryId not found.")
        val dao = RecipeDAO.new {
            name = recipe.name
            imgUrl = recipe.imgUrl
            description = recipe.description
            handsOn = recipe.cookTime.handsOn.toInt(DurationUnit.MINUTES)
            readyIn = recipe.cookTime.readyIn.toInt(DurationUnit.MINUTES)
            this.subcategoryId = subcategory
        }
        recipe.recipeParts.forEach { recipePart ->
            RecipePartDAO.new {
                name = recipePart.name
                ingredients = recipePart.ingredients.joinToString("||")
                instructions = recipePart.instructions.joinToString("||")
                this.recipeId = dao
            }
        }
    }
}