package com.bulletapps.candypricer.presentation.ui.scenes.main.user.productDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.productDetail.ProductDetailViewModel.ScreenActions
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.productDetail.ProductDetailViewModel.ScreenEvent.GoBack
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.productDetail.ProductDetailViewModel.UIState
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.CardTwoItemsHorizontal
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.ui.widgets.OutlinedButtonCustom
import com.bulletapps.candypricer.presentation.ui.widgets.TextWithLabel

@Composable
fun ScreenProductDetail(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) { viewModel.setup(sharedViewModel.selectedProduct.value) }
    val activity = LocalContext.current as MainActivity
    Screen(viewModel.uiState, viewModel::onAction)
    EventConsumer(activity, viewModel, sharedViewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: ProductDetailViewModel,
    sharedViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is GoBack -> activity.onBackPressed()
                is ProductDetailViewModel.ScreenEvent.NavigateToAddProduct -> sharedViewModel.navigate(MainViewModel.Navigation.AddProduct)
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit,
) {

    val suppliesList by uiState.selectedSupplies.collectAsState()

    CandyPricerTheme {

        Column {

            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.product)
                    )
                },
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Spacer(Modifier.height(16.dp))

                    TextWithLabel(stringResource(R.string.labor_price), "100", modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), arrangement = Arrangement.SpaceBetween)
                    TextWithLabel(stringResource(R.string.variable_expenses), "100", modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), arrangement = Arrangement.SpaceBetween)
                    TextWithLabel(stringResource(R.string.profit_margin), "100", modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), arrangement = Arrangement.SpaceBetween)

                    Spacer(Modifier.height(32.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextWithLabel(stringResource(R.string.sell_by), "100", modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
                            Spacer(Modifier.height(8.dp))
                            TextWithLabel(stringResource(R.string.sell_price_label), "100", modifier = Modifier.fillMaxWidth(), arrangement = Arrangement.SpaceBetween)
                        }
                    }
                }

                items(suppliesList) { itemSupply ->
                    CardTwoItemsHorizontal(
                        firstLabel = R.string.name_label,
                        secondLabel = R.string.quantity_label,
                        firsName = itemSupply.name,
                        secondName = itemSupply.qut.toString(),
                        onClick = {}
                    )
                }

                item {
                    Spacer(Modifier.height(32.dp))
                    Spacer(Modifier.weight(1f))

                    OutlinedButtonCustom(
                        text = stringResource(R.string.delete),
                        onClick = { }
                    )

                    NormalButton(
                        text = stringResource(R.string.edit),
                        onClick = { onAction(ScreenActions.OnClickEdit) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        onAction = {},
        uiState = UIState()
    )
}