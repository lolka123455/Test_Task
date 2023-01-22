package com.example.testtask.main_screen.data.remote

import com.example.testtask.main_screen.data.remote.models.cart.CartNetworkDto
import com.example.testtask.main_screen.data.remote.models.main_page.MainPageNetworkDto
import retrofit2.http.GET

interface MainScreenService {

    @GET("/v3/654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getMainDataAsync(): MainPageNetworkDto

    @GET("/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getCartDataAsync(): CartNetworkDto
}