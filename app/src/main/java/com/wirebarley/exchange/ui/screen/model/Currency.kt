package com.wirebarley.exchange.ui.screen.model

import androidx.annotation.StringRes
import com.wirebarley.exchange.R

enum class Currency(@StringRes val countryNameRes: Int) {
    KRW(R.string.displayNameKorea),
    JPY(R.string.displayNameJapan),
    PHP(R.string.displayNamePhilippines),
    USD(R.string.displayNameUSA)
}