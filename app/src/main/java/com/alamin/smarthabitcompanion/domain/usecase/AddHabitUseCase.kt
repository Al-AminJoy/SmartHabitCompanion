package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.AddHabitParam
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(private val habitRepository: HabitRepository) {
    suspend operator fun invoke(addHabitParam: AddHabitParam) {

        val habit = Habit(
            name = addHabitParam.name,
            target = addHabitParam.target,
            targetUnit = addHabitParam.targetUnit,
            streakCount = 0
        )
        habitRepository.addHabit(habit)
    }
}