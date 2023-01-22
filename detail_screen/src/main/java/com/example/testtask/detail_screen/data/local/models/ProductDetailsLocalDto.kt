package com.example.testtask.detail_screen.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtask.detail_screen.data.local.models.ProductDetailsLocalDto.Companion.TABLE_NAME
import com.example.testtask.detail_screen.domain.entities.ProductDetails

@Entity(tableName =TABLE_NAME)
data class ProductDetailsLocalDto(
    val cpu: String,
    val camera: String,
    val capacity: List<String>,
    val color: List<String>,
    @PrimaryKey val id: String,
    val images: List<String>,
    val isFavorites: Boolean,
    val price: Int,
    val rating: Double,
    val sd: String,
    val ram: String,
    val title: String
) {

    fun mapToDomain() = ProductDetails(
        cpu = cpu,
        camera = camera,
        capacity = capacity,
        color = color,
        id = id,
        images = images,
        isFavorites = isFavorites,
        price = price,
        rating = rating,
        sd = sd,
        ram = ram,
        title = title
    )

    companion object {

        fun fromDomain(details: ProductDetails) = with(details) {
            ProductDetailsLocalDto(
                cpu,
                camera,
                capacity,
                color,
                id,
                images,
                isFavorites,
                price,
                rating,
                sd,
                ram,
                title
            )
        }

        const val TABLE_NAME = "product_details_table"
    }
}