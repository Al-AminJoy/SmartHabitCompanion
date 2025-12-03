package com.alamin.smarthabitcompanion.ui.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class HabitUiModel(
    val id: Int,
    val name: String,
    val streak: Int,
    val target: Int,
    val targetUnit: String?,
    val isCompleted: Boolean,
    val progress: Int,
    val percentage: Int,
    val completionText: String,
    val completionColor: Color,
    val icon: ImageVector
)