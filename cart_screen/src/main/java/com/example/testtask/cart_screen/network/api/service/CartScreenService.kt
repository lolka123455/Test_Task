package com.example.testtask.cart_screen.network.api.service

import com.example.testtask.cart_screen.network.models.Checkout
import retrofit2.http.GET

interface CartScreenService {

    @GET("53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getItemsCart(): Checkout
}