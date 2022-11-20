package com.example.testtask.detail_screen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.detail_screen.network.models.ProductDetails
import com.example.testtask.detail_screen.usecases.GetFullProductDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val getFullProductDetail: GetFullProductDetail
) : ViewModel() {

    private val _productDetails = MutableLiveData<ProductDetails>()
    val productDetails: LiveData<ProductDetails> = _productDetails

    init {
        getFullDataForDetails()
    }

    private fun getFullDataForDetails() {
        viewModelScope.launch(Dispatchers.Default) {
            val productDetailsCallResult = getFullProductDetail.invoke()
            _productDetails.postValue(productDetailsCallResult)
        }
    }
}
