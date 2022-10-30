package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.util.EMPTY_STRING

@Composable
fun CardTwoItemsVertical(
    firstLabel: Int,
    firsName: String,
    secondLabel: Int,
    secondName: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .clickable { onClick.invoke() }
            .fillMaxWidth(),
        elevation = 1.dp
    ) {
        Column (
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextWithLabel(stringResource(id = firstLabel), firsName)
            TextWithLabel(stringResource(id = secondLabel), secondName)
        }
    }
}

@Composable
fun CardTwoItemsHorizontal(
    firstLabel: Int,
    firsName: String,
    secondLabel: Int,
    secondName: String,
    onClick: () -> Unit?
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick.invoke() }
            .fillMaxWidth(),
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextWithLabel(stringResource(id = firstLabel), firsName)
            TextWithLabel(stringResource(id = secondLabel), secondName)
        }
    }
}
@Composable
fun CardTwoItemsWithDetailHorizontal(
    firstLabel: Int,
    firsName: String,
    secondLabel: Int,
    secondName: String,
    thirdName: String = EMPTY_STRING,
    onClick: () -> Unit?
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick.invoke() }
            .fillMaxWidth(),
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextWithLabel(stringResource(id = firstLabel), firsName)
            Spacer(modifier = Modifier.weight(1f))
            TextWithLabel(stringResource(id = secondLabel), secondName)
            Text(thirdName)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column {

        CardTwoItemsHorizontal(
            firstLabel = R.string.name_label,
            secondLabel = R.string.quantity_short_label,
            firsName = "teste",
            secondName = "teste",
            onClick = {}
        )

        CardTwoItemsVertical(
            firstLabel = R.string.name_label,
            secondLabel = R.string.quantity_short_label,
            firsName = "teste",
            secondName = "teste"
        ) {}
    }

}