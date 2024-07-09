package com.example.cartapp.navigation.route

import androidx.navigation.NamedNavArgument

sealed class Screens(val route: String, val navArguments: List<NamedNavArgument> = emptyList()) {
    data object HomeScreen : Screens(route = "home_screen")
    data object CartScreen : Screens(route = "cart_screen")
    data object ItemDetailScreen: Screens(route = "item_detail_screen")
}