package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.presentation.model.HabitUiModel
import com.alamin.smarthabitcompanion.ui.theme.BeanRed
import com.alamin.smarthabitcompanion.ui.theme.Green
import com.alamin.smarthabitcompanion.ui.theme.Red

@Composable
fun HabitItem(
    modifier: Modifier = Modifier,
    habitUi: HabitUiModel,
    toHabitDetails: () -> Unit = {},
    addHabitRecords: (Int, Int) -> Unit = { _, _ -> },
    removeHabit: () -> Unit = { },
) {

    val swipToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
             if (it == SwipeToDismissBoxValue.EndToStart) {
                removeHabit()
                false
            }else {
                false
             }
        }
    )

    SwipeToDismissBox(state = swipToDismissBoxState, modifier = modifier, backgroundContent = {
        when (swipToDismissBoxState.dismissDirection) {
            SwipeToDismissBoxValue.StartToEnd -> {

            }

            SwipeToDismissBoxValue.EndToStart -> {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove item",
                    modifier = Modifier
                        .fillMaxSize()
                        .drawBehind {
                            drawRect(lerp(BeanRed, Red, swipToDismissBoxState.progress))
                        }
                        .wrapContentSize(Alignment.CenterEnd)
                        .padding(12.dp),
                    tint = Color.White
                )
            }

            SwipeToDismissBoxValue.Settled -> {

            }
        }
    }) {
        ElevatedCard(
            shape = MaterialTheme.shapes.medium,
            modifier = modifier.clickable {
                toHabitDetails()
            },
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(AppConstants.APP_MARGIN.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(habitUi.completionColor)
                    )
                    {
                        Icon(
                            Icons.Default.Attractions, contentDescription = null, Modifier
                                .size(32.dp)
                                .padding(
                                    AppConstants.APP_MARGIN.dp
                                ), tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))


                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${habitUi.percentage}%",
                            style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center)
                        )
                        CircularProgressIndicator(
                            progress = { habitUi.percentage.toFloat() / 100 },
                            modifier = Modifier.size(48.dp),
                            color = habitUi.completionColor,
                            strokeWidth = (AppConstants.APP_MARGIN / 2).dp,
                            trackColor = habitUi.completionColor.copy(alpha = .30f),
                            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                        )
                    }
                }

                Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN).dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Text(
                        habitUi.name,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))

                    if (habitUi.target != null) {
                        Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
                        Text(
                            "${habitUi.progress}/${habitUi.target} ${habitUi.targetUnit}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
                    Text("${habitUi.streak} Days Steak", style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            habitUi.icon,
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = habitUi.completionColor
                        )
                        Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
                        Text(
                            habitUi.completionText,
                            style = MaterialTheme.typography.bodySmall.copy(
                                textAlign = TextAlign.End,
                                color = habitUi.completionColor
                            ),
                            modifier = Modifier
                        )
                    }
                }

                Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN).dp))

                if (!habitUi.isCompleted) {
                    if (habitUi.target == null || habitUi.targetUnit == null) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = {
                                addHabitRecords(habitUi.id, 1)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = habitUi.completionColor,
                                uncheckedColor = habitUi.completionColor
                            )
                        )
                    } else {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.border(2.dp, habitUi.completionColor, CircleShape)
                        ) {
                            IconButton(
                                onClick = {
                                    addHabitRecords(habitUi.id, 1)
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(2.dp),
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,

                                    tint = habitUi.completionColor,
                                )
                            }
                        }
                    }
                }
            }

        }
    }


}