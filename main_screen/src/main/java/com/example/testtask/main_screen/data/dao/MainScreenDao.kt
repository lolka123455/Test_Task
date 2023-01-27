package com.example.testtask.main_screen.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.main_screen.data.models.cart.CartMainScreenLocalDto
import com.example.testtask.main_screen.data.models.main_page.MainPageLocalDto

@Dao
interface MainScreenDao {

    /**
     * Saves the main page data to the local database.
     *
     * @Insert - This annotation is used for inserting data into a table.
     * @param entity - The class of the entity being inserted. In this case, it is MainPageLocalDto.
     * @param onConflict - The conflict strategy to use if the insertion conflicts with an existing row.
     * In this case, it is OnConflictStrategy.REPLACE, which means that the existing row will be replaced.
     * @param mainPage - The main page data to be saved.
     */

    @Insert(entity = MainPageLocalDto::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveMainPage(mainPage: MainPageLocalDto)

    /**
     * Retrieves the main page data from the local database.
     *
     * @Query - This annotation is used for retrieving data from a table.
     * @param "SELECT * FROM main_page_table" - The SQL query to execute.
     * @return - The main page data.
     */

    @Query("SELECT * FROM main_page_table")
    fun getMainPage(): MainPageLocalDto

    /**
     * Saves the cart data to the local database.
     *
     * @Insert - This annotation is used for inserting data into a table.
     * @param entity - The class of the entity being inserted. In this case, it is CartMainScreenLocalDto.
     * @param onConflict - The conflict strategy to use if the insertion conflicts with an existing row.
     * In this case, it is OnConflictStrategy.REPLACE, which means that the existing row will be replaced.
     * @param cart - The cart data to be saved.
     */

    @Insert(entity = CartMainScreenLocalDto::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCart(cart: CartMainScreenLocalDto)

    /**
     * Retrieves the cart data from the local database.
     *
     * @Query - This annotation is used for retrieving data from a table.
     * @param "SELECT * FROM cart_items_count_table" - The SQL query to execute.
     * @return - The cart data.
     */

    @Query("SELECT * FROM cart_items_count_table")
    fun getCart(): CartMainScreenLocalDto
}