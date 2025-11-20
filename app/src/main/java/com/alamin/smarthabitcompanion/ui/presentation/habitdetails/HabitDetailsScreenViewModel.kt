package com.alamin.smarthabitcompanion.ui.presentation.habitdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.domain.model.Habit
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

@HiltViewModel
class HabitDetailsScreenViewModel @Inject constructor(private val lastSevenDayHabitRecordUseCase: LastSevenDayHabitRecordUseCase) :
    ViewModel() {

    private val mutableState = MutableStateFlow(UIState())

    val uiState = mutableState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            lastSevenDayHabitRecordUseCase.invoke()
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
                .collectLatest { habit ->
                    if (habit.isNotEmpty()) {
                        mutableState.update { it.copy(habits = habit) }
                    }
                }
        }
    }

}

data class UIState(
    val habits: List<Habit> = arrayListOf()
)