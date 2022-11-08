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
import androidx.compose.ui.text.font.FontWeight
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
import com.bulletapps.candypricer.presentation.ui.widgets.CardTwoItemsWithDetailHorizontal
import com.bulletapps.candypricer.presentation.ui.widgets.NormalButton
import com.bulletapps.candypricer.presentation.ui.widgets.OutlinedButtonCustom
import com.bulletapps.candypricer.presentation.ui.widgets.TextWithLabel
import com.bulletapps.candypricer.presentation.util.toCurrency
import com.bulletapps.candypricer.presentation.util.toPercentString

@Composable
fun ScreenProductDetail(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    viewModel.setup(sharedViewModel.selectedProduct)
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
                is ProductDetailViewModel.ScreenEvent.NavigateToAddProduct -> sharedViewModel.navigate(
                    MainViewModel.Navigation.AddProduct
                )
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: UIState,
    onAction: (ScreenActions) -> Unit,
) {

    val quantity by uiState.quantity.collectAsState()
    val unit by uiState.unit.collectAsState()
    val laborValue by uiState.laborValue.collectAsState()
    val variableExpenses by uiState.variableExpenses.collectAsState()
    val profitMargin by uiState.profitMargin.collectAsState()
    val totalSpendsValue by uiState.totalSpendsValue.collectAsState()
    val unitSaleValue by uiState.unitSaleValue.collectAsState()
    val supplyList by uiState.supplyList.collectAsState()

    CandyPricerTheme {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {


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

                    TextWithLabel(
                        stringResource(R.string.quantity_label),
                        quantity,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        arrangement = Arrangement.SpaceBetween
                    )
                    TextWithLabel(
                        stringResource(R.string.unit_label),
                        unit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        arrangement = Arrangement.SpaceBetween
                    )
                    TextWithLabel(
                        stringResource(R.string.labor_price),
                        laborValue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        arrangement = Arrangement.SpaceBetween
                    )
                    TextWithLabel(
                        stringResource(R.string.variable_expenses),
                        variableExpenses,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        arrangement = Arrangement.SpaceBetween
                    )
                    TextWithLabel(
                        stringResource(R.string.profit_margin),
                        profitMargin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        arrangement = Arrangement.SpaceBetween
                    )

                    Spacer(Modifier.height(32.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextWithLabel(
                                stringResource(R.string.total_cost),
                                totalSpendsValue,
                                modifier = Modifier.fillMaxWidth(),
                                arrangement = Arrangement.SpaceBetween
                            )
                            Spacer(Modifier.height(8.dp))
                            TextWithLabel(
                                stringResource(R.string.unit_sell_value),
                                unitSaleValue,
                                modifier = Modifier.fillMaxWidth(),
                                arrangement = Arrangement.SpaceBetween
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Text(
                        stringResource(R.string.supplies),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))
                }

                items(supplyList) { itemSupply ->
                    CardTwoItemsWithDetailHorizontal(
                        firstLabel = R.string.name_label,
                        secondLabel = R.string.quantity_short_label,
                        firsName = itemSupply.name,
                        secondName = itemSupply.quantity,
                        thirdName = itemSupply.unit,
                        onClick = {}
                    )
                }

                item {
                    Spacer(Modifier.height(32.dp))
                    Spacer(Modifier.weight(1f))

                    OutlinedButtonCustom(
                        text = stringResource(R.string.delete),
                        onClick = { onAction(ScreenActions.OnClickDelete) }
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