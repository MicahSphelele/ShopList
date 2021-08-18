package com.shoplist.mvvm.room.repos

import android.app.Application
import androidx.lifecycle.LiveData
import com.shoplist.models.ShopItem
import com.shoplist.mvvm.room.AppDB
import com.shoplist.mvvm.room.doas.ShopItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ShopItemRepo @Inject constructor(application: Application) {

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