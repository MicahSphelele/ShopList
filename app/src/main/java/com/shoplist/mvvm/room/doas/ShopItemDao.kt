package com.shoplist.mvvm.room.doas

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shoplist.models.ShopItem
import com.shoplist.util.Constants

@Dao
interface ShopItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shopItem: ShopItem) : Long

    @Update
    suspend fun update(shopItem: ShopItem) : Int

    @Delete
    suspend fun delete(shopItem: ShopItem) : Int

    @Query("SELECT * FROM ${Constants.SHOP_TABLE}")
    fun getAllShoppingItems() : LiveData<List<ShopItem>>

    @Query("SELECT SUM(${Constants.SHOP_ITEM_COST}*${Constants.SHOP_QUANTITY}) FROM ${Constants.SHOP_TABLE}")
    fun getTotalEstimationCost() : LiveData<Double>

    @Query("SELECT SUM(${Constants.SHOP_IS_MARKED}) FROM ${Constants.SHOP_TABLE}")
    fun getTotalMarkedItems() : LiveData<Int>
}