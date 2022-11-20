package com.example.testtask.cart_screen.network.models

data class Basket(
    val id: Int,
    val images: String,
    val price: Int,
    val title: String,
    val count: Int? = null
)
