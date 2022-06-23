package com.bulletapps.candypricer.presentation.ui.scenes.main.addProduct

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.UnityModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
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
    val activity = LocalContext.current as MainActivity
    LaunchedEffect(key1 = Unit) { viewModel.setup() }
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(activity, viewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: AddProductViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                ScreenEvent.GoBack -> activity.onBackPressed()
                ScreenEvent.OpenDialog -> {}
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit,
) {

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

                    MakeDropdownUnit(onAction, uiState)

                    MakeFieldLaborPrice(onAction, uiState)

                    MakeFieldVariableExpenses(onAction, uiState)

                    MakeFieldProfitMargin(onAction, uiState)

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        stringResource(R.string.supplies),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )


                }

                items(suppliesList) { itemSupply ->
                    CardTwoItemsHorizontal(
                        firstLabel = R.string.name_label,
                        secondLabel = R.string.quantity_label,
                        firsName = itemSupply.name,
                        secondName = itemSupply.qut,
                        onClick = {}
                    )
                }


                item {
                    MakeDialog(onAction, uiState)
                    OutlinedButtonCustom(text = stringResource(R.string.add_a_supply), onClick = {
                        onAction(OnClickAddSupply)
                    })
                }


                item {
                    NormalButton(
                        text = stringResource(R.string.confirm),
                        onClick = { onAction(OnClickConfirm) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MakeFieldProfitMargin(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val profitMargin by uiState.profitMargin.collectAsState()

    OutlinedTextField(
        value = profitMargin,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            onAction(OnTextChanged(FieldsTexts.ProfitMargin(it)))
        },
        placeholder = { Text(stringResource(R.string.ten_percent)) },
        label = { Text(stringResource(R.string.profit_margin)) },
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldVariableExpenses(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val variableExpenses by uiState.variableExpenses.collectAsState()

    OutlinedTextField(
        value = variableExpenses,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            onAction(OnTextChanged(FieldsTexts.VariableExpenses(it)))
        },
        placeholder = { Text(stringResource(R.string.thirty_reals)) },
        label = { Text(stringResource(R.string.variable_expenses)) },
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldLaborPrice(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val laborPrice by uiState.laborPrice.collectAsState()

    OutlinedTextField(
        value = laborPrice,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            onAction(OnTextChanged(FieldsTexts.LaborPrice(it)))
        },
        placeholder = { Text(stringResource(R.string.labor_price)) },
        label = { Text(stringResource(R.string.labor_price)) },
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeDropdownUnit(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val unities by uiState.unities.collectAsState()
    val isExpanded by uiState.isExpanded.collectAsState()
    val selectedUnit by uiState.selectedUnit.collectAsState()

    DropdownMenuOutlined(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        expanded = isExpanded,
        items = unities.map { it.label },
        selectedItem = selectedUnit,
        label = stringResource(R.string.select_a_unit),
        onClick = { onAction(OnChangeExpanded) },
        onItemSelected = { index -> onAction(OnItemSelected(index)) }
    )
}

@Composable
private fun MakeFieldName(onAction: (ScreenActions) -> Unit, uiState: UIState) {
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


@Composable
private fun MakeDialog(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val suppliesMenuList by uiState.suppliesMenuList.collectAsState()
    val isMenuSuppliesExpanded by uiState.isMenuSuppliesExpanded.collectAsState()
    val selectedSupplyItem by uiState.selectedSupplyItem.collectAsState()
    val supplyQnt by uiState.supplyQnt.collectAsState()
    val isVisible by uiState.isDialogVisible.collectAsState()

    if (isVisible) {
        Dialog(
            onDismissRequest = { onAction(OnDismissDialog) },
            DialogProperties()
        ) {

            Card(
                shape = MaterialTheme.shapes.medium
            ) {

                Column(modifier = Modifier.padding(top = 16.dp)) {

                    DropdownMenuOutlined(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        expanded = isMenuSuppliesExpanded,
                        items = suppliesMenuList.map { it.name },
                        selectedItem = selectedSupplyItem,
                        label = stringResource(R.string.select_a_supply),
                        onClick = { onAction(OnChangeExpandedMenu) },
                        onItemSelected = { index ->
                            onAction(
                                OnItemMenuSelected(
                                    index
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = supplyQnt,
                        singleLine = true,
                        onValueChange = {
                            onAction(
                                OnTextChanged(
                                    FieldsTexts.SupplyQnt(it)
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text(stringResource(R.string.fifty_grams)) },
                        label = { Text(stringResource(R.string.quantity)) },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )

                    NormalButton(
                        text = stringResource(R.string.confirm),
                        onClick = { onAction(OnClickConfirmMenu) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {},
        uiState = UIState()
    )
}