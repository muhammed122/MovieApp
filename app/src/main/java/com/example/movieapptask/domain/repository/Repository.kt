package com.example.movieapptask.domain.repository

import androidx.paging.Pager
import com.example.movieapptask.domain.entity.response.MovieDetailsModel
import com.example.movieapptask.domain.entity.response.MovieListModel
import com.example.movieapptask.domain.entity.request.GetMoviesParam

interface Repository {

    fun getMovies(param: GetMoviesParam): Pager<Int, MovieListModel>
   suspend fun getMovieDetails(id: Long): MovieDetailsModel?

}