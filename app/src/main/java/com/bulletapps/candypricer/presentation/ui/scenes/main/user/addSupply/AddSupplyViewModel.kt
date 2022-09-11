package com.bulletapps.candypricer.presentation.ui.scenes.main.user.addSupply

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.parameters.CreateSupplyParameters
import com.bulletapps.candypricer.data.parameters.UpdateSupplyParameters
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyTextUseCase
import com.bulletapps.candypricer.domain.usecase.supply.CreateSupplyUseCase
import com.bulletapps.candypricer.domain.usecase.supply.UpdateSupplyUseCase
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSupplyViewModel @Inject constructor(
    private val getUnitsUseCase: GetUnitsUseCase,
    private val validateEmptyTextUseCase: ValidateEmptyTextUseCase,
    private val createSupplyUseCase: CreateSupplyUseCase,
    private val updateSupplyUseCase: UpdateSupplyUseCase,
    ) : ViewModel(), EventFlow<AddSupplyViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup(supply: SupplyResponse?) {
        val unitsResult = getUnitsUseCase()
        when(unitsResult) {
            is Resource.Success -> uiState.unities.value = unitsResult.data.orEmpty().format()
            is Resource.Error -> showToast(uiState.textToast.value)
        }

        supply?.let { supply ->
            uiState.id.value = supply.id
            uiState.name.value = supply.name
            uiState.selectedUnit.value = supply.unit
            uiState.quantity.value = supply.quantity.round()
            uiState.price.value = supply.value.round()
        }
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnChangeExpanded -> onChangeExpanded()
        is ScreenActions.OnClickConfirm -> onClickConfirm()
        is ScreenActions.OnItemSelected -> onItemSelected(action.index)
        is ScreenActions.OnTextChanged -> onTextChanged(action.fieldsTexts)
    }

    private fun onClickConfirm() {

        viewModelScope.launch {
            uiState.isLoading.value = true

            val nameResult = validateEmptyTextUseCase(text = uiState.name.value)
            val unitResult = validateEmptyTextUseCase(text = uiState.selectedUnit.value?.name.orEmpty())
            val qntResult = validateEmptyTextUseCase(text = uiState.quantity.value)
            val priceResult = validateEmptyTextUseCase(text = uiState.price.value)

            when(nameResult) {
                is Resource.Error -> uiState.nameError.value = nameResult.message
                is Resource.Success -> uiState.nameError.value = null
            }
            when(unitResult) {
                is Resource.Error -> uiState.unitError.value = unitResult.message
                is Resource.Success -> uiState.unitError.value = null
            }
            when(qntResult) {
                is Resource.Error -> uiState.qntError.value = qntResult.message
                is Resource.Success -> uiState.qntError.value = null
            }
            when(priceResult) {
                is Resource.Error -> uiState.priceError.value = priceResult.message
                is Resource.Success -> uiState.priceError.value = null
            }

            uiState.isLoading.value = false

            if(
                nameResult is Resource.Success
                && unitResult is Resource.Success
                && qntResult is Resource.Success
                && priceResult is Resource.Success
            ) {
                if (uiState.id.value.isNegative()) handleCreateSupply() else handleEditSupply()
            }
        }
    }

    private suspend fun handleEditSupply() {
        val parameters = UpdateSupplyParameters(
            id = uiState.id.value.orNegative(),
            name = uiState.name.value,
            quantity = uiState.quantity.value.formatDouble(),
            price = uiState.price.value.formatDouble(),
            unitId = uiState.selectedUnit.value?.id.orNegative(),
        )
        updateSupplyUseCase(
            parameters
        ).also { result ->
            val updatedSupply = SupplyResponse(
                parameters.id,
                parameters.name,
                parameters.quantity,
                parameters.price,
                uiState.selectedUnit.value,
            )
            when (result) {
                is Resource.Success -> viewModelScope.sendEvent(ScreenEvent.UpdateSupply(updatedSupply))
                is Resource.Error -> showToast(result.message)
            }
        }
    }

    private suspend fun handleCreateSupply() {
        createSupplyUseCase(
            CreateSupplyParameters(
                name = uiState.name.value,
                quantity = uiState.quantity.value.formatDouble(),
                price = uiState.price.value.formatDouble(),
                unitId = uiState.selectedUnit.value?.id.orZero(),
            )
        ).also { result ->
            when (result) {
                is Resource.Success -> viewModelScope.sendEvent(ScreenEvent.GoBack)
                is Resource.Error -> showToast(result.message)
            }
        }
    }

    private fun onItemSelected(index: Int) {
        uiState.isExpanded.value = false
        uiState.selectedUnit.value = uiState.unities.value[index]
    }

    private fun onChangeExpanded() {
        uiState.isExpanded.value = !uiState.isExpanded.value
    }

    private fun onTextChanged(fieldsTexts: FieldsTexts) = when(fieldsTexts) {
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
        val id = MutableStateFlow(NEGATIVE)
        val quantity = MutableStateFlow("")
        val price = MutableStateFlow("")
        val unities = MutableStateFlow<List<UnitResponse>>(listOf())
        val isExpanded = MutableStateFlow(false)
        val isLoading = MutableStateFlow(false)
        val selectedUnit = MutableStateFlow<UnitResponse?>(null)
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
        val nameError = MutableStateFlow<UiText?>(null)
        val unitError = MutableStateFlow<UiText?>(null)
        val qntError = MutableStateFlow<UiText?>(null)
        val priceError = MutableStateFlow<UiText?>(null)
    }

    sealed class ScreenActions {
        object OnClickConfirm : ScreenActions()
        object OnChangeExpanded : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
        data class OnItemSelected(val index: Int) : ScreenActions()
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        data class UpdateSupply(val supply: SupplyResponse) : ScreenEvent()
    }
}

