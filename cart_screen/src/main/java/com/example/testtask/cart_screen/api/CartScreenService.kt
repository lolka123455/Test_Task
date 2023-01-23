package com.example.testtask.cart_screen.api

import com.example.testtask.cart_screen.api.models.CartNetworkDto
import retrofit2.http.GET

interface CartScreenService {

    @GET("/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getCart(): CartNetworkDto
}