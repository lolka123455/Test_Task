package com.example.testtask.effective.di

import androidx.room.Room
import com.example.testtask.effective.database.AppDatabase
import org.koin.dsl.module

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

fun provideMainScreenDao(database: AppDatabase) = database.mainScreenDao()

fun provideDetailsScreenDao(database: AppDatabase) = database.detailsScreenDao()

fun provideCartScreenDao(database: AppDatabase) = database.cartScreenDao()

const val DATABASE_NAME = "ecommerce_database"