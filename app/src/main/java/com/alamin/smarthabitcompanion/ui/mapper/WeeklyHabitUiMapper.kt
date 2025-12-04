package com.alamin.smarthabitcompanion.ui.mapper

import com.alamin.smarthabitcompanion.core.utils.Logger
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.presentation.model.WeeklyCompletionUiModel

private const val TAG = "WeeklyHabitUiMapper"
fun List<Habit>.toWeeklyUi(): WeeklyCompletionUiModel{

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
        val pair= Pair(dayRecord.key,dayRecord.value.count { it.second })
        dayWiseCompletion.add(pair)
    }

    return WeeklyCompletionUiModel(
        dayWiseCompletion
    )
}