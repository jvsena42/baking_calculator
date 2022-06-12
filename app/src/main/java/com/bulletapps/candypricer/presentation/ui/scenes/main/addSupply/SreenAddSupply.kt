package com.bulletapps.candypricer.presentation.ui.scenes.main.addSupply

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.addSupply.AddSupplyViewModel.FieldsTexts
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.util.CustomArrangement

@Composable
fun ScreenAddSupply(
    viewModel: AddSupplyViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    Screen(viewModel::onConfirmClicked, viewModel::onTextChanged, viewModel.uiState)
}

@Composable
fun Screen(
    onClick: () -> Unit,
    onTextChanged: (FieldsTexts) -> Unit,
    uiState: AddSupplyViewModel.UIState
) {

    val name by uiState.name.collectAsState()
    val quantity by uiState.quantity.collectAsState()
    val price by uiState.price.collectAsState()

    CandyPricerTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background),
            verticalArrangement = CustomArrangement.spacedBy(8.dp)
        ) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.add_supply)
                    )
                },
            )

            Spacer(Modifier.padding(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { onTextChanged(FieldsTexts.Name(it)) },
                placeholder = { Text(stringResource(R.string.cocoa_powder)) },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            )

            OutlinedTextField(
                value = quantity,
                onValueChange = { onTextChanged(FieldsTexts.Quantity(it)) },
                placeholder = { Text(stringResource(R.string.five_hundred)) },
                label = { Text(stringResource(R.string.quantity)) },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { onTextChanged(FieldsTexts.Price(it)) },
                placeholder = { Text(stringResource(R.string.thirty_reals)) },
                label = { Text(stringResource(R.string.price)) },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            )

            NormalButton(text = stringResource(R.string.confirm), onClick = onClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(onClick = {}, onTextChanged = {}, uiState = AddSupplyViewModel.UIState())
}