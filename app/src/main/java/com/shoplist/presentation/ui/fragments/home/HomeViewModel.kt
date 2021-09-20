package com.shoplist.presentation.ui.fragments.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.usecases.shopitems.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllShopItemsUseCase: GetAllShopItemsUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val updateShopItemUseCase: UpdateShopItemUseCase,
    private val getTotalShopItemCostUseCase: GetTotalShopItemCostUseCase,
    private val getTotalMarkedShopItemsUseCase: GetTotalMarkedShopItemsUseCase
) : ViewModel() {

    fun getAllShopItems(
        lifecycleOwner: LifecycleOwner,
        onGetAllShopItems: (List<ShopItem>?) -> Unit
    ) {
        getAllShopItemsUseCase().removeObservers(lifecycleOwner)
        getAllShopItemsUseCase().observe(lifecycleOwner) {
            onGetAllShopItems(it)
        }
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase(viewModelScope, shopItem)
    }

    fun updatedShopItem(shopItem: ShopItem, onShopItemUpdated: () -> Unit) {
        updateShopItemUseCase(viewModelScope, shopItem) {
            onShopItemUpdated()
        }
    }

    fun getTotalEstimationCost(
        lifecycleOwner: LifecycleOwner,
        onTotalEstimationCost: (Double?) -> Unit
    ) {
        getTotalShopItemCostUseCase()?.removeObservers(lifecycleOwner)
        getTotalShopItemCostUseCase()?.observe(lifecycleOwner) {
            onTotalEstimationCost(it)
        }
    }

    fun getTotalMarkedItems(lifecycleOwner: LifecycleOwner, onTotalMarkedShopItems: (Int) -> Unit) {
        getTotalMarkedShopItemsUseCase()?.observe(lifecycleOwner) {
            onTotalMarkedShopItems(it)
        }
    }

}