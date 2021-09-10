package com.shoplist.di

import android.content.Context
import com.shoplist.ShopListApplication
import com.shoplist.ui.adapters.CategoryAdapter
import com.shoplist.ui.adapters.ShopItemAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext context: Context) : ShopListApplication {
        return context as ShopListApplication
    }

    @Provides
    fun provideCategoryAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }

    @Provides
    fun provideShopItemAdapter(): ShopItemAdapter {
        return ShopItemAdapter()
    }
}