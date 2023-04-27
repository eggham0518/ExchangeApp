package com.wirebarley.exchange.ui.screen.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chargemap.compose.numberpicker.ListItemPicker
import com.wirebarley.exchange.R
import com.wirebarley.exchange.ui.screen.model.Currency
import com.wirebarley.exchange.ui.screen.model.ExchangeRateEvent
import com.wirebarley.exchange.ui.screen.model.HomeState
import com.wirebarley.exchange.ui.screen.model.ReceivingAmountState
import com.wirebarley.exchange.ui.screen.model.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeState: HomeState,
) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val sendingCurrency = homeState.sendingCurrency.collectAsStateWithLifecycle().value
    val receivingCurrency = homeState.receivingCurrency.collectAsStateWithLifecycle().value

    val lookupTime = homeState.lookupTime.collectAsStateWithLifecycle()
    val sendingAmount = homeState.sendingAmount.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 =  homeState.eventFlow) {
        homeState.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            CurrencyPicker(
                onValueChange = {
                    homeState.onEvent(ExchangeRateEvent.ReceivingCountryChange(it))
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.homeTitle),
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                ConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (sendingCountryLabel, receivingCountryLabel, exchangeRateLabel, lookupTimeLabel, sendingAmountLabel) = createRefs()
                    val (sendingCountryValue, receivingCountryValue, exchangeRateValue, lookupTimeValue, sendingAmountValue) = createRefs()

                    // 송금국가 label
                    Text(
                        text = stringResource(R.string.sendingCountry),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(sendingCountryLabel) {
                            top.linkTo(parent.top)
                        }
                    )

                    // 수취국가 label
                    Text(
                        text = stringResource(R.string.receivingCountry),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(receivingCountryLabel) {
                            top.linkTo(sendingCountryLabel.bottom, margin = 32.dp)
                            end.linkTo(sendingCountryLabel.end)
                        }
                    )

                    // 환율 label
                    Text(
                        text = stringResource(R.string.exchangeRate),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(exchangeRateLabel) {
                            top.linkTo(receivingCountryLabel.bottom, margin = 32.dp)
                            end.linkTo(sendingCountryLabel.end)
                        }
                    )

                    // 조회시간 label
                    Text(
                        text = stringResource(R.string.lookupTime),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(lookupTimeLabel) {
                            top.linkTo(exchangeRateLabel.bottom, margin = 32.dp)
                            end.linkTo(sendingCountryLabel.end)
                        }
                    )

                    // 송금액 label
                    Text(
                        text = stringResource(R.string.sendingAmount),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(sendingAmountLabel) {
                            top.linkTo(lookupTimeLabel.bottom, margin = 32.dp)
                            end.linkTo(sendingCountryLabel.end)
                        }
                    )

                    // 송금국가 value
                    Text(
                        text =context.countryCurrencyString(sendingCurrency),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(sendingCountryValue) {
                            top.linkTo(sendingCountryLabel.top, margin = 8.dp)
                            bottom.linkTo(sendingCountryLabel.bottom, margin = 8.dp)
                            start.linkTo(sendingCountryLabel.end, margin = 8.dp)
                        }
                    )

                    // 수취국가 value
                    Text(
                        text =  context.countryCurrencyString(receivingCurrency),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(receivingCountryValue) {
                            top.linkTo(receivingCountryLabel.top, margin = 8.dp)
                            bottom.linkTo(receivingCountryLabel.bottom, margin = 8.dp)
                            start.linkTo(receivingCountryLabel.end, margin = 8.dp)
                        }
                    )

                    // 환율 value
                    Text(
                        text = stringResource(
                            id = R.string.exchangeFormat,
                            homeState.exchangeRate.collectAsStateWithLifecycle().value,
                            receivingCurrency,
                            sendingCurrency
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(exchangeRateValue) {
                            top.linkTo(exchangeRateLabel.top, margin = 8.dp)
                            bottom.linkTo(exchangeRateLabel.bottom, margin = 8.dp)
                            start.linkTo(exchangeRateLabel.end, margin = 8.dp)
                        }
                    )

                    // 조회시간 value
                    Text(
                        text = lookupTime.value,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(lookupTimeValue) {
                            top.linkTo(lookupTimeLabel.top, margin = 8.dp)
                            bottom.linkTo(lookupTimeLabel.bottom, margin = 8.dp)
                            start.linkTo(lookupTimeLabel.end, margin = 8.dp)
                        }
                    )

                    // 송금액 value
                    Row(
                        modifier = Modifier.constrainAs(sendingAmountValue) {
                            top.linkTo(sendingAmountLabel.top, margin = 8.dp)
                            bottom.linkTo(sendingAmountLabel.bottom, margin = 8.dp)
                            start.linkTo(sendingAmountLabel.end, margin = 8.dp)
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = sendingAmount,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.End
                            ),
                            onValueChange = {
                                homeState.onEvent(ExchangeRateEvent.SendingAmountChange(it))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            trailingIcon = {
                                Text(
                                    text = sendingCurrency.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                val receivingAmountString =
                    homeState.receivingAmount.collectAsStateWithLifecycle().value
                // 수취금액 footer
                when (homeState.receivingAmountState.collectAsStateWithLifecycle().value) {
                    ReceivingAmountState.Loading -> {
                        CircularProgressIndicator()
                    }

                    ReceivingAmountState.Error -> {
                        Text(
                            text = stringResource(id = R.string.invalidInputError),
                            color = Color.Red,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    ReceivingAmountState.Success -> {
                        if (receivingAmountString.isNotEmpty()) {
                            Text(
                                text = stringResource(
                                    R.string.receivingAmount,
                                    receivingAmountString
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                    }
                }


            }
        }
    )
}

@Composable
private fun CurrencyPicker(
    currencyEntries: List<Currency> = listOf(Currency.KRW, Currency.JPY, Currency.PHP),
    onValueChange: (currency: Currency) -> Unit
) {
    val context = LocalContext.current
    val currencyList = remember(currencyEntries) {
        currencyEntries.map {
            it to context.countryCurrencyString(it)
        }
    }
    val state = remember { mutableStateOf(currencyList[0]) }

    ListItemPicker(
        modifier = Modifier.fillMaxWidth(),
        label = {
            it.second
        },
        value = state.value,
        onValueChange = {
            state.value = it
            onValueChange(it.first)
        },
        list = currencyList
    )
}

private fun Context.countryCurrencyString(sendingCurrency: Currency): String {
    val countryName = getString(sendingCurrency.countryNameRes)
    return getString(R.string.countryCurrencyFormat, countryName, sendingCurrency.name)
}

@Preview(showBackground = true)
@Composable
fun PreviewExchangeRatePage() {
    MaterialTheme {
        HomeScreen(
            object : HomeState {
                override val sendingCurrency: StateFlow<Currency>
                    get() = MutableStateFlow(Currency.USD)
                override val receivingCurrency: StateFlow<Currency>
                    get() = MutableStateFlow(Currency.KRW)
                override val exchangeRate: StateFlow<String>
                    get() = MutableStateFlow("1,130.05")
                override val lookupTime: StateFlow<String>
                    get() = MutableStateFlow("2019-03-20 16:13")
                override val sendingAmount: StateFlow<String>
                    get() = MutableStateFlow("100")
                override val receivingAmount: StateFlow<String>
                    get() = MutableStateFlow("113,004.98 KRW")
                override val receivingAmountState: StateFlow<ReceivingAmountState>
                    get() = MutableStateFlow(ReceivingAmountState.Error)
                override val eventFlow: SharedFlow<UiEvent>
                    get() = TODO("Not yet implemented")

                override fun onEvent(exchangeRateEvent: ExchangeRateEvent) {

                }
            })

    }
}