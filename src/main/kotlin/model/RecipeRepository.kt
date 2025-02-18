package com.example.model

import kotlin.time.DurationUnit
import kotlin.time.toDuration

object RecipeRepository {
    private val chocolatePancakeRecipe: Recipe = Recipe(
        id = 22,
        name = "Chocolate Pancakes",
        imgUrl = "http://10.0.2.2:8080/static/RecipeImages/ChocolatePancakes.jpg",
        description = "Nice chocolaty pancakes for little ones and their parents :)",
        cookTime = CookTime(15.toDuration(DurationUnit.MINUTES), 30.toDuration(DurationUnit.MINUTES)),
        recipeParts = listOf(
            RecipePart(
                name = "Pancake",
                ingredients = listOf(
                    "1 cup(240 ml) milk of your choice",
                    "3 tablespoons(45g) melted coconut oil",
                    "2 tablespoons(12.5g) cocoa powder",
                    "1 cup(140g) flour"
                ),
                instructions = listOf(
                    "Add all the ingredients in the listed order and mix till mostly lump free.",
                    "Spoon dollops onto a heated skillet..."
                )
            )
        ),
        subcategoryId = 12
    )
    private val fullRecipeList: List<Recipe> = listOf(chocolatePancakeRecipe)
    fun recipeById(recipeId: Int): Recipe? = fullRecipeList.find { it.id == recipeId }
}