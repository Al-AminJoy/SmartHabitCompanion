package com.alamin.smarthabitcompanion.ui.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.presentation.model.HabitUiModel
import com.alamin.smarthabitcompanion.ui.theme.GreenApple

@Composable
fun Habit.toHabitUi(): HabitUiModel {

    val habitProgress : Int = this.habitRecords.sumOf { it.progress }

    val progress = if (this.isCompleted) {
        100.0f
    } else if (this.target == null) {
        0
    } else if (this.target == 0) {
        0.0f
    } else {

        if (habitProgress > 0) {
            val progressPercent =
                (habitProgress.toFloat() / this.target.toFloat() * 100)
            if (progressPercent > 100) {
                100.0f
            } else {
                progressPercent
            }
        } else {
            0.0f
        }
    }

    val completionText = if (this.isCompleted) {
        "Completed"
    } else if (habitProgress > 0) {
        "In Progress"
    } else {
        "Not Started"
    }

    val completionColor = if (this.isCompleted) {
        GreenApple
    } else if (habitProgress > 0) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.error
    }

    val completionIcon = if (this.isCompleted) {
        Icons.Default.CheckCircle
    } else if (habitProgress > 0) {
        Icons.Default.HourglassTop
    } else {
        Icons.Default.Report
    }

    return HabitUiModel(
        id = id,
        name = name,
        target = target,
        targetUnit = targetUnit,
        streak = streakCount,
        isCompleted = isCompleted,
        progress = habitProgress,
        percentage = progress.toInt(),
        completionText = completionText,
        completionColor = completionColor,
        icon = completionIcon
    )
}