package com.example.testtask.cart_screen.data.local.models

import com.example.testtask.cart_screen.domain.entities.Basket

data class BasketLocalDto(
    val id: Int,
    val images: String,
    val price: Int,
    val title: String,
    val count: Int? = null
) {

    fun mapToDomain() =
        Basket(id = id, images = images, price = price, title = title, count = count)

    companion object {

        fun fromDomain(basket: Basket) = with(basket){
            BasketLocalDto(id, images, price, title, count)
        }
    }
}