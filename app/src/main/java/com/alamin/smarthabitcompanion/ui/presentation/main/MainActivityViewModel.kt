package com.alamin.smarthabitcompanion.ui.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.AddHabitParam
import com.alamin.smarthabitcompanion.domain.model.AddProfileParam
import com.alamin.smarthabitcompanion.domain.model.AlarmItem
import com.alamin.smarthabitcompanion.domain.repository.AlarmRepository
import com.alamin.smarthabitcompanion.domain.usecase.AddHabitUseCase
import com.alamin.smarthabitcompanion.domain.usecase.AddProfileUseCase
import com.alamin.smarthabitcompanion.domain.usecase.preferences.GetFirstRunUseCase
import com.alamin.smarthabitcompanion.domain.usecase.preferences.SetFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val addProfileUseCase: AddProfileUseCase,
    private val getFirstRunUseCase: GetFirstRunUseCase,
    private val setFirstRunUseCase: SetFirstRunUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val alarmRepository: AlarmRepository
) : ViewModel() {
    private val mutableUiState = MutableStateFlow(UISate())

    val uiState = mutableUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getFirstRunUseCase.invoke().collect {
                if (it) {
                    updatePersonalInformationDialog(true)
                    updateAlarmPermissionDialog(true)
                }
            }
        }
    }

    fun updateTitle(title: String) {
        mutableUiState.update { it.copy(title = title) }
    }

    fun updateAddHabitDialog(showDialog: Boolean) {
        mutableUiState.update { it.copy(showAddHabitDialog = showDialog) }
    }

    fun updatePersonalInformationDialog(showDialog: Boolean) {
        mutableUiState.update { it.copy(showPersonalInformationDialog = showDialog) }
    }

    fun updateAlarmPermissionDialog(showDialog: Boolean) {
        mutableUiState.update { it.copy(showAlarmPermissionDialog = showDialog) }
    }

    fun updateName(name: String) {
        mutableUiState.update { it.copy(name = name) }
    }

    fun updateGender(isMale: Boolean) {
        mutableUiState.update { it.copy(isMale = isMale) }
    }

    fun messageShown() {
        mutableUiState.update { it.copy(message = null) }
    }

    fun hasAlarmPermission(): Boolean {
        return alarmRepository.hasPermission()
    }

    fun insertPersonalInformation(onInsert: () -> Unit) {
        viewModelScope.launch {
            val state = uiState.value
            if (state.name.isEmpty()) {
                mutableUiState.update { it.copy(message = Pair(false, "Please Enter a Your Name")) }
            } else if (state.name.length > AppConstants.NAME_TEXT_LIMIT) {
                mutableUiState.update { it.copy(message = Pair(false, "Name is Too Long")) }
            } else {

                val waterHabit = AddHabitParam("Drink Water", 6, "Glass")
                val walkingHabit = AddHabitParam("Walking", 30, "Minutes")
                val wakeUpHabit = AddHabitParam("Breakfast")
                val profile = AddProfileParam(state.name, state.isMale)
                addProfileUseCase.invoke(profile)
                addHabitUseCase.invoke(waterHabit)
                addHabitUseCase.invoke(walkingHabit)
                addHabitUseCase.invoke(wakeUpHabit)
                setFirstRunUseCase.invoke(false)
                onInsert()
            }
        }

    }

    fun scheduleAlarm() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23) // 11 PM
            set(Calendar.MINUTE, 38)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val triggerAtMillis = calendar.timeInMillis
        alarmRepository.scheduleAlarm(
            AlarmItem(
                1,
                message = "Night Reminder",
                time = triggerAtMillis
            )
        )
    }

}

data class UISate(
    val isLoading: Boolean = false,
    val message: Pair<Boolean, String>? = null,
    val title: String = "",
    val name: String = "",
    val isMale: Boolean = true,
    val showAddHabitDialog: Boolean = false,
    val showPersonalInformationDialog: Boolean = false,
    val showAlarmPermissionDialog: Boolean = true,
)