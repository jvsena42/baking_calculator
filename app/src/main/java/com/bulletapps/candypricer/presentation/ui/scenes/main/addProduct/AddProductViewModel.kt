package com.bulletapps.candypricer.presentation.ui.scenes.main.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.data.model.Supply
import com.bulletapps.candypricer.domain.model.UnityModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.addProduct.AddProductViewModel.*
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor() : ViewModel(), EventFlow<ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    fun setup() = viewModelScope.launch {
        getUnities()
        getSupplies()
    }

    //TODO MOCK
    private suspend fun getUnities() {
        uiState.unities.value = listOf(
            UnityModel("", "Und."),
            UnityModel("", "Kg"),
            UnityModel("", "g"),
            UnityModel("", "mg"),
            UnityModel("", "L"),
            UnityModel("", "ml"),
        )
    }

    //TODO MOCK
    private suspend fun getSupplies() {
        uiState.suppliesMenuList.value = mutableListOf(
            Supply(id = 0, name = "Leite Condensado Caixa", price = "R$ 5,00", quantity = 1.0, unitType = "Unidade" ),
            Supply(id = 1, name = "Creme de leite Caixa", price = "R$ 6,00", quantity = 1.0, unitType = "Unidade" ),
            Supply(id = 2, name = "Chocolate em pó", price = "R$ 38,00", quantity = 500.0, unitType = "Gramas" ),
        )
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
            id = "",
            name = uiState.selectedSupplyItem.value,
            qut = uiState.supplyQnt.value
        )
        val currentList = uiState.suppliesList.value.apply { add(newItem) }
        uiState.suppliesList.value = currentList

        clearMenuSelection()
    }

    private fun onItemSelected(index: Int) {
        uiState.isExpanded.value = false
        uiState.selectedUnit.value = uiState.unities.value[index].label
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
        val unities = MutableStateFlow<List<UnityModel>>(listOf())
        val isExpanded = MutableStateFlow(false)
        val selectedUnit = MutableStateFlow("")
        val suppliesList = MutableStateFlow(mutableListOf<MenuItemModel>())
        val suppliesMenuList = MutableStateFlow(mutableListOf<Supply>())
        val isMenuSuppliesExpanded = MutableStateFlow(false)
        val selectedSupplyItem = MutableStateFlow("")
        val supplyQnt = MutableStateFlow("")
        val isDialogVisible = MutableStateFlow(false)
    }

    data class MenuItemModel(
        val id: String,
        val name: String,
        val qut: String
    )
}

