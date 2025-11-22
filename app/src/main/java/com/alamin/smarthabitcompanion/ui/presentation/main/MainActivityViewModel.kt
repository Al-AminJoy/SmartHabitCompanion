package com.alamin.smarthabitcompanion.ui.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.domain.model.AddHabitParam
import com.alamin.smarthabitcompanion.domain.usecase.AddHabitUseCase
import com.alamin.smarthabitcompanion.domain.usecase.preferences.GetFirstRunUseCase
import com.alamin.smarthabitcompanion.domain.usecase.preferences.SetFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getFirstRunUseCase: GetFirstRunUseCase,
    private val setFirstRunUseCase: SetFirstRunUseCase,
    private val addHabitUseCase: AddHabitUseCase,
) : ViewModel() {
    private val mutableUiState = MutableStateFlow(UISate())

    val uiState = mutableUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getFirstRunUseCase.invoke().collect {
                if (it){
                    val waterHabit = AddHabitParam("Drink Water",6,"Glass")
                    val walkingHabit = AddHabitParam("Walking",30,"Minutes")
                    val wakeUpHabit = AddHabitParam("Breakfast")

                    addHabitUseCase.invoke(waterHabit)
                    addHabitUseCase.invoke(walkingHabit)
                    addHabitUseCase.invoke(wakeUpHabit)
                    setFirstRunUseCase.invoke(false)
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

}

data class UISate(
    val isLoading: Boolean = false,
    val title: String = "",
    val showAddHabitDialog: Boolean = false,
)