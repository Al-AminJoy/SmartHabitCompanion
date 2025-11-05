package com.alamin.smarthabitcompanion.ui.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.alamin.smarthabitcompanion.ui.navigation.NavigationDestinations
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivityViewModel

@Composable
fun ProfileScreen(sharedViewModel: MainActivityViewModel, viewModel: ProfileViewModel = hiltViewModel(), toEditProfile : ()-> Unit) {
    LaunchedEffect(Unit) {
        sharedViewModel.updateTitle(NavigationDestinations.PROFILE.value)
    }
}