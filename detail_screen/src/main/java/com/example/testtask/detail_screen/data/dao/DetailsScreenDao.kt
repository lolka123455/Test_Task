package com.example.testtask.detail_screen.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.detail_screen.data.models.ProductDetailsLocalDto

@Dao
interface DetailsScreenDao {

    /**
     * Inserts or replaces a [ProductDetailsLocalDto] object in the "product_details_table" table.
     *
     * @param details The [ProductDetailsLocalDto] object to be saved.
     */

    @Insert(entity = ProductDetailsLocalDto::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveProductDetails(details: ProductDetailsLocalDto)

    /**
     * Retrieves all rows from the "product_details_table" table and returns a [ProductDetailsLocalDto] object.
     *
     * @return A [ProductDetailsLocalDto] object containing the retrieved data.
     */

    @Query("SELECT * FROM product_details_table")
    fun getProductDetails(): ProductDetailsLocalDto
}