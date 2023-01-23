package com.example.testtask.cart_screen.domain.usecases

import com.example.testtask.cart_screen.domain.repository.CartScreenRepository

class GetCartUseCase(private val repository: CartScreenRepository) {

    suspend fun execute() = repository.getCart()
}