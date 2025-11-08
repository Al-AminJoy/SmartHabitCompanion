package com.alamin.smarthabitcompanion.ui.presentation.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.domain.model.Habit
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
class HabitsScreenViewModel @Inject constructor(private val getHabitsUseCase: GetHabitsUseCase) :
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



}

data class UiState(
    val isLoading: Boolean= false,
    val message: Pair<Boolean, String> ? = null,
    val habits: List<Habit> = emptyList()
)