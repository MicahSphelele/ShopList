package com.shoplist.domain.repository

import androidx.lifecycle.LiveData
import com.shoplist.domain.models.Category

interface CategoryRepository {

    suspend fun insert(category: Category): Long?

    suspend fun update(category: Category): Int?

    suspend fun delete(category: Category): Int?

    suspend fun getOneCategory(id: Int): Category?

    fun getAllCategories(): LiveData<List<Category>?>
}