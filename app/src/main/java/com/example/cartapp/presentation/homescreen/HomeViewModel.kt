package com.example.cartapp.presentation.homescreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartapp.data.FruitsRepositoryImpl
import com.example.cartapp.domain.Fruit
import com.example.cartapp.domain.FruitsRepository
import com.example.cartapp.presentation.FruitItemState
import com.example.cartapp.roomdatabase.FruitDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {

    private val fruitsRepository: FruitsRepository =
        FruitsRepositoryImpl(FruitDatabase.getDatabase(context.applicationContext).fruitDao())

    private var _allCartFruits = MutableStateFlow(listOf<FruitItemState>())

    private var _searchQuery = MutableStateFlow("")
    var searchQuery = _searchQuery

    private val _homeState = MutableStateFlow(HomeScreenState())

    var homeScreenState = combine(
        _homeState, _allCartFruits, _searchQuery
    ) { state, cartFruits, searchQuery ->

        val allFruits = listOf(
            "Banana", "Mango", "Apple", "Grapes", "Orange"
        ).map { fruitName ->
            FruitItemState(
                _allCartFruits.value.map { it.name }.contains(fruitName), fruitName
            )
        }.let { allFruits ->
            if (searchQuery.isNotEmpty()) {
                allFruits.filter { it.name.lowercase().contains(searchQuery.lowercase()) }
            } else {
                allFruits
            }
        }

        state.copy(
            isLoading = false, fruitList = allFruits, cartItemList = cartFruits
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeScreenState())

    init {
        getCartFruits()
    }

    private fun getCartFruits() {
        viewModelScope.launch {
            fruitsRepository.getCartFruits().collectLatest { result ->
                _allCartFruits.value = result.map { FruitItemState(true, it.name) }
                _homeState.value = _homeState.value.copy(cartItemList = result.map {
                    FruitItemState(
                        true, it.name
                    )
                })
            }
        }
    }

    fun onEvent(homeScreenEvent: HomeScreenEvent) {
        when (homeScreenEvent) {
            is HomeScreenEvent.AddItemToCart -> addFruitToCart(homeScreenEvent.fruit)
            is HomeScreenEvent.DeleteItemFromCart -> deleteFruitFromCart(homeScreenEvent.fruit)
            is HomeScreenEvent.OnSearchQueryChange -> onSearchTextChange(homeScreenEvent.searchQuery)
        }
    }

    private fun addFruitToCart(fruit: Fruit) {
        viewModelScope.launch {
            fruitsRepository.addFruitToCart(fruit)
            getCartFruits()
            onSearchTextChange("")
        }
    }

    private fun deleteFruitFromCart(fruit: Fruit) {
        viewModelScope.launch {
            fruitsRepository.deleteFruitFromCart(fruit)
            getCartFruits()
            onSearchTextChange("")
        }
    }

    private fun onSearchTextChange(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
            getCartFruits()
        }
    }
}