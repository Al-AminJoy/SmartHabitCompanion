package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor(private val habitRepository: HabitRepository) {
    suspend operator fun invoke(habit: Habit) {

        habitRepository.updateHabit(habit)
    }
}