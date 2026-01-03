package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.domain.model.AddProfileParam
import com.alamin.smarthabitcompanion.domain.model.Profile
import com.alamin.smarthabitcompanion.domain.repository.ProfileRepository
import javax.inject.Inject

class AddProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(addProfileParam: AddProfileParam) {
        val profile = Profile(
            id = 0,
            name = addProfileParam.name,
            isMale = addProfileParam.isMale
        )

        profileRepository.addProfile(profile)
    }
}