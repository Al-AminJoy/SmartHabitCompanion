package com.alamin.smarthabitcompanion.ui.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alamin.smarthabitcompanion.ui.navigation.Destinations
import com.alamin.smarthabitcompanion.ui.navigation.NavGraph
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
import com.alamin.smarthabitcompanion.ui.theme.SmartHabitCompanionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel<MainActivityViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val navController = rememberNavController()
            val startDestination = NavigationDestinations.HOME
            var selectedNavigationDestination by rememberSaveable {
                mutableIntStateOf(
                    startDestination.ordinal
                )
            }

            SmartHabitCompanionTheme(darkTheme = false) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text(uiState.title)
                        }, actions = {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Menu, contentDescription = null)
                            }
                        },
                      //  colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                    )

                }, bottomBar = {
                    if (uiState.title.equals(NavigationDestinations.HOME.value,true) || uiState.title.equals(NavigationDestinations.HABITS.value,true)|| uiState.title.equals(NavigationDestinations.PROFILE.value,true) ){
                        NavigationBar(
                            windowInsets = NavigationBarDefaults.windowInsets,
                        ) {

                            NavigationDestinations.entries.forEachIndexed { index, destination ->
                                val selected = index == selectedNavigationDestination

                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        selectedNavigationDestination = index
                                        when(selectedNavigationDestination){
                                            0 -> {
                                                navController.navigate(Destinations.Home)
                                            }
                                            1 -> {
                                                navController.navigate(Destinations.Habits)
                                            }
                                            2 -> {
                                                navController.navigate(Destinations.Profile)
                                            }
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            if (selected) {
                                                destination.selectedIcon
                                            } else {
                                                destination.icon
                                            }, contentDescription = destination.value
                                        )

                                    }, label = {
                                        Text(text = destination.value)
                                    }
                                )

                            }

                        }
                    }
                }, floatingActionButton = {
                    if (uiState.title.equals(NavigationDestinations.HABITS.value,true)){
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Outlined.Add, contentDescription = null)
                        }
                    }
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        App(viewModel, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun App(viewModel: MainActivityViewModel, navController: NavHostController) {
    NavGraph(navController = navController, mainViewModel = viewModel)
}

