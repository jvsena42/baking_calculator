package com.bulletapps.candypricer.presentation.ui.scenes.main.user.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.presentation.util.EMPTY_STRING
import com.stripe.android.payments.paymentlauncher.PaymentResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUIState())
    val uiState = _uiState.asStateFlow()

    fun onAction(screenAction: ScreenActions) {
        viewModelScope.launch {
            when(screenAction) {
                is ScreenActions.OnPaymentResult -> handlePaymentResult(screenAction.result)
            }
        }
    }

    private fun makePayment() {

    }

    private fun handlePaymentResult(result: PaymentResult) {
        when(result) {
            PaymentResult.Canceled -> onPaymentCanceled()
            PaymentResult.Completed -> onPaymentCompleted()
            is PaymentResult.Failed -> onPaymentFailed()
        }
    }

    private fun onPaymentCanceled() {
        _uiState.update { paymentState ->
            paymentState.copy(
                isLoading = false,
                status = "Pagamento cancelado"
            )
        }
    }

    private fun onPaymentCompleted() {
        _uiState.update { paymentState ->
            paymentState.copy(
                isLoading = false,
                status = "Pagamento finalizado com sucesso"
            )
        }
    }

    private fun onPaymentFailed() {
        _uiState.update { paymentState ->
            paymentState.copy(
                isLoading = false,
                status = "Falha ao realizar pagamento"
            )
        }
    }

    sealed interface ScreenActions {
        data class OnPaymentResult(val result: PaymentResult) : ScreenActions
    }

    data class PaymentUIState(
        val clientSecret: String? = null,
        val isLoading: Boolean = false,
        val status: String = EMPTY_STRING
    )
}