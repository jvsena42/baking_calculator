package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies.SuppliesViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies.SuppliesViewModel.ScreenEvent
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardSupply
import com.bulletapps.candypricer.presentation.ui.widgets.ScreenErrorRequest
import com.bulletapps.candypricer.presentation.ui.widgets.ScreenLoading
import com.bulletapps.candypricer.presentation.ui.widgets.TextEmpty

@Composable
fun ScreenSupplies(
    viewModel: SuppliesViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel,
    navigateSupplyDetail: (SupplyModel) -> Unit,
    navigateAddSupply: () -> Unit,
    navigateLogout: () -> Unit,
) {
    viewModel.setup()
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(viewModel, navigateSupplyDetail, navigateAddSupply, navigateLogout)
}

@Composable
private fun EventConsumer(
    viewModel: SuppliesViewModel,
    navigateSupplyDetail: (SupplyModel) -> Unit,
    navigateAddSupply: () -> Unit,
    navigateLogout: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.NavigateToAddSupply -> navigateAddSupply.invoke()
                is ScreenEvent.NavigateSupplyDetail -> navigateSupplyDetail.invoke(event.supply)
                is ScreenEvent.Login -> navigateLogout.invoke()
            }
        }
    }
}

@Composable
fun Screen(
    uiState: SuppliesUIState,
    onAction: (ScreenActions) -> Unit,
) {

    val screenState = uiState.screenState.collectAsState().value

    CandyPricerTheme {
        when (screenState) {
            is SuppliesUIState.ScreenState.Failure -> ErrorScreen(onAction)
            is SuppliesUIState.ScreenState.Loading -> ScreenLoading()
            is SuppliesUIState.ScreenState.ShowScreen -> ScreenContent(onAction, uiState)
        }
    }
}

@Composable
private fun ScreenContent(
    onAction: (ScreenActions) -> Unit,
    uiState: SuppliesUIState
) {
    Scaffold(
        backgroundColor = colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.supplies)
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = colors.secondary,
                contentColor = colors.background,
                onClick = { onAction(ScreenActions.OnClickAdd) },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_),
                    contentDescription = stringResource(id = R.string.add_supply),
                )
            }
        }
    ) {
        MakeList(uiState, onAction)
    }
}

@Composable
private fun MakeList(uiState: SuppliesUIState, onAction: (ScreenActions) -> Unit) {
    val list by uiState.suppliesList.collectAsState()

    if (list.isEmpty()) {
        TextEmpty(stringResource(R.string.add_supply_and_start_pricing))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                items(list.size) { index ->
                    val item = list[index]
                    CardSupply(item, onClick = { onAction(ScreenActions.OnClickSupply(item)) })
                }
            }
        )
    }

}

@Composable
private fun ErrorScreen(onAction: (ScreenActions) -> Unit) {
    ScreenErrorRequest(reloadAction = {
        onAction(
            ScreenActions.OnRetry
        )
    }, logoutAction = {
        onAction(ScreenActions.OnLogout)
    })
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {}, uiState = SuppliesUIState()
    )
}