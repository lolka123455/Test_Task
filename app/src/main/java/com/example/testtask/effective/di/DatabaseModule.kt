package com.example.testtask.effective.di

import androidx.room.Room
import com.example.testtask.effective.database.AppDatabase
import org.koin.dsl.module

/**
 * DatabaseModule
 *
 * This package contains code related to the creation and provision of the app's Room database instance.
 * It uses the Koin library for Dependency Injection.
 *
 * @property DatabaseModule a singleton Koin module that provides the Room database instance, the main screen DAO, the details screen DAO, the cart screen DAO and the DATABASE_NAME constant.
 *
 * @constructor Creates a new instance of the DatabaseModule
 *
 * @function provideMainScreenDao which provides the main screen DAO instance
 * @function provideDetailsScreenDao which provides the details screen DAO instance
 * @function provideCartScreenDao which provides the cart screen DAO instance
 *
 * @see Room
 * @see AppDatabase
 * @see org.koin.dsl.module
 *
 */

val DatabaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME).build()
    }
    single {
        provideMainScreenDao(get())
    }
    single {
        provideDetailsScreenDao(get())
    }
    single {
        provideCartScreenDao(get())
    }

}

/**
 * This function provides the main screen DAO instance
 *
 * @param database the AppDatabase instance
 *
 * @return mainScreenDao instance
 *
 * @see AppDatabase
 *
 */
fun provideMainScreenDao(database: AppDatabase) = database.mainScreenDao()

/**
 * This function provides the details screen DAO instance
 *
 * @param database the AppDatabase instance
 *
 * @return detailsScreenDao instance
 *
 * @see AppDatabase
 *
 */

fun provideDetailsScreenDao(database: AppDatabase) = database.detailsScreenDao()


/**
 * This function provides the cart screen DAO instance
 *
 * @param database the AppDatabase instance
 *
 * @return cartScreenDao instance
 *
 * @see AppDatabase
 *
 */

fun provideCartScreenDao(database: AppDatabase) = database.cartScreenDao()

const val DATABASE_NAME = "ecommerce_database"