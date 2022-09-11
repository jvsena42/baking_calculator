package com.bulletapps.candypricer.presentation.ui.widgets

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Toast(
    text: String,
    duration: Int = Toast.LENGTH_LONG
) {
    if(text.isEmpty()) return
    val context = LocalContext.current
    val toast = Toast.makeText(context, text, duration)
    toast.show()
}
