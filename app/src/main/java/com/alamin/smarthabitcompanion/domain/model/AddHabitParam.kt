package com.alamin.smarthabitcompanion.domain.model

data class AddHabitParam(
    val name: String,
    val target: Int?,
    val targetUnit: Int?
)