package com.example.resumegenerator.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.resumegenerator.home.presentation.HomeComposable


@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = HOME,
        route = ROOT
    ){
        navigation (
            startDestination = Screens.Home.route,
            route = HOME
        ){
            composable(route = Screens.Home.route){
                HomeComposable(navController = navController)
            }
        }
    }
}