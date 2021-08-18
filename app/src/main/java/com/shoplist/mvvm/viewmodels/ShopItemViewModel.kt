package com.shoplist.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shoplist.models.ShopItem
import com.shoplist.mvvm.room.repos.ShopItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopItemViewModel @Inject constructor(private val shopRepo: ShopItemRepository) :
    ViewModel() {

    suspend fun insert(shopItem: ShopItem): Long? {
        return shopRepo.insert(shopItem)
    }

    suspend fun delete(shopItem: ShopItem): Int? {
        return shopRepo.delete(shopItem)
    }

    suspend fun update(shopItem: ShopItem): Int? {
        return shopRepo.update(shopItem)
    }

    fun getTotalEstimationCost(): LiveData<Double>? {
        return shopRepo.getTotalEstimationCost()
    }

    fun getTotalMarkedItems(): LiveData<Int>? {
        return shopRepo.getTotalMarkedItems()
    }

    fun getAllShoppingItems(): LiveData<List<ShopItem>>? {
        return shopRepo.getAllShoppingItems()
    }
}