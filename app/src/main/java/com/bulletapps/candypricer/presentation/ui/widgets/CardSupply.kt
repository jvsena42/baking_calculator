package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardSupply() {
    Card {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                TextLabel("Nome: ")
                Text("Leite condensado")
            }
            Row {
                TextLabel("qnt")
                Text("Leite condensado")

                TextLabel("Medida")
                Text("Caixa")


                TextLabel("Valor")
                Text("2,00")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CardSupply()
}