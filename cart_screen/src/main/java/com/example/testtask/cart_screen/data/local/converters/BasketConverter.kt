package com.example.testtask.cart_screen.data.local.converters

import androidx.room.TypeConverter
import com.example.testtask.cart_screen.data.local.models.BasketLocalDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BasketConverter {

    @TypeConverter
    fun fromJson(basket: String): List<BasketLocalDto> {
        val type = object : TypeToken<List<BasketLocalDto>>() {}.type
        return Gson().fromJson(basket, type)
    }

    @TypeConverter
    fun toJson(basket: List<BasketLocalDto>): String {
        val type = object : TypeToken<List<BasketLocalDto>>() {}.type
        return Gson().toJson(basket, type)
    }
}