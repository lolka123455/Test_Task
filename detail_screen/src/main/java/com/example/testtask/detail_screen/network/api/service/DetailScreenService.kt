package com.example.testtask.detail_screen.network.api.service

import com.example.testtask.detail_screen.network.models.ProductDetails
import retrofit2.http.GET

interface DetailScreenService {

    @GET("6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getDetails(): ProductDetails

}