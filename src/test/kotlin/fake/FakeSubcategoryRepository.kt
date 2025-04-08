package sh.deut.recipeapp.fake

import sh.deut.recipeapp.model.PartialRecipe
import sh.deut.recipeapp.model.SubCategory
import sh.deut.recipeapp.model.SubcategoryRepository
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class FakeSubcategoryRepository : SubcategoryRepository {
    val subcategoryRecipeList: List<PartialRecipe> = listOf(
        PartialRecipe(
            12,
            "Pumpkin Pie",
            "http://10.0.2.2:8080/static/RecipeImages/ChocolatePancakes.jpg",
            150.toDuration(DurationUnit.MINUTES),
            13
        ),
        PartialRecipe(
            22,
            "Chocolate Pancakes",
            "http://10.0.2.2:8080/static/RecipeImages/ChocolatePancakes.jpg",
            30.toDuration(DurationUnit.MINUTES),
            12
        ),
        PartialRecipe(
            32,
            "Pumpkin Pancakes",
            "http://10.0.2.2:8080/static/RecipeImages/PumpkinPancakes.jpg",
            30.toDuration(DurationUnit.MINUTES),
            12
        ),
        PartialRecipe(
            41,
            "Fluffy Eggs",
            "http://10.0.2.2:8080/static/RecipeImages/ChocolatePancakes.jpg",
            30.toDuration(DurationUnit.MINUTES),
            14
        ),
        PartialRecipe(
            42,
            "Baked Oatmeal",
            "http://10.0.2.2:8080/static/RecipeImages/ChocolatePancakes.jpg",
            30.toDuration(DurationUnit.MINUTES),
            14
        ),
    )

    override suspend fun recipesBySubcategoryId(id: Int): List<PartialRecipe> =
        subcategoryRecipeList.filter { it.subcategoryId == id }

    override suspend fun addSubcategory(subcategory: SubCategory, categoryId: Int) {
        // No op
    }
}