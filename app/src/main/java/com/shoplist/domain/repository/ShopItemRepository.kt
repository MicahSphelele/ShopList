package com.shoplist.domain.repository

import androidx.lifecycle.LiveData
import com.shoplist.domain.models.ShopItem

interface ShopItemRepository {

    suspend fun insert(shopItem: ShopItem): Long?

    suspend fun delete(shopItem: ShopItem): Int?

    suspend fun update(shopItem: ShopItem): Int?

    fun getAllShoppingItems(): LiveData<List<ShopItem>?>

    fun getTotalEstimationCost(): LiveData<Double>?

    fun getTotalMarkedItems(): LiveData<Int>?
}