package com.bulletapps.candypricer.presentation.ui.scenes.main.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.login.LoginViewModel.*
import com.bulletapps.candypricer.presentation.ui.scenes.main.login.LoginViewModel.ScreenActions.OnClickConfirm
import com.bulletapps.candypricer.presentation.ui.scenes.main.login.LoginViewModel.ScreenActions.OnTextChanged
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton

@Composable
fun ScreenLogin(
    viewModel: LoginViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    Screen(viewModel.uiState, viewModel::onAction)
}


@Composable
private fun Screen(
    uiState: LoginViewModel.UIState,
    onAction: (ScreenActions) -> Unit,
) {
    CandyPricerTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            Spacer(Modifier.height(16.dp))

            MakeFieldEmail(onAction, uiState)

            MakeFieldPassword(onAction, uiState)

            NormalButton(
                text = stringResource(R.string.login),
                onClick = { onAction(OnClickConfirm) }
            )
        }
    }
}


@Composable
private fun MakeFieldEmail(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val name by uiState.email.collectAsState()

    OutlinedTextField(
        value = name,
        singleLine = true,
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Email(it))) },
        label = { Text(stringResource(R.string.email)) },
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldPassword(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val name by uiState.email.collectAsState()

    OutlinedTextField(
        value = name,
        singleLine = true,
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Password(it))) },
        label = { Text(stringResource(R.string.password)) },
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {},
        uiState = UIState()
    )
}