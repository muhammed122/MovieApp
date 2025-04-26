package com.example.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class PagingDataSource<T : BaseDataModel, Y : BasePagingModel<T>> : PagingSource<Int, T>() {

    abstract suspend fun loadMoreData(page: Int): Y?

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = loadMoreData(page)
            val items = response?.pagingDataItems ?: emptyList()

            val nextPage = if (items.isNotEmpty()) page + 1 else null
            val prevPage = if (page > FIRST_PAGE) page - 1 else null

            LoadResult.Page(
                data = items,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        } ?: FIRST_PAGE
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}
