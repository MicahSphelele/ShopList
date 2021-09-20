package com.shoplist.di

import com.shoplist.domain.repository.CategoryRepository
import com.shoplist.domain.repository.ShopItemRepository
import com.shoplist.domain.usecases.category.GetAllCategoriesUseCase
import com.shoplist.domain.usecases.shopitems.*
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

    @Provides
    fun providesInsertShopItemUseCase(shopItemRepository: ShopItemRepository) : InsertShopItemUseCase =
        InsertShopItemUseCase(shopItemRepository)

    @Provides
    fun providesDeleteShopItemUseCase(shopItemRepository: ShopItemRepository) : DeleteShopItemUseCase =
        DeleteShopItemUseCase(shopItemRepository)

    @Provides
    fun providesUpdateShopItemUseCase(shopItemRepository: ShopItemRepository) : UpdateShopItemUseCase =
        UpdateShopItemUseCase(shopItemRepository)

    @Provides
    fun providesGetTotalShopItemCostUseCase(shopItemRepository: ShopItemRepository) : GetTotalShopItemCostUseCase =
        GetTotalShopItemCostUseCase(shopItemRepository)

    @Provides
    fun providesGetTotalMarkedShopItemsUseCase(shopItemRepository: ShopItemRepository) : GetTotalMarkedShopItemsUseCase =
        GetTotalMarkedShopItemsUseCase(shopItemRepository)
}