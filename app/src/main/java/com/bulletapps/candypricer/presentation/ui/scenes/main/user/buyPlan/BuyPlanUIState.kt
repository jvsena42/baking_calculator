package com.bulletapps.candypricer.presentation.ui.scenes.main.user.buyPlan

import com.bulletapps.candypricer.domain.model.PlanModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.products.ProductsViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.util.EMPTY_STRING

data class BuyPlanUIState(
    val screenState: ScreenState = ScreenState.Loading,
    val planList: List<PlanModel> = emptyList(),
    val isLoading : Boolean = false,
    val isDialogVisible: Boolean = false,
    val textToast: String = EMPTY_STRING
) {
    sealed class ScreenState {
        object Loading : ScreenState()
        object ShowScreen : ScreenState()
        data class Failure(val retry: ScreenActions, val logout: ScreenActions): ScreenState()
    }
}