package com.bulletapps.candypricer.presentation.ui.scenes.main.user.payment

import androidx.lifecycle.ViewModel
import com.bulletapps.candypricer.presentation.util.EMPTY_STRING
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUIState())
    val uiState = _uiState.asStateFlow()

    fun handlePaymentResult(result: PaymentSheetResult) {
        when(result) {
            PaymentSheetResult.Canceled -> onPaymentCanceled()
            PaymentSheetResult.Completed -> onPaymentCompleted()
            is PaymentSheetResult.Failed -> onPaymentFailed()
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

    data class PaymentUIState(
        val clientSecret: String? = null,
        val isLoading: Boolean = false,
        val status: String = EMPTY_STRING
    )
}