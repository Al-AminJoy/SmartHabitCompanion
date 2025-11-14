package com.alamin.smarthabitcompanion.ui.presentation.habits

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.AddHabitParam
import com.alamin.smarthabitcompanion.domain.model.AddHabitRecordParam
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.usecase.AddHabitUseCase
import com.alamin.smarthabitcompanion.domain.usecase.AddRecordUseCase
import com.alamin.smarthabitcompanion.domain.usecase.DeleteHabitUseCase
import com.alamin.smarthabitcompanion.domain.usecase.GetHabitsUseCase
import com.alamin.smarthabitcompanion.domain.usecase.HabitCompleteUseCase
import com.alamin.smarthabitcompanion.ui.theme.GreenApple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsScreenViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val addRecordUseCase: AddRecordUseCase,
    private val habitCompleteUseCase: HabitCompleteUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
) :
    ViewModel() {

    private val mutableUiSate = MutableStateFlow(UiState())

    val uiState = mutableUiSate.asStateFlow()

    init {

        viewModelScope.launch {
            getHabitsUseCase().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            ).collectLatest { habits ->
                if (habits.isNotEmpty()) {
                    mutableUiSate.update {
                        it.copy(habits = habits.map {
                            habitCompleteUseCase.invoke(
                                it
                            )
                        })
                    }
                }
            }
        }
    }

    fun onNameChange(value: String) {
        mutableUiSate.update { it.copy(habitName = value) }
    }

    fun onTargetChange(value: String) {
        mutableUiSate.update { it.copy(target = value) }
    }

    fun clearHabitInput() {
        mutableUiSate.update { it.copy(habitName = "", target = "", targetUnit = "") }
    }

    fun onTargetUnitChange(value: String) {
        mutableUiSate.update { it.copy(targetUnit = value) }
    }


    fun addHabit(onAddHabit: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = uiState.value
            if (state.habitName.isEmpty()) {
                mutableUiSate.update { it.copy(message = Pair(false, "Please Enter a Habit Name")) }
            } else if (state.habitName.length > AppConstants.ADD_HABIT_NAME_TEXT_LIMIT) {
                mutableUiSate.update { it.copy(message = Pair(false, "Habit Name is Too Long")) }
            } else if (state.target.length > AppConstants.ADD_HABIT_TARGET_TEXT_LIMIT) {
                mutableUiSate.update { it.copy(message = Pair(false, "Habit Target is Too Long")) }
            } else if (state.targetUnit.length > AppConstants.ADD_HABIT_UNIT_TEXT_LIMIT) {
                mutableUiSate.update {
                    it.copy(
                        message = Pair(
                            false,
                            "Habit Target Unit Text is Too Long"
                        )
                    )
                }
            } else {
                addHabitUseCase.invoke(
                    AddHabitParam(
                        state.habitName,
                        state.target.toIntOrNull(),
                        state.targetUnit
                    )
                )
                clearHabitInput()
                onAddHabit()
            }
        }
    }



    fun addHabitRecords(habitId: Int, value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addRecordUseCase.invoke(AddHabitRecordParam(habitId, value))
        }
    }

    fun removeHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteHabitUseCase.invoke(habit)
        }
    }

    fun messageShown() {
        mutableUiSate.update { it.copy(message = null) }
    }


}

data class UiState(
    val isLoading: Boolean = false,
    val message: Pair<Boolean, String>? = null,
    val showAddHabitDialog: Boolean = false,
    val habitName: String = "",
    val target: String = "",
    val targetUnit: String = "",
    val habits: List<Habit> = emptyList()
)