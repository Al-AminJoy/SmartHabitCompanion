package com.alamin.smarthabitcompanion.ui.presentation.home

import android.R.attr.resource
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.alamin.smarthabitcompanion.R
import com.alamin.smarthabitcompanion.core.network.ServerConstants
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Weather
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
import com.alamin.smarthabitcompanion.ui.presentation.components.buildImageRequest
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel
import com.alamin.smarthabitcompanion.ui.theme.BasketBallOrange
import com.alamin.smarthabitcompanion.ui.theme.ForestGreen
import com.alamin.smarthabitcompanion.ui.theme.Gold
import com.alamin.smarthabitcompanion.ui.theme.Grapefruit
import com.alamin.smarthabitcompanion.ui.theme.Green
import com.alamin.smarthabitcompanion.ui.theme.GreenApple
import com.alamin.smarthabitcompanion.ui.theme.LavaRed
import com.alamin.smarthabitcompanion.ui.theme.LightCoral
import com.alamin.smarthabitcompanion.ui.theme.LightSalmon
import com.alamin.smarthabitcompanion.ui.theme.Orange
import com.alamin.smarthabitcompanion.ui.theme.Orchid
import com.alamin.smarthabitcompanion.ui.theme.PumpkinOrange
import com.alamin.smarthabitcompanion.ui.theme.SaddleBrown
import com.alamin.smarthabitcompanion.ui.theme.SandyBrown
import com.alamin.smarthabitcompanion.ui.theme.SunYellow
import com.alamin.smarthabitcompanion.ui.theme.White
import com.alamin.smarthabitcompanion.ui.theme.Yellow
import com.alamin.smarthabitcompanion.ui.theme.YellowGreen
import kotlinx.coroutines.delay

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    sharedViewModel: MainActivityViewModel,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    toHabit: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val progress by remember { mutableFloatStateOf(0f) }

    val progressValue = produceState(progress) {

        while (value <= 65f) {
            delay(4)
            value += .1f
            Log.d(TAG, "HomeScreen: $value")

        }

    }

    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(NavigationDestinations.HOME.value)
    }

    LaunchedEffect(uiState.animateWeatherIcon) {
        if (!uiState.animateWeatherIcon) {
            delay(300)
            viewModel.updateUIState(uiState.copy(animateWeatherIcon = true))
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
                    uiState.animateWeatherIcon,
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
                Text(
                    "65%",
                    style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Completed Today",
                    style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN).dp))
                LinearProgressIndicator(
                    progress = { (progressValue.value / 100f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((AppConstants.APP_MARGIN).dp)
                        .padding(
                            horizontal =
                                (AppConstants.APP_MARGIN * 4).dp
                        ),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = .30f),
                    strokeCap = StrokeCap.Round,
                    gapSize = 2.dp
                )
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
                        icon = Icons.Default.CheckCircle,
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
                        icon = Icons.Default.LockClock,
                        color = GreenApple,
                        title = "Completed",
                        10
                    )
                    SummaryCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = (AppConstants.APP_MARGIN / 2).dp),
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "$title | ",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
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
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))
            Text(
                title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
            )
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
                Text(
                    text = "${weather.condition}",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
            AnimatedVisibility(
                visible = weatherIconVisibility,
                enter = slideInHorizontally(animationSpec = tween(3000)){
                    with(density) {
                        50.dp.roundToPx()
                    }
                }+fadeIn(animationSpec = tween(3000)),
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
                ),exit = ExitTransition.None
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