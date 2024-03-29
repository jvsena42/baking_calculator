package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bulletapps.candypricer.presentation.ui.theme.buttonDisabled

@Composable
fun NormalButton(
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
    isEnabled: Boolean = true,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            disabledBackgroundColor = buttonDisabled
        )
    ) {
        Text(text, color = Color.White)
    }
}

@Composable
fun OutlinedButtonCustom(
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
    isEnabled: Boolean = true,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.secondary
        ),
        enabled = isEnabled,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent
        ),
        modifier = modifier,
    ) {
        Text(text, color = MaterialTheme.colors.secondary)
    }
}