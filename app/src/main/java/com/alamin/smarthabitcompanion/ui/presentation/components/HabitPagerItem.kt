package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.R
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.presentation.model.HabitUiModel
import java.time.LocalDate
import kotlin.math.absoluteValue

private const val TAG = "HabitDashboardPager"
@Composable
fun HabitPagerItem(modifier: Modifier = Modifier, habitUiModel: HabitUiModel) {

    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppConstants.APP_MARGIN.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painterResource(R.drawable.ic_target),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    habitUiModel.name,
                    style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Start),
                    modifier = Modifier
                        .padding(horizontal = (AppConstants.APP_MARGIN).dp)
                )
            }

            val achieved = habitUiModel.progress
            val target = habitUiModel.target

            val achievedPercentage = habitUiModel.percentage


            Text(
                "Goal : ${achieved}/${target}",
                style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = (AppConstants.APP_MARGIN).dp)
            )

            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN).dp))

            LinearProgressIndicator(
                progress = { achievedPercentage.toFloat()/100 },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = .30f),
                strokeCap = StrokeCap.Round,
                modifier = Modifier
                    .height((AppConstants.APP_MARGIN-2).dp)
                    .padding(horizontal = (AppConstants.APP_MARGIN * 2).dp)
            )
            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN/2).dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Attractions,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

        }
    }
}