package com.example.testtask.cart_screen.domain.entities

data class Cart(
    val basket: List<Basket>,
    val delivery: String,
    val id: String,
    val total: Int
)