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

    /**
     * Retrieves the cart details from the local database, if not found, it fetches the data from the network.
     *
     * @return [FetchResult] that contains the cart details or an error message
     */

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

    /**
     * Fetches the cart details from the network.
     *
     * @return [FetchResult] that contains the cart details or an error message
     */

    private suspend fun getCartFromNetwork(): FetchResult<Cart> =
        withContext(Dispatchers.IO) {
            try {
                val cart = cartScreenService.getCart().mapToDomain()
                FetchResult.SuccessDataUpload(cart)
            } catch (e: IOException) {
                FetchResult.ErrorLoadingData(e.message)
            }
        }

    /**
     * Saves the cart details in the local database.
     *
     * @param details [Cart] object that needs to be saved
     */

    private fun saveCartInDatabase(details: Cart) {
        cartScreenDao.saveCart(CartLocalDto.fromDomain(details))
    }
}