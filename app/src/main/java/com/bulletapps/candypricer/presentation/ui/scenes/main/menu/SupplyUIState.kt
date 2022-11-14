package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.presentation.util.NEGATIVE
import kotlinx.coroutines.flow.MutableStateFlow

class SupplyUIState(
    val id: MutableStateFlow<Int> = MutableStateFlow(NEGATIVE),
    val supplyName: MutableStateFlow<String> = MutableStateFlow(""),
    val supplyQuantity: MutableStateFlow<String> = MutableStateFlow(""),
    val supplyUnitName: MutableStateFlow<String> = MutableStateFlow(""),
    val supplyPrice: MutableStateFlow<String> = MutableStateFlow(""),
    val textToast: MutableStateFlow<UiText> = MutableStateFlow(UiText.DynamicString(""))
)