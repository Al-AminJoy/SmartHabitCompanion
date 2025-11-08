package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Habit

@Preview
@Composable
fun HabitItem(
    habit: Habit = Habit(1, "Drink Water", 5, "Glass", 1),
    modifier: Modifier = Modifier,
    toHabitDetails: (Int) -> Unit = {}
) {
    ElevatedCard (shape = MaterialTheme.shapes.medium, modifier = modifier,) {
        Column(modifier = Modifier.fillMaxWidth().padding(AppConstants.APP_MARGIN.dp)) {

            Text(habit.name)

        }
    }
}