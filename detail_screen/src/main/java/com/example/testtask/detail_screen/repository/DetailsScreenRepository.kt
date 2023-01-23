package com.example.testtask.detail_screen.repository

import com.example.testtask.detail_screen.entities.ProductDetails
import com.example.testtask.state_network_connection.FetchResult

interface DetailsScreenRepository {

    suspend fun getDetails(): FetchResult<ProductDetails>
}