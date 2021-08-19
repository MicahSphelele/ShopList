package com.shoplist.di

import com.shoplist.ui.adapters.CategoryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    fun provideAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }
}