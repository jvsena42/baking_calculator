@file:OptIn(ExperimentalFoundationApi::class)

package com.bulletapps.candypricer.presentation.ui.scenes.main.supplies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.data.model.Supply
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardSupply
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ScreenSupplies(
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
            modifier = Modifier
                .fillMaxSize()
                .background(color=colors.background),
        ) {
            TopAppBar(
                backgroundColor = colors.primary,
            ) {
                IconButton(onClick = {  }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.back),
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.supplies)
                )
            }
            SuppliesList(items.value)
        }
    }
}

@Composable
fun SuppliesList(supplyList: List<Supply>) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(supplyList.size) { index ->
                val item = supplyList[index]
                CardSupply(item) {

                }
            }
        }
    )
}