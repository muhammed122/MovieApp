package com.example.movieapptask.domain.entity.response

data class MovieDetailsModel(
    val id: Long,
    val title: String,
    val overview: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val rating: Double,
    val releaseDate: String?,
    val runtimeMinutes: Int?,
    val genres: List<String>,
    val productionCountries: List<String>,
    val spokenLanguages: List<String>,
    val homepageUrl: String?,
    val budget: Int?,
    val revenue: Int?
)
