package com.example.movieapptask.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object MovieDetails : Screen("movie_details/{movieId}") {
        fun createRoute(movieId: Long) = "movie_details/$movieId"
    }
}
