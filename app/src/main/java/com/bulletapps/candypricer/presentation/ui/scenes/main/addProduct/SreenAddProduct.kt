package com.bulletapps.candypricer.presentation.ui.scenes.main.addProduct

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.addProduct.AddProductViewModel.*
import com.bulletapps.candypricer.presentation.ui.scenes.main.addProduct.AddProductViewModel.ScreenActions.*
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.*

@Composable
fun ScreenAddProduct(
    viewModel: AddProductViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    viewModel.setup() // todo: remove after integration
    Screen(
        onAction = { action -> viewModel.onAction(action) },
        viewModel.uiState
    )
}

@Composable
private fun Screen(
    onAction: ((ScreenActions) -> Unit),
    uiState: UIState,
) {

    val laborPrice by uiState.laborPrice.collectAsState()
    val profitMargin by uiState.profitMargin.collectAsState()
    val variableExpenses by uiState.variableExpenses.collectAsState()
    val unities by uiState.unities.collectAsState()
    val isExpanded by uiState.isExpanded.collectAsState()
    val selectedUnit by uiState.selectedUnit.collectAsState()
    val suppliesList by uiState.suppliesList.collectAsState()

    CandyPricerTheme {

        Column {

            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.add_product)
                    )
                },
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Spacer(Modifier.height(16.dp))

                    MakeFieldName(onAction, uiState)

                    DropdownMenuOutlined(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        expanded = isExpanded,
                        items = unities.map { it.label },
                        selectedItem = selectedUnit,
                        label = stringResource(R.string.select_a_unit),
                        onClick = { onAction(OnChangeExpanded) },
                        onItemSelected = { index -> OnItemSelected(index) }
                    )

                    OutlinedTextField(
                        value = laborPrice,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            OnTextChanged(
                                FieldsTexts.LaborPrice(
                                    it
                                )
                            )
                        },
                        placeholder = { Text(stringResource(R.string.labor_price)) },
                        label = { Text(stringResource(R.string.labor_price)) },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = variableExpenses,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            OnTextChanged(
                                FieldsTexts.VariableExpenses(
                                    it
                                )
                            )
                        },
                        placeholder = { Text(stringResource(R.string.thirty_reals)) },
                        label = { Text(stringResource(R.string.variable_expenses)) },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = profitMargin,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            OnTextChanged(
                                FieldsTexts.ProfitMargin(
                                    it
                                )
                            )
                        },
                        placeholder = { Text(stringResource(R.string.ten_percent)) },
                        label = { Text(stringResource(R.string.profit_margin)) },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        stringResource(R.string.supplies),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )


                }

                items(suppliesList) { supply ->
                    CardSupply(supply) {

                    }
                }

                item {
                    OutlinedButtonCustom(text = stringResource(R.string.add_a_supply), onClick = {})
                }


                item {
                    NormalButton(
                        text = stringResource(R.string.confirm),
                        onClick = { OnClickConfirm })
                }
            }
        }
    }
}

@Composable
private fun MakeFieldName(
    onAction: ((ScreenActions) -> Unit),
    uiState: UIState,
) {
    val name by uiState.name.collectAsState()

    OutlinedTextField(
        value = name,
        singleLine = true,
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Name(it))) },
        placeholder = { Text(stringResource(R.string.cocoa_powder)) },
        label = { Text(stringResource(R.string.name)) },
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {},
        uiState = UIState()
    )
}