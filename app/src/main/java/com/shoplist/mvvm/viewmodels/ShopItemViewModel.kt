package com.shoplist.mvvm.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shoplist.models.ShopItem
import com.shoplist.mvvm.room.repos.ShopItemRepo

class ShopItemViewModel(private val application: Application) : ViewModel(), ViewModelProvider.Factory {

    private var shopRepo : ShopItemRepo? = null

    init {
        shopRepo = ShopItemRepo(application)
    }

    fun insert(shopItem: ShopItem) : Long? {
        return shopRepo?.insert(shopItem)
    }

    fun delete(shopItem: ShopItem) : Int?{
        return shopRepo?.delete(shopItem)
    }

    fun update(shopItem: ShopItem) : Int?{
        return shopRepo?.update(shopItem)
    }

    fun getTotalEstimationCost() : LiveData<Double>?{
        return shopRepo?.getTotalEstimationCost()
    }

    fun getTotalMarkedItems() : LiveData<Int>?{
        return shopRepo?.getTotalMarkedItems()
    }

    fun getAllShoppingItems() : LiveData<List<ShopItem>>?{
        return shopRepo?.getAllShoppingItems()
    }


    fun init(application: Application){
        shopRepo = ShopItemRepo(application)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ShopItemViewModel(application) as T
    }
}