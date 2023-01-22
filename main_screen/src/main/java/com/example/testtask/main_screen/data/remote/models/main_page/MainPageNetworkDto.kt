package com.example.testtask.main_screen.data.remote.models.main_page

import com.example.testtask.main_screen.domain.entities.main_page.MainPage

data class MainPageNetworkDto(
    val best_seller: List<BestSellerNetworkDto>,
    val home_store: List<HotSaleNetworkDto>
) {

    fun mapToDomain() = MainPage(
        bestSeller = best_seller.map { it.mapToDomain() },
        hotSale = home_store.map { it.mapToDomain() })
}