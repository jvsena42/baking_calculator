package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.theme.colorAccent

@Composable
fun LogoCircle(modifier: Modifier = Modifier, bgColor: Color = colorAccent) {
    Box(
        modifier = modifier
            .background(color = bgColor, shape = CircleShape)
            .size(85.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.app_name),
        )
    }
}

@Composable
fun LogoWithText(text: String = stringResource(R.string.pricing_with_tricia)) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LogoCircle()
        Spacer(modifier = Modifier.height(4.dp))
        TextLabelBrown(text, textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
private fun preview() {
    Column {
        LogoCircle()
        LogoWithText()
    }
}