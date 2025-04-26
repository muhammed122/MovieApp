package com.example.movieapptask.domain.usecase

import androidx.paging.PagingData
import com.example.movieapptask.domain.entity.request.GetMoviesParam
import com.example.movieapptask.domain.entity.response.MovieListModel
import com.example.movieapptask.domain.repository.Repository
import com.example.utils.coroutine_dispatcher.IoDispatcher
import com.example.utils.usecase.FlowPagingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FlowPagingUseCase<GetMoviesParam, PagingData<MovieListModel>>(ioDispatcher) {
    override fun execute(parameters: GetMoviesParam): Flow<PagingData<MovieListModel>> =
        repository.getMovies(parameters).flow

}