package com.example.movieapptask.domain.usecase

import com.example.movieapptask.domain.entity.response.MovieDetailsModel
import com.example.movieapptask.domain.repository.Repository
import com.example.utils.coroutine_dispatcher.IoDispatcher
import com.example.utils.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<Long, MovieDetailsModel?>(ioDispatcher) {
    override fun execute(parameters: Long): Flow<MovieDetailsModel?> =
        flow {
            emit(repository.getMovieDetails(parameters))
        }
}