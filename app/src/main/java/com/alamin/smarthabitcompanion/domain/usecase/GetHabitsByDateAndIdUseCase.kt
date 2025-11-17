package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHabitsByDateAndIdUseCase @Inject constructor(private val habitsRepository: HabitRepository) {
    suspend operator fun invoke(habitId: Int,date: String): Flow<Habit?>{
        return habitsRepository.getHabitByDateAndId(habitId,date)
    }
}