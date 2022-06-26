package com.bulletapps.candypricer.presentation.ui.scenes.main.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.settings.SettingsViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.settings.SettingsViewModel.ScreenActions.OnClickLogout
import com.bulletapps.candypricer.presentation.ui.scenes.main.settings.SettingsViewModel.ScreenEvent.GoBack
import com.bulletapps.candypricer.presentation.ui.scenes.main.settings.SettingsViewModel.ScreenEvent.Login
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.LogoCircle
import com.bulletapps.candypricer.presentation.ui.widgets.OutlinedButtonCustom

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

            Spacer(Modifier.height(64.dp))

            LogoCircle()

            Spacer(Modifier.weight(1f))

            OutlinedButtonCustom(
                text = stringResource(R.string.logout),
                onClick = { onAction(OnClickLogout) }
            )

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