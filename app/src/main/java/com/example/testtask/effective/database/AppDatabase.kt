package com.example.testtask.effective.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testtask.detail_screen.data.local.converters.ProductDetailsConverter
import com.example.testtask.detail_screen.data.local.dao.DetailsScreenDao
import com.example.testtask.detail_screen.data.local.models.ProductDetailsLocalDto
import com.example.testtask.main_screen.data.local.converters.BestSellersConverter
import com.example.testtask.main_screen.data.local.converters.HotSalesConverter
import com.example.testtask.main_screen.data.local.dao.MainScreenDao
import com.example.testtask.main_screen.data.local.models.cart.CartMainScreenLocalDto
import com.example.testtask.main_screen.data.local.models.main_page.MainPageLocalDto


@Database(
    entities = [MainPageLocalDto::class, ProductDetailsLocalDto::class, CartMainScreenLocalDto::class],
    version = 1
)
@TypeConverters(
    BestSellersConverter::class,
    HotSalesConverter::class,
    ProductDetailsConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mainScreenDao(): MainScreenDao

    abstract fun detailsScreenDao(): DetailsScreenDao
}