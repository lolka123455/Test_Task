package com.example.testtask.main_screen.api.models.main_page

import com.example.testtask.main_screen.entities.main_page.BestSeller

data class BestSellerNetworkDto(
    val discount_price: Int,
    val id: Int,
    val is_favorites: Boolean,
    val picture: String,
    val price_without_discount: Int,
    val title: String
) {

    fun mapToDomain() = BestSeller(
        discountPrice = discount_price,
        id = id,
        isFavorites = is_favorites,
        picture = picture,
        priceWithoutDiscount = price_without_discount,
        title = title
    )
}