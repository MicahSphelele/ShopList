package com.shoplist.domain.usecases.shopitems

import androidx.lifecycle.LiveData
import com.shoplist.domain.repository.ShopItemRepository
import javax.inject.Inject

class GetTotalShopItemCostUseCase @Inject constructor(private val shopItemRepository: ShopItemRepository) {

    operator fun invoke() : LiveData<Double>? {
        return shopItemRepository.getTotalEstimationCost()
    }
}