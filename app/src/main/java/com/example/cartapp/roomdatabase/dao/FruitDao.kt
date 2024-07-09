package com.example.cartapp.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.cartapp.data.entities.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Upsert
    suspend fun insertFruitToCart(cartEntity: CartEntity)

    @Query("DELETE FROM cart WHERE name=:name")
    suspend fun deleteFruitToCart(name: String)

    @Query("SELECT * FROM cart")
    fun getAllFruitsFromFruitsCart(): Flow<List<CartEntity>>
}