package com.bulletapps.candypricer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuildUI()
        }
    }
}

@Composable
fun BuildUI() {
    CandyPricerTheme {
        Column (
            modifier = Modifier.fillMaxSize(),
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BuildUI()
}