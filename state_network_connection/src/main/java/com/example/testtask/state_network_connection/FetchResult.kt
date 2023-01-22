package com.example.testtask.state_network_connection

sealed class FetchResult<T> {

    class Success<T>(val data: T) : FetchResult<T>()
    class Error<T>(val message: String?) : FetchResult<T>()
}