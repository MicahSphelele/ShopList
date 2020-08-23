package com.shoplist.mvvm.room.repos

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.shoplist.models.Category
import com.shoplist.mvvm.room.AppDB
import com.shoplist.mvvm.room.doas.CategoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CategoryRepo(application: Application) {

    private var categoryDao : CategoryDao ? = null

    init {
       categoryDao = AppDB.getInstance(application.applicationContext).categoryDao()
    }

    suspend fun insert(category: Category) : Long ? {
        return categoryDao?.insert(category)
    }

    suspend fun update(category: Category) : Int ? {
        return categoryDao?.update(category)
    }

    suspend fun delete(category: Category) : Int ?{
        return categoryDao?.update(category)
    }

    fun getAllCategories() : LiveData<List<Category>>? {

        return categoryDao?.getAllCategories()
    }

    fun getOneCategory(id:Int) : Category?{
       var category: Category? = null
       runBlocking {
           launch(Dispatchers.Default){
               val job = async {
                   categoryDao?.getCategoryById(id)
               }
               category = job.await()
           }
       }
        return category
    }
}