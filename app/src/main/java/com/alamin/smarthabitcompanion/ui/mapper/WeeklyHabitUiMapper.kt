package com.alamin.smarthabitcompanion.ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.core.utils.Logger
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.presentation.model.WeeklyCompletionUiModel

private const val TAG = "WeeklyHabitUiMapper"
@Composable
fun List<Habit>.toWeeklyUi(initialAnimation: Boolean): WeeklyCompletionUiModel{

    val screenWidth = LocalConfiguration.current.screenWidthDp


    val completionPair = arrayListOf<Pair<String, Boolean>>()

    for (habit in this){
        val dayWiseGroup = habit.habitRecords.groupBy { it.date }

        for (dayRecord in dayWiseGroup){
            val totalProgress = dayRecord.value.sumOf { it.progress }
            completionPair.add(Pair(dayRecord.key, totalProgress >= (habit.target ?: 1) ))
        }
    }

    val dayWiseGroup = completionPair.groupBy { it.first }

    val dayWiseCompletion = arrayListOf<Pair<String,Int>>()
    for (dayRecord in dayWiseGroup){
        val completionPercent = (dayRecord.value.count { it.second }.toFloat()/this.size.toFloat()) * 100
        val pair= Pair(dayRecord.key,completionPercent.toInt())
        dayWiseCompletion.add(pair)
    }

    var barWidth = 0

    if (dayWiseCompletion.isNotEmpty()){
        val margin = AppConstants.APP_MARGIN
        val screenWidthWithoutMargin = screenWidth-margin
        barWidth = screenWidthWithoutMargin/dayWiseCompletion.size
    }

    return WeeklyCompletionUiModel(
        initialAnimation,
        dayWiseCompletion,
        barWidth,

    )
}