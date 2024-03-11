package com.example.a653380118_9_massage

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun navGraph() {
    val navController = rememberNavController()
    val contextToast = LocalContext.current.applicationContext
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(route = Screen.Home.route){
            Home(navHostController = navController)
        }
        composable(route = Screen.Second.route){
            SecondScreen(navHostController = navController)
        }
    }
}