package com.example.movieapptask.data.source.remote

import com.example.movieapptask.data.model.response.MovieDetailsResponseDto
import com.example.movieapptask.data.model.response.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("page") page: Int,
    ): MoviesResponseDto?

    @GET("search/movie")
    suspend fun getMoviesBySearch(
        @Query("page") page: Int,
        @Query("query") query: String=""
    ): MoviesResponseDto?

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Long,
    ): MovieDetailsResponseDto?


}