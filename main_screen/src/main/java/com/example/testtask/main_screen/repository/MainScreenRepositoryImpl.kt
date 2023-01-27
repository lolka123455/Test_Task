package com.example.testtask.main_screen.repository

import android.database.sqlite.SQLiteException
import com.example.testtask.main_screen.data.dao.MainScreenDao
import com.example.testtask.main_screen.data.models.cart.CartMainScreenLocalDto
import com.example.testtask.main_screen.api.MainScreenService
import com.example.testtask.main_screen.data.models.main_page.MainPageLocalDto
import com.example.testtask.state_network_connection.FetchResult
import com.example.testtask.main_screen.entities.cart.Cart
import com.example.testtask.main_screen.entities.main_page.MainPage
import java.io.IOException
import java.lang.Exception

class MainScreenRepositoryImpl(
    private val mainScreenService: MainScreenService,
    private val mainScreenDao: MainScreenDao
) : MainScreenRepository {

    override suspend fun getMainPage(): FetchResult<MainPage> =
        try {
            getMainPageFromDatabase()
        } catch (e: SQLiteException) {
            val mainPageNetworkResult = getMainPageFromNetwork()
            if (mainPageNetworkResult is FetchResult.SuccessDataUpload) {
                saveMainPageInDatabase(mainPageNetworkResult.data)
            }
            mainPageNetworkResult
        }

    private fun getMainPageFromDatabase(): FetchResult<MainPage> =
        FetchResult.SuccessDataUpload(mainScreenDao.getMainPage().mapToDomain())

    private suspend fun getMainPageFromNetwork(): FetchResult<MainPage> =
        try {
            val mainPage = mainScreenService.getMainDataAsync().mapToDomain()
            FetchResult.SuccessDataUpload(mainPage)
        } catch (e: IOException) {
            FetchResult.ErrorLoadingData(e.message)
        }

    private fun saveMainPageInDatabase(mainPage: MainPage) {
        mainScreenDao.saveMainPage(MainPageLocalDto.fromDomain(mainPage))
    }

    override suspend fun getCart(): FetchResult<Cart> =
        try {
            getCartFromDatabase()
        } catch (e: SQLiteException) {
            val cartNetworkResult = getCartFromNetwork()
            if (cartNetworkResult is FetchResult.SuccessDataUpload) {
                saveCartInDatabase(cartNetworkResult.data)
            }
            cartNetworkResult
        }

    private fun getCartFromDatabase(): FetchResult<Cart> =
        FetchResult.SuccessDataUpload(mainScreenDao.getCart().mapToDomain())

    private suspend fun getCartFromNetwork() =
        try {
            val cart = mainScreenService.getCartDataAsync().mapToDomain()
            FetchResult.SuccessDataUpload(cart)
        } catch (e: IOException) {
            FetchResult.ErrorLoadingData(e.message)
        }

    private fun saveCartInDatabase(cart: Cart) {
        mainScreenDao.saveCart(CartMainScreenLocalDto.fromDomain(cart))
    }
}