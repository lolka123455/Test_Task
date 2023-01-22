package com.example.testtask.detail_screen.data.remote

import com.example.testtask.detail_screen.data.remote.models.ProductDetailsNetworkDto
import retrofit2.http.GET

interface DetailsScreenService {

    @GET("/v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getDetails(): ProductDetailsNetworkDto
}