package com.example.movieapptask.data.source.datasource

import com.example.movieapptask.data.model.response.MovieDetailsResponseDto
import com.example.movieapptask.data.source.remote.ApiService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {

    override suspend fun getMovieDetails(id: Long): MovieDetailsResponseDto? {
        return apiService.getMovieDetails(id)
    }

}