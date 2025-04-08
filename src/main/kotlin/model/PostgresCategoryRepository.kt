package sh.deut.recipeapp.model

import sh.deut.recipeapp.db.CategoryDAO
import sh.deut.recipeapp.db.SubcategoryDAO
import sh.deut.recipeapp.db.daoToModel
import sh.deut.recipeapp.db.suspendTransaction

class PostgresCategoryRepository : CategoryRepository {
    override suspend fun allCategories(): List<Category> = suspendTransaction {
        CategoryDAO.all().map(::daoToModel)
    }

    override suspend fun categoryById(id: Int): Category? = suspendTransaction {
        CategoryDAO.findById(id)?.let { daoToModel(it) }
    }

    override suspend fun addCategory(category: Category): Unit = suspendTransaction {
        val dao = CategoryDAO.new {
            name = category.name
            imgUrl = category.imgUrl
        }
        category.subcategoryList.forEach { subcategory ->
            SubcategoryDAO.new {
                name = subcategory.name
                this.categoryId = dao
            }
        }
    }
}