package com.example.testtask.main_screen.data.models.main_page

import com.example.testtask.main_screen.entities.main_page.BestSeller

data class BestSellerLocalDto(
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

    companion object {

        fun fromDomain(bestSeller: BestSeller) = with(bestSeller) {
            BestSellerLocalDto(discountPrice, id, isFavorites, picture, priceWithoutDiscount, title)
        }
    }
}