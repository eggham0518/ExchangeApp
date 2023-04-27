package com.wirebarley.exchange.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LiveCurrencyData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("source")
    val source: String,
    @SerializedName("quotes")
    val quotes: Map<String, Double>
)
