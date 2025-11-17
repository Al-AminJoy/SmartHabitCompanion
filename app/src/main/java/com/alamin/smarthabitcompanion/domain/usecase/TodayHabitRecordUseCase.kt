package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodayHabitRecordUseCase @Inject constructor (private val habitRepository: HabitRepository) {
    suspend operator fun invoke(): Flow<List<Habit>> {
       return habitRepository.getTodayHabitRecord()
    }
}