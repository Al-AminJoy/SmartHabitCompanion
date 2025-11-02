package com.alamin.smarthabitcompanion.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.alamin.smarthabitcompanion.core.network.ServerConstants
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Weather
import com.alamin.smarthabitcompanion.ui.presentation.components.buildImageRequest
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel
import com.alamin.smarthabitcompanion.ui.theme.BasketBallOrange
import com.alamin.smarthabitcompanion.ui.theme.Grapefruit
import com.alamin.smarthabitcompanion.ui.theme.Green
import com.alamin.smarthabitcompanion.ui.theme.LightCoral
import com.alamin.smarthabitcompanion.ui.theme.LightSalmon
import com.alamin.smarthabitcompanion.ui.theme.Orange
import com.alamin.smarthabitcompanion.ui.theme.Orchid
import com.alamin.smarthabitcompanion.ui.theme.PumpkinOrange
import com.alamin.smarthabitcompanion.ui.theme.SunYellow
import com.alamin.smarthabitcompanion.ui.theme.White
import com.alamin.smarthabitcompanion.ui.theme.Yellow
import com.alamin.smarthabitcompanion.ui.theme.YellowGreen

@Composable
fun HomeScreen(
    sharedViewModel: MainActivityViewModel,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    toHabit: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle("Smart Habit Companion")
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            if (uiState.weather != null) {
                WeatherInfo(
                    weather = uiState.weather!!, modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            AppConstants.APP_MARGIN.dp
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
fun WeatherInfo(weather: Weather = dummyWeather, modifier: Modifier = Modifier) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(

        )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            BasketBallOrange.copy(alpha = .60f),
                             SunYellow, White,MaterialTheme.colorScheme.primary.copy(
                                alpha = .2f
                            ),MaterialTheme.colorScheme.primary.copy(
                                alpha = .3f
                            )
                        )
                    )
                )
                .padding((AppConstants.APP_MARGIN * 2).dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Column() {
                Text(
                    text = "${weather.temperature} \u00B0C",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${weather.city}, ${weather.country}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column {
                Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
                    if (weather.icon != null) {
                        AsyncImage(
                            model = buildImageRequest(if (weather.icon.contains("//")) "https:${weather.icon}" else weather.icon),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Weather Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            Icons.Default.BrokenImage,
                            contentDescription = "Image Not Found",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Text(text = weather.condition ?: "", style = MaterialTheme.typography.bodySmall)

            }

        }

    }


}