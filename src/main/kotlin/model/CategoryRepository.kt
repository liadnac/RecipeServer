package sh.deut.recipeapp.model

interface CategoryRepository {
    suspend fun allCategories(): List<Category>
    suspend fun categoryById(id: Int): Category?
}