package com.example.testtask.state_network_connection

sealed class FetchResult<T> {

    class SuccessDataUpload<T>(val data: T) : FetchResult<T>()
    class ErrorLoadingData<T>(val message: String?) : FetchResult<T>()
}   