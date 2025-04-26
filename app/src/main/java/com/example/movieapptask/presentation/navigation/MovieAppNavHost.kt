package com.example.movieapptask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapptask.presentation.details.MovieDetailsScreen
import com.example.movieapptask.presentation.home.HomeScreen

@Composable
fun MovieAppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetails = { id ->
                    navController.navigate(Screen.MovieDetails.createRoute(id))
                }
            )
        }
        composable(
            route = Screen.MovieDetails.route,
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            MovieDetailsScreen(id = id)
        }
    }
}
