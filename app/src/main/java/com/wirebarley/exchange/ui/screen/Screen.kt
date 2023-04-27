package com.wirebarley.exchange.ui.screen

sealed class Screen(val route: String) {
    object Home: Screen("home")
}
