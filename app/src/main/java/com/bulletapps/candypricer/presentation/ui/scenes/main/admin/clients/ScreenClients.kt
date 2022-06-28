package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.User
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ClientsViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ClientsViewModel.UIState
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardClient
import com.bulletapps.candypricer.presentation.util.formatToDayMonthYear
import java.util.*

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
            CLientsList(uiState, onAction)
        }
    }
}

@Composable
private fun CLientsList(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit
) {
    val list by uiState.clients.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(list.size) { index ->
                val user = list[index]
                CardClient(
                    firstLabel = R.string.name_label,
                    secondLabel = R.string.expires_at_label,
                    firsName = user.name,
                    secondName = user.expirationDate.time.formatToDayMonthYear(),
                    leftBTLabel = R.string.change_expiring_date,
                    rightBTLabel = R.string.send_message,
                    onClickLeft = { onAction(ScreenActions.OnClickChangeExpirationDate) },
                    onClickRight = { onAction(ScreenActions.OnClickMessage) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Screen(
        onAction = {},
        uiState = UIState().apply {
            clients.value = listOf(
                User(
                    name = "Maria Júlia",
                    expirationDate = Calendar.getInstance(),
                    phone = "86998006407"
                ),
                User(
                    name = "Ana Maria Braga",
                    expirationDate = Calendar.getInstance(),
                    phone = "86998006407"
                ),
            )
        }
    )
}