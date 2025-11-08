package com.alamin.smarthabitcompanion.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.alamin.smarthabitcompanion.data.local.datastore.AppPreferenceRepository.Companion.PreferenceKey.FIRST_RUN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class AppPreferenceRepository @Inject constructor(@ApplicationContext private val  context:Context)  {

    private val dataStore = context.appPreference

    val isFirstRun = dataStore.data.map{
        it[FIRST_RUN] ?: true
    }

    suspend fun setFirstRun(isFirstRun : Boolean = false){
        dataStore.edit {
            it[FIRST_RUN] = isFirstRun
        }
    }

    companion object {
        private const val PREF_NAME = "app_preference"
        val Context.appPreference by preferencesDataStore(PREF_NAME)

        object PreferenceKey {
            val FIRST_RUN = booleanPreferencesKey("first_run")
        }

    }
}