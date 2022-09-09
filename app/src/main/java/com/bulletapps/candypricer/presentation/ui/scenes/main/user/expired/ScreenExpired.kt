package com.bulletapps.candypricer.presentation.ui.scenes.main.user.expired

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.expired.ExpiredViewModel.*
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings.SettingsViewModel
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.*
import com.bulletapps.candypricer.presentation.util.openWhatsapp

@Composable
fun ScreenExpired(
    viewModel: ExpiredViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: ExpiredViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.OpenWhatsApp -> activity.openWhatsapp(event.number, activity.getString(R.string.update_plan_text))
                ScreenEvent.Login -> sharedViewModel.navigate(MainViewModel.Navigation.Login)
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
            Spacer(modifier = Modifier.height(42.dp))

            LogoCircle()

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                stringResource(R.string.your_account_has_expiring),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(42.dp))

            Text(
                stringResource(R.string.send_a_message_to),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextButton(onClick = { onAction(ScreenActions.OnClickMessage) }) {
                Text(
                    stringResource(R.string.whatsapp_number),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                stringResource(R.string.and_renew_your_account),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButtonCustom(
                stringResource(R.string.i_want_update_my_account),
                onClick = { onAction(ScreenActions.OnClickDelete) }
            )

            OutlinedButtonCustom(
                text = stringResource(R.string.logout),
                onClick = { onAction(ScreenActions.OnClickLogout) }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MakeDialog(uiState: UIState, onAction: (ScreenActions) -> Unit) {
    val isVisible by uiState.isDialogVisible.collectAsState()

    if (isVisible) {
        Dialog(
            onDismissRequest = { onAction(ScreenActions.OnDismissDialog) },
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Card(
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextTitle(stringResource(R.string.are_you_sure, 32.sp))
                    TextTitle(stringResource(R.string.warning_emoji, 64.sp))
                    TextTitle(stringResource(R.string.all_your_data_will_be_lost, 24.sp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        OutlinedButtonCustom(
                            text = stringResource(R.string.no),
                            modifier = Modifier.padding(16.dp),
                            onClick = { onAction(ScreenActions.OnDismissDialog) }
                        )
                        NormalButton(
                            text = stringResource(R.string.yes),
                            modifier = Modifier.padding(16.dp),
                            onClick = { onAction(ScreenActions.OnConfirmDelete) }
                        )
                    }
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
            isDialogVisible.value = true
        }
    )
}