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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ClientsViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ClientsViewModel.UIState
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardClient
import com.bulletapps.candypricer.presentation.ui.widgets.DatePicker
import com.bulletapps.candypricer.presentation.ui.widgets.Toast
import com.bulletapps.candypricer.presentation.util.formatToDayMonthYear
import com.bulletapps.candypricer.presentation.util.openWhatsapp
import com.bulletapps.candypricer.presentation.util.toDate

@Composable
fun ScreenClients(
    viewModel: ClientsViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    LaunchedEffect(key1 = Unit) { viewModel.setup() }
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: ClientsViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ClientsViewModel.ScreenEvent.OpenWhatsApp -> activity.openWhatsapp(event.number)
            }
        }
    }
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
            BuildCalendar(uiState, onAction)
            CLientsList(uiState, onAction)
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
                    secondName = user.expirationDate.toDate().formatToDayMonthYear(),
                    leftBTLabel = R.string.change_expiring_date,
                    rightBTLabel = R.string.send_message,
                    onClickLeft = { onAction(ScreenActions.OnClickChangeExpirationDate(user)) },
                    onClickRight = { onAction(ScreenActions.OnClickMessage(user.phone)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Composable
fun BuildCalendar(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit
) {
    val isDialogVisible by uiState.isDialogVisible.collectAsState()
    val selectedUser by uiState.selectedUser.collectAsState()

    val calendar = selectedUser.expirationDate.toDate()
    val datePickerDialog = DatePicker()
    datePickerDialog.builder.apply {
        selectedDate = calendar.time
        onDateSelect = { result -> onAction(ScreenActions.OnConfirmDate(result)) }
        onCancel = { onAction(ScreenActions.OnDismissDialog) }
        onDismiss = { onAction(ScreenActions.OnDismissDialog) }
    }

    if (isDialogVisible) {
        val activity = LocalContext.current as MainActivity
        datePickerDialog.show(activity.supportFragmentManager)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Screen(
        onAction = {},
        uiState = UIState().apply {
            clients.value = listOf(
                UserResponse(
                    id = 0,
                    name = "Maria Júlia",
                    expirationDate = "123456",
                    phone = "86998006407",
                    isAdmin = false,
                    email = "abc",
                    isActive = true
                ),
            )
        }
    )
}