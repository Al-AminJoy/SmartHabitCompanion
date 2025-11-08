package com.alamin.smarthabitcompanion.domain.model

data class AddHabitParam(
    val name: String,
    val target: Int? = 0,
    val targetUnit: String? = null
)