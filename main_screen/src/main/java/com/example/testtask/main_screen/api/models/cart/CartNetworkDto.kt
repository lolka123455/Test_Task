package com.example.testtask.main_screen.api.models.cart

import com.example.testtask.main_screen.entities.cart.Cart

data class CartNetworkDto(
    val basket: List<BasketNetworkDto>
) {

    fun mapToDomain() = Cart(itemsCount = basket.size)
}