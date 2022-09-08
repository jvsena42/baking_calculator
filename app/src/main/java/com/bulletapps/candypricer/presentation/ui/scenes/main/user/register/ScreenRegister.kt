package com.bulletapps.candypricer.presentation.ui.scenes.main.user.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.register.RegisterViewModel.*
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.register.RegisterViewModel.ScreenActions.OnClickConfirm
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.register.RegisterViewModel.ScreenActions.OnTextChanged
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.LogoWithText
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.ui.widgets.TextButtonCustom
import com.bulletapps.candypricer.presentation.ui.widgets.Toast
import com.bulletapps.candypricer.presentation.util.navigateUrl
import com.bulletapps.candypricer.presentation.util.visualTransformation.MaskPatterns.BR_PHONE_MASK
import com.bulletapps.candypricer.presentation.util.visualTransformation.MaskVisualTransformation

@Composable
fun ScreenRegister(
    viewModel: RegisterViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: RegisterViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                ScreenEvent.MainScreen -> sharedViewModel.navigate(MainViewModel.Navigation.MainMenu)
                ScreenEvent.NavigateTerms -> activity.navigateUrl(activity.getString(R.string.terms_url))
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

            LogoWithText(text = stringResource(R.string.do_sign_in))

            Spacer(Modifier.height(32.dp))

            MakeFieldName(onAction, uiState)

            Spacer(Modifier.height(4.dp))

            MakeFieldEmail(onAction, uiState)

            Spacer(Modifier.height(4.dp))

            MakeFieldPhone(onAction, uiState)

            Spacer(Modifier.height(4.dp))

            MakeFieldPassword(onAction, uiState)

            Spacer(Modifier.height(4.dp))

            MakeFieldConfirmPassword(onAction, uiState)

            Spacer(Modifier.weight(1f))

            MakeCheckBox(onAction, uiState)

            MakeConfirmButton(onAction, uiState)

            Spacer(Modifier.height(16.dp))

            DisplayToast(uiState)
        }
    }
}


@Composable
private fun MakeCheckBox(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val isChecked by uiState.isChecked.collectAsState()

    Row {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onAction(ScreenActions.OnCheckChanged(it)) }
        )
        TextButtonCustom(stringResource(R.string.accept_user_terms), onClick = {
            onAction(ScreenActions.OnClickTerms)
        })
    }


}

@Composable
private fun DisplayToast(uiState: UIState) {
    val toastMessage by uiState.textToast.collectAsState()
    val message = toastMessage.asString()
    if (message.isNotEmpty()) Toast(message)
}

@Composable
private fun MakeConfirmButton(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val isLoading by uiState.isLoading.collectAsState()

    NormalButton(
        isEnabled = !isLoading,
        text = stringResource(R.string.confirm),
        onClick = { onAction(OnClickConfirm) }
    )
}

@Composable
private fun MakeFieldName(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val name by uiState.name.collectAsState()
    val error by uiState.nameError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = name,
        singleLine = true,
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Name(it))) },
        label = { Text(error?.asString() ?: stringResource(R.string.name)) },
        isError = isError,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldEmail(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val email by uiState.email.collectAsState()
    val error by uiState.emailError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = email,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Email(it))) },
        label = { Text(error?.asString() ?: stringResource(R.string.email)) },
        isError = isError,
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldPhone(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val phone by uiState.phone.collectAsState()
    val error by uiState.phoneError.collectAsState()
    val isError = !error?.asString().isNullOrEmpty()

    OutlinedTextField(
        value = phone,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Phone(it))) },
        visualTransformation = MaskVisualTransformation(BR_PHONE_MASK),
        placeholder = { Text(stringResource(R.string.phone_placeholder)) },
        label = { Text(error?.asString() ?: stringResource(R.string.whatsapp)) },
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        visualTransformation = if (passwordVisible)  VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { onAction(OnTextChanged(FieldsTexts.Password(it))) },
        label = { Text(error?.asString() ?: stringResource(R.string.password)) },
        trailingIcon = {
            IconButton(onClick = { onAction(ScreenActions.OnClickTogglePassword) }) {
                Icon(
                    painter = painterResource(id = iconRef),
                    contentDescription = stringResource(id = R.string.change_visibility),
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        isError = !error?.asString().isNullOrEmpty(),
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    )
}

@Composable
private fun MakeFieldConfirmPassword(onAction: (ScreenActions) -> Unit, uiState: UIState) {
    val confirmPassword by uiState.confirmPassword.collectAsState()
    val passwordVisible by uiState.isPasswordConfirmVisible.collectAsState()
    val error by uiState.passwordConfError.collectAsState()

    val iconRef = if(passwordVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off

    OutlinedTextField(
        value = confirmPassword,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions { onAction(OnClickConfirm) },
        visualTransformation = if (passwordVisible)  VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { onAction(OnTextChanged(FieldsTexts.ConfirmPassword(it))) },
        label = { Text(error?.asString() ?: stringResource(R.string.repeat_password)) },
        trailingIcon = {
            IconButton(onClick = { onAction(ScreenActions.OnClickTogglePasswordConf) }) {
                Icon(
                    painter = painterResource(id = iconRef),
                    contentDescription = stringResource(id = R.string.change_visibility),
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
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