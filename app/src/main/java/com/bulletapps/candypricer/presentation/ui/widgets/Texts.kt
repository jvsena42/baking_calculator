package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.theme.brownText
import com.bulletapps.candypricer.presentation.ui.theme.primary

@Composable
fun TextLabel(text: String) {
    Text(text, fontWeight = FontWeight.Bold)
}

@Composable
fun TextLabelBrown(text: String, textAlign: TextAlign? = null) {
    Text(text, fontWeight = FontWeight.Bold, color = brownText, textAlign = textAlign)
}

@Composable
fun TextWithLabel(text: String, label: String, modifier: Modifier = Modifier, arrangement: Arrangement.Horizontal = Arrangement.Start) {
    Row(modifier = modifier, arrangement) {
        TextLabel("$text ")
        Text(label)
    }
}

@Composable
fun TextButtonCustom(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        content = {
            Text(
                text,
                fontWeight = FontWeight.Bold,
                color = primary,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        },
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun TextTitle(text: String, fontSize: TextUnit = 16.sp) {
    Text(
        text,
        textAlign = TextAlign.Center,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )
}