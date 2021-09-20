package com.shoplist.di

import com.shoplist.domain.repository.CategoryRepository
import com.shoplist.domain.repository.ShopItemRepository
import com.shoplist.domain.usecases.category.GetAllCategoriesUseCase
import com.shoplist.domain.usecases.shopitems.GetAllShopItemsUseCase
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

    @Provides
    fun providesGetAllShopItemsUseCase(shopItemRepository: ShopItemRepository) : GetAllShopItemsUseCase =
        GetAllShopItemsUseCase(shopItemRepository)
}