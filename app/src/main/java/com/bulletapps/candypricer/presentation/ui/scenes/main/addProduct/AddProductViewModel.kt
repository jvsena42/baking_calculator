package com.bulletapps.candypricer.presentation.ui.scenes.main.addProduct

import androidx.lifecycle.ViewModel
import com.bulletapps.candypricer.domain.model.UnityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor() : ViewModel() {

    val uiState = UIState()

    fun setup() {
        uiState.unities.value = listOf(
            UnityModel("", "Und."),
            UnityModel("", "Kg"),
            UnityModel("", "g"),
            UnityModel("", "mg"),
            UnityModel("", "L"),
            UnityModel("", "ml"),
        )
    }

    fun onClickConfirm() {

    }

    fun onItemSelected(index: Int) {
        uiState.isExpanded.value = false
        uiState.selectedUnit.value = uiState.unities.value[index].label
    }

    fun onChangeExpanded() {
        uiState.isExpanded.value = !uiState.isExpanded.value
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
        val unities = MutableStateFlow<List<UnityModel>>(listOf())
        val isExpanded = MutableStateFlow(false)
        val selectedUnit = MutableStateFlow("")
    }
}

