package com.shoplist.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.imageview.ShapeableImageView

fun ViewGroup.viewHolderItemBinding(resId: Int): ViewDataBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this.context),
        resId,
        this,
        false
    )
}

@BindingAdapter("imageDrawable")
fun loadImage(view: ShapeableImageView, image: Int) = view.setImageDrawable(ContextCompat.getDrawable(view.context, image))