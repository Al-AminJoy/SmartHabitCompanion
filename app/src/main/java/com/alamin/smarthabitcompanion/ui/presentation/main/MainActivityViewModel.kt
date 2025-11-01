package com.alamin.smarthabitcompanion.ui.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val mutableUiState = MutableStateFlow(UISate())

    val uiState = mutableUiState.asStateFlow()

    fun updateTitle(title: String) {
        mutableUiState.update { it.copy(title = title) }
    }

}

data class UISate(
    val isLoading: Boolean = false,
    val title: String = ""
)