package com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.model.Supply
import com.bulletapps.candypricer.domain.model.UnitModel
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyTextUseCase
import com.bulletapps.candypricer.domain.usecase.product.CreateProductUseCase
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct.AddProductViewModel.*
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import com.bulletapps.candypricer.presentation.util.toMenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val getAllSuppliesUseCase: GetAllSuppliesUseCase,
    private val validateEmptyTextUseCase: ValidateEmptyTextUseCase,
    private val getUnitsUseCase: GetUnitsUseCase,
    private val createProductUseCase: CreateProductUseCase
    ) : ViewModel(), EventFlow<ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup() {
        getUnits()
        getSupplies()
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    private suspend fun getUnits() {
        val unitsResult = getUnitsUseCase()
        when(unitsResult) {
            is Resource.Error -> uiState.unities.value = unitsResult.data.orEmpty()
            is Resource.Success -> showToast(uiState.textToast.value)
        }
    }

    //TODO MOCK
    private suspend fun getSupplies() {
        val suppliesResult = getAllSuppliesUseCase()
        when(suppliesResult) {
            is Resource.Error -> showToast(suppliesResult.message)
            is Resource.Success -> uiState.suppliesList.value = suppliesResult.data.toMenuItem()
        }
    }

    //todo fix id
    private fun onClickConfirm() {

    }

    private fun clearMenuSelection() {
        uiState.selectedSupplyItem.value = ""
        uiState.supplyQnt.value = ""
    }

    private fun onClickConfirmMenu() {
        uiState.isDialogVisible.value = false

        val newItem = MenuItemModel(
            id = -1,
            name = uiState.selectedSupplyItem.value,
            qut = uiState.supplyQnt.value
        )
        val currentList = uiState.suppliesList.value.apply { add(newItem) }
        uiState.suppliesList.value = currentList

        clearMenuSelection()
    }

    private fun onItemSelected(index: Int) {
        uiState.isExpanded.value = false
        uiState.selectedUnit.value = uiState.unities.value[index].name
    }

    private fun onItemMenuSelected(index: Int) {
        uiState.isMenuSuppliesExpanded.value = false
        uiState.selectedSupplyItem.value = uiState.suppliesMenuList.value[index].name
    }

    private fun onChangeExpanded() {
        uiState.isExpanded.value = !uiState.isExpanded.value
    }
    private fun onChangeExpandedMenu() {
        uiState.isMenuSuppliesExpanded.value = !uiState.isMenuSuppliesExpanded.value
    }

    private fun onShowDialog() {
        uiState.isDialogVisible.value = true
    }
    private fun onDismissDialog() {
        uiState.isDialogVisible.value = false
    }

    private fun onTextChanged(fieldsTexts: FieldsTexts) = when(fieldsTexts) {
        is FieldsTexts.Name -> uiState.name.value = fieldsTexts.text
        is FieldsTexts.LaborPrice -> uiState.laborPrice.value = fieldsTexts.text
        is FieldsTexts.ProfitMargin -> uiState.profitMargin.value = fieldsTexts.text
        is FieldsTexts.VariableExpenses -> uiState.variableExpenses.value = fieldsTexts.text
        is FieldsTexts.SupplyQnt -> uiState.supplyQnt.value = fieldsTexts.text
    }

    sealed class FieldsTexts {
        data class Name(val text: String) : FieldsTexts()
        data class LaborPrice(val text: String) : FieldsTexts()
        data class ProfitMargin(val text: String) : FieldsTexts()
        data class VariableExpenses(val text: String) : FieldsTexts()
        data class SupplyQnt(val text: String) : FieldsTexts()
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnChangeExpanded -> onChangeExpanded()
        is ScreenActions.OnClickConfirm -> onClickConfirm()
        is ScreenActions.OnItemSelected -> onItemSelected(action.index)
        is ScreenActions.OnTextChanged -> onTextChanged(action.fieldsTexts)
        is ScreenActions.OnChangeExpandedMenu -> onChangeExpandedMenu()
        is ScreenActions.OnClickConfirmMenu -> onClickConfirmMenu()
        is ScreenActions.OnItemMenuSelected -> onItemMenuSelected(action.index)
        is ScreenActions.OnClickAddSupply -> onShowDialog()
        is ScreenActions.OnDismissDialog -> onDismissDialog()
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickConfirm : ScreenActions()
        object OnChangeExpanded : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
        data class OnItemSelected(val index: Int) : ScreenActions()
        data class OnItemMenuSelected(val index: Int) : ScreenActions()
        object OnClickConfirmMenu : ScreenActions()
        object OnChangeExpandedMenu: ScreenActions()
        object OnClickAddSupply: ScreenActions()
        object OnDismissDialog: ScreenActions()

    }

    class UIState {
        val name = MutableStateFlow("")
        val laborPrice = MutableStateFlow("")
        val profitMargin = MutableStateFlow("")
        val variableExpenses = MutableStateFlow("")
        val unities = MutableStateFlow<List<UnitResponse>>(listOf())
        val isExpanded = MutableStateFlow(false)
        val selectedUnit = MutableStateFlow("")
        val suppliesList = MutableStateFlow(mutableListOf<MenuItemModel>())
        val suppliesMenuList = MutableStateFlow(mutableListOf<SupplyResponse>())
        val isMenuSuppliesExpanded = MutableStateFlow(false)
        val selectedSupplyItem = MutableStateFlow("")
        val supplyQnt = MutableStateFlow("")
        val isDialogVisible = MutableStateFlow(false)
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))

    }

    data class MenuItemModel(
        val id: Int,
        val name: String,
        val qut: Int
    )
}

