package com.example.cartapp.presentation.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cartapp.domain.Fruit
import com.example.cartapp.presentation.FruitItemState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenState,
    searchText: String,
    onEvent: (HomeScreenEvent) -> Unit,
    onCartClick: () -> Unit,
    onItemClick: (FruitItemState) -> Unit
) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(
            modifier = Modifier
                .imePadding()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (homeScreenState.isLoading) {
                CircularProgressIndicator()
            } else if (homeScreenState.error != null) {
                Text(text = homeScreenState.error)
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    TextField(
                        value = searchText,
                        onValueChange = { onEvent(HomeScreenEvent.OnSearchQueryChange(it)) },
                        maxLines = 1,
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "search",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        },
                        trailingIcon = {
                            if (searchText.isNotBlank()) {
                                IconButton(onClick = { onEvent(HomeScreenEvent.OnSearchQueryChange("")) }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "close",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        },
                        placeholder = { Text(text = "Search") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                            disabledIndicatorColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(CircleShape)
                    )

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(homeScreenState.fruitList) {
                            FruitsItem(fruitItemState = it, onEvent = onEvent, onItemClick = onItemClick)
                        }
                    }
                    if (homeScreenState.cartItemList.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(color = Color.LightGray)
                                .clickable {
                                    onCartClick()
                                }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LazyRow(
                                modifier = Modifier.weight(1f)
                            ) {
                                items(homeScreenState.cartItemList) {
                                    Text(text = it.name.plus(", "))
                                }
                            }
                            CartIconButton(
                                onCartClick = onCartClick,
                                itemCount = homeScreenState.cartItemList.size
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FruitsItem(
    modifier: Modifier = Modifier,
    fruitItemState: FruitItemState,
    onEvent: (HomeScreenEvent) -> Unit,
    onItemClick: (FruitItemState) -> Unit
) {
    val (checked, setChecked) = remember(fruitItemState.isChecked) { mutableStateOf(fruitItemState.isChecked) }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(fruitItemState)
            }) {
        Checkbox(checked = checked, onCheckedChange = {
            setChecked(it)
            if (it) {
                onEvent(HomeScreenEvent.AddItemToCart(Fruit(fruitItemState.name)))
            } else {
                onEvent(HomeScreenEvent.DeleteItemFromCart(Fruit(fruitItemState.name)))
            }
        })
        Text(text = fruitItemState.name)
    }
}

@Composable
fun CartIconButton(onCartClick: () -> Unit, itemCount: Int) {
    Box(modifier = Modifier.wrapContentSize()) {
        IconButton(onClick = onCartClick) {
            Icon(
                imageVector = Icons.Rounded.ShoppingCart,
                contentDescription = "Cart"
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(22.dp)
                .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape)
        ) {
            Text(
                text = itemCount.toString(),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}