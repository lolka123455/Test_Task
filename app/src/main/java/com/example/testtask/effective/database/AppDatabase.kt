package com.example.testtask.effective.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testtask.cart_screen.data.converters.BasketConverter
import com.example.testtask.cart_screen.data.dao.CartScreenDao
import com.example.testtask.cart_screen.data.models.CartLocalDto
import com.example.testtask.detail_screen.data.converters.ProductDetailsConverter
import com.example.testtask.detail_screen.data.dao.DetailsScreenDao
import com.example.testtask.detail_screen.data.models.ProductDetailsLocalDto
import com.example.testtask.main_screen.data.converters.BestSellersConverter
import com.example.testtask.main_screen.data.converters.HotSalesConverter
import com.example.testtask.main_screen.data.dao.MainScreenDao
import com.example.testtask.main_screen.data.models.cart.CartMainScreenLocalDto
import com.example.testtask.main_screen.data.models.main_page.MainPageLocalDto

/**
 * AppDatabase
 *
 * This class is used to create an instance of the Room database for the app.
 * It uses the Room library and TypeConverters to help with data storage.
 *
 * @constructor Creates a new instance of the AppDatabase
 *
 * @property entities the entities to be used in the database
 * @property version the version of the database
 * @property bestSellersConverter the TypeConverter for best sellers
 * @property hotSalesConverter the TypeConverter for hot sales
 * @property productDetailsConverter the TypeConverter for product details
 * @property basketConverter the TypeConverter for basket
 *
 * @see RoomDatabase
 */

@Database(
    entities = [MainPageLocalDto::class, ProductDetailsLocalDto::class, CartMainScreenLocalDto::class, CartLocalDto::class],
    version = 1
)
@TypeConverters(
    BestSellersConverter::class,
    HotSalesConverter::class,
    ProductDetailsConverter::class,
    BasketConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Returns an instance of MainScreenDao which is responsible for interacting with the main screen data.
     */

    abstract fun mainScreenDao(): MainScreenDao

    /**
     * Returns an instance of DetailsScreenDao which is responsible for interacting with the details screen data.
     */

    abstract fun detailsScreenDao(): DetailsScreenDao

    /**
     * Returns an instance of CartScreenDao which is responsible for interacting with the cart screen data.
     */

    abstract fun cartScreenDao(): CartScreenDao
}