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
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplyDetail.SupplyDetailViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplyDetail.SupplyDetailViewModel.ScreenEvent
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.ui.widgets.OutlinedButtonCustom
import com.bulletapps.candypricer.presentation.ui.widgets.TextWithLabel
import com.bulletapps.candypricer.presentation.ui.widgets.Toast

@Composable
fun ScreenSupplyDetail(
    viewModel: SupplyDetailViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel,
    supplyModel: SupplyModel?,
    navigateUpdateSupply: () -> Unit
) {
    val activity = LocalContext.current as MainActivity
    viewModel.setup(supplyModel)
    Screen(
        viewModel.uiState,
        viewModel::onAction
    )
    EventConsumer(activity, viewModel, sharedViewModel, navigateUpdateSupply)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: SupplyDetailViewModel,
    sharedViewModel: MainViewModel,
    navigateUpdateSupply: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.NavigateUpdateSupply -> navigateUpdateSupply.invoke()
                is ScreenEvent.PopScreen -> activity.onBackPressed()
            }
        }
    }
}

@Composable
fun Screen(
    uiState: SupplyDetailViewModel.UIState,
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

            OutlinedButtonCustom(
                text = stringResource(R.string.delete),
                onClick = { onAction(ScreenActions.OnCLickDelete) })

            NormalButton(
                text = stringResource(R.string.edit),
                onClick = { onAction(ScreenActions.OnCLickEdit) })

            Spacer(Modifier.height(32.dp))

            val toastMessage by uiState.textToast.collectAsState()
            Toast(toastMessage.asString())
        }
    }
}

@Composable
private fun MakeCard(uiState: SupplyDetailViewModel.UIState) {
    val name by uiState.supplyName.collectAsState()
    val quantity by uiState.supplyQuantity.collectAsState()
    val unit by uiState.supplyUnitName.collectAsState()
    val price by uiState.supplyPrice.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextWithLabel(
                stringResource(R.string.name_label),
                name,
                modifier = Modifier.fillMaxWidth(),
                arrangement = Arrangement.SpaceBetween
            )

            Spacer(Modifier.height(8.dp))

            TextWithLabel(
                stringResource(R.string.quantity_label),
                quantity,
                modifier = Modifier.fillMaxWidth(),
                arrangement = Arrangement.SpaceBetween
            )

            Spacer(Modifier.height(8.dp))

            TextWithLabel(
                stringResource(R.string.measure_type_label),
                unit,
                modifier = Modifier.fillMaxWidth(),
                arrangement = Arrangement.SpaceBetween
            )

            Spacer(Modifier.height(8.dp))

            TextWithLabel(
                stringResource(R.string.cost_label),
                price,
                modifier = Modifier.fillMaxWidth(),
                arrangement = Arrangement.SpaceBetween
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {}, uiState = SupplyDetailViewModel.UIState()
    )
}