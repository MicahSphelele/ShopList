package com.shoplist.mvvm.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shoplist.models.Category
import com.shoplist.mvvm.room.repos.CategoryRepo

class CategoryViewModel @ViewModelInject constructor(private val categoryRepo : CategoryRepo) : ViewModel(){

    fun getAllCategories() : LiveData<List<Category>>? {

        return categoryRepo.getAllCategories()
    }

     fun getCategoryById(id:Int) : Category?{
        return categoryRepo.getOneCategory(id)
    }


}