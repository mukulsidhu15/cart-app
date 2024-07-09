package com.example.cartapp.presentation.itemdetailscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cartapp.presentation.FruitItemState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    modifier: Modifier = Modifier, item: FruitItemState, onNavigateBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Item Detail") }, navigationIcon = {
                IconButton(onClick = onNavigateBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "navigate back"
                    )
                }
            })
        }, modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = item.name, fontWeight = FontWeight.Bold)
            Text(text = "Fruit", fontWeight = FontWeight.Bold)
        }
    }
}