package com.alamin.smarthabitcompanion.ui.presentation.model

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.HabitRecord

data class HabitDetailsUiModel(
    val completePercent: Float,
    val progress: Int,
    val habitGroup: Map<String, List<HabitRecord>>,
    val barWidth: Int,
    val habitRecord: List<HabitRecord>,
    val habit: Habit
)
