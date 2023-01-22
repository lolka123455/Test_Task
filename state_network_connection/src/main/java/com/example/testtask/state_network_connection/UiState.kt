package com.example.testtask.state_network_connection

sealed class UiState {

    object Success : UiState()
    object Error : UiState()
    object Loading : UiState()
}