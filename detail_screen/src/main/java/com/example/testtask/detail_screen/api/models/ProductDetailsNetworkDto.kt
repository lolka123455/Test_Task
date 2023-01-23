package com.example.testtask.detail_screen.api.models

import com.example.testtask.detail_screen.entities.ProductDetails

data class ProductDetailsNetworkDto(
    val CPU: String,
    val camera: String,
    val capacity: List<String>,
    val color: List<String>,
    val id: String,
    val images: List<String>,
    val isFavorites: Boolean,
    val price: Int,
    val rating: Double,
    val sd: String,
    val ssd: String,
    val title: String
) {
    fun mapToDomain()= ProductDetails(
        cpu = CPU,
        camera = camera,
        capacity = capacity,
        color = color,
        id = id,
        images = images,
        isFavorites = isFavorites,
        price = price,
        rating = rating,
        sd = sd,
        ram = ssd,
        title = title
    )
}