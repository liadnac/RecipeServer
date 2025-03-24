package sh.deut.recipeapp.db

import sh.deut.recipeapp.model.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.time.DurationUnit
import kotlin.time.toDuration

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: CategoryDAO) = Category(
    id = dao.id.value,
    name = dao.name,
    imgUrl = dao.imgUrl,
    subcategoryList = dao.subcategories.map { daoToModel(it) }.toList(),
)

fun daoToModel(dao: SubcategoryDAO) = SubCategory(
    id = dao.id.value,
    name = dao.name,
)

fun daoToModel(dao: RecipeDAO) = Recipe(
    id = dao.id.value,
    name = dao.name,
    imgUrl = dao.imgUrl,
    description = dao.description,
    cookTime = CookTime(dao.handsOn.toDuration(DurationUnit.MINUTES), dao.readyIn.toDuration(DurationUnit.MINUTES)),
    recipeParts = dao.recipeParts.map { daoToModel(it) }.toList(),
    subcategoryId = dao.subcategoryId.id.value
)

fun daoToModel(dao: RecipePartDAO) = RecipePart(
    name = dao.name,
    ingredients = dao.ingredients.split("||"),
    instructions = dao.instructions.split("||")
)

object CategoryTable : IntIdTable("category") {
    val name = varchar("name", 50)
    val imgUrl = varchar("img_url", 500)
}

class CategoryDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryDAO>(CategoryTable)

    var name by CategoryTable.name
    var imgUrl by CategoryTable.imgUrl
    val subcategories by SubcategoryDAO referrersOn SubcategoryTable.categoryId
}

object SubcategoryTable : IntIdTable("subcategory") {
    val name = varchar("name", 50)
    val categoryId = reference("category_id", CategoryTable, onDelete = ReferenceOption.CASCADE)
}

class SubcategoryDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SubcategoryDAO>(SubcategoryTable)

    var name by SubcategoryTable.name
    var categoryId by CategoryDAO referencedOn SubcategoryTable.categoryId
}

object RecipeTable : IntIdTable("recipe") {
    val name = varchar("name", 50)
    val imgUrl = text("img_url")
    val description = text("description")
    val handsOn = integer("hands_on")
    val readyIn = integer("ready_in")
    val subcategoryId = reference("subcategory_id", SubcategoryTable, onDelete = ReferenceOption.CASCADE)
}

class RecipeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeDAO>(RecipeTable)

    var name by RecipeTable.name
    var imgUrl by RecipeTable.imgUrl
    var description by RecipeTable.description
    var handsOn by RecipeTable.handsOn
    var readyIn by RecipeTable.readyIn
    var subcategoryId by SubcategoryDAO referencedOn RecipeTable.subcategoryId
    val recipeParts by RecipePartDAO referrersOn RecipePartTable.recipeId
}

object RecipePartTable : IntIdTable("recipe_part") {
    val name = varchar("name", 50)
    val ingredients = text("ingredients")
    val instructions = text("instructions")
    val recipeId = reference("recipe_id", RecipeTable, onDelete = ReferenceOption.CASCADE)
}

class RecipePartDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipePartDAO>(RecipePartTable)

    var name by RecipePartTable.name
    var ingredients by RecipePartTable.ingredients
    var instructions by RecipePartTable.instructions
    var recipeId by RecipeDAO referencedOn RecipePartTable.recipeId
}