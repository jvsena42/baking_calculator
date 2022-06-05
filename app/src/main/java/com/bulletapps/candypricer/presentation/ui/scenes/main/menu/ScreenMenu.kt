@file:OptIn(ExperimentalFoundationApi::class)

package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.MenuItem

@Composable
fun ScreenMenu() {
    BuildUI()
}

@Composable
fun BuildUI() {
    CandyPricerTheme {
        Column (
            modifier = Modifier.fillMaxSize(),
        ) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.main_menu)
                    )
                },
            )
            MenuGrid(listOf(
                MenuModel(R.string.my_products, R.drawable.ic_store, -1),
                MenuModel(R.string.supplies, R.drawable.ic_shopping_cart, -1),
                MenuModel(R.string.settings, R.drawable.ic_build, -1),
            ))
        }
    }
}

@Composable
fun MenuGrid(menuItems: List<MenuModel>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(top = 32.dp),
        content = {
            items(menuItems.size) { index ->
                val item = menuItems[index]
                MenuItem(item) {

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ScreenMenuPreview() {
    BuildUI()
}