package com.example.movieapptask.presentation.home

import androidx.paging.PagingData
import com.example.movieapptask.domain.entity.response.MovieListModel
import com.example.ui.base.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class HomeViewState(
    val movies : Flow<PagingData<MovieListModel>> = flowOf(PagingData.empty()),
    val query: String = "",
    override val isLoading: Boolean=false,
    override val error: String?=null
) : ViewState
