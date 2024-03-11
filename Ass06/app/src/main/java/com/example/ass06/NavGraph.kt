package com.example.ass06

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(route = Screen.Home.route){
            HomeScreen()
        }
        composable(route = Screen.Friend1.route){
            Friend1()
        }
        composable(route = Screen.Friend2.route){
            Friend2()
        }
    }
}