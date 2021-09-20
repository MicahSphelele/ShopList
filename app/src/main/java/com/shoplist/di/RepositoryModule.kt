package com.shoplist.di

import android.app.Application
import com.shoplist.data.daos.CategoryDao
import com.shoplist.data.repository.RealCategoryRepository
import com.shoplist.domain.repository.ShopItemRepository
import com.shoplist.domain.repository.interfaces.CategoryRepository
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
    fun providesCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return RealCategoryRepository(categoryDao)
    }

    @Singleton
    @Provides
    fun providesShopItemRepository(application: Application): ShopItemRepository {
        return ShopItemRepository(application)
    }
}