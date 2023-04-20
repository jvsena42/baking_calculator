package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun BuyPlanItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 1.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(title, fontWeight = FontWeight.Bold)
                TextWithLabel(text = description, label = stringResource(id = R.string.value))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        NormalButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.buy),
            onClick = onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BuyPlanItem("PLano anual","12 x 29,74") { }
        BuyPlanItem("PLano anual","12 x 29,74") { }
        BuyPlanItem("PLano anual","12 x 29,74") { }
    }
}