package com.alamin.smarthabitcompanion.ui.mapper

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.presentation.model.HabitOverviewUiModel
import java.time.LocalDate

fun List<Habit>.toUi(): HabitOverviewUiModel {

    val highestStreak = this.maxByOrNull { it.streakCount }
    val lowestStreak = this.minByOrNull { it.streakCount }
    val todayHabitRecord = this.map {
        val todayHabit = it.habitRecords.filter { it.date.equals(LocalDate.now().toString(), true) }
        it.copy(habitRecords = todayHabit)
    }


    val completedToday = todayHabitRecord.filter { it.isCompleted }

    val pending = todayHabitRecord.size - completedToday.size

    val completePercent =
        if (todayHabitRecord.isEmpty()) 0.0 else (completedToday.size.toFloat() / todayHabitRecord.size.toFloat()) * 100




    return HabitOverviewUiModel(
        this.size,
        completedToday.size,
        pending,
        completePercent.toInt(),
        highestStreak?.streakCount?:0,
        lowestStreak?.streakCount?:0,
        highestStreak?.name?:"Not Found",
        this
    )
}