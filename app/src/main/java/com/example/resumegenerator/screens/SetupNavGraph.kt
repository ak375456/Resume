package com.example.resumegenerator.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.resumegenerator.editor.presentation.EditorScreen
import com.example.resumegenerator.home.presentation.HomeComposable
import java.net.URLDecoder


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
        route = ROOT
    ) {
        composable(route = Screens.Home.route) {
            HomeComposable(navController = navController)
        }
        composable(
            route = Screens.Editor.route,
            arguments = listOf(navArgument("templateName") { type = NavType.StringType })
            ) { backStackEntry ->
                val templateName = backStackEntry.arguments?.getString("templateName") ?: ""
                EditorScreen(
                    templateName = templateName,
                    navController = navController,
                )

        }
    }
}