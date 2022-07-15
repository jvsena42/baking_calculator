package com.bulletapps.candypricer.presentation.ui.scenes.main.user.addSupply

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addSupply.AddSupplyViewModel.*
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
        viewModel.uiState,
        viewModel::onAction
    )
}

@Composable
fun Screen(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit,
) {
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

            MakeFieldName(onAction, uiState)

            MakeFieldQuantity(onAction, uiState)

            MakeFieldUnit(onAction, uiState)

            MakeFieldPrice(onAction, uiState)

            Spacer(modifier = Modifier.weight(1f))

            NormalButton(text = stringResource(R.string.confirm), onClick = { onAction(ScreenActions.OnClickConfirm) })
        }
    }
}

@Composable
private fun MakeFieldName(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val name by uiState.name.collectAsState()

    OutlinedTextField(
        value = name,
        singleLine = true,
        onValueChange = { onAction(ScreenActions.OnTextChanged(FieldsTexts.Name(it))) },
        placeholder = { Text(stringResource(R.string.cocoa_powder)) },
        label = { Text(stringResource(R.string.name)) },
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldQuantity(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val quantity by uiState.quantity.collectAsState()

    OutlinedTextField(
        value = quantity,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { onAction(ScreenActions.OnTextChanged(FieldsTexts.Quantity(it))) },
        placeholder = { Text(stringResource(R.string.five_hundred)) },
        label = { Text(stringResource(R.string.quantity)) },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    )
}

@Composable
private fun MakeFieldUnit(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val unities by uiState.unities.collectAsState()
    val isExpanded by uiState.isExpanded.collectAsState()
    val selectedUnit by uiState.selectedUnit.collectAsState()

    DropdownMenuOutlined(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        expanded = isExpanded,
        items = unities.map { it.label },
        selectedItem = selectedUnit,
        label = stringResource(R.string.select_a_unit),
        onClick = { onAction(ScreenActions.OnChangeExpanded) },
        onItemSelected = { index -> onAction(ScreenActions.OnItemSelected(index)) }
    )
}

@Composable
private fun MakeFieldPrice(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val price by uiState.price.collectAsState()

    OutlinedTextField(
        value = price,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { onAction(ScreenActions.OnTextChanged(FieldsTexts.Price(it))) },
        placeholder = { Text(stringResource(R.string.thirty_reals)) },
        label = { Text(stringResource(R.string.price)) },
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(onAction = {}, uiState = UIState())
}