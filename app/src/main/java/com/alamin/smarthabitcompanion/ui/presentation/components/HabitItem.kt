package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.theme.GreenApple
import com.alamin.smarthabitcompanion.ui.theme.Red

@Preview
@Composable
fun HabitItem(
    habit: Habit = Habit(1, "Drink Water", 5, "Glass", 1, false),
    modifier: Modifier = Modifier,
    toHabitDetails: (Habit) -> Unit = {}
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.clickable {
            toHabitDetails(habit)
        },
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(AppConstants.APP_MARGIN.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
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
                val progress = if (habit.isCompleted) {
                    100
                } else if (habit.target == null){
                    0
                }else if (habit.target == 0){
                    0
                }else{
                    val habitProgress = habit.habitRecords.sumOf { it.progress }
                    if (habitProgress > 0) {
                       val progressPercent =  (habitProgress.toFloat() / habit.target.toFloat() * 100).toInt()
                        if (progressPercent > 100){
                            100
                        }else{
                            progressPercent
                        }
                    } else {
                        0
                    }
                }
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "$progress%",style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center))
                    CircularProgressIndicator(
                        progress = { (".$progress").toFloat() },
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = (AppConstants.APP_MARGIN / 2).dp,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = .30f),
                        strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                    )
                }
            }

            Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN).dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {
                Text(
                    habit.name,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
                val habitProgress = habit.habitRecords.sumOf { it.progress }
                if (habit.target != null) {
                    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
                    Text(
                        "${habitProgress}/${habit.target} ${habit.targetUnit}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
                Text("${habit.streakCount} Days Steak", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.weight(1f))
                Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End,verticalAlignment = Alignment.CenterVertically){
                    val completionText = if (habit.isCompleted) {
                        "Completed"
                    } else if (habitProgress > 0) {
                        "In Progress"
                    } else {
                        "Not Started"
                    }

                    val completionColor = if (habit.isCompleted) {
                        GreenApple
                    } else if (habitProgress > 0) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }

                    val completionIcon = if (habit.isCompleted) {
                        Icons.Default.CheckCircle
                    } else if (habitProgress > 0) {
                        Icons.Default.HourglassTop
                    } else {
                        Icons.Default.Report
                    }
                    Icon(completionIcon, contentDescription = null, Modifier.size(24.dp),tint = completionColor)
                    Spacer(modifier = Modifier.padding((AppConstants.APP_MARGIN / 2).dp))
                    Text(
                        completionText,
                        style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.End,color = completionColor),
                        modifier = Modifier
                    )
                }
            }
        }


    }
}