package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TextLabel(text: String) {
    Text(text, fontWeight = FontWeight.Bold)
}

@Composable
fun TextWithLabel(text: String, label: String) {
    Row {
        TextLabel("$text ")
        Text(label)
    }
}