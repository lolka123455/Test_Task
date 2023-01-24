package com.example.testtask.cart_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.cart_screen.entities.Basket
import com.example.testtask.cart_screen.usecases.GetCartUseCase
import com.example.testtask.state_network_connection.FetchResult
import com.example.testtask.state_network_connection.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartUseCase: GetCartUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _cartItems = MutableStateFlow<List<Basket>>(emptyList())
    val cartItems: StateFlow<List<Basket>> = _cartItems

    private val _total = MutableStateFlow(0)
    val total: StateFlow<Int> = _total

    private val _delivery = MutableStateFlow("")
    val delivery: StateFlow<String> = _delivery

    init {
        getCart()
    }

    private fun getCart() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val cartCallResult = getCartUseCase.execute()
            if (cartCallResult is FetchResult.SuccessDataUpload) {
                _cartItems.value = cartCallResult.data.basket
                _total.value = cartCallResult.data.total
                _delivery.value = cartCallResult.data.delivery

                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error
            }
        }
    }

    fun retryNetworkCall() {
        getCart()
    }
}