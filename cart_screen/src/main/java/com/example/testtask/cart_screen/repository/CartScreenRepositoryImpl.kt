package com.example.testtask.cart_screen.repository

import com.example.testtask.cart_screen.data.dao.CartScreenDao
import com.example.testtask.cart_screen.data.models.CartLocalDto
import com.example.testtask.cart_screen.api.CartScreenService
import com.example.testtask.state_network_connection.FetchResult
import com.example.testtask.cart_screen.entities.Cart
import java.lang.Exception

class CartScreenRepositoryImpl(
    private val cartScreenService: CartScreenService,
    private val cartScreenDao: CartScreenDao
) : CartScreenRepository {

    override suspend fun getCart(): FetchResult<Cart> =
        try {
            getCartFromDatabase()
        } catch (e: NullPointerException) {
            val cartNetworkResult = getCartFromNetwork()
            if (cartNetworkResult is FetchResult.Success) {
                saveCartInDatabase(cartNetworkResult.data)
            }
            cartNetworkResult
        }

    private fun getCartFromDatabase(): FetchResult<Cart> =
        FetchResult.Success(cartScreenDao.getCart().mapToDomain())

    private suspend fun getCartFromNetwork(): FetchResult<Cart> =
        try {
            val cart = cartScreenService.getCart().mapToDomain()
            FetchResult.Success(cart)
        } catch (e: Exception) {
            FetchResult.Error(e.message)
        }

    private fun saveCartInDatabase(details: Cart) {
        cartScreenDao.saveCart(CartLocalDto.fromDomain(details))
    }
}