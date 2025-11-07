package com.alamin.smarthabitcompanion.domain.model

data class Habit(
    val id: Int = 0,
    val name: String,
    val target: Int?,
    val targetUnit: Int?,
    val streakCount: Int = 0,
    val habitRecords: List<HabitRecord> = arrayListOf()
)
