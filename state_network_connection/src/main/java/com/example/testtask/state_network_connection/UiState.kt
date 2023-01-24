package com.example.testtask.state_network_connection

/**
 * This is a sealed class that represents the different states of the UI.
 *
 * @property Success represents a successful state
 * @property Error represents an error state
 * @property Loading represents a loading state
 *
 */

sealed class UiState {

    object Success : UiState()
    object Error : UiState()
    object Loading : UiState()
}