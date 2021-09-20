package com.shoplist.data.repository

import androidx.lifecycle.LiveData
import com.shoplist.data.daos.ShopItemDao
import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.repository.interfaces.ShopItemRepository
import javax.inject.Inject

class RealShopItemRepository @Inject constructor(private val shopItemDao: ShopItemDao) :
    ShopItemRepository {

    override suspend fun insert(shopItem: ShopItem): Long? {
        return shopItemDao.insert(shopItem)
    }

    override suspend fun delete(shopItem: ShopItem): Int? {
        return shopItemDao.delete(shopItem)
    }

    override suspend fun update(shopItem: ShopItem): Int? {
        return shopItemDao.update(shopItem)
    }

    override fun getAllShoppingItems(): LiveData<List<ShopItem>?> {
        return shopItemDao.getAllShoppingItems()
    }

    override fun getTotalEstimationCost(): LiveData<Double>? {
        return shopItemDao.getTotalEstimationCost()
    }

    override fun getTotalMarkedItems(): LiveData<Int>? {
        return shopItemDao.getTotalMarkedItems()
    }

}