package com.bulletapps.candypricer.presentation.ui.scenes.main.addSupply

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.addSupply.AddSupplyViewModel.FieldsTexts
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.DropdownMenuOutlined
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton

@Composable
fun ScreenAddSupply(
    viewModel: AddSupplyViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    viewModel.setup() // todo: remove after integration
    Screen(
        onClickConfirm = viewModel::onClickConfirm,
        onchangeExpanded = viewModel::onChangeExpanded,
        onTextChanged = viewModel::onTextChanged,
        onItemSelected = {index -> viewModel.onItemSelected(index)},
        viewModel.uiState
    )
}

@Composable
fun Screen(
    onClickConfirm: () -> Unit,
    onchangeExpanded: () -> Unit,
    onTextChanged: (FieldsTexts) -> Unit,
    onItemSelected: ((Int) -> Unit),
    uiState: AddSupplyViewModel.UIState,
) {

    val name by uiState.name.collectAsState()
    val quantity by uiState.quantity.collectAsState()
    val price by uiState.price.collectAsState()
    val unities by uiState.unities.collectAsState()
    val isExpanded by uiState.isExpanded.collectAsState()
    val selectedUnit by uiState.selectedUnit.collectAsState()

    CandyPricerTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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

            Spacer(Modifier.height(8.dp))

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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            DropdownMenuOutlined(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                expanded = isExpanded,
                items = unities.map { it.label },
                selectedItem = selectedUnit,
                label = stringResource(R.string.select_a_unit),
                onClick = onchangeExpanded,
                onItemSelected = { index -> onItemSelected(index) }
            )

            OutlinedTextField(
                value = price,
                onValueChange = { onTextChanged(FieldsTexts.Price(it)) },
                placeholder = { Text(stringResource(R.string.thirty_reals)) },
                label = { Text(stringResource(R.string.price)) },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            NormalButton(text = stringResource(R.string.confirm), onClick = onClickConfirm)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(onClickConfirm = {}, onchangeExpanded = {}, onTextChanged = {}, onItemSelected = {}, uiState = AddSupplyViewModel.UIState())
}