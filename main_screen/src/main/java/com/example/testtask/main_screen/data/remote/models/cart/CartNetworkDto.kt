package com.example.testtask.main_screen.data.remote.models.cart

import com.example.testtask.main_screen.domain.entities.cart.Cart

data class CartNetworkDto(
    val basket: List<BasketNetworkDto>
) {

    fun mapToDomain() = Cart(itemsCount = basket.size)
}