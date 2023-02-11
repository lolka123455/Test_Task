package com.example.testtask.detail_screen.repository

import android.database.sqlite.SQLiteException
import com.example.testtask.detail_screen.data.dao.DetailsScreenDao
import com.example.testtask.detail_screen.data.models.ProductDetailsLocalDto
import com.example.testtask.detail_screen.api.DetailsScreenService
import com.example.testtask.state_network_connection.FetchResult
import com.example.testtask.detail_screen.entities.ProductDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
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
            if (productDetailsNetworkResult is FetchResult.SuccessDataUpload) {
                saveProductDetailsInDatabase(productDetailsNetworkResult.data)
            }
            productDetailsNetworkResult
        }

    private fun getProductDetailsFromDatabase(): FetchResult<ProductDetails> =
        FetchResult.SuccessDataUpload(detailsScreenDao.getProductDetails().mapToDomain())

    private suspend fun getProductDetailsFromNetwork(): FetchResult<ProductDetails> =
        try {
            val productDetails = detailsScreenService.getDetails().mapToDomain()
            FetchResult.SuccessDataUpload(productDetails)
        } catch (e: Exception) {
            FetchResult.ErrorLoadingData(e.message)
        }

    private fun saveProductDetailsInDatabase(details: ProductDetails) {
        detailsScreenDao.saveProductDetails(ProductDetailsLocalDto.fromDomain(details))
    }
}