package com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct

import androidx.compose.foundation.background
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
import com.bulletapps.candypricer.domain.model.ProductModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct.AddProductViewModel.*
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct.AddProductViewModel.ScreenActions.*
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardTwoItemsHorizontal
import com.bulletapps.candypricer.presentation.ui.widgets.DropdownMenuOutlined
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.ui.widgets.OutlinedButtonCustom

@Composable
fun ScreenAddProduct(
    viewModel: AddProductViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel,
    productModel: ProductModel? = null
) {
    val activity = LocalContext.current as MainActivity
    viewModel.setup(productModel)
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: AddProductViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                ScreenEvent.GoBack -> activity.onBackPressed() //TODO UPDATE PRODUCT
                ScreenEvent.GoHome -> sharedViewModel.navigate(MainViewModel.Navigation.MainMenu)
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit,
) {

    val suppliesList by uiState.selectedSupplies.collectAsState()
    val isCreation by uiState.isCreation.collectAsState()
    val toolbarTitle by uiState.toolbarTitle.collectAsState()

    CandyPricerTheme {

        Column {

            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(toolbarTitle)
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

                    MakeFieldQuantity(onAction, uiState)

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
                        secondLabel = R.string.quantity_short_label,
                        firsName = itemSupply.name,
                        secondName = itemSupply.quantity,
                        onClick = {}
                    )
                }

                if (isCreation) {
                    item {
                        MakeDialog(onAction, uiState)
                        OutlinedButtonCustom(text = stringResource(R.string.add_a_supply), onClick = {
                            onAction(OnClickAddSupply)
                        })
                    }
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
private fun MakeFieldQuantity(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val quantity by uiState.quantity.collectAsState()
    val error by uiState.qntError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = quantity,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Quantity(it))) },
        placeholder = { Text(stringResource(R.string.five_hundred)) },
        label = { Text(error?.asString() ?: stringResource(R.string.quantity)) },
        isError = isError,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    )
}

@Composable
private fun MakeFieldProfitMargin(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val profitMargin by uiState.profitMargin.collectAsState()
    val error by uiState.profitMarginError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = profitMargin,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            onAction(OnTextChanged(FieldsTexts.ProfitMargin(it)))
        },
        placeholder = { Text(stringResource(R.string.ten_percent)) },
        label = { Text(error?.asString() ?: stringResource(R.string.profit_margin)) },
        isError = isError,
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldVariableExpenses(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val variableExpenses by uiState.variableExpenses.collectAsState()
    val error by uiState.variableExpensesError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = variableExpenses,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            onAction(OnTextChanged(FieldsTexts.VariableExpenses(it)))
        },
        placeholder = { Text(stringResource(R.string.ten_percent)) },
        label = { Text(error?.asString() ?: stringResource(R.string.variable_expenses)) },
        isError = isError,
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldLaborPrice(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val laborPrice by uiState.laborPrice.collectAsState()
    val error by uiState.laborError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = laborPrice,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            onAction(OnTextChanged(FieldsTexts.LaborPrice(it)))
        },
        placeholder = { Text(stringResource(R.string.ten_percent)) },
        label = { Text(error?.asString() ?: stringResource(R.string.labor_price)) },
        isError = isError,
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeDropdownUnit(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val unities by uiState.unities.collectAsState()
    val isExpanded by uiState.isExpanded.collectAsState()
    val selectedUnit by uiState.selectedUnit.collectAsState()
    val error by uiState.unitError.collectAsState()

    DropdownMenuOutlined(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        expanded = isExpanded,
        items = unities.map { it.label },
        selectedItem = selectedUnit.label,
        label =  error?.asString() ?: stringResource(R.string.select_a_unit),
        onClick = { onAction(OnChangeExpanded) },
        onItemSelected = { index -> onAction(OnItemSelected(index)) }
    )
}

@Composable
private fun MakeFieldName(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val name by uiState.name.collectAsState()
    val error by uiState.nameError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = name,
        singleLine = true,
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Name(it))) },
        placeholder = { Text(stringResource(R.string.cocoa_powder)) },
        label = { Text(error?.asString() ?: stringResource(R.string.name)) },
        isError = isError,
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}


@Composable
private fun MakeDialog(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val suppliesMenuList by uiState.suppliesMenuList.collectAsState()
    val isMenuSuppliesExpanded by uiState.isMenuSuppliesExpanded.collectAsState()
    val selectedSupplyItem by uiState.selectedSupplyItem.collectAsState()
    val selectedSupplyUnit by uiState.selectedSupplyUnit.collectAsState()
    val supplyQnt by uiState.supplyQnt.collectAsState()
    val error by uiState.supplyQntError.collectAsState()
    val isVisible by uiState.isDialogVisible.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

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
                        selectedItem = selectedSupplyItem.name,
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
                        onValueChange = { onAction(OnTextChanged(FieldsTexts.SupplyQnt(it))) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text(stringResource(R.string.fifty_grams)) },
                        label = { Text(error?.asString() ?: stringResource(R.string.quantity)) },
                        isError = isError,
                        trailingIcon = { Text(selectedSupplyUnit) },
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