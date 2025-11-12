package com.alamin.smarthabitcompanion.ui.presentation.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.domain.model.AddHabitParam
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.usecase.AddHabitUseCase
import com.alamin.smarthabitcompanion.domain.usecase.GetHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsScreenViewModel @Inject constructor(private val getHabitsUseCase: GetHabitsUseCase,private val addHabitUseCase: AddHabitUseCase) :
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
                    mutableUiSate.update { it.copy(habits = habits) }
                }
            }
        }
    }

    fun onNameChange(value: String) {
        mutableUiSate.update { it.copy(habitName = value) }
    }

    fun onTargetChange(value: Int) {
        mutableUiSate.update { it.copy(target = value) }
    }

    fun onTargetUnitChange(value: String) {
        mutableUiSate.update { it.copy(targetUnit = value) }
    }

    fun addHabit(onAddHabit: () -> Unit) {
        viewModelScope.launch {
            val state = uiState.value

            if (state.habitName.isEmpty()){
                mutableUiSate.update { it.copy(message = Pair(false, "Please Enter a Habit Name")) }
            }else{
                addHabitUseCase.invoke(AddHabitParam(state.habitName,state.target,state.targetUnit))
                onAddHabit()
            }
        }
    }

    fun messageShown() {
        mutableUiSate.update { it.copy(message = null) }
    }


}

data class UiState(
    val isLoading: Boolean= false,
    val message: Pair<Boolean, String> ? = null,
    val showAddHabitDialog: Boolean = false,
    val habitName: String = "",
    val target: Int = 0,
    val targetUnit: String = "",
    val habits: List<Habit> = emptyList()
)