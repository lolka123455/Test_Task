package com.example.testtask.cart_screen.data.converters

import androidx.room.TypeConverter
import com.example.testtask.cart_screen.data.models.BasketLocalDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BasketConverter {

    /**
     * This function is used to convert a JSON string to List<BasketLocalDto>
     *
     * @param basket the JSON string representation of a list of BasketLocalDto
     * @return the list of BasketLocalDto
     *
     */

    @TypeConverter
    fun fromJson(basket: String): List<BasketLocalDto> {
        val type = object : TypeToken<List<BasketLocalDto>>() {}.type
        return Gson().fromJson(basket, type)
    }

    /**
     * This function is used to convert a list of BasketLocalDto to JSON string
     *
     * @param basket the list of BasketLocalDto
     * @return the JSON string representation of the list of BasketLocalDto
     *
     */

    @TypeConverter
    fun toJson(basket: List<BasketLocalDto>): String {
        val type = object : TypeToken<List<BasketLocalDto>>() {}.type
        return Gson().toJson(basket, type)
    }
}