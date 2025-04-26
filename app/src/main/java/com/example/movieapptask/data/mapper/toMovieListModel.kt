package com.example.movieapptask.data.mapper

import com.example.movieapptask.data.model.response.MovieItemDto
import com.example.movieapptask.domain.entity.response.MovieListModel

fun MovieItemDto.toMovieListModel(): MovieListModel {
    val movieId = id ?: 0
    val movieTitle = title ?: originalTitle ?: ""
    val movieRating = voteAverage ?: 0.0
    val movieReleaseDate = releaseDate ?: "Unknown"

    return MovieListModel(
        id = movieId,
        title = movieTitle,
        posterUrl = "http://image.tmdb.org/t/p/w342$posterPath",
        rating = movieRating,
        releaseDate = movieReleaseDate
    )
}
