package com.alamin.smarthabitcompanion.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.core.network.ServerConstants
import com.alamin.smarthabitcompanion.core.utils.Result
import com.alamin.smarthabitcompanion.core.utils.extension.getMessage
import com.alamin.smarthabitcompanion.domain.model.CurrentWeatherRequestParam
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.Weather
import com.alamin.smarthabitcompanion.domain.usecase.CurrentWeatherRequestUseCase
import com.alamin.smarthabitcompanion.domain.usecase.CurrentWeatherUseCase
import com.alamin.smarthabitcompanion.domain.usecase.GetHabitsUseCase
import com.alamin.smarthabitcompanion.domain.usecase.HabitCompleteUseCase
import com.alamin.smarthabitcompanion.domain.usecase.TodayHabitRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currentWeatherRequestUseCase: CurrentWeatherRequestUseCase,
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val getHabitsUseCase: GetHabitsUseCase,
    private val getTodayHabitRecordUseCase: TodayHabitRecordUseCase,
    private val habitCompleteUseCase: HabitCompleteUseCase
) :
    ViewModel() {
    private val mutableUIState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = mutableUIState


    init {
        requestCurrentWeather()
        observeWeatherData()
        observeHabits()
        observeTodayHabits()
    }

    private fun observeWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            currentWeatherUseCase.invoke().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(), null
            ).collectLatest {
                it?.let {
                    updateUIState(uiState.value.copy(weather = it))
                }
            }
        }
    }

    private fun observeHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            getHabitsUseCase.invoke().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(), emptyList()
            ).collectLatest { habits ->
                if (habits.isNotEmpty()) {
                    mutableUIState.update { it.copy(habits = habits ) }
                }
            }

        }

    }

    private fun observeTodayHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            getTodayHabitRecordUseCase.invoke().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(), emptyList()
            ).collectLatest { habits ->
                if (habits.isNotEmpty()) {
                    mutableUIState.update { it.copy(todayHabits = habits.map { habitCompleteUseCase.invoke(it) }) }
                }
            }

        }

    }

    fun updateUIState(uiState: UIState) {
        mutableUIState.update { uiState }
    }


    fun requestCurrentWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableUIState.update { it.copy(isLoading = true) }
            val weatherRequestParam = CurrentWeatherRequestParam("Dhaka", ServerConstants.API_KEY)
            mutableUIState.update {
                when (val result = currentWeatherRequestUseCase.invoke(weatherRequestParam)) {
                    is Result.Error -> {
                        it.copy(isLoading = false, errorMessage = result.exception.getMessage())
                    }

                    is Result.Success<Weather> -> {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }


}

data class UIState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val weather: Weather? = null,
    val todayHabits: List<Habit> = arrayListOf(),
    val habits: List<Habit> = arrayListOf(),
    val initialAnimation: Boolean = false,
    val isMale: Boolean = true
)