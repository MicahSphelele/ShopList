package com.shoplist.di

import android.content.Context
import com.shoplist.data.AppDB
import com.shoplist.data.daos.CategoryDao
import com.shoplist.data.daos.ShopItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabaseInstance(@ApplicationContext context: Context) : AppDB = AppDB.getInstance(context)

    @Provides
    fun providesShopItemDao(db: AppDB): ShopItemDao = db.shopItemDao()

    @Provides
    fun providesCategoryDao(db: AppDB): CategoryDao = db.categoryDao()

}