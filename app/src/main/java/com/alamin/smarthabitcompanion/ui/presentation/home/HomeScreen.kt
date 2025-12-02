package com.alamin.smarthabitcompanion.ui.presentation.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.mapper.toUi
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
import com.alamin.smarthabitcompanion.ui.presentation.components.HabitOverview
import com.alamin.smarthabitcompanion.ui.presentation.components.WeatherInfo
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel
import kotlinx.coroutines.delay

private const val TAG = "HomeScreen"

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    sharedViewModel: MainActivityViewModel,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    toHabit: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.initialAnimation) {
        if (!uiState.initialAnimation) {
            delay(500)
            viewModel.updateUIState(uiState.copy(initialAnimation = true))
        }
    }

    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(NavigationDestinations.HOME.value)
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


            HabitOverview(initialAnimation = uiState.initialAnimation,uiModel = uiState.todayHabits.toUi())

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



