package com.example.movieapptask.presentation.details

import androidx.lifecycle.viewModelScope
import com.example.movieapptask.domain.usecase.GetMovieDetailsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.ViewEvent
import com.example.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel<MovieDetailsState, ViewEvent, MovieDetailsAction>(
    MovieDetailsState()
) {
    override fun handleAction(action: MovieDetailsAction) {
        when (action) {
            is MovieDetailsAction.GetMovieDetails -> {
                getMovieDetails(action.id)
            }
        }
    }

    private fun getMovieDetails(id: Long) {
        getMovieDetailsUseCase(id)
            .onEach {
                when (it) {
                    is Resource.Success -> setState { copy(movie = it.value, isLoading = false) }
                    is Resource.Failure -> setState { copy(error = it.exception.message, isLoading = false) }
                    is Resource.Loading -> setState { copy(isLoading = true) }
                    Resource.Empty -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun clearError() {
        setState { copy(error = null) }
    }
}