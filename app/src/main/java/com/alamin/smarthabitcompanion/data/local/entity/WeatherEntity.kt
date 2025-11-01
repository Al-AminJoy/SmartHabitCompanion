package com.alamin.smarthabitcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val city: String,
    val country: String,
    val temperature: Double,
    val lastUpdated: String,
    val condition: String?,
    val icon: String?
)
