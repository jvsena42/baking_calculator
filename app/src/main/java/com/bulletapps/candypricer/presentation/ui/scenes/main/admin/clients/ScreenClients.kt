package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.User
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
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
            BuildCalendar(uiState, onAction)
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

@Composable
fun BuildCalendar(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit
) {
    val isDialogVisible by uiState.isDialogVisible.collectAsState()

    val context = LocalContext.current

    val year: Int
    val month: Int
    val day: Int

    // TODO ALTERAR PARA A DATA DO MODEL
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/$month/$year"
        }, year, month, day
    ).apply {
        setOnDismissListener {
            onAction(ScreenActions.OnDismissDialog)
        }
    }

    if (isDialogVisible && !datePickerDialog.isShowing) {
        datePickerDialog.show()
    } else if (!isDialogVisible && datePickerDialog.isShowing) {
        datePickerDialog.dismiss()
    }
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