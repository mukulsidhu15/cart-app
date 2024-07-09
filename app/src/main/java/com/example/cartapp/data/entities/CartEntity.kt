package com.example.cartapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cartapp.domain.Fruit

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)

fun CartEntity.toFruit(): Fruit = Fruit(name)