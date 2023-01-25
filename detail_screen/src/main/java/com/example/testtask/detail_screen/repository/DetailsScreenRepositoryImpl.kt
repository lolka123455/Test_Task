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
        withContext(Dispatchers.IO) {
            try {
                val productDetails = getProductDetailsFromDatabase()
                if (productDetails != null) {
                    return@withContext FetchResult.SuccessDataUpload(productDetails)
                } else {
                    val productDetailsNetworkResult = getProductDetailsFromNetwork()
                    if (productDetailsNetworkResult is FetchResult.SuccessDataUpload) {
                        saveProductDetailsInDatabase(productDetailsNetworkResult.data)
                    }
                    return@withContext productDetailsNetworkResult
                }
            } catch (e: SQLiteException) {
                return@withContext FetchResult.ErrorLoadingData(e.message ?: "Unknown Error")
            }
        }

    private fun getProductDetailsFromDatabase(): ProductDetails {
        return detailsScreenDao.getProductDetails().mapToDomain()
    }

    private suspend fun getProductDetailsFromNetwork(): FetchResult<ProductDetails> =
        withContext(Dispatchers.IO) {
            try {
                val productDetails = detailsScreenService.getDetails().mapToDomain()
                return@withContext FetchResult.SuccessDataUpload(productDetails)
            } catch (e: IOException) {
                return@withContext FetchResult.ErrorLoadingData(e.message ?: "Unknown Error")
            }
        }

    private fun saveProductDetailsInDatabase(details: ProductDetails) {
        detailsScreenDao.saveProductDetails(ProductDetailsLocalDto.fromDomain(details))
    }
}