package com.example.testtask.main_screen.data.converters

import androidx.room.TypeConverter
import com.example.testtask.main_screen.data.models.main_page.BestSellerLocalDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BestSellersConverter {

    @TypeConverter
    fun fromJson(bestSellers: String): List<BestSellerLocalDto> {
        val type = object : TypeToken<List<BestSellerLocalDto>>() {}.type
        return Gson().fromJson(bestSellers, type)
    }

    @TypeConverter
    fun toJson(bestSellers: List<BestSellerLocalDto>): String {
        val type = object : TypeToken<List<BestSellerLocalDto>>() {}.type
        return Gson().toJson(bestSellers, type)
    }
}