package com.example.common.module

import androidx.annotation.StringRes


sealed class DataState<out T> {
    object Loading : DataState<Nothing>()

    data class Success<T>(
        val data: T
    ) : DataState<T>()

    data class Error(
        val exception: Exception,
        @StringRes var errorMessage : Int = -1
    ) : DataState<Nothing>()
}