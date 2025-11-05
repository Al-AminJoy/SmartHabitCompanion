package com.alamin.smarthabitcompanion.ui.presentation.habits

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel

@Composable
fun HabitsScreen(
    sharedViewModel: MainActivityViewModel,
    viewModel: HabitsScreenViewModel = hiltViewModel(),
    toAddHabit: () -> Unit,
    toHabitDetails: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(NavigationDestinations.HABITS.value)
    }
}