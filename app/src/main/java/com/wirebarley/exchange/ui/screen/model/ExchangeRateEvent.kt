package com.wirebarley.exchange.ui.screen.model

sealed interface ExchangeRateEvent {
    data class ReceivingCountryChange(val currency: Currency) : ExchangeRateEvent
    data class SendingAmountChange(val amount: String) : ExchangeRateEvent
}