package com.shoplist.presentation.ui.fragments.addoredit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoplist.domain.models.Category
import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.usecases.category.GetAllCategoriesUseCase
import com.shoplist.domain.usecases.category.GetCategoryByIdUseCase
import com.shoplist.domain.usecases.shopitems.InsertShopItemUseCase
import com.shoplist.domain.usecases.shopitems.UpdateShopItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddOrEditViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val insertShopItemUseCase: InsertShopItemUseCase,
    private val updateShopItemUseCase: UpdateShopItemUseCase
) : ViewModel() {

    fun getAllCategories(
        lifecycleOwner: LifecycleOwner,
        onAllCategoryItems: (List<Category>?) -> Unit
    ) {
        getAllCategoriesUseCase().removeObservers(lifecycleOwner)
        getAllCategoriesUseCase().observe(lifecycleOwner) {
            onAllCategoryItems(it)
        }
    }

    fun getCategoryById(
        id: Int,
        onCategoryById: (Category?) -> Unit
    ) {
        getCategoryByIdUseCase(viewModelScope, id) {
            onCategoryById(it)
        }

    }

    fun insertShopItem(shopItem: ShopItem) {
        insertShopItemUseCase(viewModelScope, shopItem)
    }

    fun updatedShopItem(shopItem: ShopItem, onShopItemUpdated: () -> Unit) {
        updateShopItemUseCase(viewModelScope, shopItem) {
            onShopItemUpdated()
        }
    }
}