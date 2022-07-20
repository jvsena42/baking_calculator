package com.bulletapps.candypricer.presentation.ui.scenes.main.user.products

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.products.ProductsViewModel.*
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardTwoItemsVertical

@Composable
fun ScreenProducs(
    viewModel: ProductsViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    LaunchedEffect(key1 = Unit) { viewModel.setup() }
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(activity, viewModel, sharedViewModel
    )
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: ProductsViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.NavigateToAddProduct -> sharedViewModel.navigate(MainViewModel.Navigation.AddProduct)
            }
        }
    }
}

@Composable
fun Screen(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit,
    ) {

    CandyPricerTheme {
        Scaffold(
            backgroundColor = colors.background,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.my_products)
                        )
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = colors.secondary,
                    contentColor = colors.background,
                    onClick = {onAction(ScreenActions.OnClickAdd)},
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_),
                        contentDescription = stringResource(id = R.string.add_product),
                    )
                }
            }
        ) {
            MakeList(uiState)
        }
    }
}

@Composable
private fun MakeList(uiState: UIState) {
    val list by uiState.productsList.collectAsState()
    ProductsList(list)
}

@Composable
private fun ProductsList(list: List<ProductResponse>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(list.size) { index ->
                val item = list[index]
                CardTwoItemsVertical(
                    firstLabel = R.string.name_label,
                    firsName = item.name,
                    secondLabel = R.string.cost_label,
                    secondName = item.price.toString(),
                    onClick = {}
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Screen( onAction = {}, uiState = UIState() )
}