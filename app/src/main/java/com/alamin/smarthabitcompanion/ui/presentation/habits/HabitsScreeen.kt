package com.alamin.smarthabitcompanion.ui.presentation.habits

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
import com.alamin.smarthabitcompanion.ui.presentation.components.AddHabitDialog
import com.alamin.smarthabitcompanion.ui.presentation.components.HabitItem
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel

@Composable
fun HabitsScreen(
    sharedViewModel: MainActivityViewModel,
    viewModel: HabitsScreenViewModel = hiltViewModel(),
    toAddHabit: () -> Unit,
    toHabitDetails: (Habit) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sharedUiState by sharedViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(NavigationDestinations.HABITS.value)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        if (uiState.habits.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth().padding(horizontal = AppConstants.APP_MARGIN.dp)) {
                items(uiState.habits.size) { index ->
                    val habit = uiState.habits[index]
                    HabitItem(habit, Modifier.fillMaxWidth(), toHabitDetails = toHabitDetails)
                    Spacer(modifier = Modifier.padding(vertical = (AppConstants.APP_MARGIN/2).dp))
                }
            }
        }

        LaunchedEffect(uiState.message) {
            val message = uiState.message
            if (message != null) {
                Toast.makeText(context, message.second, Toast.LENGTH_SHORT).show()
                viewModel.messageShown()
            }
        }

        if (sharedUiState.showAddHabitDialog) {
            AddHabitDialog(
                modifier = Modifier.fillMaxWidth(),
                habitName = uiState.habitName,
                habitTarget = uiState.target,
                uiState.targetUnit,
                onChangeHabitName = {
                    viewModel.onNameChange(it)
                },
                onChangeHabitTarget = {
                    viewModel.onTargetChange(it)
                },
                onChangeHabitUnit = {
                    viewModel.onTargetUnitChange(it)
                },
                onAddHabit = {
                    viewModel.addHabit(onAddHabit = {
                        sharedViewModel.updateAddHabitDialog(false)
                    })
                },
                onDismiss = {
                    sharedViewModel.updateAddHabitDialog(false)
                })
        }

    }
}