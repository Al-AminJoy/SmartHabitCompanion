package com.alamin.smarthabitcompanion.domain.usecase

import android.os.Build
import com.alamin.smarthabitcompanion.domain.model.AlarmItem
import com.alamin.smarthabitcompanion.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmPermissionCheckUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {

    operator fun invoke(): Boolean{

       return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmRepository.hasPermission()
        } else {
            true
        }

    }
}