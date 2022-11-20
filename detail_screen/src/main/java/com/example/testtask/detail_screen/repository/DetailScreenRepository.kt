package com.example.testtask.detail_screen.repository

import com.example.testtask.detail_screen.network.models.ProductDetails

interface DetailScreenRepository {

    suspend fun getDetails(): ProductDetails
}