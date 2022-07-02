package com.bulletapps.candypricer.presentation.ui.scenes.main.user.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.login.LoginViewModel.*
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.login.LoginViewModel.ScreenActions.OnClickConfirm
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.login.LoginViewModel.ScreenActions.OnTextChanged
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.LogoWithText
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton

@Composable
fun ScreenLogin(
    viewModel: LoginViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    viewModel: LoginViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.MainScreen -> sharedViewModel.navigate(MainViewModel.Navigation.MainMenu)
                is ScreenEvent.RegisterScreen -> sharedViewModel.navigate(MainViewModel.Navigation.Register)
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
                .background(color = MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(64.dp))

            LogoWithText()

            Spacer(Modifier.height(32.dp))

            MakeFieldEmail(onAction, uiState)

            Spacer(Modifier.height(4.dp))

            MakeFieldPassword(onAction, uiState)

            MakeButtonLogin(onAction, uiState)

            Spacer(modifier = Modifier.weight(1f))

            MakeRegisterText(onAction)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
private fun MakeButtonLogin(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val isLoading by uiState.isLoading.collectAsState()

    NormalButton(
        isEnabled = !isLoading,
        text = stringResource(R.string.login),
        onClick = { onAction(OnClickConfirm) }
    )
}

@Composable
private fun MakeRegisterText(onAction: (ScreenActions) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.dont_you_have_an_account))
        TextButton(onClick = { onAction(ScreenActions.OnClickRegister) }) {
            Text(stringResource(R.string.do_sign_in))
        }
    }
}


@Composable
private fun MakeFieldEmail(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val name by uiState.email.collectAsState()
    val error by uiState.emailError.collectAsState()

    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = name,
        singleLine = true,
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Email(it))) },
        label = { Text(error?.asString() ?: stringResource(R.string.email)) },
        isError = isError,
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldPassword(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val password by uiState.password.collectAsState()
    val passwordVisible by uiState.isPasswordVisible.collectAsState()
    val error by uiState.passwordError.collectAsState()

    val iconRef = if(passwordVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off

    OutlinedTextField(
        value = password,
        singleLine = true,
        visualTransformation = if (passwordVisible)  VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { onAction(ScreenActions.OnClickTogglePassword) }) {
                Icon(
                    painter = painterResource(id = iconRef),
                    contentDescription = stringResource(id = R.string.change_visibility),
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Password(it))) },
        label = { Text(error?.asString() ?: stringResource(R.string.password)) },
        isError = !error?.asString().isNullOrEmpty(),
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