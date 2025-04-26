package com.example.movieapptask.presentation.details

import com.example.ui.base.ViewAction

sealed class MovieDetailsAction: ViewAction {
    data class GetMovieDetails(val id: Long) : MovieDetailsAction()
}