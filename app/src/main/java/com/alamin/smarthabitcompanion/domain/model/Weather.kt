package com.alamin.smarthabitcompanion.domain.model

data class Weather(
    val id: Int,
    val city: String,
    val country: String,
    val temperature: Double,
    val lastUpdated: String,
    val condition: String?,
    val icon: String?
)
