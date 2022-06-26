package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bulletapps.candypricer.domain.model.Supply

@Composable
fun SuppliesList(supplyList: List<Supply>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth().fillMaxHeight(),
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