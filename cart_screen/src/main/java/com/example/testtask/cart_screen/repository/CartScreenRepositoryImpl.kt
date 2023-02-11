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

/**
 * Implementation of the [CartScreenRepository] interface that handles data operations for the cart screen.
 *
 * @param cartScreenService instance of [CartScreenService] that handles network operations
 * @param cartScreenDao instance of [CartScreenDao] that handles database operations
 */

class CartScreenRepositoryImpl(
    private val cartScreenService: CartScreenService,
    private val cartScreenDao: CartScreenDao
) : CartScreenRepository {

    override suspend fun getCart(): FetchResult<Cart> =
        try {
            getCartFromDatabase()
        } catch (e: NullPointerException) {
            val cartNetworkResult = getCartFromNetwork()
            if (cartNetworkResult is FetchResult.SuccessDataUpload) {
                saveCartInDatabase(cartNetworkResult.data)
            }
            cartNetworkResult
        }

    private fun getCartFromDatabase(): FetchResult<Cart> =
        FetchResult.SuccessDataUpload(cartScreenDao.getCart().mapToDomain())

    private suspend fun getCartFromNetwork(): FetchResult<Cart> =
        try {
            val cart = cartScreenService.getCart().mapToDomain()
            FetchResult.SuccessDataUpload(cart)
        } catch (e: Exception) {
            FetchResult.ErrorLoadingData(e.message)
        }

    private fun saveCartInDatabase(details: Cart) {
        cartScreenDao.saveCart(CartLocalDto.fromDomain(details))
    }
}