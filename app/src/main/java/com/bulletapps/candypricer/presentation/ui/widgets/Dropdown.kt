package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun DropdownMenuOutlined(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    items: List<String>,
    selectedItem: String,
    label: String = "",
    onClick: (() -> Unit),
    onItemSelected: ((Int) -> Unit),
) {
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Column(modifier = modifier) {
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
            expanded = expanded,
            onDismissRequest = {},
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = { onItemSelected(index) },
                    content = { Text(item, color = Color.Black) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    DropdownMenuOutlined(
    expanded = true,
    items = listOf("item 1", "item 2", "item 3"),
    selectedItem = "",
    label = "Select an item",
    onClick = {},
    onItemSelected = {},
    )
}

