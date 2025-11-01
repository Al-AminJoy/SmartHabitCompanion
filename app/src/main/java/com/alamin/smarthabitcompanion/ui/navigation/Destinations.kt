package com.alamin.smarthabitcompanion.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object Home: Destinations()
}