package com.shoplist.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun ViewGroup.viewHolderItemBinding(resId: Int): ViewDataBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this.context),
        resId,
        this,
        false
    )
}