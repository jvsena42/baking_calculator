package com.bulletapps.candypricer.presentation.ui.scenes.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bulletapps.candypricer.presentation.ui.scenes.main.menu.ScreenMenu
import com.bulletapps.candypricer.presentation.util.setNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setNavigation(
                startDestination = MainViewModel.Navigation.MainMenu.router,
                navGraphBuilder = ::navigationBuilder,
                navEventFlow = sharedViewModel.eventFlow,
                navEvent = ::navEvent
            )
        }
    }

    private fun navigationBuilder(builder: NavGraphBuilder) = builder.apply {
        composable(MainViewModel.Navigation.MainMenu.router) {
            ScreenMenu(sharedViewModel = sharedViewModel)
        }
        composable(MainViewModel.Navigation.Supplies.router) {
            ScreenMenu(sharedViewModel = sharedViewModel)
        }
    }

    private fun navEvent(navController: NavController, navScreen: MainViewModel.Navigation) {
        navController.navigate(route = navScreen.router)
    }
}