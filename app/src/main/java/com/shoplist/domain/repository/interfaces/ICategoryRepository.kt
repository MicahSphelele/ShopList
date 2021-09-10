package com.shoplist.domain.repository.interfaces

import androidx.lifecycle.LiveData
import com.shoplist.domain.models.Category

interface ICategoryRepository {

    suspend fun insert(category: Category): Long?

    suspend fun update(category: Category): Int?

    suspend fun delete(category: Category): Int?

    suspend fun getOneCategory(id: Int): Category?

    fun getAllCategories(): LiveData<List<Category>>?

}