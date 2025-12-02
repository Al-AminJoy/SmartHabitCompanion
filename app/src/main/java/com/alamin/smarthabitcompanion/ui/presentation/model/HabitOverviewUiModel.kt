package com.alamin.smarthabitcompanion.ui.presentation.model

import com.alamin.smarthabitcompanion.domain.model.Habit

data class HabitOverviewUiModel(
    val habitSize: Int,
    val completed: Int,
    val notCompleted: Int,
    val completionPercent: Int,
    val highestStreak: Int,
    val lowestStreak: Int,
    val bestStreak: String,
    val habits: List<Habit>
)
