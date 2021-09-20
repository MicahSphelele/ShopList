package com.shoplist.di

import android.content.Context
import com.shoplist.ShopListApplication
import com.shoplist.data.AppDB
import com.shoplist.presentation.ui.adapters.CategoryAdapter
import com.shoplist.presentation.ui.adapters.ShopItemAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext context: Context): ShopListApplication =
        context as ShopListApplication

    @Provides
    fun provideCategoryAdapter(): CategoryAdapter = CategoryAdapter()

    @Provides
    fun provideShopItemAdapter(): ShopItemAdapter = ShopItemAdapter()

    @Provides
    fun providesCoroutinesScope(): CoroutineScope =
        CoroutineScope(CoroutineName("ApplicationScope") + Dispatchers.IO)
}