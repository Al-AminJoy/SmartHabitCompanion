package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.AddHabitRecordParam
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class AddRecordUseCase @Inject constructor(private val habitRepository: HabitRepository,
                                           private val updateSteakUseCase: UpdateSteakUseCase) {
    suspend operator fun invoke(addRecordParam: AddHabitRecordParam) {

        val record = HabitRecord(
            habitId = addRecordParam.habitId,
            date = LocalDate.now().toString(),
            progress = addRecordParam.progress,
        )
        habitRepository.addRecord(record)
        updateSteakUseCase.invoke(addRecordParam.habitId)
    }
}