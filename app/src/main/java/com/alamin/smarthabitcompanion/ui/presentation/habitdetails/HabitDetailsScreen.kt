package com.alamin.smarthabitcompanion.ui.presentation.habitdetails

import android.annotation.SuppressLint
import android.app.LocaleConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HabitDetailsScreen(
    sharedViewModel: MainActivityViewModel,
    viewModel: HabitDetailsScreenViewModel = hiltViewModel(), habit: Habit
) {

    val config = LocalConfiguration.current
    val height = config.screenHeightDp

    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(habit.name)
    }


    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { .30f },
                    modifier = Modifier
                        .size((height / 5).dp),
                    color = MaterialTheme.colorScheme.secondary,
                    strokeWidth = 8.dp,
                    trackColor = MaterialTheme.colorScheme.secondary.copy(alpha = .60f),
                    strokeCap = StrokeCap.Round,
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.Attractions, contentDescription = null)
                    Spacer(modifier = Modifier.height(AppConstants.APP_MARGIN.dp))
                    Text("${habit.habitRecords.sumOf { it.progress }}/${habit.target ?: 1}",
                        style = MaterialTheme.typography.titleLarge)
                    if (!habit.targetUnit.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(AppConstants.APP_MARGIN.dp))
                        Text(habit.targetUnit, style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(modifier = Modifier.height(AppConstants.APP_MARGIN.dp))
                    Text("daily goal", style = MaterialTheme.typography.titleMedium)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )

                Text(
                    "${habit.streakCount} days streak",
                    style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End),
                    modifier = Modifier.padding(AppConstants.APP_MARGIN.dp)
                )
            }
            Spacer(modifier = Modifier.height(AppConstants.APP_MARGIN.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = (AppConstants.APP_MARGIN * 2).dp,
                            topEnd = (AppConstants.APP_MARGIN * 2).dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.onPrimary)
            ) {

                Column {

                    Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))
                    Text(
                        "Weekly Performance",
                        style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier.padding(horizontal = AppConstants.APP_MARGIN.dp)
                    )
                    Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))

                }

            }

        }

        /**/
    }

}