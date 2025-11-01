package com.alamin.smarthabitcompanion.data.remote.dto


import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("current")
    val currentDto: CurrentDto,
    @SerializedName("location")
    val location: Location
)
data class ConditionDto(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("text")
    val text: String?
)
data class CurrentDto(
    @SerializedName("condition")
    val conditionDto: ConditionDto?,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("temp_c")
    val tempC: Double
)
data class Location(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("localtime")
    val localtime: String,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("tz_id")
    val tzId: String
)
