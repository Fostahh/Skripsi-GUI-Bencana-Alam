package com.mohammadazri.gui_bencana_alam.core.data.source.remote.util

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class Error(val errorMessage: String): ApiResponse<Nothing>()
    object Loading: ApiResponse<Nothing>()
}