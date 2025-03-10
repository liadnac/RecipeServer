package com.example.model

class FakeCategoryRepository: CategoryRepository {
    private val categories = listOf(
        Category(
            1,
            "Kids",
            "http://10.0.2.2:8080/static/CategoryImages/KidsImg.jpg",
            listOf(SubCategory(12, "Pancakes"))
        ),
        Category(
            2,
            "Meals",
            "http://10.0.2.2:8080/static/CategoryImages/MealsImg.jpg",
            listOf(SubCategory(14, "Breakfast"))
        )
    )

    override suspend fun allCategories(): List<Category> = categories

    override suspend fun categoryById(id: Int) = categories.find { it.id == id }
}