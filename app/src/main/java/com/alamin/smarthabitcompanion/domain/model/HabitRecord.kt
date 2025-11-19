package com.alamin.smarthabitcompanion.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HabitRecord(
    val id: Int = 0,
    val habitId: Int,
    val date: String,
    val progress: Int,
)
