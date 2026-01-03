package com.alamin.smarthabitcompanion.data.mapper

import com.alamin.smarthabitcompanion.data.local.room.entity.ProfileEntity
import com.alamin.smarthabitcompanion.domain.model.Profile

fun Profile.toEntity(): ProfileEntity {
    return ProfileEntity(this.id, this.name, this.isMale)}

fun ProfileEntity.toDomain(): Profile {
    return Profile(this.id, this.name, this.isMale)}