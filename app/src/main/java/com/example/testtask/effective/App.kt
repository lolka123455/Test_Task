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

/**
 * This is the Application class of the app. It is responsible for initializing the Koin dependency injection framework,
 * and for providing the list of modules to be used throughout the app.
 *
 * @constructor Creates a new instance of the App
 *
 * @property modules the list of modules to be used throughout the app
 *
 * @see android.app.Application
 * @see org.koin.core.context.startKoin
 *
 */

class App : Application() {

    /**
     * The list of modules to be used throughout the app
     *
     * @see CartScreenModule
     * @see MainScreenModule
     * @see DetailsScreenModule
     * @see NetworkModule
     * @see DatabaseModule
     *
     */

    private val modules = listOf(
        CartScreenModule,
        MainScreenModule,
        DetailsScreenModule,
        NetworkModule,
        DatabaseModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(modules)
        }
    }
}