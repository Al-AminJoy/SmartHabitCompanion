package com.alamin.smarthabitcompanion.domain.model

data class Habit(
    val id: Int = 0,
    val name: String,
    val target: Int?,
    val targetUnit: String?,
    val streakCount: Int = 0,
    val isCompleted: Boolean = false,
    val habitRecords: List<HabitRecord> = arrayListOf()
)
