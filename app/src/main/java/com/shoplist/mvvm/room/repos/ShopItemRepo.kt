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

    fun insert(shopItem: ShopItem) : Long? {
        var insert : Long? = null
        runBlocking {
            launch(Dispatchers.Default){
                val job = async {shopItemDao?.insert(shopItem) }
                insert = job.await()
            }

        }
        return insert
    }

    fun delete(shopItem: ShopItem) : Int? {
        var delete : Int? = null
        runBlocking {
            launch(Dispatchers.Default) {
                val job = async { shopItemDao?.delete(shopItem) }
                delete = job.await()
            }
        }
        return delete
    }

     fun update(shopItem: ShopItem) : Int? {
         var update : Int? = null
         runBlocking {
             val  job = async { shopItemDao?.update(shopItem) }
             update = job.await()
         }
        return update
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