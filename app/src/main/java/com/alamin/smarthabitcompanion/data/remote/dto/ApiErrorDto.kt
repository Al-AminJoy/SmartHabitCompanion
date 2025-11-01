package com.alamin.smarthabitcompanion.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorDto(
    @SerialName("timestamp")
    var timestamp: String?,
    @SerialName("status")
    var status: Int?,
    @SerialName("error")
    var error: String?,
    @SerialName("message")
    var message: String?,
    @SerialName("path")
    var path: String?
)