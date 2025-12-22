package com.alamin.smarthabitcompanion.ui.presentation.habitdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.core.utils.Logger
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import com.alamin.smarthabitcompanion.domain.usecase.GetSevenDayHabitRecordByIdUseCase
import com.alamin.smarthabitcompanion.domain.usecase.LastSevenDayHabitRecordUseCase
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

private const val TAG = "HabitDetailsScreenViewModel"
@HiltViewModel
class HabitDetailsScreenViewModel @Inject constructor(private val lastSevenDayHabitRecordByIdUseCase: GetSevenDayHabitRecordByIdUseCase) :
    ViewModel() {

    private val mutableState = MutableStateFlow(UIState())

    val uiState = mutableState.asStateFlow()


    fun observeHabit(habitId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            lastSevenDayHabitRecordByIdUseCase.invoke(habitId)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
                .collectLatest { habit ->
                    if (habit.isNotEmpty()) {
                        mutableState.update { it.copy(habitRecord = habit) }
                    }
                }
        }
    }

    fun updateHabit(habit: Habit) {
        mutableState.update { it.copy(habit = habit) }
    }


}

data class UIState(
    val habitRecord: List<HabitRecord> = arrayListOf(),
    val habit: Habit? = null
)