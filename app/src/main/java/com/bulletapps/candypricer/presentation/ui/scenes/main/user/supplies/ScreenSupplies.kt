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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies.SuppliesViewModel.*
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardSupply

@Composable
fun ScreenSupplies(
    viewModel: SuppliesViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    LaunchedEffect(key1 = Unit) { viewModel.setup() }
    Screen(
        viewModel.uiState,
        viewModel::onAction
    )
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: SuppliesViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.NavigateToAddSupply -> sharedViewModel.navigate(MainViewModel.Navigation.AddSupply)
                is ScreenEvent.NavigateSupplyDetail -> {
                    sharedViewModel.selectedSupply.value = event.supply
                    sharedViewModel.navigate(MainViewModel.Navigation.SupplyDetail)
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

    CandyPricerTheme {
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
}

@Composable
private fun MakeList(uiState: UIState, onAction: (ScreenActions) -> Unit) {
    val list by uiState.suppliesList.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(list.size) { index ->
                val item = list[index]
                CardSupply(item, onClick = { onAction(ScreenActions.OnClickSupply(item)) })
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {}, uiState = UIState()
    )
}