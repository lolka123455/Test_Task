package com.example.testtask.cart_screen.usecases

import com.example.testtask.cart_screen.repository.CartScreenRepository

class GetCartItemsToCheckoutUseCase(private val cartScreenRepository: CartScreenRepository) {

    suspend fun invoke() = cartScreenRepository.getItemsCartToCheckout()
}
