package com.example.movieapptask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movieapptask.data.mapper.toMovieDetails
import com.example.movieapptask.data.source.datasource.MoviesPagingSource
import com.example.movieapptask.data.source.datasource.RemoteDataSource
import com.example.movieapptask.domain.entity.response.MovieDetailsModel
import com.example.movieapptask.domain.entity.response.MovieListModel
import com.example.movieapptask.domain.entity.request.GetMoviesParam
import com.example.movieapptask.domain.repository.Repository
import com.example.utils.network.NetworkChecker
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val moviesPagingSource: MoviesPagingSource.MoviesPagingDataSourceFactory,
    private val networkChecker: NetworkChecker,
) : Repository {
    override fun getMovies(param: GetMoviesParam): Pager<Int, MovieListModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2,
                initialLoadSize = 40,
            )
        )
        {
            moviesPagingSource.createNewInstance(param)
        }
    }

    override suspend fun getMovieDetails(id: Long): MovieDetailsModel? {
        return remoteDataSource.getMovieDetails(id)?.toMovieDetails()
    }

}