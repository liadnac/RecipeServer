package com.example.model

import com.example.db.CategoryDAO
import com.example.db.CategoryTable
import com.example.db.daoToModel
import com.example.db.suspendTransaction

class PostgresCategoryRepository : CategoryRepository {
    override suspend fun allCategories(): List<Category> = suspendTransaction {
        CategoryDAO.all().map(::daoToModel)
    }

    override suspend fun categoryById(id: Int): Category? = suspendTransaction {
        CategoryDAO.findById(id)?.let { daoToModel(it) }
    }
}