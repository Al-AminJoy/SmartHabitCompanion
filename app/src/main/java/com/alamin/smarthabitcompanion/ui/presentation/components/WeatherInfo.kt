package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.R
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.Weather

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
            ).animateContentSize(),
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
                    expandFrom = Alignment.End
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

val dummyWeather = Weather(
    id = 1,
    city = "Dhaka",
    country = "Bangladesh",
    temperature = 30.7,
    lastUpdated = "2025-10-17 15:45",
    condition = "Partly Cloudy",
    icon = "https://cdn.weatherapi.com/weather/64x64/day/116.png"
)