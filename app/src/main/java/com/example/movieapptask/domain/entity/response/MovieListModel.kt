package com.example.movieapptask.domain.entity.response

import com.example.ui.paging.BaseDataModel

data class MovieListModel(
    val id: Long,
    val title: String,
    val posterUrl: String?,
    val rating: Double,
    val releaseDate: String
):BaseDataModel
