package com.bulletapps.candypricer.presentation.ui.scenes.main.user.payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bulletapps.candypricer.BuildConfig
import com.stripe.android.payments.paymentlauncher.PaymentLauncher

@Composable
fun ScreenPayment(
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Screen(uiState = uiState, onAction = viewModel::onAction)
}

@Composable
fun Screen(
    uiState: PaymentViewModel.PaymentUIState,
    onAction: (PaymentViewModel.ScreenActions) -> Unit,
) {
    val paymentLauncher = rememberPaymentLauncher(onAction)

}

@Composable
private fun rememberPaymentLauncher(
    onAction: (PaymentViewModel.ScreenActions) -> Unit,
): PaymentLauncher {
    return PaymentLauncher.rememberLauncher(
        publishableKey = BuildConfig.STRIPE_PUBLISHABLE_KEY,
        stripeAccountId = BuildConfig.STRIPE_ACCOUNT_ID,
        callback = { result -> onAction(PaymentViewModel.ScreenActions.OnPaymentResult(result)) }
    )
}