package com.example.testtask.cart_screen.network.models

data class Checkout (
    val basket: List<Basket>,
    val delivery: String,
    val id: String,
    val total: Int
)