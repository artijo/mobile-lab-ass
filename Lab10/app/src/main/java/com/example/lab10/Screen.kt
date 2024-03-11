package com.example.lab10

sealed class Screen (val route: String, val name: String) {
    object Login: Screen(route = "login_screen", name = "Login")
    object Register: Screen(route = "register_screen", name = "Register")
    object Profile: Screen(route = "profile_screen", name = "Profile")
    object ShowAllUser:Screen(route = "showalluser_screen", name = "AllUser")
}