package com.shoplist.domain.usecases.shopitems

import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.repository.ShopItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateShopItemUseCase @Inject constructor(private val shopItemRepository: ShopItemRepository) {

    operator fun invoke(scope: CoroutineScope, shopItem: ShopItem, onShopItemUpdated: () -> Unit) {
        scope.launch {
            val updated = shopItemRepository.update(shopItem)
            if (updated != null) {
                onShopItemUpdated()
            }
        }
    }

}