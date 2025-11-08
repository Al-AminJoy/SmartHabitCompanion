package com.alamin.smarthabitcompanion.ui.presentation.habits

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
import com.alamin.smarthabitcompanion.ui.presentation.components.HabitItem
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel

@Composable
fun HabitsScreen(
    sharedViewModel: MainActivityViewModel,
    viewModel: HabitsScreenViewModel = hiltViewModel(),
    toAddHabit: () -> Unit,
    toHabitDetails: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(NavigationDestinations.HABITS.value)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        if (uiState.habits.isNotEmpty()){
            LazyColumn {
                items(uiState.habits){ habit ->
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