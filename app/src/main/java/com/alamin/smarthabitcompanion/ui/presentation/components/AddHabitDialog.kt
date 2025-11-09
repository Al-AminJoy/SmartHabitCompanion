package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.AddHabitParam

@Preview
@Composable
fun AddHabitDialog(modifier: Modifier = Modifier,onAddHabit:(habit: AddHabitParam) -> Unit = {},onDismiss: () -> Unit = {}) {
    Dialog(onDismissRequest = { onDismiss() }, properties = DialogProperties(dismissOnBackPress = true,dismissOnClickOutside = true)) {

        Column (modifier= modifier.padding(AppConstants.APP_MARGIN.dp)){
            Row (verticalAlignment = Alignment.CenterVertically){
                Box (modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.primary)) {
                    Icon(Icons.Default.Attractions, contentDescription = null, Modifier.padding(
                        AppConstants.APP_MARGIN.dp), tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))
                Text("Add New Habit", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}