package com.shoplist.presentation.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.usecases.shopitems.GetAllShopItemsUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(getAllShopItemsUseCase: GetAllShopItemsUseCase) : ViewModel() {

    fun getAllShopItemsUseCase() : LiveData<List<ShopItem>?> {

        return getAllShopItemsUseCase()
    }

}