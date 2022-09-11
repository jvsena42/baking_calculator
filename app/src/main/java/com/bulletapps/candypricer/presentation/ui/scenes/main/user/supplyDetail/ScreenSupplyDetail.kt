package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplyDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplyDetail.SupplyDetailViewModel.*
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.ui.widgets.OutlinedButtonCustom
import com.bulletapps.candypricer.presentation.ui.widgets.TextWithLabel
import com.bulletapps.candypricer.presentation.ui.widgets.Toast
import com.bulletapps.candypricer.presentation.util.formatUnit
import com.bulletapps.candypricer.presentation.util.toCurrency

@Composable
fun ScreenSupplyDetail(
    viewModel: SupplyDetailViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    LaunchedEffect(key1 = Unit) { viewModel.setup(sharedViewModel.selectedSupply.value) }
    Screen(
        viewModel.uiState,
        viewModel::onAction
    )
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: SupplyDetailViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.NavigateToAddSupply -> sharedViewModel.navigate(MainViewModel.Navigation.AddSupply)
                is ScreenEvent.PopScreen -> activity.onBackPressed()
            }
        }
    }
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
                .background(color = colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.supply)
                    )
                },
            )

            Spacer(Modifier.height(32.dp))

            MakeCard(uiState)

            Spacer(Modifier.weight(1f))

            OutlinedButtonCustom(text = stringResource(R.string.delete), onClick = { onAction(ScreenActions.OnCLickDelete) })
            NormalButton(text = stringResource(R.string.edit), onClick = { onAction(ScreenActions.OnCLickEdit) })
            Spacer(Modifier.height(32.dp))

            val toastMessage by uiState.textToast.collectAsState()
            Toast(toastMessage.asString())
        }
    }
}

@Composable
private fun MakeCard(uiState: UIState) {
    val supply by uiState.supply.collectAsState()

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextWithLabel(stringResource(R.string.name_label), supply.name, modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
            Spacer(Modifier.height(8.dp))
            TextWithLabel(stringResource(R.string.quantity_label), supply.quantity.toString(), modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
            Spacer(Modifier.height(8.dp))
            TextWithLabel(stringResource(R.string.measure_type_label), supply.unit?.name.formatUnit(), modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
            Spacer(Modifier.height(8.dp))
            TextWithLabel(stringResource(R.string.cost_label), supply.value.toCurrency(), modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {}, uiState = UIState()
    )
}