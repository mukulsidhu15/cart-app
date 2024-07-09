package com.example.cartapp.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cartapp.data.entities.CartEntity
import com.example.cartapp.roomdatabase.dao.FruitDao

@Database(entities = [CartEntity::class], version = 1, exportSchema = false)
abstract class FruitDatabase : RoomDatabase() {
    abstract fun fruitDao(): FruitDao

    companion object {
        @Volatile
        private var Instance: FruitDatabase? = null

        fun getDatabase(context: Context): FruitDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FruitDatabase::class.java,
                    "cart.db"
                ).build()
                    .also { Instance = it }
            }
        }
    }
}