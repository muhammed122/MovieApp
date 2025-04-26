package com.example.movieapptask.data.mapper

import com.example.movieapptask.data.model.response.MovieDetailsResponseDto
import com.example.movieapptask.domain.entity.response.MovieDetailsModel

fun MovieDetailsResponseDto.toMovieDetails(): MovieDetailsModel? {
    val movieId = id ?: return null
    val movieTitle = title ?: originalTitle ?: return null
    val movieRating = voteAverage ?: 0.0

    return MovieDetailsModel(
        id = movieId,
        title = movieTitle,
        overview = overview,
        posterUrl ="http://image.tmdb.org/t/p/w342$posterPath",
        backdropUrl = "http://image.tmdb.org/t/p/w342$backdropPath",
        rating = movieRating,
        releaseDate = releaseDate,
        runtimeMinutes = runtime,
        genres = genres?.mapNotNull { it.name } ?: emptyList(),
        productionCountries = productionCountries?.mapNotNull { it.name } ?: emptyList(),
        spokenLanguages = spokenLanguages?.mapNotNull { it.englishName } ?: emptyList(),
        homepageUrl = homepage,
        budget = budget,
        revenue = revenue
    )
}
