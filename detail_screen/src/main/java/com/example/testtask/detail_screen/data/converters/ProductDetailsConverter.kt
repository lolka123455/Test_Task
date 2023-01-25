package com.example.testtask.detail_screen.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductDetailsConverter {

    /**
     * Converts a JSON string to a List of Strings.
     *
     * @param listOfString the JSON string to convert
     * @return the converted List of Strings
     */

    @TypeConverter
    fun fromJson(listOfString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(listOfString, type)
    }

    /**
     * Converts a List of Strings to its JSON representation.
     *
     * @param listOfString the List of Strings to convert
     * @return the JSON representation of the List of Strings
     */

    @TypeConverter
    fun toJson(listOfString: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(listOfString, type)
    }
}