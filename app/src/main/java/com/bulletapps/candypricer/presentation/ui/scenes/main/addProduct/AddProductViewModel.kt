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
        is FieldsTexts.LaborPrice -> uiState.laborPrice.value = fieldsTexts.text
        is FieldsTexts.ProfitMargin -> uiState.profitMargin.value = fieldsTexts.text
        is FieldsTexts.VariableExpenses -> uiState.variableExpenses.value = fieldsTexts.text
    }

    sealed class FieldsTexts {
        data class Name(val text: String) : FieldsTexts()
        data class LaborPrice(val text: String) : FieldsTexts()
        data class ProfitMargin(val text: String) : FieldsTexts()
        data class VariableExpenses(val text: String) : FieldsTexts()
    }

    class UIState {
        val name = MutableStateFlow("")
        val laborPrice = MutableStateFlow("")
        val profitMargin = MutableStateFlow("")
        val variableExpenses = MutableStateFlow("")
        val unities = MutableStateFlow<List<UnityModel>>(listOf())
        val isExpanded = MutableStateFlow(false)
        val selectedUnit = MutableStateFlow("")
    }
}

