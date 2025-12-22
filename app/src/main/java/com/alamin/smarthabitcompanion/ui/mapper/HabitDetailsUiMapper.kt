package com.alamin.smarthabitcompanion.ui.mapper

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import com.alamin.smarthabitcompanion.ui.presentation.model.HabitDetailsUiModel

@Composable
fun List<HabitRecord>.toHabitDetailsUi(habit: Habit): HabitDetailsUiModel{

    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp

   val progress = habit.habitRecords.sumOf { it.progress }

    val completePercent = if (habit.habitRecords.isEmpty()) {
        0.0f
    } else {
        (progress.toFloat() / (habit.target ?: 1)) * 100
    }

    val habitGroup: Map<String, List<HabitRecord>> = this.groupBy { it.date }

    var barWidth = 0

    if (this.isNotEmpty()) {
        val margin = AppConstants.APP_MARGIN
        val screenWidthWithoutMargin = screenWidth - margin
        barWidth = screenWidthWithoutMargin / habitGroup.size
    }

    return HabitDetailsUiModel(completePercent,progress,habitGroup,barWidth,this,habit)
}