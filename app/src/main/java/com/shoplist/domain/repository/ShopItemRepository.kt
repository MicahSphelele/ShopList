package com.shoplist.domain.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.shoplist.domain.models.ShopItem
import com.shoplist.data.AppDB
import com.shoplist.data.daos.ShopItemDao
import javax.inject.Inject

class ShopItemRepository @Inject constructor(application: Application) {

    private var shopItemDao : ShopItemDao? = null

    init {
        shopItemDao = AppDB.getInstance(application.applicationContext).shopItemDao()
    }

   suspend fun insert(shopItem: ShopItem) : Long? {

        return shopItemDao?.insert(shopItem)
    }

   suspend fun delete(shopItem: ShopItem) : Int? {

        return shopItemDao?.delete(shopItem)
    }

   suspend fun update(shopItem: ShopItem) : Int? {

        return shopItemDao?.update(shopItem)
    }

    fun getAllShoppingItems() : LiveData<List<ShopItem>>?{

        return shopItemDao?.getAllShoppingItems()
    }

    fun getTotalEstimationCost() : LiveData<Double>?{

        return shopItemDao?.getTotalEstimationCost()
    }

    fun getTotalMarkedItems() : LiveData<Int>?{

        return shopItemDao?.getTotalMarkedItems()
    }
}