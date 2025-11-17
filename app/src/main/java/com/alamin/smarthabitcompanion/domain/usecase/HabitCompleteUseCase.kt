package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.Habit
import javax.inject.Inject

class HabitCompleteUseCase @Inject constructor() {
    operator fun invoke(habit: Habit): Habit{
        val totalProgress = habit.habitRecords.sumOf { it.progress }
        return habit.copy(isCompleted = totalProgress >= (habit.target?:0))
    }
}