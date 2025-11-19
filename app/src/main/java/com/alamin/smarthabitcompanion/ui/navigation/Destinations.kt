package com.alamin.smarthabitcompanion.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object Home: Destinations()
    @Serializable
    data object Habits: Destinations()
    @Serializable
    data object Profile: Destinations()

    @Serializable
    data class HabitDetails(val habit: String): Destinations()


}