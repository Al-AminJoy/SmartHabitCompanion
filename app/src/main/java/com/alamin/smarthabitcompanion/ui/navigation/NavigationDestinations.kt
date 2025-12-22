package com.alamin.smarthabitcompanion.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationDestinations(val value: String,val icon: ImageVector,val selectedIcon: ImageVector) {
     HOME("Home",Icons.Outlined.Home,Icons.Filled.Home),
     HABITS("Habits",Icons.Filled.FormatListBulleted,Icons.Outlined.FormatListBulleted),
   //  PROFILE("Profile", Icons.Outlined.Person,Icons.Filled.Person)
}