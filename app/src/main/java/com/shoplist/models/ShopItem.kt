package com.shoplist.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shoplist.util.Constants
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.SHOP_TABLE)
data class ShopItem(
    @ColumnInfo(name = Constants.SHOP_ITEM)
    var name: String,

    @ColumnInfo(name = Constants.SHOP_DATE)
    var dateAdded: String,

    @ColumnInfo(name = Constants.SHOP_QUANTITY)
    var quantity: Int = 1,

    @ColumnInfo(name = Constants.SHOP_CAT_ID)
    var categoryId: Int,

    @ColumnInfo(name = Constants.SHOP_ITEM_COST)
    var itemCost: Double,

    @ColumnInfo(name = Constants.SHOP_IS_MARKED)
    var isMarked: Boolean = false

) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.SHOP_ID)
    var id: Int = 0
}