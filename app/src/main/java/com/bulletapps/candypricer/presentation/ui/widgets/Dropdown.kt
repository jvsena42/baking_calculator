package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun DropdownMenuCustom(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    items: List<String>,
    selectedItem: String,
    label: String = "",
    onClick: (() -> Unit)
) {
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Column(modifier = modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text(label) },
            trailingIcon = {
                Icon(icon, contentDescription = null, modifier = Modifier.clickable(onClick = onClick))
            }
        )

        DropdownMenu(

        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    DropdownMenu()
}

