package com.shoplist.di

import android.app.Application
import com.shoplist.mvvm.room.repos.CategoryRepo
import com.shoplist.mvvm.room.repos.ShopItemRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Provides
    fun providesCategoryRepo(application: Application): CategoryRepo {
        return CategoryRepo(application)
    }

    @Provides
    fun providesShopItemRepo(application: Application): ShopItemRepo {
        return ShopItemRepo(application)
    }
}