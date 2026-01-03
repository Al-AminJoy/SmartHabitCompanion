package com.alamin.smarthabitcompanion.ui.presentation.components

import android.opengl.Visibility
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.theme.DarkGray
import com.alamin.smarthabitcompanion.ui.theme.GreenApple
import com.alamin.smarthabitcompanion.ui.theme.LightGrey
import kotlinx.coroutines.delay

@Preview
@Composable
fun PersonalInformationDialog(
    modifier: Modifier = Modifier,
    name: String = "",
    isMale: Boolean = true,
    onChangeName: (String) -> Unit = {},
    onSelectGender: (Boolean) -> Unit = {},
    onAddInformation: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
    ) {

        var startAnimation by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(100)
            startAnimation = true
        }

        val focusManager = LocalFocusManager.current

        AnimatedVisibility(startAnimation, enter = scaleIn(animationSpec = tween(500))) {
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
                            Icons.Default.Person, contentDescription = null, Modifier.padding(
                                AppConstants.APP_MARGIN.dp
                            ), tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))
                    Text(
                        "Personal Information",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))
                InformationInput(
                    "Your Name",
                    name,
                    "eg. John Doe",
                    focusManager = focusManager,
                    textLimit = AppConstants.NAME_TEXT_LIMIT,
                    onValueChange = onChangeName
                )
                Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        onClick = {
                            focusManager.clearFocus()
                            onSelectGender(true) },
                        selected = isMale,
                    )
                    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN/2).dp))
                    Text(
                        "Male",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                    )
                    Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))
                    RadioButton(
                        onClick = {
                            focusManager.clearFocus()
                            onSelectGender(false) },
                        selected = !isMale,
                    )
                    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN/2).dp))

                    Text(
                        "Female",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                    )
                }

                Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))



                Row {
                    TextButton(
                        onClick = {
                            onAddInformation()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
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
}

@Composable
fun InformationInput(
    title: String,
    value: String,
    hint: String,
    textLimit: Int = 0,
    focusManager: FocusManager,
    isNumber: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Text(
        title,
        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurface)
    )
    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
    BasicTextField(
        value = value, onValueChange = {
            onValueChange(it)
        }, modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (value.length <= textLimit) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.error
                }, MaterialTheme.shapes.small
            )
            .padding(
                horizontal = (AppConstants.APP_MARGIN + 4).dp,
                vertical = (AppConstants.APP_MARGIN + 4).dp
            ),
        keyboardActions = KeyboardActions(onDone = {
            onValueChange(value)
            focusManager.clearFocus()
        }),
        keyboardOptions = KeyboardOptions(keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = if (value.length <= textLimit) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.error
            }
        ),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(hint, style = MaterialTheme.typography.bodyMedium)
            }
            innerTextField()
        }
    )
}