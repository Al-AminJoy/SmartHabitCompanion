package com.alamin.smarthabitcompanion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.ui.presentation.habitdetails.HabitDetailsScreen
import com.alamin.smarthabitcompanion.ui.presentation.habits.HabitsScreen
import com.alamin.smarthabitcompanion.ui.presentation.home.HomeScreen
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel
import com.alamin.smarthabitcompanion.ui.presentation.profile.ProfileScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(navController: NavHostController, mainViewModel: MainActivityViewModel, startDestination: Destinations = Destinations.Home) {

    NavHost(navController = navController, startDestination = startDestination){
        composable<Destinations.Home>{
            HomeScreen(mainViewModel){

            }
        }
        composable<Destinations.Habits> {
            HabitsScreen(sharedViewModel = mainViewModel, toHabitDetails = { it ->
                val habit = Json.encodeToString(it)
                navController.navigate(Destinations.HabitDetails(habit))
            })
        }

        composable<Destinations.HabitDetails> {
            val route = it.toRoute<Destinations.HabitDetails>()
            val habit = Json.decodeFromString<Habit>(route.habit)
            HabitDetailsScreen(sharedViewModel = mainViewModel,habit = habit)
        }

        composable<Destinations.Profile> {
            ProfileScreen(sharedViewModel = mainViewModel) { }
        }
    }
}