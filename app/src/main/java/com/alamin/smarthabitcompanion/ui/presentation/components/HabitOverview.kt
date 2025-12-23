package com.alamin.smarthabitcompanion.ui.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.R
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.core.utils.Logger
import com.alamin.smarthabitcompanion.ui.mapper.toHabitUi
import com.alamin.smarthabitcompanion.ui.mapper.toWeeklyUi
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
fun HabitOverview(
    modifier: Modifier = Modifier,
    initialAnimation: Boolean,
    uiModel: HabitOverviewUiModel
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState { uiModel.habitSize }
    LaunchedEffect(uiModel.habitSize) {
        if (uiModel.habitSize > 0) {
            var currentIndex = 1
            var isReverseTurn = false
            while (true) {
                if (!isReverseTurn) {
                    val reminder = currentIndex % (uiModel.habitSize)
                    if (reminder == 0) {
                        currentIndex = currentIndex - 2
                        isReverseTurn = true
                    }
                    delay(1000)
                    pagerState.animateScrollToPage(
                        currentIndex,
                        animationSpec = tween(durationMillis = 1000)
                    )
                    currentIndex++
                } else {
                    delay(1000)
                    currentIndex--
                    pagerState.animateScrollToPage(
                        currentIndex,
                        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                    )
                    if (currentIndex == 0) {
                        currentIndex = 1
                        isReverseTurn = false
                    }
                }

            }
        }

    }


    val progressValue by animateIntAsState(
        if (initialAnimation) uiModel.completionPercent else 0,
        animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .clip(
                RoundedCornerShape(
                    topStart = (AppConstants.APP_MARGIN * 2).dp,
                    topEnd = (AppConstants.APP_MARGIN * 2).dp
                )
            )
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN).dp))

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
                        style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
                        modifier = Modifier
                    )
                    Text(
                        "Completed",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = uiModel.progressColor
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
                        .size(100.dp),
                    color = uiModel.progressColor,
                    trackColor = uiModel.progressColor.copy(alpha = .30f),
                    strokeCap = StrokeCap.Round,
                    strokeWidth = AppConstants.APP_MARGIN.dp,
                    gapSize = 2.dp
                )

            }

            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN).dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()                 ,
                contentPadding = PaddingValues(horizontal = AppConstants.APP_MARGIN.dp),
            ) { index ->
                val pageOffset = pagerState.getOffsetDistanceInPages(index).absoluteValue
                val item = uiModel.habits[index]
                HabitPagerItem(
                    modifier = Modifier
                        .fillMaxWidth()
                      //  .height(contentHeight * (1 - pageOffset))
                        .padding(AppConstants.APP_MARGIN.dp), habitUiModel = item.toHabitUi()
                )

            }


        }

        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN).dp))

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
                initialAnimation = initialAnimation,
                icon = painterResource(R.drawable.ic_award),
                color = SandyBrown,
                title = "Best",
                uiModel.bestStreak,
                unit = ""
            )


        }
        WeeklyCompletionChart(
            modifier = Modifier,
            uiModel.sevenDayHabits.toWeeklyUi(initialAnimation)
        )

    }
}