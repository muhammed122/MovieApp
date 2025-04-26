package com.example.movieapptask.presentation.home

import com.example.ui.base.ViewAction

sealed class HomeViewAction: ViewAction {
    data object LoadMovies : HomeViewAction()
    data class SearchQueryChanged(val query: String) : HomeViewAction()
    data class NavigateToMovieDetails(val id: Int) : HomeViewAction()

}