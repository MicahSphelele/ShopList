package com.shoplist.mvvm.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shoplist.models.Category
import com.shoplist.mvvm.room.repos.CategoryRepo

class CategoryViewModel(private val application: Application) : ViewModel(), ViewModelProvider.Factory {

    private var categoryRepo : CategoryRepo ? = null

    init {
        categoryRepo = CategoryRepo(application)
    }

    fun getAllCategories() : LiveData<List<Category>>? {

        return categoryRepo?.getAllCategories()
    }

     fun getCategoryById(id:Int) : Category?{
        return categoryRepo?.getOneCategory(id)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CategoryViewModel(application) as T
    }

}