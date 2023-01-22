package com.example.testtask.effective

import android.app.Application
import com.example.testtask.cart_screen.di.CartScreenModule
import com.example.testtask.detail_screen.di.DetailsScreenModule
import com.example.testtask.effective.di.DatabaseModule
import com.example.testtask.effective.di.NetworkModule
import com.example.testtask.main_screen.di.MainScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                CartScreenModule,
                MainScreenModule,
                DetailsScreenModule,
                NetworkModule,
                DatabaseModule
            )
        }
    }
}