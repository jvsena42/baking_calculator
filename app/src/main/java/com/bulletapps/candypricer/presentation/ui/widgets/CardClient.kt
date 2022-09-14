package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
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
    thirdLabel: Int,
    thirdName: String,
    leftBTLabel: Int,
    rightBTLabel: Int,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
) {
    Column {
        Card(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            elevation = 1.dp
        ) {
            Column (
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextWithLabel(stringResource(id = firstLabel), firsName)
                TextWithLabel(stringResource(id = secondLabel), secondName)
                TextWithLabel(stringResource(id = thirdLabel), thirdName)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButtonCustom(
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp).weight(1f),
                text = stringResource(leftBTLabel),
                onClick = onClickLeft
            )
            Spacer(modifier = Modifier.width(8.dp))
            NormalButton(
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp).weight(1f),
                text = stringResource(rightBTLabel),
                onClick = onClickRight
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CardClient(
        firstLabel = R.string.name_label,
        secondLabel = R.string.email,
        thirdLabel = R.string.expires_at_label,
        firsName = "teste",
        secondName = "teste",
        thirdName = "teste",
        leftBTLabel = R.string.change_expiring_date,
        rightBTLabel = R.string.send_message,
        onClickLeft = {},
        onClickRight = {}
    )
}