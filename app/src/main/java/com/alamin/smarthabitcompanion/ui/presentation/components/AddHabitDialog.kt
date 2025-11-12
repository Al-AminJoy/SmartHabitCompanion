package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.theme.GreenApple
import com.alamin.smarthabitcompanion.ui.theme.LightGrey

@Preview
@Composable
fun AddHabitDialog(
    modifier: Modifier = Modifier,
    habitName: String = "",
    habitTarget: Int = 0,
    habitUnit: String = "",
    onChangeHabitName: (String) -> Unit = {},
    onChangeHabitTarget: (Int) -> Unit = {},
    onChangeHabitUnit: (String) -> Unit = {},
    onAddHabit: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {

        Column(
            modifier = modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .padding(AppConstants.APP_MARGIN.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        Icons.Default.Attractions, contentDescription = null, Modifier.padding(
                            AppConstants.APP_MARGIN.dp
                        ), tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))
                Text(
                    "Add New Habit",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))
            HabitInput("Habit Name", habitName, "eg. Exercise", textLimit = 24, onValueChange = onChangeHabitName)
            Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
            HabitInput("Target", habitTarget.toString(), "eg. 10", textLimit = 6, isNumber = true, onValueChange = {
                onChangeHabitTarget(it.toIntOrNull() ?: 0)
            })
            Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
            HabitInput("Target Unit", habitUnit, "eg. Minute", textLimit = 16, onValueChange = onChangeHabitUnit)
            Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
            Row {
                TextButton(
                    onClick = {
                        onChangeHabitName("")
                        onChangeHabitTarget(0)
                        onChangeHabitUnit("")
                        onDismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = LightGrey,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = AppConstants.APP_MARGIN.dp)
                ) {
                    Text("Cancel", style = MaterialTheme.typography.labelSmall)
                }
                TextButton(
                    onClick = {
                        onAddHabit()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = GreenApple,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ), contentPadding = PaddingValues(vertical = 2.dp, horizontal = 2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = AppConstants.APP_MARGIN.dp)
                ) {
                    Text("Save", style = MaterialTheme.typography.labelSmall)
                }
            }

        }
    }
}

@Composable
fun HabitInput(title: String, value: String, hint: String,textLimit:Int=0, isNumber: Boolean = false, onValueChange: (String) -> Unit) {
    Text(title, style = MaterialTheme.typography.labelMedium)
    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
    BasicTextField(
        value = value, onValueChange = {
            onValueChange(it)
        }, modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (value.length <= textLimit){
                    MaterialTheme.colorScheme.onSurface
                }else {
                    MaterialTheme.colorScheme.error
                }, MaterialTheme.shapes.small
            )
            .padding(
                horizontal = (AppConstants.APP_MARGIN+4).dp,
                vertical = (AppConstants.APP_MARGIN+4).dp
            ),
        keyboardActions = KeyboardActions(onDone = {
            onValueChange(value)
        }),
        keyboardOptions = KeyboardOptions(keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = if (value.length <= textLimit){
            MaterialTheme.colorScheme.onSurface
        }else {
            MaterialTheme.colorScheme.error
        }),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(hint, style = MaterialTheme.typography.bodyMedium)
            }
            innerTextField()
        }
    )
}