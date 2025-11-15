package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHabitsByIdUseCase @Inject constructor(private val habitsRepository: HabitRepository) {
    suspend operator fun invoke(habitId: Int): Flow<Habit?>{
        return habitsRepository.getHabitById(habitId)
    }
}