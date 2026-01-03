package com.alamin.smarthabitcompanion.data.repository

import com.alamin.smarthabitcompanion.data.local.room.dao.ProfileDao
import com.alamin.smarthabitcompanion.data.mapper.toDomain
import com.alamin.smarthabitcompanion.data.mapper.toEntity
import com.alamin.smarthabitcompanion.domain.model.Profile
import com.alamin.smarthabitcompanion.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor (private val profileDao: ProfileDao) : ProfileRepository {
    override suspend fun getProfile(): Flow<Profile?> {
        return profileDao.getProfile().map { it?.toDomain() }
    }

    override suspend fun addProfile(profile: Profile) {
        profileDao.insertProfile(profile.toEntity())
    }
}