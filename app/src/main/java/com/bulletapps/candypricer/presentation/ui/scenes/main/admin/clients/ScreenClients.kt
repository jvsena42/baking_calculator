package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.Product
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.products.ProductsViewModel
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardProduct
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ScreenClients(
    viewModel: ProductsViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    Screen(viewModel.productsList, onClickAdd = { sharedViewModel.navigate(MainViewModel.Navigation.AddProduct) })
}

@Composable
fun Screen(
    itemsState: MutableStateFlow<List<Product>>,
    onClickAdd: () -> Unit,
    ) {

    CandyPricerTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.clients)
                    )
                },
            )
        }
    }
}

@Composable
private fun ProductsList(supplyList: List<Product>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(supplyList.size) { index ->
                val item = supplyList[index]
                CardProduct(item) {

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Screen(
        MutableStateFlow(
            listOf(
                Product(
                    id = "",
                    name = "Brigadeiro",
                    price = "R$ 5,00",
                    quantity = 1.0,
                    unitType = "Unidade",
                    componentIds = listOf()
                ),
                Product(
                    id = "",
                    name = "Brigadeiro",
                    price = "R$ 5,00",
                    quantity = 1.0,
                    unitType = "Unidade",
                    componentIds = listOf()
                ),
                Product(
                    id = "",
                    name = "Brigadeiro",
                    price = "R$ 5,00",
                    quantity = 1.0,
                    unitType = "Unidade",
                    componentIds = listOf()
                )
            )
        ),
        {}
    )
}