package com.alamin.smarthabitcompanion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alamin.smarthabitcompanion.ui.presentation.home.HomeScreen
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel

@Composable
fun NavGraph(navController: NavHostController, mainViewModel: MainActivityViewModel, startDestination: Destinations = Destinations.Home) {

    NavHost(navController = navController, startDestination = startDestination){
        composable<Destinations.Home>{
            HomeScreen(mainViewModel){

            }
        }
    }
}