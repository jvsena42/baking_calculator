package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.domain.model.UserModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ClientsViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ClientsViewModel.UIState
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.*
import com.bulletapps.candypricer.presentation.util.formatToDayMonthYear
import com.bulletapps.candypricer.presentation.util.openWhatsapp
import com.bulletapps.candypricer.presentation.util.toDate
import com.bulletapps.candypricer.presentation.util.visualTransformation.MaskPatterns
import com.bulletapps.candypricer.presentation.util.visualTransformation.MaskVisualTransformation

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
            ClientsList(uiState, onAction)
            DisplayToast(uiState)
        }
    }
}


@Composable
private fun DisplayToast(uiState: UIState) {
    val toastMessage by uiState.textToast.collectAsState()
    val message = toastMessage.asString()
    Toast(message)
}

@Composable
private fun ClientsList(
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
                    secondLabel = R.string.email_label,
                    thirdLabel = R.string.expires_at_label,
                    firsName = user.name,
                    secondName = user.email,
                    thirdName = user.expirationDate.toDate().formatToDayMonthYear(),
                    leftBTLabel = R.string.change_expiring_date,
                    rightBTLabel = R.string.send_message,
                    onClickLeft = { onAction(ScreenActions.OnClickChangeExpirationDate(user.id)) },
                    onClickRight = { onAction(ScreenActions.OnClickMessage(user.phone)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                MakeDialog(onAction, uiState)
            }
        }
    )
}

@Composable
private fun MakeDialog(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val isVisible by uiState.isDialogVisible.collectAsState()
    val date by uiState.date.collectAsState()

    if (isVisible) {
        Dialog(
            onDismissRequest = { onAction(ScreenActions.OnDismissDialog) },
            DialogProperties()
        ) {

            Card(
                shape = MaterialTheme.shapes.medium
            ) {

                Column(modifier = Modifier.padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                    OutlinedTextField(
                        value = date,
                        singleLine = true,
                        onValueChange = { onAction(
                            ScreenActions.OnTextChanged(
                                ClientsViewModel.FieldsTexts.Date(it))
                        ) },
                        visualTransformation = MaskVisualTransformation(MaskPatterns.DD_MM_YYY_MASK),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text(stringResource(R.string.day_month_year)) },
                        label = { Text(stringResource(R.string.expiration)) },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    NormalButton(
                        text = stringResource(R.string.confirm),
                        onClick = { onAction(ScreenActions.OnConfirmDate) }
                    )

                    OutlinedButtonCustom(
                        modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 0.dp),
                        text = stringResource(R.string.cancel),
                        onClick = { onAction(ScreenActions.OnDismissDialog) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    Screen(
        onAction = {},
        uiState = UIState().apply {
            clients.value = listOf(
                UserModel(
                    id = 1,
                    name = "Maria Júlia",
                    expirationDate = "123456",
                    phone = "86998006407",
                    isAdmin = false,
                    email = "abc",
                    isActive = true
                ),
            )
            isDialogVisible.value = true
        }
    )
}