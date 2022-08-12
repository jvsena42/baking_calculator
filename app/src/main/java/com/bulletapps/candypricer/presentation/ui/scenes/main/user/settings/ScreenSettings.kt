package com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings.SettingsViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings.SettingsViewModel.ScreenActions.OnClickLogout
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings.SettingsViewModel.ScreenEvent.GoBack
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings.SettingsViewModel.ScreenEvent.Login
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.LogoCircle
import com.bulletapps.candypricer.presentation.ui.widgets.OutlinedButtonCustom
import com.bulletapps.candypricer.presentation.ui.widgets.TextWithLabel

@Composable
fun ScreenSettings(
    viewModel: SettingsViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    Screen(viewModel::onAction)
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: SettingsViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                GoBack -> activity.onBackPressed()
                Login -> sharedViewModel.navigate(MainViewModel.Navigation.Login)
            }
        }
    }
}

@Composable
private fun Screen(
    onAction: (ScreenActions) -> Unit,
) {
    CandyPricerTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.settings)
                    )
                },
            )

            Spacer(Modifier.height(32.dp))

            LogoCircle()

            Spacer(Modifier.height(16.dp))

            Text(
                stringResource(R.string.hello_user),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            Text(
                stringResource(R.string.your_data),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextWithLabel(stringResource(R.string.email), "100", modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
                    Spacer(Modifier.height(16.dp))
                    TextWithLabel(stringResource(R.string.whatsapp), "100", modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
                    Spacer(Modifier.height(16.dp))
                    TextWithLabel(stringResource(R.string.valid_at), "100", modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
                }
            }

            Spacer(Modifier.weight(1f))

            OutlinedButtonCustom(
                text = stringResource(R.string.logout),
                onClick = { onAction(OnClickLogout) }
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {}
    )
}