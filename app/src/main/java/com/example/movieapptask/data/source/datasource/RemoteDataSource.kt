package com.example.movieapptask.data.source.datasource

import com.example.movieapptask.data.model.response.MovieDetailsResponseDto

interface RemoteDataSource {


    suspend fun getMovieDetails(id: Long): MovieDetailsResponseDto?


}