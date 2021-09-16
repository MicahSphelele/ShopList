package com.shoplist.extensions

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.shoplist.R
import com.shoplist.databinding.ItemShopBinding
import com.shoplist.presentation.ui.adapters.ShopItemAdapter.ShopItemAction
import com.shoplist.util.Constants

fun ViewGroup.viewHolderItemBinding(@LayoutRes resId: Int): ViewDataBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this.context),
        resId,
        this,
        false
    )
}

fun ItemShopBinding.showPopupMenu(view: View,itemSelected: (ShopItemAction) -> Unit ) {
    val popup =
        PopupMenu(ContextThemeWrapper(this.root.context, R.style.ItemPopUpMenuStyle), view)

    popup.menuInflater.inflate(R.menu.shop_item_menu, popup.menu)

    if (popup.menu is MenuBuilder) {
        (popup.menu as MenuBuilder).setOptionalIconsVisible(true)
    }

    popup.setOnMenuItemClickListener { item: MenuItem ->
        if (item.itemId == R.id.item_edit) {
            itemSelected(ShopItemAction.EDIT)
        }
        if (item.itemId == R.id.item_delete) {
            itemSelected(ShopItemAction.DELETE)
        }
        true
    }
    popup.show()
}

fun Context.getViewBinder(layout: Int): ViewDataBinding {
    return DataBindingUtil.bind(LayoutInflater.from(this).inflate(layout, null))!!
}

fun Int?.returnItemsOrItem(): String {

    if (this != null) {
        return if (this > 1) {
            "$this items"
        } else {
            "$this item"
        }
    }

    return "0 items"
}

fun Context.hideDeviceSoftKeyboard(view: View) {
    val inputMethodManger = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManger.hideSoftInputFromWindow(view.windowToken, 0)
}


@BindingAdapter("imageDrawable")
fun loadImage(view: ShapeableImageView, image: Int) = view.setImageDrawable(ContextCompat.getDrawable(view.context, image))

@BindingAdapter("strikeThrough")
fun strikeThrough(view: MaterialTextView, strikeThrough: Boolean) {
    if (strikeThrough) {
        view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        view.paintFlags = view.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

@BindingAdapter("formattedCurrency")
fun setFormattedCurrency(view: MaterialTextView, itemCost: Double) {
    view.text = Constants.formatCurrency(itemCost)
}