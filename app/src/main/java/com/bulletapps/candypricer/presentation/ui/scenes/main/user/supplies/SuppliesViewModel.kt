package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies

import androidx.lifecycle.ViewModel
import com.bulletapps.candypricer.domain.model.Supply
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SuppliesViewModel @Inject constructor() : ViewModel() {

    val suppliesList: MutableStateFlow<List<Supply>> = MutableStateFlow(
        listOf(
            Supply(id = 0, name = "Leite Condensado Caixa", price = "R$ 5,00", quantity = 1.0, unitType = "Unidade" ),
            Supply(id = 1, name = "Creme de leite Caixa", price = "R$ 6,00", quantity = 1.0, unitType = "Unidade" ),
            Supply(id = 2, name = "Chocolate em pó", price = "R$ 38,00", quantity = 500.0, unitType = "Gramas" ),
        ),
    )
}

