package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSevenDayHabitRecordByIdUseCase @Inject constructor(private val habitsRepository: HabitRepository) {
    suspend operator fun invoke(habitId: Int): Flow<List<HabitRecord>> {
        return habitsRepository.getSevenDayHabitRecordById(habitId)
    }
}