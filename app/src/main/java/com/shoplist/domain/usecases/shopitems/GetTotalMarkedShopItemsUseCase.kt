package com.shoplist.domain.usecases.shopitems

import androidx.lifecycle.LiveData
import com.shoplist.domain.repository.ShopItemRepository
import javax.inject.Inject

class GetTotalMarkedShopItemsUseCase @Inject constructor(private val shopItemRepository: ShopItemRepository) {

    operator fun invoke() : LiveData<Int>? {
        return shopItemRepository.getTotalMarkedItems()
    }
}