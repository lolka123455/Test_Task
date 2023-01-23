package com.example.testtask.main_screen.data.models.main_page

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtask.main_screen.data.models.main_page.MainPageLocalDto.Companion.TABLE_NAME
import com.example.testtask.main_screen.entities.main_page.MainPage

@Entity(tableName = TABLE_NAME)
data class MainPageLocalDto(
    val best_seller: List<BestSellerLocalDto>,
    val home_store: List<HotSaleLocalDto>
) {

    @PrimaryKey(autoGenerate = true) var id: Int? = null

    fun mapToDomain() = MainPage(
        bestSeller = best_seller.map { it.mapToDomain() },
        hotSale = home_store.map { it.mapToDomain() })

    companion object {

        fun fromDomain(mainPage: MainPage) = MainPageLocalDto(
            mainPage.bestSeller.map {
                BestSellerLocalDto.fromDomain(it)
            },
            mainPage.hotSale.map {
                HotSaleLocalDto.fromDomain(it)
            }
        )

        const val TABLE_NAME = "main_page_table"
    }
}