package com.example.testtask.cart_screen.domain.repository

import com.example.testtask.cart_screen.domain.entities.Cart
import com.example.testtask.state_network_connection.FetchResult

interface CartScreenRepository {

    suspend fun getCart(): FetchResult<Cart>
}