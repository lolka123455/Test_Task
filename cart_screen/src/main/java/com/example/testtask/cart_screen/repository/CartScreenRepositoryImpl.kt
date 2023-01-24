package com.example.testtask.cart_screen.repository

import android.database.sqlite.SQLiteException
import com.example.testtask.cart_screen.data.dao.CartScreenDao
import com.example.testtask.cart_screen.data.models.CartLocalDto
import com.example.testtask.cart_screen.api.CartScreenService
import com.example.testtask.state_network_connection.FetchResult
import com.example.testtask.cart_screen.entities.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

class CartScreenRepositoryImpl(
    private val cartScreenService: CartScreenService,
    private val cartScreenDao: CartScreenDao
) : CartScreenRepository {

    override suspend fun getCart(): FetchResult<Cart> =
        withContext(Dispatchers.IO) {
            try {
                val cart = cartScreenDao.getCart()
                if (cart != null) {
                    FetchResult.SuccessDataUpload(cart.mapToDomain())
                } else {
                    val cartNetworkResult = getCartFromNetwork()
                    if (cartNetworkResult is FetchResult.SuccessDataUpload) {
                        saveCartInDatabase(cartNetworkResult.data)
                    }
                    cartNetworkResult
                }
            } catch (e: SQLiteException) {
                FetchResult.ErrorLoadingData(e.message)
            }
        }

    private suspend fun getCartFromNetwork(): FetchResult<Cart> =
        withContext(Dispatchers.IO) {
            try {
                val cart = cartScreenService.getCart().mapToDomain()
                FetchResult.SuccessDataUpload(cart)
            } catch (e: IOException) {
                FetchResult.ErrorLoadingData(e.message)
            }
        }

    private suspend fun saveCartInDatabase(details: Cart) {
        cartScreenDao.saveCart(CartLocalDto.fromDomain(details))
    }
}