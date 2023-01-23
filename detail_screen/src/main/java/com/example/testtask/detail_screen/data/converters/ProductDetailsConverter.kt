package com.example.testtask.detail_screen.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductDetailsConverter {

    @TypeConverter
    fun fromJson(listOfString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(listOfString, type)
    }

    @TypeConverter
    fun toJson(listOfString: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(listOfString, type)
    }
}