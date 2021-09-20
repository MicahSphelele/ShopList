package com.shoplist.data.repository

import androidx.lifecycle.LiveData
import com.shoplist.data.daos.CategoryDao
import com.shoplist.domain.models.Category
import com.shoplist.domain.repository.interfaces.CategoryRepository
import javax.inject.Inject

class RealCategoryRepository @Inject constructor(private val categoryDao: CategoryDao) : CategoryRepository {

    override suspend fun insert(category: Category): Long? {
        return categoryDao.insert(category)
    }

    override suspend fun update(category: Category): Int? {
        return categoryDao.update(category)
    }

    override suspend fun delete(category: Category): Int? {
        return categoryDao.delete(category)
    }

    override suspend fun getOneCategory(id: Int): Category? {
        return categoryDao.getCategoryById(id)
    }

    override fun getAllCategories(): LiveData<List<Category>?> {
        return categoryDao.getAllCategories()
    }

}