package com.shoplist.domain.usecases.category

import com.shoplist.domain.models.Category
import com.shoplist.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {

    operator fun invoke(scope: CoroutineScope, id: Int, onGetCategoryById: (Category?) -> Unit) {
        scope.launch {
            onGetCategoryById(categoryRepository.getOneCategory(id))
        }
    }
}