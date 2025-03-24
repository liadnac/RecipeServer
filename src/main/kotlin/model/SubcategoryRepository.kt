package sh.deut.recipeapp.model

interface SubcategoryRepository {
    suspend fun recipesBySubcategoryId(id: Int): List<PartialRecipe>
}