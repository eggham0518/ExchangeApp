package com.wirebarley.exchange.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wirebarley.exchange.data.di.DefaultDispatcher
import com.wirebarley.exchange.data.di.TestRepository
import com.wirebarley.exchange.data.repo.CurrencyLayerRepository
import com.wirebarley.exchange.data.source.remote.response.LiveCurrencyData
import com.wirebarley.exchange.ui.screen.model.Currency
import com.wirebarley.exchange.ui.screen.model.ExchangeRateEvent
import com.wirebarley.exchange.ui.screen.model.HomeState
import com.wirebarley.exchange.ui.screen.model.InvalidInputException
import com.wirebarley.exchange.ui.screen.model.ReceivingAmountState
import com.wirebarley.exchange.ui.screen.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    @TestRepository private val currencyLayerRepository: CurrencyLayerRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel(), HomeState {

    private val lastLiveCurrencyData = MutableSharedFlow<LiveCurrencyData>()

    private val _sendingCurrency = MutableStateFlow(Currency.USD)
    override val sendingCurrency: StateFlow<Currency> = _sendingCurrency

    private val _receivingCurrency = MutableStateFlow(Currency.KRW)
    override val receivingCurrency: StateFlow<Currency> = _receivingCurrency

    private val _exchangeRate = MutableStateFlow("")
    override val exchangeRate: StateFlow<String> = _exchangeRate

    private val _lookupTime = MutableStateFlow("")
    override val lookupTime: StateFlow<String> = _lookupTime

    private val _sendingAmount = MutableStateFlow("100")
    override val sendingAmount: StateFlow<String> = _sendingAmount

    private val _receivingAmount = MutableStateFlow("")
    override val receivingAmount: StateFlow<String> = _receivingAmount

    private val _receivingAmountState =
        MutableStateFlow<ReceivingAmountState>(ReceivingAmountState.Loading)
    override val receivingAmountState: StateFlow<ReceivingAmountState> = _receivingAmountState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    override val eventFlow: SharedFlow<UiEvent> = _eventFlow

    init {
        observeReceivingCurrency()
        observeLastLiveCurrencyData()
        observeSendingAmount()
    }

    override fun onEvent(exchangeRateEvent: ExchangeRateEvent) {
        when (exchangeRateEvent) {
            is ExchangeRateEvent.ReceivingCountryChange -> {
                updateReceivingCurrency(exchangeRateEvent)
            }

            is ExchangeRateEvent.SendingAmountChange -> {
                updateSendingAmount(exchangeRateEvent)
            }
        }
    }

    private fun observeReceivingCurrency() {
        _receivingCurrency
            .onEach {
                fetchLiveCurrencyData()
                resetResults()
            }.launchIn(viewModelScope)
    }

    private fun observeLastLiveCurrencyData() {
        lastLiveCurrencyData
            .onEach { liveCurrencyData ->
                updateExchangeRate(liveCurrencyData)
                updateLookupTime(liveCurrencyData)
            }.launchIn(viewModelScope)
    }

    private fun observeSendingAmount() {
        lastLiveCurrencyData
            .combine(_sendingAmount) { liveCurrencyData, sendingAmount ->
                updateReceivingAmount(liveCurrencyData, sendingAmount)
            }
            .launchIn(viewModelScope)
    }

    private fun fetchLiveCurrencyData() {
        viewModelScope.launch {
            kotlin.runCatching {
                _receivingAmountState.value = ReceivingAmountState.Loading
                currencyLayerRepository.getLiveCurrencyData()
            }.onSuccess { liveCurrencyData ->
                lastLiveCurrencyData.emit(liveCurrencyData)
            }.onFailure {
                _eventFlow.emit(UiEvent.ShowSnackbar(it.toString()))
            }
        }
    }

    private fun resetResults() {
        _receivingAmount.value = ""
        _exchangeRate.value = ""
    }

    private fun updateReceivingCurrency(receivingCountryChange: ExchangeRateEvent.ReceivingCountryChange) {
        _receivingCurrency.value = receivingCountryChange.currency
    }

    private fun updateSendingAmount(exchangeRateEvent: ExchangeRateEvent.SendingAmountChange) {
        _sendingAmount.value = exchangeRateEvent.amount
    }

    private fun updateLookupTime(liveCurrencyData: LiveCurrencyData) {
        viewModelScope.launch {
            _lookupTime.value = liveCurrencyData.timestamp.toLookupTime()
        }
    }

    private fun updateExchangeRate(liveCurrencyData: LiveCurrencyData) {
        viewModelScope.launch {
            val rate = liveCurrencyData.quotes[getKey()] ?: return@launch
            _exchangeRate.value = rate.toDecimalFormat()
        }
    }

    private fun updateReceivingAmount(liveCurrencyData: LiveCurrencyData, sendingAmount: String) =
        viewModelScope.launch {
            kotlin.runCatching {
                val input = validateAmount(sendingAmount)
                val exchangeRate = liveCurrencyData.quotes[getKey()]!!
                val receivingAmount = calculateReceivingAmount(exchangeRate, input)
                _receivingAmount.value = "$receivingAmount ${_receivingCurrency.value.name}"
            }.onSuccess {
                _receivingAmountState.value = ReceivingAmountState.Success
            }.onFailure {
                when (it) {
                    is InvalidInputException -> {
                        _receivingAmountState.value = ReceivingAmountState.Error
                    }

                    else -> {
                        _eventFlow.emit(UiEvent.ShowSnackbar(it.toString()))
                    }
                }
            }
        }

    private suspend fun Double.toDecimalFormat(): String = withContext(defaultDispatcher) {
        val formatter = DecimalFormat("#,##0.00")
        formatter.format(this@toDecimalFormat)
    }

    private suspend fun Long.toLookupTime(): String = withContext(defaultDispatcher) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = Date(this@toLookupTime * 1000L)
        dateFormat.format(date)
    }

    private suspend fun calculateReceivingAmount(exchangeRate: Double, input: Long): String =
        withContext(defaultDispatcher) {
            (exchangeRate * input).toDecimalFormat()
        }

    private fun getKey(): String {
        return _sendingCurrency.value.name + _receivingCurrency.value.name
    }

    private suspend fun validateAmount(input: String): Long = withContext(defaultDispatcher) {
        val amount = input.toLongOrNull() ?: throw InvalidInputException()
        if (amount < 0 || amount > 10000) {
            throw InvalidInputException()
        }
        amount
    }

}


