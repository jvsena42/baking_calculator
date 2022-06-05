package com.bulletapps.candypricer.presentation.util

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow

fun <T> ComponentActivity.setNavigation(
    startDestination: String,
    navGraphBuilder: NavGraphBuilder.() -> Unit,
    navEventFlow: Flow<T>,
    navEvent: (navController: NavHostController, navScreen: T) -> Unit,
) {
    setContent {
        val navController = rememberNavController()
        LaunchedEffect(key1 = Unit) {
            navEventFlow.collect { navEvent(navController, it)}
        }
        NavHost(
            navController = navController,
            startDestination = startDestination,
            builder = navGraphBuilder
        )
    }
}