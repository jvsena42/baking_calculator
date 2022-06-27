package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ClientsViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ClientsViewModel.UIState
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardClient

@Composable
fun ScreenClients(
    viewModel: ClientsViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) { viewModel.setup() }
    Screen(viewModel.uiState, viewModel::onAction)
}

@Composable
private fun Screen(
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
                        text = stringResource(R.string.clients)
                    )
                },
            )
            ProductsList(uiState, onAction)
        }
    }
}

@Composable
private fun ProductsList(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit
) {
    val list by uiState.clients.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(list.size) { index ->
                val user = list[index]
                CardClient(
                    firstLabel = R.string.name_label,
                    secondLabel = R.string.expires_at_label,
                    firsName = user.name,
                    secondName = user.phone,
                    leftBTLabel = R.string.change_expiring_date,
                    rightBTLabel = R.string.send_message,
                    onClickLeft = { onAction(ScreenActions.OnClickChangeExpirationDate) },
                    onClickRight = { onAction(ScreenActions.OnClickMessage) }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Screen(
        onAction = {},
        uiState = UIState()
    )
}