package com.example.cartapp.presentation.homescreen

import com.example.cartapp.domain.Fruit

sealed class HomeScreenEvent {
    data class AddItemToCart(val fruit: Fruit) : HomeScreenEvent()
    data class DeleteItemFromCart(val fruit: Fruit) : HomeScreenEvent()
    data class OnSearchQueryChange(val searchQuery: String) : HomeScreenEvent()
}