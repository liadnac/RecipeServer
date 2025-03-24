package sh.deut.recipeapp.model

interface RecipeRepository {
    suspend fun recipeById(recipeId: Int): Recipe?
}