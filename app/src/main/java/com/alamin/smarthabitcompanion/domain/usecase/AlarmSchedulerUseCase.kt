package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.AlarmItem
import com.alamin.smarthabitcompanion.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmSchedulerUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
     operator fun invoke(){
        val firstAlarm = AlarmItem(1, 8, 0, "Don’t forget to update your habit today \uD83D\uDCAA")
        val secondAlarm = AlarmItem(2, 12, 30, "Don’t forget to update your habit today \uD83D\uDCAA")
        val thirdAlarm = AlarmItem(3, 10, 0, "Don’t forget to update your habit today \uD83D\uDCAA")
        alarmRepository.scheduleAlarm(firstAlarm)
        alarmRepository.scheduleAlarm(secondAlarm)
        alarmRepository.scheduleAlarm(thirdAlarm)

    }
}