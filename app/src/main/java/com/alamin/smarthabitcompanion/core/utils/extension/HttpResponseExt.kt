package com.alamin.smarthabitcompanion.core.utils.extension

import com.alamin.smarthabitcompanion.core.utils.Logger
import com.alamin.smarthabitcompanion.core.utils.AppException
import com.alamin.smarthabitcompanion.data.remote.dto.ApiErrorDto
import com.google.gson.Gson
import retrofit2.Response

private const val TAG = "HttpResponseExt"
fun <T> Response<T>.getException(): AppException {
    return try {

        if (this.body() == null) {
            AppException.OthersException("Response body is null")
        } else {
            Logger.log(TAG, "getException: ${this}")
            if (this.isSuccessful && this.body() != null && this.body() is Boolean) {
                AppException.OthersException("Something went wrong, please try again later")
            } else {

                when (this.code()) {
                    401 -> AppException.AuthException()
                    404 -> AppException.ServerException("Resource Not Found")
                    else -> {
                        val apiErrorBody = this.errorBody()?.toString()
                        try {
                            val apiError = Gson().fromJson(apiErrorBody, ApiErrorDto::class.java)
                            AppException.OthersException(apiError.message ?: "Something went wrong, please try again later")

                        } catch (e: Exception) {
                            AppException.OthersException("Unexpected Error")
                        }
                    }
                }


            }
        }

    } catch (ex: Exception) {
        Logger.log(TAG, "getException: Exception $ex")
        AppException.OthersException("Unexpected Error")
    }
}
