package com.shoplist.util

import android.app.Application
import com.shoplist.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val SHOP_TABLE = "shop"
    const val SHOP_ID = "shopId"
    const val SHOP_ITEM = "item_name"
    const val SHOP_DATE = "date_added"
    const val SHOP_QUANTITY = "quantity"
    const val SHOP_CAT_ID = "categoryId"
    const val SHOP_ITEM_COST = "item_cost"
    const val SHOP_IS_MARKED = "item_marked"

    const val CATEGORY_TABLE = "category"
    const val CATEGORY_ID = "categoryId"
    const val CATEGORY_NAME = "categoryName"
    const val CATEGORY_IMAGE = "categoryImage"

    const val ACTION_EDIT_VAL = "edit"
    const val ACTION_ADD_VAL = "add"

    val CATEGORY_IMAGES = listOf(
        R.mipmap.food, R.mipmap.fashion, R.mipmap.personal_care, R.mipmap.appliances,
        R.mipmap.toiletries, R.mipmap.electronics, R.mipmap.utencils
    )

    fun getCurrentDateTime(): String {
        return SimpleDateFormat("yyyy-M-dd hh:mm a", Locale.getDefault()).format(Date())
    }

    fun formatCurrency(amount: Double): String? {
        return NumberFormat.getCurrencyInstance(Locale("en", "ZA"))
            .format(amount)
    }

//    fun getCurrency() : Currency {
//        val locale = Locale.getDefault()
//        return Currency.getInstance(locale)
//    }
//
//    fun getLocalList() : List<Locale> {
//        return listOf(Locale("en", "ZA"), Locale.UK, Locale.US)
//    }

    fun getAppVersion(application: Application): String {
        return application.packageManager.getPackageInfo(application.packageName, 0).versionName
    }
}