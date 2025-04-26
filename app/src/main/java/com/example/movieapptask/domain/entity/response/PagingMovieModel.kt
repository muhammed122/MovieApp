package com.example.movieapptask.domain.entity.response

import com.example.ui.paging.BasePagingModel

data class PagingMovieModel(
    val movies: List<MovieListModel>,
) : BasePagingModel<MovieListModel> {
    override val pagingDataItems: List<MovieListModel>
        get() = movies
}
