package com.shoplist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shoplist.domain.models.Category
import com.shoplist.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel() {

    fun getAllCategories(): LiveData<List<Category>?> {

        return categoryRepository.getAllCategories()
    }

    suspend fun getCategoryById(id: Int): Category? {
        
        return categoryRepository.getOneCategory(id)
    }
}