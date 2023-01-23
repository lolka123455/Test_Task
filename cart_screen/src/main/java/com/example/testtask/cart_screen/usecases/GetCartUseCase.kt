package com.example.testtask.cart_screen.usecases

import com.example.testtask.cart_screen.repository.CartScreenRepository

class GetCartUseCase(private val repository: CartScreenRepository) {

    suspend fun execute() = repository.getCart()
}