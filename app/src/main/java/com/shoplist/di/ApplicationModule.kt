package com.shoplist.di

import com.shoplist.ui.adapters.CategoryAdapter
import com.shoplist.ui.adapters.ShopItemAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    fun provideCategoryAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }

    @Provides
    fun provideShopItemAdapter(): ShopItemAdapter {
        return ShopItemAdapter()
    }
}