package com.alamin.smarthabitcompanion.data.di

import android.content.Context
import com.alamin.smarthabitcompanion.data.local.datastore.AppPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): AppPreferenceRepository{
        return AppPreferenceRepository(context)
    }
}