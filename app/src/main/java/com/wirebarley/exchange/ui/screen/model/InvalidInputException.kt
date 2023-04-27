package com.wirebarley.exchange.ui.screen.model

class InvalidInputException : Exception()

sealed class ReceivingAmountState {
    object Loading : ReceivingAmountState()
    object Success : ReceivingAmountState()
    object Error : ReceivingAmountState()
}
