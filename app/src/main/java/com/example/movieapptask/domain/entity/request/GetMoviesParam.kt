package com.example.movieapptask.domain.entity.request

sealed class GetMoviesParam {
    data object Default : GetMoviesParam()
    data class Search(val query: String) : GetMoviesParam()
}