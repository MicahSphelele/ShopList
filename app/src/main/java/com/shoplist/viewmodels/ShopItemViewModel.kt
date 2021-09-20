package com.shoplist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.repository.ShopItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopItemViewModel @Inject constructor(private val shopRepository: ShopItemRepository) :
    ViewModel() {

    suspend fun insert(shopItem: ShopItem): Long? {
        return shopRepository.insert(shopItem)
    }

    suspend fun delete(shopItem: ShopItem): Int? {
        return shopRepository.delete(shopItem)
    }

    suspend fun update(shopItem: ShopItem): Int? {
        return shopRepository.update(shopItem)
    }

    fun getTotalEstimationCost(): LiveData<Double>? {
        return shopRepository.getTotalEstimationCost()
    }

    fun getTotalMarkedItems(): LiveData<Int>? {
        return shopRepository.getTotalMarkedItems()
    }

    fun getAllShoppingItems(): LiveData<List<ShopItem>?> {
        return shopRepository.getAllShoppingItems()
    }
}