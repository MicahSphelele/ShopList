package com.shoplist.domain.usecases.shopitems

import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.repository.ShopItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(private val shopItemRepository: ShopItemRepository) {

    operator fun invoke(scope: CoroutineScope, shopItem: ShopItem) {
        scope.launch {
            shopItemRepository.delete(shopItem)
        }
    }
}