package com.bulletapps.candypricer.presentation.ui.scenes.main.user.payment

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.BuildConfig
import com.stripe.android.payments.paymentlauncher.PaymentLauncher

@Composable
fun ScreenPayment(
    viewModel: PaymentViewModel = hiltViewModel()
) {

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