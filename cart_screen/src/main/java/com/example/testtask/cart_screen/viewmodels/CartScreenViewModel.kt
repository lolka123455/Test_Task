package com.example.testtask.cart_screen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.cart_screen.network.models.Basket
import com.example.testtask.cart_screen.usecases.GetCartItemsToCheckoutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartScreenViewModel(
    private val getCartItemsToCheckoutUseCase: GetCartItemsToCheckoutUseCase
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<Basket>>()
    private val _total = MutableLiveData<Int>()
    private val _delivery = MutableLiveData<String>()

    val cartItems: LiveData<List<Basket>> = _cartItems
    val total: LiveData<Int> = _total
    val delivery: LiveData<String> = _delivery

    init {
        getCart()
    }

    private fun getCart() {
        viewModelScope.launch(Dispatchers.Default) {
            val cartCallResult = getCartItemsToCheckoutUseCase.invoke()
            _cartItems.postValue(cartCallResult.basket)
            _total.postValue(cartCallResult.total)
            _delivery.postValue(cartCallResult.delivery)
        }
    }

}