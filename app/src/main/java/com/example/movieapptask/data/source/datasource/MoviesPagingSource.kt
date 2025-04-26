package com.example.movieapptask.data.source.datasource

import com.example.movieapptask.data.mapper.toMovieListModel
import com.example.movieapptask.data.source.remote.ApiService
import com.example.movieapptask.domain.entity.response.MovieListModel
import com.example.movieapptask.domain.entity.response.PagingMovieModel
import com.example.movieapptask.domain.entity.request.GetMoviesParam
import com.example.ui.paging.PagingDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MoviesPagingSource @AssistedInject constructor(
    private val apiService: ApiService,
    @Assisted private val param: GetMoviesParam,
) : PagingDataSource<MovieListModel, PagingMovieModel>() {

    @AssistedFactory
    interface MoviesPagingDataSourceFactory {
        fun createNewInstance(
            param: GetMoviesParam,
        ): MoviesPagingSource
    }

    override suspend fun loadMoreData(page: Int): PagingMovieModel {
        val movies = when (param) {
            is GetMoviesParam.Default -> {
                apiService.getAllMovies(page)
            }
            is GetMoviesParam.Search -> {
                apiService.getMoviesBySearch(page, param.query)
            }
        }?.results?.map { it.toMovieListModel() } ?: emptyList()

        return PagingMovieModel(movies = movies)
    }
}
