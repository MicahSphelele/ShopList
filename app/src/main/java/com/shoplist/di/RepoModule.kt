package com.shoplist.di

import android.app.Application
import com.shoplist.mvvm.room.repos.CategoryRepo
import com.shoplist.mvvm.room.repos.ShopItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Singleton
    @Provides
    fun providesCategoryRepo(application: Application): CategoryRepo {
        return CategoryRepo(application)
    }

    @Singleton
    @Provides
    fun providesShopItemRepository(application: Application): ShopItemRepository {
        return ShopItemRepository(application)
    }
}