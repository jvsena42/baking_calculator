package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.presentation.ui.theme.colorAccent

@Composable
fun MenuItem(item: MenuModel, onClick: () -> Unit?) {
    Box(
        modifier = Modifier.padding(8.dp),
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                backgroundColor = colorAccent,
                modifier = Modifier.size(60.dp).clickable { onClick.invoke() }
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