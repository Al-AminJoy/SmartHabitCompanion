package com.alamin.smarthabitcompanion.ui.mapper

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.presentation.model.HabitOverviewUiModel
import com.alamin.smarthabitcompanion.ui.theme.BeanRed
import com.alamin.smarthabitcompanion.ui.theme.GreenApple
import com.alamin.smarthabitcompanion.ui.theme.primary
import java.time.LocalDate

fun List<Habit>.toUi(sevenDayHabits: List<Habit>): HabitOverviewUiModel {

    val highestStreak = this.maxByOrNull { it.streakCount }
    val lowestStreak = this.minByOrNull { it.streakCount }
    val todayHabitRecord = this.map {
        val todayHabit = it.habitRecords.filter { it.date.equals(LocalDate.now().toString(), true) }
        it.copy(habitRecords = todayHabit)
    }


    val completedToday = todayHabitRecord.filter { it.isCompleted }

    val pending = todayHabitRecord.size - completedToday.size

    val completePercent = if (todayHabitRecord.isEmpty()) {
        0.0f
    } else {
        (completedToday.size.toFloat() / todayHabitRecord.size.toFloat()) * 100
    }

    val progressColor = if (completePercent == 100f) {
        GreenApple
    }else if (completePercent > 0.0){
        primary
    } else {
        BeanRed
    }


    return HabitOverviewUiModel(
        this.size,
        completedToday.size,
        pending,
        completePercent.toInt(),
        progressColor,
        highestStreak?.streakCount ?: 0,
        lowestStreak?.streakCount ?: 0,
        highestStreak?.name ?: "Not Found",
        this,
        sevenDayHabits
    )
}