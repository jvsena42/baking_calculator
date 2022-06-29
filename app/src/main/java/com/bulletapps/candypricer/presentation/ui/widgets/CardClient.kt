package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.R

@Composable
fun CardClient(
    firstLabel: Int,
    firsName: String,
    secondLabel: Int,
    secondName: String,
    leftBTLabel: Int,
    rightBTLabel: Int,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
) {
    Column {
        CardTwoItemsVertical(
            firstLabel = firstLabel,
            firsName = firsName,
            secondLabel = secondLabel,
            secondName = secondName,
        )
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            OutlinedButtonCustom(
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp).weight(1f),
                text = stringResource(leftBTLabel),
                onClick = onClickLeft
            )
            NormalButton(
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp).weight(1f),
                text = stringResource(rightBTLabel),
                onClick = onClickRight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CardClient(
        firstLabel = R.string.name_label,
        secondLabel = R.string.quantity_label,
        firsName = "teste",
        secondName = "teste",
        leftBTLabel = R.string.change_expiring_date,
        rightBTLabel = R.string.send_message,
        onClickLeft = {},
        onClickRight = {}
    )
}