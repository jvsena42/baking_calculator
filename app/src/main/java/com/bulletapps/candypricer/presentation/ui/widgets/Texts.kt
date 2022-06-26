package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bulletapps.candypricer.presentation.ui.theme.brownText

@Composable
fun TextLabel(text: String) {
    Text(text, fontWeight = FontWeight.Bold)
}

@Composable
fun TextLabelBrown(text: String, textAlign: TextAlign? = null) {
    Text(text, fontWeight = FontWeight.Bold, color = brownText, textAlign = textAlign)
}

@Composable
fun TextWithLabel(text: String, label: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        TextLabel("$text ")
        Text(label)
    }
}