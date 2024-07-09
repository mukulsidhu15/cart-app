package com.example.cartapp.domain

import com.example.cartapp.data.entities.CartEntity

data class Fruit(
    val name: String
)

fun Fruit.toCartEntity(): CartEntity = CartEntity(name = name)