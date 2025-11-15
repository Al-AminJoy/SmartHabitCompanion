package com.alamin.smarthabitcompanion.domain.usecase

import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateSteakUseCase @Inject constructor(private val getHabitsByIdUseCase: GetHabitsByIdUseCase,
                                             private val habitCompleteUseCase: HabitCompleteUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase) {
    suspend fun invoke(habitId: Int){

        val habit = getHabitsByIdUseCase(
            habitId,
        ).first()

        habit?.let {
            val habit = habitCompleteUseCase.invoke(it)

            it.streakCount = if (habit.isCompleted) habit.streakCount + 1 else 0

            updateHabitUseCase(it)
        }

    }
}