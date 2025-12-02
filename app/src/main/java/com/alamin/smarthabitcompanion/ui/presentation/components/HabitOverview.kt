package com.alamin.smarthabitcompanion.ui.presentation.components

import android.util.Log
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.R
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.mapper.toHabitUi
import com.alamin.smarthabitcompanion.ui.presentation.home.StreakHighlightCard
import com.alamin.smarthabitcompanion.ui.presentation.home.SummaryCard
import com.alamin.smarthabitcompanion.ui.presentation.model.HabitOverviewUiModel
import com.alamin.smarthabitcompanion.ui.theme.GreenApple
import com.alamin.smarthabitcompanion.ui.theme.LavaRed
import com.alamin.smarthabitcompanion.ui.theme.SandyBrown
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

private const val TAG = "HabitCompletionOverview"
@Composable
fun HabitOverview(modifier: Modifier = Modifier, initialAnimation: Boolean, uiModel: HabitOverviewUiModel){

    val pagerState = rememberPagerState { uiModel.habitSize }

    LaunchedEffect(uiModel.habitSize) {
        if (uiModel.habitSize > 0) {
            var isReverse = false
            while (true) {
                if (!isReverse) {
                    for (i in 0..uiModel.habitSize) {
                        delay(2000)
                        val current = pagerState.currentPage + 1
                        Log.d(TAG, "HomeScreen: Not Reversed $i")
                        if (i == uiModel.habitSize) {
                            isReverse = true
                        } else {
                            pagerState.animateScrollToPage(
                                current,
                                animationSpec = tween(durationMillis = 1000)
                            )
                        }
                    }
                } else {
                    for (i in uiModel.habitSize downTo 0) {
                        delay(2000)
                        val current = pagerState.currentPage + 1
                        Log.d(TAG, "HomeScreen:  Reversed $i")

                        if (i == 0) {
                            isReverse = false
                        } else {
                            pagerState.animateScrollToPage(
                                current,
                                animationSpec = tween(durationMillis = 1000)
                            )
                        }
                    }
                }
            }
        }

    }






    val progressValue by animateIntAsState(
        if (initialAnimation) uiModel.completionPercent else 0,
        animationSpec = tween(durationMillis = 2000, easing = EaseInCirc)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    topStart = (AppConstants.APP_MARGIN * 2).dp,
                    topEnd = (AppConstants.APP_MARGIN * 2).dp
                )
            )
            .background(
                MaterialTheme.colorScheme.onPrimary
            )
    ) {

        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (AppConstants.APP_MARGIN).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "${progressValue}%",
                        style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                        modifier = Modifier
                    )
                    Text(
                        "Completed",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = if (progressValue > 0.0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                    )
                    Text(
                        "Today",
                        style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
                        modifier = Modifier
                    )
                }
                CircularProgressIndicator(
                    progress = { (progressValue.toFloat() / 100f) },
                    modifier = Modifier
                        .size(130.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = .30f),
                    strokeCap = StrokeCap.Round,
                    strokeWidth = AppConstants.APP_MARGIN.dp,
                    gapSize = 2.dp
                )

            }

            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN).dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = AppConstants.APP_MARGIN.dp),
            ) { index ->
                val pageOffset = pagerState.getOffsetDistanceInPages(index).absoluteValue

                val item = uiModel.habits[index]
                HabitPagerItem(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp * (1 - pageOffset))
                    .padding(AppConstants.APP_MARGIN.dp), habitUiModel = item.toHabitUi())

            }


        }


        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))
        Text(
            "Today's Summary",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (AppConstants.APP_MARGIN).dp)
        )
        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (AppConstants.APP_MARGIN).dp)
        ) {

            SummaryCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = (AppConstants.APP_MARGIN / 2).dp),
                initialAnimation,
                icon = Icons.Default.Dataset,
                color = MaterialTheme.colorScheme.secondary,
                title = "Total",
                uiModel.habitSize
            )
            SummaryCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = (AppConstants.APP_MARGIN / 2).dp,
                        end = (AppConstants.APP_MARGIN / 2).dp
                    ),
                initialAnimation,
                icon = Icons.Default.CheckCircle,
                color = GreenApple,
                title = "Completed",
                uiModel.completed
            )
            SummaryCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = (AppConstants.APP_MARGIN / 2).dp),
                initialAnimation,
                icon = Icons.Default.LockClock,
                color = MaterialTheme.colorScheme.tertiary,
                title = "Pending",
                uiModel.notCompleted
            )


        }
        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))
        Text(
            "Streak Highlights",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (AppConstants.APP_MARGIN).dp)
        )
        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (AppConstants.APP_MARGIN).dp)
        ) {

            StreakHighlightCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = (AppConstants.APP_MARGIN / 2).dp),
                initialAnimation = initialAnimation,
                icon = painterResource(R.drawable.ic_trophy),
                color = SandyBrown,
                title = "Highest",
                value = "${uiModel.highestStreak}",
                unit = "Days"
            )
            StreakHighlightCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = (AppConstants.APP_MARGIN / 2).dp,
                        end = (AppConstants.APP_MARGIN / 2).dp
                    ),
                initialAnimation = initialAnimation,
                icon = painterResource(R.drawable.ic_lowest),
                color = LavaRed,
                title = "Lowest",
                "${uiModel.lowestStreak}",
                unit = "Days"
            )
            StreakHighlightCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = (AppConstants.APP_MARGIN / 2).dp),
                initialAnimation =initialAnimation,
                icon = painterResource(R.drawable.ic_award),
                color = SandyBrown,
                title = "Best",
                uiModel.bestStreak,
                unit = ""
            )


        }
        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))
        Text(
            "Last 7 Days Completion Chart",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (AppConstants.APP_MARGIN).dp)
        )
        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))

    }
}