@file:OptIn(ExperimentalFoundationApi::class)

package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.login.LoginViewModel
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ScreenMenu(
    viewModel: MenuViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.setup(sharedViewModel.menuItems.value)
        sharedViewModel.setupMenu()
    }
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(viewModel, sharedViewModel)
}


@Composable
private fun EventConsumer(
    viewModel: MenuViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is MenuViewModel.ScreenEvent.ExpiredScreen -> sharedViewModel.navigate(MainViewModel.Navigation.Expired)
                is MenuViewModel.ScreenEvent.Navigate -> sharedViewModel.navigate(event.path)
            }
        }
    }
}

@Composable
fun Screen(
    uiState: MenuViewModel.UIState,
    onAction: (MenuViewModel.ScreenActions) -> Unit
) {
    CandyPricerTheme {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color= MaterialTheme.colors.background),
        ) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.main_menu)
                    )
                },
            )
            MenuGrid(uiState, onAction)
        }
    }
}

@Composable
fun MenuGrid(
    uiState: MenuViewModel.UIState,
    onAction: (MenuViewModel.ScreenActions) -> Unit
) {
    val menuItems by uiState.menuItems.collectAsState()

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxSize(),
        content = {
            items(menuItems.size) { index ->
                val item = menuItems[index]
                MenuItem(
                    item,
                    onClick = { onAction(MenuViewModel.ScreenActions.OnClickItem(item.path)) }
                )
            }
        }
    )
}