package com.bulletapps.candypricer.domain.model

import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel

data class MenuModel(
    val labelRef: Int,
    val iconRef: Int,
    var path: MainViewModel.Navigation
)