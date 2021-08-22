package com.shoplist.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shoplist.util.Constants

@Entity(tableName = Constants.CATEGORY_TABLE)
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.CATEGORY_ID)
    var id: Int = 0,
    @ColumnInfo(name = Constants.CAT_NAME)
    var catName: String,
    @ColumnInfo(name = Constants.CAT_IMAGE)
    var catImage: Int
)