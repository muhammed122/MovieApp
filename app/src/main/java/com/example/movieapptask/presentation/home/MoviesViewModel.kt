package com.example.movieapptask.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapptask.domain.entity.request.GetMoviesParam
import com.example.movieapptask.domain.usecase.GetMoviesUseCase
import com.example.ui.base.BaseViewModel
import com.example.utils.network.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val networkChecker: NetworkChecker,
) : BaseViewModel<HomeViewState, HomeViewEvent, HomeViewAction>(
    HomeViewState()
) {


    private var lastSearchQuery: String = ""

    init {
        if (!networkChecker.isConnected()) {
            setEvent { HomeViewEvent.NoInternetConnection }
        }
        handleAction(HomeViewAction.LoadMovies)
    }

    override fun handleAction(action: HomeViewAction) {
        when (action) {
            HomeViewAction.LoadMovies -> loadMovies(GetMoviesParam.Default)

            is HomeViewAction.SearchQueryChanged -> {
                if (action.query != lastSearchQuery) {
                    lastSearchQuery = action.query
                    val param = if (action.query.length > 1)
                        GetMoviesParam.Search(action.query)
                    else
                        GetMoviesParam.Default

                    loadMovies(param)
                }
            }

            is HomeViewAction.NavigateToMovieDetails -> {
                // Handle navigation event
            }
        }
    }

    fun clearError() {
        setState { copy(error = null) }
    }

    private fun loadMovies(param: GetMoviesParam) {
        setState { copy(isLoading = true) }

        val movieFlow = getMoviesUseCase(param)
            .cachedIn(viewModelScope)

        setState {
            copy(
                movies = movieFlow,
                isLoading = false,
                error = null
            )
        }
    }
}