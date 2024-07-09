package com.example.cartapp.data

import com.example.cartapp.data.entities.toFruit
import com.example.cartapp.domain.Fruit
import com.example.cartapp.domain.FruitsRepository
import com.example.cartapp.domain.toCartEntity
import com.example.cartapp.roomdatabase.dao.FruitDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class FruitsRepositoryImpl(private val fruitDao: FruitDao) : FruitsRepository {

    override suspend fun getCartFruits(): Flow<List<Fruit>> = channelFlow {
        fruitDao.getAllFruitsFromFruitsCart()
            .collectLatest { result -> send(result.map { it.toFruit() }) }
    }

    override suspend fun addFruitToCart(fruit: Fruit) {
        fruitDao.insertFruitToCart(fruit.toCartEntity())
    }

    override suspend fun deleteFruitFromCart(fruit: Fruit) {
        fruitDao.deleteFruitToCart(fruit.name)
    }
}