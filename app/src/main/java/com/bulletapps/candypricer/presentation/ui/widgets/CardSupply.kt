package com.bulletapps.candypricer.presentation.ui.widgets

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

@Composable
fun CardSupply() {
    Card {
        Column(modifier = Modifier.padding(8.dp)) {
            TextWithLabel(stringResource(id = R.string.name_label),"Leite condensado")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextWithLabel(stringResource(id = R.string.quantity_label), "2")
                TextWithLabel(stringResource(id = R.string.measure_type_label),"Caixa")
                TextWithLabel(stringResource(id = R.string.cost_label), "R$ 2,00")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CardSupply()
}