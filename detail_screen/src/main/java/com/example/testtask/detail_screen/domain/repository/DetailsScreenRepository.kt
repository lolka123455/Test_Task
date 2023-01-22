package com.example.testtask.detail_screen.domain.repository

import com.example.testtask.detail_screen.domain.entities.ProductDetails
import com.example.testtask.state_network_connection.FetchResult

interface DetailsScreenRepository {

    suspend fun getDetails(): FetchResult<ProductDetails>
}