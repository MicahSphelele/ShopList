package com.shoplist.di

import android.app.Application
import com.shoplist.mvvm.room.repos.CategoryRepository
import com.shoplist.mvvm.room.repos.ShopItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providesCategoryRepository(application: Application): CategoryRepository {
        return CategoryRepository(application)
    }

    @Singleton
    @Provides
    fun providesShopItemRepository(application: Application): ShopItemRepository {
        return ShopItemRepository(application)
    }
}