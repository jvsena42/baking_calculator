package com.bulletapps.candypricer.presentation.ui.scenes.main.addSupply

import androidx.lifecycle.ViewModel
import com.bulletapps.candypricer.data.model.Supply
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddSupplyViewModel @Inject constructor() : ViewModel() {

    val uiState = UIState()

    fun onConfirmClicked() {

    }

    fun onTextChanged(fieldsTexts: FieldsTexts) = when(fieldsTexts) {
        is FieldsTexts.Name -> uiState.name.value = fieldsTexts.text
        is FieldsTexts.Price -> uiState.price.value = fieldsTexts.text
        is FieldsTexts.Quantity -> uiState.quantity.value = fieldsTexts.text
    }

    sealed class FieldsTexts {
        data class Name(val text: String) : FieldsTexts()
        data class Quantity(val text: String) : FieldsTexts()
        data class Price(val text: String) : FieldsTexts()
    }

    class UIState {
        val name = MutableStateFlow("")
        val quantity = MutableStateFlow("")
        val price = MutableStateFlow("")
    }
}

