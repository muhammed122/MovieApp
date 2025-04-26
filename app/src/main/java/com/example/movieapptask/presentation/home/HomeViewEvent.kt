package com.example.movieapptask.presentation.home

import com.example.ui.base.ViewEvent

sealed class HomeViewEvent : ViewEvent {
    data class NavigateToMovieDetails(val id:Int) : HomeViewEvent()
    data object NoInternetConnection : HomeViewEvent()
}