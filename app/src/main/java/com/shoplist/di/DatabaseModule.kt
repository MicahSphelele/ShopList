package com.shoplist.di

import android.content.Context
import com.shoplist.data.AppDB
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    fun providesDatabaseInstance(@ApplicationContext context: Context) : AppDB = AppDB.getInstance(context)
}