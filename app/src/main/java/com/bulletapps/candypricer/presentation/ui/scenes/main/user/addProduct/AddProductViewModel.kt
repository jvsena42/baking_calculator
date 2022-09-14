package com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.parameters.CreateProductParameters
import com.bulletapps.candypricer.data.parameters.UpdateProductParameters
import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.model.MenuItemModel
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyListUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyTextUseCase
import com.bulletapps.candypricer.domain.usecase.product.CreateProductUseCase
import com.bulletapps.candypricer.domain.usecase.product.UpdateProductUseCase
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct.AddProductViewModel.ScreenEvent
import com.bulletapps.candypricer.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val getAllSuppliesUseCase: GetAllSuppliesUseCase,
    private val validateEmptyTextUseCase: ValidateEmptyTextUseCase,
    private val validateEmptyListUseCase: ValidateEmptyListUseCase,
    private val getUnitsUseCase: GetUnitsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
    ) : ViewModel(), EventFlow<ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()
    private val emptySupply = SupplyResponse(id = -1, name = "", quantity = ZERO_DOUBLE, value = ZERO_DOUBLE, null)
    private val selectedSuppliesList =  mutableListOf<MenuItemModel>()

    suspend fun setup(product: ProductResponse?) {
        getUnits()
        getSupplies()

        product?.let {
            uiState.isExpanded.value = false
            uiState.toolbarTitle.value = R.string.edit_product
            uiState.id.value = it.id
            uiState.name.value = it.name
            uiState.selectedUnit.value = it.unit.format()
            uiState.quantity.value = it.quantity.toString()
            uiState.profitMargin.value = it.profitMargin.toString()
            uiState.laborPrice.value = it.laborValue.toString()
            uiState.variableExpenses.value = it.variableExpenses.toString()
            selectedSuppliesList.addAll(it.supplies.toItemMenuList(it.amountQuantitySupply))
            uiState.selectedSupplies.value = selectedSuppliesList.toList()
        }
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    private suspend fun getUnits() {
        getUnitsUseCase().also {
            when (it) {
                is Resource.Success -> uiState.unities.value = it.data.orEmpty().format()
                is Resource.Error -> showToast(uiState.textToast.value)
            }
        }
    }

    private suspend fun getSupplies() {
        getAllSuppliesUseCase().also {
            when (it) {
                is Resource.Error -> showToast(it.message)
                is Resource.Success -> uiState.suppliesMenuList.value = it.data.orEmpty().toMutableList()
            }
        }
    }

    private fun onClickConfirm() {
        viewModelScope.launch {
            uiState.isLoading.value = true

            val nameResult = validateEmptyTextUseCase(text = uiState.name.value)
            val qntResult = validateEmptyTextUseCase(text = uiState.quantity.value)
            val unitResult = validateEmptyTextUseCase(text = uiState.selectedUnit.value.name)
            val laborPriceResult = validateEmptyTextUseCase(text = uiState.laborPrice.value)
            val variableExpensesResult = validateEmptyTextUseCase(text = uiState.variableExpenses.value)
            val profitMarginResult = validateEmptyTextUseCase(text = uiState.profitMargin.value)
            val supplyResult = validateEmptyListUseCase(list = uiState.selectedSupplies.value)

            when(nameResult) {
                is Resource.Error -> uiState.nameError.value = nameResult.message
                is Resource.Success -> uiState.nameError.value = null
            }
            when(qntResult) {
                is Resource.Error -> uiState.qntError.value = qntResult.message
                is Resource.Success -> uiState.qntError.value = null
            }
            when(unitResult) {
                is Resource.Error -> uiState.unitError.value = unitResult.message
                is Resource.Success -> uiState.unitError.value = null
            }
            when(laborPriceResult) {
                is Resource.Error -> uiState.laborError.value = laborPriceResult.message
                is Resource.Success -> uiState.laborError.value = null
            }
            when(variableExpensesResult) {
                is Resource.Error -> uiState.variableExpensesError.value = variableExpensesResult.message
                is Resource.Success -> uiState.variableExpensesError.value = null
            }
            when(profitMarginResult) {
                is Resource.Error -> uiState.profitMarginError.value = profitMarginResult.message
                is Resource.Success -> uiState.profitMarginError.value = null
            }
            when(supplyResult) {
                is Resource.Error -> showToast(supplyResult.message)
                is Resource.Success -> {}
            }

            if(
                nameResult is Resource.Success
                && unitResult is Resource.Success
                && qntResult is Resource.Success
                && laborPriceResult is Resource.Success
                && variableExpensesResult is Resource.Success
                && profitMarginResult is Resource.Success
                && supplyResult is Resource.Success
            ) {
                if (uiState.id.value.isNegative()) handleCreateProduct() else handleEditProduct()
            }
        }
    }

    private suspend fun handleEditProduct() {
        val parameters = UpdateProductParameters(
            id = uiState.id.value,
            name = uiState.name.value,
            unitId = uiState.selectedUnit.value.id.orZero(),
            quantity = uiState.quantity.value.formatDouble(),
            suppliesId = uiState.selectedSupplies.value.map { it.id },
            profitMargin = uiState.profitMargin.value.formatDouble().toPercent(),
            laborValue = uiState.laborPrice.value.formatDouble().toPercent(),
            variableExpenses = uiState.variableExpenses.value.formatDouble().toPercent(),
            amountQuantitySupply = uiState.selectedSupplies.value.map { it.qut.formatDouble() }
        )

        updateProductUseCase(parameters).also { result ->
            when (result) {
                is Resource.Success -> viewModelScope.sendEvent(ScreenEvent.GoBack)
                is Resource.Error -> showToast(result.message)
            }
        }
    }

    private suspend fun handleCreateProduct() {
        val createProductParameters = CreateProductParameters(
            name = uiState.name.value,
            quantity = uiState.quantity.value.formatDouble(),
            unitId = uiState.selectedUnit.value.id.orZero(),
            suppliesId = uiState.selectedSupplies.value.map { it.id },
            profitMargin = uiState.profitMargin.value.formatDouble().toPercent(),
            laborValue = uiState.laborPrice.value.formatDouble().toPercent(),
            variableExpenses = uiState.variableExpenses.value.formatDouble().toPercent(),
            amountQuantitySupply = uiState.selectedSupplies.value.map { it.qut.formatDouble() }
        )
        createProductUseCase(
            createProductParameters
        ).also { result ->
            when (result) {
                is Resource.Success -> viewModelScope.sendEvent(ScreenEvent.GoBack)
                is Resource.Error -> showToast(result.message)
            }
        }
    }

    private fun clearMenuSelection() {
        uiState.selectedSupplyItem.value = emptySupply
        uiState.supplyQnt.value = ""
    }

    private fun onClickConfirmMenu() {
        uiState.isDialogVisible.value = false

        val newItem = MenuItemModel(
            id = uiState.selectedSupplyItem.value.id,
            name = uiState.selectedSupplyItem.value.name,
            qut = uiState.supplyQnt.value
        )
        selectedSuppliesList.add(newItem) // TODO MERGE ITEMS
        uiState.selectedSupplies.value = selectedSuppliesList.toList()
        clearMenuSelection()
    }

    private fun onItemSelected(index: Int) {
        uiState.isExpanded.value = false
        uiState.selectedUnit.value = uiState.unities.value[index]
    }

    private fun onItemMenuSelected(index: Int) {
        uiState.isMenuSuppliesExpanded.value = false
        uiState.selectedSupplyItem.value = uiState.suppliesMenuList.value[index]
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
        is FieldsTexts.Quantity -> uiState.quantity.value = fieldsTexts.text
    }

    sealed class FieldsTexts {
        data class Name(val text: String) : FieldsTexts()
        data class Quantity(val text: String) : FieldsTexts()
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
        val id = MutableStateFlow(-1)
        val name = MutableStateFlow("")
        val quantity = MutableStateFlow("")
        val laborPrice = MutableStateFlow("")
        val profitMargin = MutableStateFlow("")
        val variableExpenses = MutableStateFlow("")
        val price = MutableStateFlow("")
        val unities = MutableStateFlow<List<UnitResponse>>(listOf())
        val isExpanded = MutableStateFlow(false)
        val isCreation = MutableStateFlow(true)
        val toolbarTitle = MutableStateFlow(R.string.add_product)
        val selectedUnit = MutableStateFlow(UnitResponse(0, ""))
        val selectedSupplies = MutableStateFlow(listOf<MenuItemModel>())
        val suppliesMenuList = MutableStateFlow(listOf<SupplyResponse>())
        val isMenuSuppliesExpanded = MutableStateFlow(false)
        val selectedSupplyItem = MutableStateFlow(SupplyResponse(id = -1, name = "", quantity = ZERO_DOUBLE, value = ZERO_DOUBLE, null))
        val supplyQnt = MutableStateFlow("")
        val isDialogVisible = MutableStateFlow(false)
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
        val isLoading = MutableStateFlow(false)
        val nameError = MutableStateFlow<UiText?>(null)
        val qntError = MutableStateFlow<UiText?>(null)
        val unitError = MutableStateFlow<UiText?>(null)
        val laborError = MutableStateFlow<UiText?>(null)
        val variableExpensesError = MutableStateFlow<UiText?>(null)
        val profitMarginError = MutableStateFlow<UiText?>(null)
    }
}

