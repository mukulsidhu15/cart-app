package com.example.cartapp.presentation.homescreen

import com.example.cartapp.presentation.FruitItemState

data class HomeScreenState(
    val isLoading: Boolean = true,
    val fruitList: List<FruitItemState> = emptyList(),
    val cartItemList: List<FruitItemState> = emptyList(),
    val error: String? = null
)