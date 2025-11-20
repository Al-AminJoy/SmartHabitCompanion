package com.alamin.smarthabitcompanion.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.geometry.Offset
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
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
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
        sharedViewModel.updateTitle(NavigationDestinations.HOME.value)
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)) {
            if (uiState.weather != null) {
                WeatherInfo(
                    weather = uiState.weather!!, modifier = Modifier
                        .fillMaxWidth()

                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = (AppConstants.APP_MARGIN * 2).dp, topEnd = (AppConstants.APP_MARGIN * 2).dp)).background(
                        MaterialTheme.colorScheme.onPrimary)
            ) {


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

    Row(
        modifier = modifier
            .padding(AppConstants.APP_MARGIN.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
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

        Column {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        2.dp, MaterialTheme.colorScheme.onPrimary,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Image Not Found",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppConstants.APP_MARGIN.dp)
                )
            }
            Spacer(modifier = Modifier.size(AppConstants.APP_MARGIN.dp))
            Text(
                text = "Al-Amin Joy",
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        }


    }

}