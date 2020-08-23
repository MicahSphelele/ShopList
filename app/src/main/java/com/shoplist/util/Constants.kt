package com.shoplist.util

import com.shoplist.R
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val SHOP_TABLE = "shop"
    const val SHOP_ID = "shopId";
    const val SHOP_ITEM = "item_name"
    const val SHOP_DATE = "date_added"
    const val SHOP_QUANTITY = "quantity"
    const val SHOP_CAT_ID = "categoryId"
    const val SHOP_ITEM_COST = "item_cost"
    const val SHOP_IS_MARKED = "item_marked"

    const val CAT_TABLE = "category"
    const val CAT_ID = "categoryId"
    const val CAT_NAME = "categoryName"
    const val CAT_IMAGE = "categoryImage"

    const val ACTION_EDIT_VAL = "edit"
    const val ACTION_ADD_VAL = "add"

    val CAT_IMAGES = listOf( R.mipmap.food,R.mipmap.fashion, R.mipmap.personal_care,R.mipmap.appliances,
         R.mipmap.toiletries,R.mipmap.electronics, R.mipmap.utencils)

    fun getCurrentDateTime() : String{
        return SimpleDateFormat("yyyy-M-dd hh:mm a",Locale.getDefault()).format(Date())
    }

    fun getResId(resName: String?, c: Class<*>): Int {
        return try {
            val idField: Field = c.getDeclaredField(resName!!)
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}