package com.example.testtask.detail_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.state_network_connection.UiState
import com.example.testtask.detail_screen.entities.ProductDetails
import com.example.testtask.detail_screen.usecases.GetProductDetailsUseCase
import com.example.testtask.state_network_connection.FetchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    private val _productDetails = MutableLiveData<ProductDetails>()

    val productDetails: LiveData<ProductDetails> = _productDetails
    val uiState: LiveData<UiState> = _uiState

    init {
        getDetails()
    }

    private fun getDetails() {
        viewModelScope.launch(dispatcherIo) {
            val productDetailsCallResult = getProductDetailsUseCase.execute()
            if (productDetailsCallResult is FetchResult.SuccessDataUpload) {
                _productDetails.postValue(productDetailsCallResult.data!!)
                _uiState.postValue(UiState.Success)
            } else {
                _uiState.postValue(UiState.Error)
            }
        }
    }

    fun retryNetworkCall() {
        getDetails()
    }
}