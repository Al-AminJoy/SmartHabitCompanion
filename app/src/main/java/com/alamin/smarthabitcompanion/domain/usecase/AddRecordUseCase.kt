package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.AddHabitRecordParam
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class AddRecordUseCase @Inject constructor(private val habitRepository: HabitRepository) {
    suspend operator fun invoke(addRecordParam: AddHabitRecordParam) {
        val habit = habitRepository.getHabitById(
            addRecordParam.habitId,
        ).first()

        val todayRecord = habitRepository.getRecordByHabitIdAndDate(
            addRecordParam.habitId,
            LocalDate.now().toString()
        ).first()

        if (todayRecord == null) {
            val record = HabitRecord(
                habitId = addRecordParam.habitId,
                date = LocalDate.now(),
                progress = 0,
            )
            habitRepository.addRecord(record)
        } else {
            val record = todayRecord.copy(
                progress = todayRecord.progress + addRecordParam.progress,
            )

            habitRepository.updateRecord(record)

        }
    }
}