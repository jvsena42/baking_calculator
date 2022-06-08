@file:OptIn(ExperimentalFoundationApi::class)

package com.bulletapps.candypricer.presentation.ui.scenes.main.supplies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.data.model.Supply
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardSupply
import com.bulletapps.candypricer.presentation.ui.widgets.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ScreenMenu(
    viewModel: SuppliesViewModel = hiltViewModel()
) {
    Screen(viewModel.suppliesList)
}

@Composable
fun Screen(
    itemsState: MutableStateFlow<List<Supply>>
) {
    val items = itemsState.collectAsState()
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
            SuppliesList(items.value)
        }
    }
}

@Composable
fun SuppliesList(supplyList: List<Supply>) {
    LazyColumn (
        content = {
            items(supplyList.size) { index ->
                val item = supplyList[index]
                CardSupply(item)
            }
        }
    )
}