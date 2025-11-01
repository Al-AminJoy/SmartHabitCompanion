package com.alamin.smarthabitcompanion.core.utils.extension

import com.alamin.smarthabitcompanion.core.utils.AppException
import com.alamin.smarthabitcompanion.core.utils.Logger
import java.net.ConnectException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

private const val TAG = "ExceptionExt"

fun Exception.getSpecificException(): AppException{
    Logger.log(TAG, "getSpecificException: ${this.message}")
   return when(this){
        is UnresolvedAddressException -> AppException.NetworkException()
        is UnknownHostException, is ConnectException -> AppException.ServerException(message = "Unable to Communicate With Server")
        else -> AppException.OthersException(message = "Something Went Wrong")
    }
}