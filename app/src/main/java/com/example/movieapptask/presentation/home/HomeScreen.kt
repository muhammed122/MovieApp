package com.example.movieapptask.presentation.home

import BasicScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapptask.presentation.home.components.MovieCardItem
import com.example.ui.component.EmptyView
import com.example.ui.component.ErrorView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class,)
@Composable
fun HomeScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onNavigateToDetails: (Long) -> Unit,
) {
    val movies =
        viewModel.uiState.collectAsStateWithLifecycle().value.movies.collectAsLazyPagingItems()

    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        snapshotFlow { query }
            .debounce(1000) // 1 second debounce
            .collectLatest { debouncedQuery ->
                viewModel.handleAction(HomeViewAction.SearchQueryChanged(debouncedQuery))
            }
    }
    BasicScreen(
        viewModel = viewModel,
        toolbar = {
            Column {
                TopAppBar(title = { Text("Movies") })
                TextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Search movies...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

        },
        onErrorDismissed = {  }
    ) { state ->
            when {
                movies.loadState.refresh is LoadState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }

                movies.loadState.refresh is LoadState.Error -> {
                    val error = (movies.loadState.refresh as LoadState.Error).error
                    ErrorView(message = error.localizedMessage ?: "Something went wrong") {
                        movies.retry()
                    }
                }

                movies.itemCount == 0 -> {
                    EmptyView("No movies found.")
                }

                else -> {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(movies.itemCount) { index ->
                            movies[index]?.let { movie ->
                                MovieCardItem(movie = movie) {
                                    onNavigateToDetails.invoke(movie.id)
                                }
                            }
                        }

                        when (val append = movies.loadState.append) {
                            is LoadState.Loading -> {
                                item(span = { GridItemSpan(2) }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                    }
                                }
                            }

                            is LoadState.Error -> {
                                item(span = { GridItemSpan(2) }) {
                                    ErrorView(
                                        message = append.error.localizedMessage
                                            ?: "Load more failed",
                                        onRetry = { movies.retry() }
                                    )
                                }
                            }

                            else -> Unit
                        }
                    }
                }
            }
    }
}
