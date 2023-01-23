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
import com.example.testtask.main_screen.data.local.converters.BestSellersConverter
import com.example.testtask.main_screen.data.local.converters.HotSalesConverter
import com.example.testtask.main_screen.data.local.dao.MainScreenDao
import com.example.testtask.main_screen.data.local.models.cart.CartMainScreenLocalDto
import com.example.testtask.main_screen.data.local.models.main_page.MainPageLocalDto


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

    abstract fun mainScreenDao(): MainScreenDao

    abstract fun detailsScreenDao(): DetailsScreenDao

    abstract fun cartScreenDao(): CartScreenDao
}