package com.alamin.smarthabitcompanion.core.utils

sealed class Result< out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: AppException) : Result<Nothing>()
}