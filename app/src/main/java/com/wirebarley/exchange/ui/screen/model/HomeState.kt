package com.wirebarley.exchange.ui.screen.model

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow


interface HomeState {
    val sendingCurrency: StateFlow<Currency>
    val receivingCurrency: StateFlow<Currency>
    val exchangeRate: StateFlow<String>
    val lookupTime: StateFlow<String>
    val sendingAmount: StateFlow<String>
    val receivingAmount: StateFlow<String>
    val receivingAmountState : StateFlow<ReceivingAmountState>
    val eventFlow: SharedFlow<UiEvent>

    fun onEvent(exchangeRateEvent: ExchangeRateEvent)
}

