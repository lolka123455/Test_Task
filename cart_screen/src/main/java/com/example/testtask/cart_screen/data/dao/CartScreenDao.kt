package com.example.testtask.cart_screen.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.cart_screen.data.models.CartLocalDto

@Dao
interface CartScreenDao {

    /**
     * This function is used to save a cart to the database.
     *
     * @param cart a CartLocalDto to be saved
     *
     */

    @Insert(entity = CartLocalDto::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCart(cart: CartLocalDto)

    /**
     * This function is used to retrieve the cart from the database.
     *
     * @return the CartLocalDto
     *
     */

    @Query("SELECT * FROM cart_table")
    fun getCart(): CartLocalDto
}