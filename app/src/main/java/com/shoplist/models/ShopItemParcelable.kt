package com.shoplist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopItemParcelable(var id:Int,var name:String,
                              var dateAdded:String,var quantity:Int,
                              var categoryId:Int,var itemCost:Double,
                              var isMarked:Boolean) : Parcelable {
}