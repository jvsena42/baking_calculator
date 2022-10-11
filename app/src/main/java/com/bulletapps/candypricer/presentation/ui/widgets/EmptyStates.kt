package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.presentation.ui.theme.CandyPricerTheme

@Composable
fun ScreenErrorRequest(
    reloadAction: () -> Unit,
    logoutAction: () -> Unit
) {
    CandyPricerTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            LogoCircle()

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                stringResource(R.string.unexpected_error),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(42.dp))

            Text(
                stringResource(R.string.verify_your_connection),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))


            NormalButton(
                text = stringResource(R.string.reload),
                onClick = reloadAction
            )

            OutlinedButtonCustom(
                text = stringResource(R.string.logout),
                onClick = logoutAction
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun TextEmpty(text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun preview() {
    Column {
        ScreenErrorRequest({}, {})
    }
}