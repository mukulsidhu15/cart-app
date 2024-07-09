package com.example.cartapp.domain

import kotlinx.coroutines.flow.Flow

interface FruitsRepository {
    suspend fun getCartFruits(): Flow<List<Fruit>>
    suspend fun addFruitToCart(fruit: Fruit)
    suspend fun deleteFruitFromCart(fruit: Fruit)
}