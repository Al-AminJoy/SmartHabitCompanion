package com.alamin.smarthabitcompanion.ui.presentation.model

data class WeeklyCompletionUiModel(
    val initialAnimation: Boolean,
    val sevenDayHabits: List<Pair<String,Int>>,
    val barWidth: Int
)
