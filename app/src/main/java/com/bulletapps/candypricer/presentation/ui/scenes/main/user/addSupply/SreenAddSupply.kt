package com.bulletapps.candypricer.presentation.ui.scenes.main.user.addSupply

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addSupply.AddSupplyViewModel.*
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.DropdownMenuOutlined
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.ui.widgets.Toast

@Composable
fun ScreenAddSupply(
    viewModel: AddSupplyViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel,
    supplyModel: SupplyModel? = null
) {
    val activity = LocalContext.current as MainActivity
    viewModel.setup(supplyModel)
    Screen(
        viewModel.uiState,
        viewModel::onAction
    )
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: AddSupplyViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.GoBack -> activity.onBackPressed()
                is ScreenEvent.UpdateSupply -> {
                    activity.onBackPressed()
                }
            }
        }
    }
}

@Composable
fun Screen(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit,
) {
    val toolbarTitle by uiState.toolbarTitle.collectAsState()

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
                        text = stringResource(toolbarTitle)
                    )
                },
            )

            Spacer(Modifier.height(8.dp))

            MakeFieldName(onAction, uiState)

            MakeFieldQuantity(onAction, uiState)

            MakeFieldUnit(onAction, uiState)

            MakeFieldPrice(onAction, uiState)

            Spacer(modifier = Modifier.weight(1f))

            MakeButtonConfirm(onAction, uiState)

            DisplayToast(uiState)
        }
    }
}

@Composable
private fun DisplayToast(uiState: UIState) {
    val toastMessage by uiState.textToast.collectAsState()
    val message = toastMessage.asString()
    if (message.isNotEmpty()) Toast(message)
}

@Composable
private fun MakeFieldName(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val name by uiState.name.collectAsState()
    val error by uiState.nameError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = name,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        onValueChange = { onAction(ScreenActions.OnTextChanged(FieldsTexts.Name(it))) },
        placeholder = { Text(stringResource(R.string.cocoa_powder)) },
        label = { Text(error?.asString() ?: stringResource(R.string.name)) },
        isError = isError,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun MakeFieldQuantity(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val quantity by uiState.quantity.collectAsState()
    val error by uiState.qntError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = quantity,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        onValueChange = { onAction(ScreenActions.OnTextChanged(FieldsTexts.Quantity(it))) },
        placeholder = { Text(stringResource(R.string.five_hundred)) },
        label = { Text(error?.asString() ?: stringResource(R.string.quantity)) },
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun MakeFieldUnit(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val unities by uiState.unities.collectAsState()
    val isExpanded by uiState.isExpanded.collectAsState()
    val selectedUnit by uiState.selectedUnitLabel.collectAsState()
    val error by uiState.unitError.collectAsState()

    DropdownMenuOutlined(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        expanded = isExpanded,
        items = unities.map { it.label },
        selectedItem = selectedUnit,
        label = error?.asString() ?: stringResource(R.string.select_a_unit),
        onClick = { onAction(ScreenActions.OnChangeExpanded) },
        onItemSelected = { index -> onAction(ScreenActions.OnItemSelected(index)) }
    )
}

@Composable
private fun MakeFieldPrice(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val price by uiState.price.collectAsState()
    val error by uiState.priceError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = price,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions { onAction(ScreenActions.OnClickConfirm) },
        onValueChange = { onAction(ScreenActions.OnTextChanged(FieldsTexts.Price(it))) },
        placeholder = { Text(stringResource(R.string.thirty_reals)) },
        label = { Text(error?.asString() ?: stringResource(R.string.price)) },
        isError = isError,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun MakeButtonConfirm(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val isLoading by uiState.isLoading.collectAsState()

    NormalButton(
        isEnabled = !isLoading,
        text = stringResource(R.string.confirm),
        onClick = { onAction(ScreenActions.OnClickConfirm) }
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(onAction = {}, uiState = UIState())
}