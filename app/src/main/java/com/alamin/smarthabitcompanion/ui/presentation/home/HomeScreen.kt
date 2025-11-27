package com.alamin.smarthabitcompanion.ui.presentation.home

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alamin.smarthabitcompanion.R
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Weather
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
import com.alamin.smarthabitcompanion.ui.presentation.components.TypeTextAnimation
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel
import com.alamin.smarthabitcompanion.ui.theme.Green
import com.alamin.smarthabitcompanion.ui.theme.GreenApple
import com.alamin.smarthabitcompanion.ui.theme.LavaRed
import com.alamin.smarthabitcompanion.ui.theme.Red
import com.alamin.smarthabitcompanion.ui.theme.SandyBrown
import kotlinx.coroutines.delay
import java.text.BreakIterator
import java.text.StringCharacterIterator

private const val TAG = "HomeScreen"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    sharedViewModel: MainActivityViewModel,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    toHabit: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val progress by remember { mutableFloatStateOf(0f) }

    val progressValue = produceState(progress) {

        while (value < 65f) {
            delay(1)
            value += .1f
            Log.d(TAG, "HomeScreen: $value")

        }

    }


    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(NavigationDestinations.HOME.value)
    }

    LaunchedEffect(uiState.initialAnimation) {
        if (!uiState.initialAnimation) {
            delay(500)
            viewModel.updateUIState(uiState.copy(initialAnimation = true))
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            if (uiState.weather != null) {
                WeatherInfo(
                    isMale = true,
                    weather = uiState.weather!!,
                    uiState.initialAnimation,
                    modifier = Modifier
                        .fillMaxWidth()

                )
            }

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
                        .padding(horizontal = (AppConstants.APP_MARGIN).dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "${progressValue.value.toInt()}%",
                                style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                                modifier = Modifier
                            )
                            Text(
                                "Completed",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = if (progressValue.value > 0.0f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
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
                            progress = { (progressValue.value / 100f) },
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

                    ElevatedCard(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppConstants.APP_MARGIN.dp)
                        ) {
                            Text(
                                "Top Habit Progress",
                                style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = (AppConstants.APP_MARGIN).dp)
                            )
                            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Attractions,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                                Column {
                                    Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                                        Text("Drinking Water")
                                    }


                                }
                            }

                        }
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
                        uiState.initialAnimation,
                        icon = Icons.Default.Dataset,
                        color = MaterialTheme.colorScheme.secondary,
                        title = "Total",
                        10
                    )
                    SummaryCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                start = (AppConstants.APP_MARGIN / 2).dp,
                                end = (AppConstants.APP_MARGIN / 2).dp
                            ),
                        uiState.initialAnimation,
                        icon = Icons.Default.CheckCircle,
                        color = GreenApple,
                        title = "Completed",
                        10
                    )
                    SummaryCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = (AppConstants.APP_MARGIN / 2).dp),
                        uiState.initialAnimation,
                        icon = Icons.Default.LockClock,
                        color = MaterialTheme.colorScheme.tertiary,
                        title = "Pending",
                        10
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
                        initialAnimation = uiState.initialAnimation,
                        icon = painterResource(R.drawable.ic_trophy),
                        color = SandyBrown,
                        title = "Highest",
                        value = "10",
                        unit = "Days"
                    )
                    StreakHighlightCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                start = (AppConstants.APP_MARGIN / 2).dp,
                                end = (AppConstants.APP_MARGIN / 2).dp
                            ),
                        initialAnimation = uiState.initialAnimation,
                        icon = painterResource(R.drawable.ic_lowest),
                        color = LavaRed,
                        title = "Lowest",
                        "10",
                        unit = "Days"
                    )
                    StreakHighlightCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = (AppConstants.APP_MARGIN / 2).dp),
                        initialAnimation = uiState.initialAnimation,
                        icon = painterResource(R.drawable.ic_award),
                        color = SandyBrown,
                        title = "Best",
                        "Drinking Water",
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

    }

}

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    initialAnimation: Boolean,
    icon: ImageVector,
    color: Color,
    title: String,
    value: Int
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppConstants.APP_MARGIN.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))
            /* Row(verticalAlignment = Alignment.CenterVertically) {



             }*/
            AnimatedVisibility(
                initialAnimation,
                enter = expandVertically(expandFrom = Alignment.Bottom),
                exit = ExitTransition.None
            ) {
                Text(
                    "$title",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))
            AnimatedVisibility(
                initialAnimation,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = ExitTransition.None
            ) {
                Text(
                    "$value",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = color
                    )
                )
            }

        }
    }
}

@Composable
fun StreakHighlightCard(
    modifier: Modifier = Modifier,
    initialAnimation: Boolean,
    icon: Painter,
    color: Color,
    title: String,
    value: String,
    unit: String
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppConstants.APP_MARGIN.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))

            AnimatedVisibility(
                initialAnimation,
                enter = expandVertically(expandFrom = Alignment.Bottom),
                exit = ExitTransition.None
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))
            AnimatedVisibility(
                initialAnimation,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = ExitTransition.None
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        value,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = color
                        )
                    )
                    Text(
                        " $unit",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                        )
                    )

                }

            }
        }
    }
}

val dummyWeather = Weather(
    id = 1,
    city = "Dhaka",
    country = "Bangladesh",
    temperature = 30.7,
    lastUpdated = "2025-10-17 15:45",
    condition = "Partly Cloudy",
    icon = "https://cdn.weatherapi.com/weather/64x64/day/116.png"
)

@Preview
@Composable
fun WeatherInfo(
    isMale: Boolean = true,
    weather: Weather = dummyWeather,
    weatherIconVisibility: Boolean = false,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .padding(
                start = (AppConstants.APP_MARGIN).dp, end = (AppConstants.APP_MARGIN * 2).dp,
                bottom = AppConstants.APP_MARGIN.dp
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {

        val density = LocalDensity.current


        Row(modifier = Modifier.fillMaxHeight()) {

            Column(modifier = Modifier.padding(top = (AppConstants.APP_MARGIN * 4).dp)) {
                Text(
                    text = "${weather.temperature} \u00B0C",
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
                Text(
                    text = "${weather.city}, ${weather.country}",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)

                )
                Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))

                TypeTextAnimation(
                    text = "${weather.condition}",
                    textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.secondary)
                )

            }
            AnimatedVisibility(
                visible = weatherIconVisibility,
                enter = slideInHorizontally(animationSpec = tween(3000)) {
                    with(density) {
                        50.dp.roundToPx()
                    }
                } + fadeIn(animationSpec = tween(3000)),
                exit = ExitTransition.None,
                modifier = Modifier
            ) {
                Box(modifier = Modifier.size(52.dp), contentAlignment = Alignment.Center) {
                    Image(
                        painterResource(R.drawable.weather),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
        }

        Column(
            modifier = Modifier.padding(top = (AppConstants.APP_MARGIN * 4).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = weatherIconVisibility, enter = expandHorizontally(
                    expandFrom = Alignment.Start
                ), exit = ExitTransition.None
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .border(
                            2.dp, MaterialTheme.colorScheme.onPrimary,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(if (isMale) R.drawable.img_pofile_male else R.drawable.img_profile_female),
                        contentDescription = "Image Not Found",
                        modifier = Modifier
                            .clip(CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.size(AppConstants.APP_MARGIN.dp))
            Text(
                text = "Al-Amin Joy",
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        }


    }

}