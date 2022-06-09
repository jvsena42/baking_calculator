package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
fun CardSupply(supply: Supply, onClick: () -> Unit?) {
    Card(modifier = Modifier.clickable { onClick }) {
        Column(modifier = Modifier.padding(8.dp)) {
            TextWithLabel(stringResource(id = R.string.name_label),supply.name)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextWithLabel(stringResource(id = R.string.quantity_label), supply.quantity.toString())
                TextWithLabel(stringResource(id = R.string.measure_type_label),supply.unitType)
                TextWithLabel(stringResource(id = R.string.cost_label), supply.price)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CardSupply(Supply(id = 0, name = "Leite Condensado Caixa", price = "R$ 5,00", quantity = 1.0, unitType = "Unidade" )) {

    }
}