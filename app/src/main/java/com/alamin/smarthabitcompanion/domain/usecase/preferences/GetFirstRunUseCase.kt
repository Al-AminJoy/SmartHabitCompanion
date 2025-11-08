package com.alamin.smarthabitcompanion.domain.usecase.preferences

import com.alamin.smarthabitcompanion.data.local.datastore.AppPreferenceRepository
import javax.inject.Inject

class GetFirstRunUseCase @Inject constructor(private val appPreferenceRepository: AppPreferenceRepository) {
    suspend operator fun invoke() = appPreferenceRepository.isFirstRun
}