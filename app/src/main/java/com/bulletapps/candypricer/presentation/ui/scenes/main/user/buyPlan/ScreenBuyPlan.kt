package com.bulletapps.candypricer.presentation.ui.scenes.main.user.buyPlan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
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
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.buyPlan.BuyPlanViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.buyPlan.BuyPlanViewModel.ScreenEvent
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.LogoCircle
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.ui.widgets.OutlinedButtonCustom
import com.bulletapps.candypricer.presentation.ui.widgets.TextButtonCustom
import com.bulletapps.candypricer.presentation.ui.widgets.TextTitle
import com.bulletapps.candypricer.presentation.ui.widgets.Toast
import com.bulletapps.candypricer.presentation.util.openWhatsapp

@Composable
fun ScreenExpired(
    viewModel: BuyPlanViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    val uiState by viewModel.uiState.collectAsState()

    Screen(uiState, viewModel::onAction)
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: BuyPlanViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.OpenWhatsApp -> activity.openWhatsapp(event.number, activity.getString(R.string.update_plan_text))
                is ScreenEvent.Login -> sharedViewModel.navigate(MainViewModel.Navigation.Login)
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: BuyPlanUIState,
    onAction: (ScreenActions) -> Unit,
) {

    CandyPricerTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            LogoCircle()

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                stringResource(R.string.choose_your_plan),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colors.background),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item {
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

            MakeDialog(uiState, onAction)

            DisplayToast(uiState)
        }
    }
}

@Composable
private fun DisplayToast(uiState: BuyPlanUIState) {
    val toastMessage = uiState.textToast
    if (toastMessage.isNotEmpty()) Toast(toastMessage)
}

@Composable
private fun MakeDialog(uiState: BuyPlanUIState, onAction: (ScreenActions) -> Unit) {
    val isVisible = uiState.isDialogVisible

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
        uiState = BuyPlanUIState()
    )
}