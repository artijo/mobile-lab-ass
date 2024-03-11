package com.example.a653380118_9_massage

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Second: Screen(route = "second_screen")
}