package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.theme.DarkGray

@Composable
fun HabitDeleteConfirmDialog(modifier: Modifier, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(onDismissRequest = {
        onDismiss()
    }, confirmButton = {
        Text(
            text = "Delete",
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(horizontal = AppConstants.APP_MARGIN.dp).clickable {
                onConfirm()
            })
    }, dismissButton = {
        Text(
            text = "Cancel",
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.padding(horizontal = AppConstants.APP_MARGIN.dp).clickable {
                onDismiss()
            })
    }, title = {
        Text(text = "Delete Habit !")
    }, text = {
        Text(text = "Are you sure you want to delete this habit?")
    }, icon = {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Habit",
            tint = MaterialTheme.colorScheme.error
        )
    })
}