package com.alamin.smarthabitcompanion.domain.repository

import com.alamin.smarthabitcompanion.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<Profile?>

    suspend fun addProfile(profile: Profile)

}