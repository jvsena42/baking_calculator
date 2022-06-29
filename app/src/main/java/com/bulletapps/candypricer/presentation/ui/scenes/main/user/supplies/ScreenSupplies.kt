package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.Supply
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainActivity
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme
import com.bulletapps.candypricer.presentation.ui.widgets.SuppliesList
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ScreenSupplies(
    viewModel: SuppliesViewModel = hiltViewModel(),
    sharedViewModel: MainViewModel
) {
    Screen(viewModel.suppliesList, onClickAdd = { sharedViewModel.navigate(MainViewModel.Navigation.AddSupply) })
}

@Composable
fun Screen(
    itemsState: MutableStateFlow<List<Supply>>,
    onClickAdd: () -> Unit,
    ) {
    val items = itemsState.collectAsState()
    val activity = LocalContext.current as MainActivity

    CandyPricerTheme {
        Scaffold(
            backgroundColor = colors.background,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.supplies)
                        )
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = colors.secondary,
                    contentColor = colors.background,
                    onClick = onClickAdd,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_),
                        contentDescription = stringResource(id = R.string.add_supply),
                    )
                }
            }
        ) {
            SuppliesList(items.value)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen(
        MutableStateFlow(
            listOf(
                Supply(
                    id = 0,
                    name = "Leite Condensado Caixa",
                    price = "R$ 5,00",
                    quantity = 1.0,
                    unitType = "Unidade"
                ),
                Supply(
                    id = 1,
                    name = "Creme de leite Caixa",
                    price = "R$ 6,00",
                    quantity = 1.0,
                    unitType = "Unidade"
                ),
                Supply(
                    id = 2,
                    name = "Chocolate em pó",
                    price = "R$ 38,00",
                    quantity = 500.0,
                    unitType = "Gramas"
                ),
            )
        ),
        {}
    )
}