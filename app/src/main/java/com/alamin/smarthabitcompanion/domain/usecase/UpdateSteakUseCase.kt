package com.alamin.smarthabitcompanion.domain.usecase

import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class UpdateSteakUseCase @Inject constructor(
    private val getHabitsByDateAndIdUseCase: GetHabitsByDateAndIdUseCase,
    private val habitCompleteUseCase: HabitCompleteUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase
) {
    suspend fun invoke(habitId: Int) {

        var todayHabit = getHabitsByDateAndIdUseCase(
            habitId,
            LocalDate.now().toString()
        ).first()

        var previousDayHabit = getHabitsByDateAndIdUseCase(
            habitId,
            LocalDate.now().minusDays(1).toString()
        ).first()

        if (previousDayHabit != null) {
            previousDayHabit = habitCompleteUseCase.invoke(previousDayHabit)
        }

        if (todayHabit != null) {
            todayHabit = habitCompleteUseCase.invoke(todayHabit)
        }

        if (todayHabit != null) {

            if (todayHabit.isCompleted) {
                val updatedHabit = if (previousDayHabit != null && previousDayHabit.isCompleted) {
                    todayHabit.copy(streakCount = previousDayHabit.streakCount + 1)
                } else {
                    todayHabit.copy(streakCount = 1)
                }

                updateHabitUseCase.invoke(updatedHabit)
            }
        }

    }
}