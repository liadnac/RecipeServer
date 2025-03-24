package sh.deut.recipeapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val subcategoryList: List<SubCategory>
)

@Serializable
data class SubCategory(
    val id: Int,
    val name: String,
)