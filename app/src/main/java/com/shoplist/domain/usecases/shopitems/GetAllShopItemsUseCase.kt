package com.shoplist.domain.usecases.shopitems

import androidx.lifecycle.LiveData
import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.repository.ShopItemRepository
import javax.inject.Inject

class GetAllShopItemsUseCase @Inject constructor(private val shopItemRepository: ShopItemRepository) {

    operator fun invoke() : LiveData<List<ShopItem>?> {
        return shopItemRepository.getAllShoppingItems()
    }
}