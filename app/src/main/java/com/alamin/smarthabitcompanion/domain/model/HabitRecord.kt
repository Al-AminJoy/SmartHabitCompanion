package com.alamin.smarthabitcompanion.domain.model

import java.time.LocalDate

data class HabitRecord(
    val id: Int = 0,
    val habitId: Int,
    val date: LocalDate,
    val progress: Int,
)
