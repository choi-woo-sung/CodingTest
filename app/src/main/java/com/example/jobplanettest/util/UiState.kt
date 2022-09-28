package com.example.jobplanettest.util

sealed class UiState<out T : Any> {

    object Loading : UiState<Nothing>()
    data class Success<T : Any>(val data: T) : UiState<T>()
    data class Error(val error: Throwable?) : UiState<Nothing>()
}
