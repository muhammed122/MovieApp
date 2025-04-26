package com.example.movieapptask.presentation.details

import com.example.movieapptask.domain.entity.response.MovieDetailsModel
import com.example.ui.base.ViewState

data class MovieDetailsState(
    val movie: MovieDetailsModel? = null,
    override val isLoading: Boolean=false,
    override val error: String?=null,
) : ViewState