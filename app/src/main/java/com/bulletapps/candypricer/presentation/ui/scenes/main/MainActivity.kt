package com.bulletapps.candypricer.presentation.ui.scenes.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients.ScreenClients
import com.bulletapps.candypricer.presentation.ui.scenes.main.menu.ScreenMenu
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct.ScreenAddProduct
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addSupply.ScreenAddSupply
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.expired.ScreenExpired
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.login.ScreenLogin
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.productDetail.ScreenProductDetail
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.products.ScreenProducts
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.register.ScreenRegister
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings.ScreenSettings
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies.ScreenSupplies
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplyDetail.ScreenSupplyDetail
import com.bulletapps.candypricer.presentation.util.setNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val sharedViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { sharedViewModel.isLoading.value }
        }
        setContent {
            setNavigation(
                startDestination = MainViewModel.Navigation.Login.router,
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
        composable(MainViewModel.Navigation.Login.router) {
            ScreenLogin(sharedViewModel = sharedViewModel)
        }
        composable(MainViewModel.Navigation.Register.router) {
            ScreenRegister(sharedViewModel = sharedViewModel)
        }
        composable(MainViewModel.Navigation.Supplies.router) {
            ScreenSupplies(
                sharedViewModel = sharedViewModel,
                navigateSupplyDetail = { supply ->
                    sharedViewModel.supplyModel = supply
                    sharedViewModel.navigate(MainViewModel.Navigation.SupplyDetail)
                },
                navigateAddSupply = { sharedViewModel.navigate(MainViewModel.Navigation.AddSupply) },
                navigateLogout = sharedViewModel::logout
            )
        }
        composable(MainViewModel.Navigation.AddSupply.router) {
            ScreenAddSupply(sharedViewModel = sharedViewModel)
        }
        composable(MainViewModel.Navigation.UpdateSupply.router) {
            ScreenAddSupply(
                sharedViewModel = sharedViewModel,
                supplyModel = sharedViewModel.supplyModel
            )
        }
        composable(MainViewModel.Navigation.Products.router) {
            ScreenProducts(
                sharedViewModel = sharedViewModel,
                navigateProductDetail = { product ->
                    sharedViewModel.productModel = product
                    sharedViewModel.navigate(MainViewModel.Navigation.ProductDetail)
                },
                navigateAddProduct = { sharedViewModel.navigate(MainViewModel.Navigation.AddProduct) },
                navigateSupplies = { sharedViewModel.navigate(MainViewModel.Navigation.Supplies) },
                navigateLogout = sharedViewModel::logout
            )
        }
        composable(MainViewModel.Navigation.AddProduct.router) {
            ScreenAddProduct(sharedViewModel = sharedViewModel)
        }
        composable(MainViewModel.Navigation.UpdateProduct.router) {
            ScreenAddProduct(
                sharedViewModel = sharedViewModel,
                productModel = sharedViewModel.productModel
            )
        }
        composable(MainViewModel.Navigation.Settings.router) {
            ScreenSettings(sharedViewModel = sharedViewModel)
        }
        composable(MainViewModel.Navigation.Clients.router) {
            ScreenClients(sharedViewModel = sharedViewModel)
        }
        composable(MainViewModel.Navigation.Expired.router) {
            ScreenExpired(sharedViewModel = sharedViewModel)
        }
        composable(MainViewModel.Navigation.SupplyDetail.router) {
            ScreenSupplyDetail(
                sharedViewModel = sharedViewModel,
                supplyModel = sharedViewModel.supplyModel,
                navigateUpdateSupply = { sharedViewModel.navigate(MainViewModel.Navigation.UpdateSupply) }
            )
        }
        composable(MainViewModel.Navigation.ProductDetail.router) {
            ScreenProductDetail(
                sharedViewModel = sharedViewModel,
                productModel = sharedViewModel.productModel,
                navigateUpdateProduct = { sharedViewModel.navigate(MainViewModel.Navigation.UpdateProduct) }
            )
        }
    }

    private fun navEvent(navController: NavController, navScreen: MainViewModel.Navigation) {
        navController.navigate(route = navScreen.router) {
            launchSingleTop = true
            val currentScreen = navController.currentBackStackEntry?.destination?.route.orEmpty()
            if (navScreen.shouldPop && currentScreen.isNotEmpty()) {
                popUpTo(currentScreen) { inclusive = true }
                return@navigate
            }

            if (navScreen.popHome) {
                popUpTo(MainViewModel.Navigation.MainMenu.router) { inclusive = false }
                return@navigate
            }
        }
    }
}