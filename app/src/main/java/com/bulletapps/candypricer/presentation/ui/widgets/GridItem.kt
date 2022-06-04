package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.presentation.ui.scenes.main.MenuModel
import com.bulletapps.candypricer.presentation.ui.theme.colorAccent

@Composable
fun MenuItem(item: MenuModel) {
    Card(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier.padding(8.dp),
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                backgroundColor = colorAccent,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    painter = painterResource(id = item.iconRef),
                    contentDescription = stringResource(id = item.labelRef),
                    modifier = Modifier.padding(8.dp)
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                textAlign = TextAlign.Center,
                text = stringResource(item.labelRef)

            )
        }
    }
}