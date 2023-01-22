package com.example.testtask.main_screen.domain.entities.main_page

data class BestSeller(
    val discountPrice: Int,
    val id: Int,
    val isFavorites: Boolean,
    val picture: String,
    val priceWithoutDiscount: Int,
    val title: String
)