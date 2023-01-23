package com.example.testtask.cart_screen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.cart_screen.data.local.models.CartLocalDto

@Dao
interface CartScreenDao {

    @Insert(entity = CartLocalDto::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCart(cart: CartLocalDto)

    @Query("SELECT * FROM cart_table")
    fun getCart(): CartLocalDto
}