package com.wirebarley.exchange.data.source.remote.api

import com.wirebarley.exchange.data.source.remote.response.LiveCurrencyData
import retrofit2.http.GET

interface CurrencyLayerAPI {

    @GET("currency_data/live")
    suspend fun getLiveCurrencyData(): LiveCurrencyData

}