package com.example.testtask.cart_screen.api.models

import com.example.testtask.cart_screen.entities.Cart

data class CartNetworkDto(
    val basket: List<BasketNetworkDto>,
    val delivery: String,
    val id: String,
    val total: Int
) {

    /**
     * This function maps the properties of an object to the Cart model for the domain layer.
     *
     * @return a new instance of the Cart model
     *
     * @see Cart
     *
     */

    fun mapToDomain() = Cart(
        basket = basket.map { it.mapToDomain() },
        delivery = delivery,
        id = id,
        total = total
    )
}