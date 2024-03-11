package com.example.ass06

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val name: String, val icon: ImageVector) {
    object Home: Screen(route = "home_screen", name = "Home", icon = Icons.Default.Home)
    object Friend1: Screen(route = "friend1_screen", name = "Friend 1", icon = Icons.Default.Person)
    object Friend2: Screen(route = "friend2_screen", name = "Friend 2", icon = Icons.Outlined.PersonPin)
}