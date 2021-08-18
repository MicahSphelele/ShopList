package com.shoplist.mvvm.room.repos

import android.app.Application
import androidx.lifecycle.LiveData
import com.shoplist.models.Category
import com.shoplist.mvvm.room.AppDB
import com.shoplist.mvvm.room.doas.CategoryDao
import javax.inject.Inject

class CategoryRepo @Inject constructor(application: Application) {

    private var categoryDao: CategoryDao? = null

    init {
        categoryDao = AppDB.getInstance(application.applicationContext).categoryDao()
    }

    suspend fun insert(category: Category): Long? {
        return categoryDao?.insert(category)
    }

    suspend fun update(category: Category): Int? {
        return categoryDao?.update(category)
    }

    suspend fun delete(category: Category): Int? {
        return categoryDao?.update(category)
    }

    fun getAllCategories(): LiveData<List<Category>>? {

        return categoryDao?.getAllCategories()
    }

   suspend fun getOneCategory(id: Int): Category? {

        return categoryDao?.getCategoryById(id)
   }
}