package com.shoplist.mvvm.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shoplist.models.Category
import com.shoplist.mvvm.room.repos.CategoryRepo

class CategoryViewModel() : ViewModel() {

    private var categoryRepo : CategoryRepo ? = null

    fun getAllCategories() : LiveData<List<Category>>? {

        return categoryRepo?.getAllCategories()
    }

     fun getOneCategory(id:Int) : Category?{
        return categoryRepo?.getOneCategory(id)
    }

    fun init(application: Application){
        categoryRepo = CategoryRepo(application)
    }


}