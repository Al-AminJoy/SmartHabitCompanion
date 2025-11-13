package com.alamin.smarthabitcompanion.domain.model

data class AddHabitParam(
    val name: String,
    val target: Int? = null,
    val targetUnit: String? = null
)