package com.example.testtask.cart_screen.repository

import com.example.testtask.cart_screen.network.models.Checkout

interface CartScreenRepository {

    suspend fun getItemsCartToCheckout(): Checkout
}