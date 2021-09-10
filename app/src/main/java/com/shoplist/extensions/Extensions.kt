package com.shoplist.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.imageview.ShapeableImageView

fun ViewGroup.viewHolderItemBinding(@LayoutRes resId: Int): ViewDataBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this.context),
        resId,
        this,
        false
    )
}

fun Context.getViewBinder(layout: Int): ViewDataBinding {
    return DataBindingUtil.inflate(LayoutInflater.from(this), layout, null, false)
}

fun Context.hideDeviceSoftKeyboard(view: View) {
    val inputMethodManger = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManger.hideSoftInputFromWindow(view.windowToken, 0)
}


@BindingAdapter("imageDrawable")
fun loadImage(view: ShapeableImageView, image: Int) = view.setImageDrawable(ContextCompat.getDrawable(view.context, image))