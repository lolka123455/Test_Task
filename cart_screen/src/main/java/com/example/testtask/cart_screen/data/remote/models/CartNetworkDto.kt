package com.example.testtask.cart_screen.data.remote.models

import com.example.testtask.cart_screen.domain.entities.Cart

data class CartNetworkDto(
    val basket: List<BasketNetworkDto>,
    val delivery: String,
    val id: String,
    val total: Int
) {

    fun mapToDomain() = Cart(
        basket = basket.map { it.mapToDomain() },
        delivery = delivery,
        id = id,
        total = total
    )
}