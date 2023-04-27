package com.wirebarley.exchange.ui.screen.model

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}