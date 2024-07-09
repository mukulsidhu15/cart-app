package com.example.cartapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cartapp.navigation.route.Screens
import com.example.cartapp.presentation.FruitItemState
import com.example.cartapp.presentation.cartscreen.CartScreen
import com.example.cartapp.presentation.homescreen.HomeScreen
import com.example.cartapp.presentation.homescreen.HomeViewModel
import com.example.cartapp.presentation.itemdetailscreen.ItemDetailScreen

@Composable
fun NavHostController(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val navController = rememberNavController()
    val viewModel = HomeViewModel(context)

    val homeScreenState = viewModel.homeScreenState.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()

    var itemsClicked: FruitItemState? = null

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
        modifier = modifier
    ) {
        composable(route = Screens.HomeScreen.route, arguments = Screens.HomeScreen.navArguments) {
            HomeScreen(
                homeScreenState = homeScreenState.value,
                searchText = searchQuery.value,
                onEvent = viewModel::onEvent,
                onCartClick = {
                    navController.navigate(Screens.CartScreen.route)
                },
                onItemClick = {
                    itemsClicked = it
                    navController.navigate(Screens.ItemDetailScreen.route)
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        composable(route = Screens.CartScreen.route, arguments = Screens.CartScreen.navArguments) {
            CartScreen(
                cartList = homeScreenState.value.cartItemList,
                onNavigateBackClick = {
                    navController.popBackStack()
                },
                modifier = modifier.fillMaxSize()
            )
        }

        composable(
            route = Screens.ItemDetailScreen.route,
            arguments = Screens.ItemDetailScreen.navArguments
        ) {
            if (itemsClicked != null) {
                ItemDetailScreen(item = itemsClicked!!, onNavigateBackClick = {
                    navController.popBackStack()
                })
            }
        }
    }
}