package com.shoplist.domain.usecases.category

import androidx.lifecycle.LiveData
import com.shoplist.domain.models.Category
import com.shoplist.domain.repository.interfaces.CategoryRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {

    operator fun invoke() : LiveData<List<Category>?> {
        return categoryRepository.getAllCategories()
    }
}