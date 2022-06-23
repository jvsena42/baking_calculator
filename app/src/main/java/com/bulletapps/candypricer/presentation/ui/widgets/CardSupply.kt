package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.data.model.Supply
import com.bulletapps.candypricer.data.model.UnitType

@Composable
fun CardSupply(supply: Supply,modifier: Modifier = Modifier, onClick: () -> Unit?) {
    Card(
        modifier = modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .clickable { onClick.invoke() }
            .fillMaxWidth(),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextWithLabel(stringResource(id = R.string.name_label), supply.name)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TextWithLabel(
                    stringResource(id = R.string.quantity_label),
                    supply.quantity.toString()
                )
                TextWithLabel(stringResource(id = R.string.measure_type_label), supply.unitType)
                TextWithLabel(stringResource(id = R.string.cost_label), supply.price)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CardSupply(
        Supply(
            id = 0,
            name = "Leite Condensado Caixa",
            price = "R$ 5,00",
            quantity = 1.0,
            unitType = "Unidade"
        )
    ) {

    }
}