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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _productDetails = MutableStateFlow<ProductDetails?>(null)
    val productDetails: StateFlow<ProductDetails?> = _productDetails

    init {
        getDetails()
    }

    private fun getDetails() {
        viewModelScope.launch(dispatcherIo) {
            val productDetailsCallResult = getProductDetailsUseCase.execute()
            if (productDetailsCallResult is FetchResult.SuccessDataUpload) {
                _productDetails.value = productDetailsCallResult.data
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error
            }
        }
    }

    fun retryNetworkCall() {
        getDetails()
    }
}