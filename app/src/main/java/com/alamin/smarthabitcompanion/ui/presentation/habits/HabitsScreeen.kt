package com.alamin.smarthabitcompanion.ui.presentation.habits

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(NavigationDestinations.HABITS.value)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        if (uiState.habits.isNotEmpty()){
            LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(
                (AppConstants.APP_MARGIN).dp), horizontalArrangement = Arrangement.spacedBy(
                AppConstants.APP_MARGIN.dp), verticalArrangement = Arrangement.spacedBy((AppConstants.APP_MARGIN).dp)) {
                items(uiState.habits.size){ index ->
                    val habit = uiState.habits[index]
                    HabitItem(habit,Modifier.fillMaxWidth(),toHabitDetails = toHabitDetails)
                }
            }
        }

        LaunchedEffect(uiState.message) {
            val message= uiState.message
            if (message != null) {
                Toast.makeText(context, message.second , Toast.LENGTH_SHORT).show()
            }
        }

    }
}