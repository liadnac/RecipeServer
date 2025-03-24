package sh.deut.recipeapp.model

import sh.deut.recipeapp.db.CategoryDAO
import sh.deut.recipeapp.db.CategoryTable
import sh.deut.recipeapp.db.daoToModel
import sh.deut.recipeapp.db.suspendTransaction

class PostgresCategoryRepository : CategoryRepository {
    override suspend fun allCategories(): List<Category> = suspendTransaction {
        CategoryDAO.all().map(::daoToModel)
    }

    override suspend fun categoryById(id: Int): Category? = suspendTransaction {
        CategoryDAO.findById(id)?.let { daoToModel(it) }
    }
}