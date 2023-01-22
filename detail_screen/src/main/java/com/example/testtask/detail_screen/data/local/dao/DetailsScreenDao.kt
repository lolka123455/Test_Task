package com.example.testtask.detail_screen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.detail_screen.data.local.models.ProductDetailsLocalDto

@Dao
interface DetailsScreenDao {

    @Insert(entity = ProductDetailsLocalDto::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveProductDetails(details: ProductDetailsLocalDto)

    @Query("SELECT * FROM product_details_table")
    fun getProductDetails(): ProductDetailsLocalDto
}