package com.alamin.smarthabitcompanion.ui.presentation.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
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
import androidx.compose.runtime.LaunchedEffect
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
import com.alamin.smarthabitcompanion.ui.presentation.components.PersonalInformationDialog
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

            LaunchedEffect(uiState.message) {
                val message = uiState.message
                if (message != null) {
                    Toast.makeText(this@MainActivity, message.second, Toast.LENGTH_SHORT).show()
                    viewModel.messageShown()
                }
            }

            LaunchedEffect(Unit){
                if (!viewModel.hasAlarmPermission()){
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                         val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                         startActivity(intent)
                     }
                }
            }

            if (uiState.showPersonalInformationDialog) {
                PersonalInformationDialog(
                    modifier = Modifier.fillMaxWidth(),
                    name = uiState.name,
                    isMale = uiState.isMale,
                    onChangeName = {
                        viewModel.updateName(it)
                    },
                    onSelectGender = {
                        viewModel.updateGender(it)
                    },
                    onAddInformation = {
                        viewModel.insertPersonalInformation{
                            viewModel.updatePersonalInformationDialog(false)
                        }
                    },
                    onDismiss = {
                        viewModel.updatePersonalInformationDialog(false)
                    })
            }

            SmartHabitCompanionTheme() {
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            if (!uiState.title.equals(NavigationDestinations.HOME.value, true)) {
                                Text(uiState.title)
                            }
                        }, actions = {

                        }, navigationIcon = {
                            if (!uiState.title.equals(NavigationDestinations.HOME.value, true)) {
                                selectedNavigationDestination =
                                    NavigationDestinations.HABITS.ordinal
                                IconButton(onClick = {
                                    navController.navigateUp()
                                }) {
                                    Icon(
                                        Icons.Outlined.ArrowBackIosNew,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            } else {
                                selectedNavigationDestination = NavigationDestinations.HOME.ordinal
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                }, bottomBar = {
                    if (uiState.title.equals(
                            NavigationDestinations.HOME.value,
                            true
                        ) || uiState.title.equals(
                            NavigationDestinations.HABITS.value,
                            true
                        )// || uiState.title.equals(NavigationDestinations.PROFILE.value, true)
                    ) {
                        NavigationBar(
                            windowInsets = NavigationBarDefaults.windowInsets,
                        ) {

                            NavigationDestinations.entries.forEachIndexed { index, destination ->
                                val selected = index == selectedNavigationDestination

                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        selectedNavigationDestination = index
                                        when (selectedNavigationDestination) {
                                            0 -> {
                                                navController.navigate(Destinations.Home)
                                            }

                                            1 -> {
                                                navController.navigate(Destinations.Habits)
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
                    if (uiState.title.equals(NavigationDestinations.HABITS.value, true)) {
                        FloatingActionButton(onClick = {
                            viewModel.updateAddHabitDialog(true)
                        }) {
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

