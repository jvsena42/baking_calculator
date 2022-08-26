package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.presentation.util.toCurrency

@Composable
fun CardSupply(supply: SupplyResponse, modifier: Modifier = Modifier, onClick: () -> Unit?) {
    Card(
        modifier = modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .clickable { onClick.invoke() }
            .fillMaxWidth(),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextWithLabel(stringResource(id = R.string.name_label), supply.name)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextWithLabel(
                    stringResource(id = R.string.quantity_label),
                    supply.quantity.toString()
                )
                TextWithLabel(stringResource(id = R.string.measure_type_label), supply.unit?.name.orEmpty())
                TextWithLabel(stringResource(id = R.string.cost_label), supply.value.toCurrency())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CardSupply(
        SupplyResponse(
            id = 0,
            name = "Leite Condensado Caixa",
            value = 5.0,
            quantity = 1.0,
            unit = UnitResponse(0,"und")
        )
    ) {

    }
}