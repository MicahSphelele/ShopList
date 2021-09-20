package com.shoplist.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shoplist.domain.models.Category
import com.shoplist.util.Constants

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category) : Long

    @Update
    suspend fun update(category: Category) : Int

    @Delete
    suspend fun delete(category: Category) : Int

    @Query("SELECT * FROM ${Constants.CATEGORY_TABLE}")
    fun getAllCategories() : LiveData<List<Category>>

    @Query("SELECT * FROM ${Constants.CATEGORY_TABLE} WHERE categoryId=:id")
    suspend fun getCategoryById(id:Int) : Category

}
