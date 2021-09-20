package com.shoplist.di

import com.shoplist.domain.repository.interfaces.CategoryRepository
import com.shoplist.domain.usecases.category.GetAllCategoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun providesGetAllCategoriesUseCase(categoryRepository: CategoryRepository) : GetAllCategoriesUseCase =
        GetAllCategoriesUseCase(categoryRepository)
}