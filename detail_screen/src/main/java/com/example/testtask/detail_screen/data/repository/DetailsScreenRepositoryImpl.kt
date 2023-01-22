package com.example.testtask.detail_screen.data.repository

import com.example.testtask.detail_screen.data.local.dao.DetailsScreenDao
import com.example.testtask.detail_screen.data.local.models.ProductDetailsLocalDto
import com.example.testtask.detail_screen.data.remote.DetailsScreenService
import com.example.testtask.state_network_connection.FetchResult
import com.example.testtask.detail_screen.domain.entities.ProductDetails
import com.example.testtask.detail_screen.domain.repository.DetailsScreenRepository
import java.lang.Exception

class DetailsScreenRepositoryImpl(
    private val detailsScreenService: DetailsScreenService,
    private val detailsScreenDao: DetailsScreenDao
) : DetailsScreenRepository {

    override suspend fun getDetails(): FetchResult<ProductDetails> =
        try {
            getProductDetailsFromDatabase()
        } catch (e: NullPointerException) {
            val productDetailsNetworkResult = getProductDetailsFromNetwork()
            if (productDetailsNetworkResult is FetchResult.Success) {
                saveProductDetailsInDatabase(productDetailsNetworkResult.data)
            }
            productDetailsNetworkResult
        }

    private fun getProductDetailsFromDatabase(): FetchResult<ProductDetails> =
        FetchResult.Success(detailsScreenDao.getProductDetails().mapToDomain())

    private suspend fun getProductDetailsFromNetwork(): FetchResult<ProductDetails> =
        try {
            val productDetails = detailsScreenService.getDetails().mapToDomain()
            FetchResult.Success(productDetails)
        } catch (e: Exception) {
            FetchResult.Error(e.message)
        }

    private fun saveProductDetailsInDatabase(details: ProductDetails) {
        detailsScreenDao.saveProductDetails(ProductDetailsLocalDto.fromDomain(details))
    }
}