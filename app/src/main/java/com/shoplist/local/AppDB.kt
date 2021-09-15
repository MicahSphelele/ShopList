package com.shoplist.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shoplist.domain.models.Category
import com.shoplist.domain.models.ShopItem
import com.shoplist.local.doas.CategoryDao
import com.shoplist.local.doas.ShopItemDao
import com.shoplist.util.Constants

@Database(entities = [Category::class, ShopItem::class], version = 2, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun shopItemDao(): ShopItemDao

    companion object {

        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return instance ?: synchronized(AppDB::class.java) {
                instance ?: buildAppDB(context).also {
                    instance = it
                }
            }
        }

        private fun buildAppDB(context: Context): AppDB {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java, "appDB.db"
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .addCallback(callBack)
                .build()
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE ${Constants.CATEGORY_TABLE} ADD COLUMN ${Constants.CATEGORY_IMAGE} INTEGER")
            }
        }

        private val callBack = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO ${Constants.CATEGORY_TABLE} (${Constants.CATEGORY_NAME},${Constants.CATEGORY_IMAGE}) VALUES('Food','${Constants.CATEGORY_IMAGES[0]}') ")
                db.execSQL("INSERT INTO ${Constants.CATEGORY_TABLE} (${Constants.CATEGORY_NAME},${Constants.CATEGORY_IMAGE}) VALUES('Fashion','${Constants.CATEGORY_IMAGES[1]}') ")
                db.execSQL("INSERT INTO ${Constants.CATEGORY_TABLE} (${Constants.CATEGORY_NAME},${Constants.CATEGORY_IMAGE}) VALUES('Personal Care','${Constants.CATEGORY_IMAGES[2]}') ")
                db.execSQL("INSERT INTO ${Constants.CATEGORY_TABLE} (${Constants.CATEGORY_NAME},${Constants.CATEGORY_IMAGE}) VALUES('Appliances','${Constants.CATEGORY_IMAGES[3]}') ")
                db.execSQL("INSERT INTO ${Constants.CATEGORY_TABLE} (${Constants.CATEGORY_NAME},${Constants.CATEGORY_IMAGE}) VALUES('Toiletries','${Constants.CATEGORY_IMAGES[4]}') ")
                db.execSQL("INSERT INTO ${Constants.CATEGORY_TABLE} (${Constants.CATEGORY_NAME},${Constants.CATEGORY_IMAGE}) VALUES('Electronics','${Constants.CATEGORY_IMAGES[5]}') ")
                db.execSQL("INSERT INTO ${Constants.CATEGORY_TABLE} (${Constants.CATEGORY_NAME},${Constants.CATEGORY_IMAGE}) VALUES('Utensils','${Constants.CATEGORY_IMAGES[6]}') ")
            }

        }
    }

}