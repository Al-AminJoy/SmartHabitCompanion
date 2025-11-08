package com.alamin.smarthabitcompanion.domain.usecase.preferences

import com.alamin.smarthabitcompanion.data.local.datastore.AppPreferenceRepository
import javax.inject.Inject

class SetFirstRunUseCase  @Inject constructor(private val appPreferenceRepository: AppPreferenceRepository) {
    suspend operator fun invoke(isFirst: Boolean) {
        appPreferenceRepository.setFirstRun(isFirst)}
}