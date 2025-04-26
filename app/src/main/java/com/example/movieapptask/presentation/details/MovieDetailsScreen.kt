package com.example.movieapptask.presentation.details

import BasicScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    id: Long,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    SideEffect {
        viewModel.handleAction(MovieDetailsAction.GetMovieDetails(id))
    }

    BasicScreen(
        viewModel = viewModel,
        onErrorDismissed = { viewModel.clearError() },
        toolbar = {
            TopAppBar(title = { Text("Movie Details") })
        }
    ) { state ->
        when {
            state.isLoading -> {
                // Show centered CircularProgressIndicator while loading
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.CircularProgressIndicator()
                }
            }

            state.movie != null -> {
                val movie = state.movie

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    movie.backdropUrl?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }

                    Text(text = movie.title, style = MaterialTheme.typography.headlineSmall)

                    Text(text = "Rating: ${movie.rating} ⭐", fontWeight = FontWeight.SemiBold)
                    movie.releaseDate?.let { Text(text = "Release: $it") }
                    movie.runtimeMinutes?.let { Text(text = "Runtime: $it minutes") }

                    if (movie.genres.isNotEmpty())
                        Text(text = "Genres: ${movie.genres.joinToString()}")

                    if (movie.productionCountries.isNotEmpty())
                        Text(text = "Produced in: ${movie.productionCountries.joinToString()}")

                    if (movie.spokenLanguages.isNotEmpty())
                        Text(text = "Languages: ${movie.spokenLanguages.joinToString()}")

                    movie.budget?.let { Text(text = "Budget: $${"%,d".format(it)}") }
                    movie.revenue?.let { Text(text = "Revenue: $${"%,d".format(it)}") }

                    movie.homepageUrl?.let {
                        Text(
                            text = "Visit Homepage",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    movie.overview?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Overview:", fontWeight = FontWeight.Bold)
                        Text(text = it)
                    }
                }
            }

            else -> {
                // Optional: Handle case where movie is null and not loading (empty view)
                Text(
                    text = "Movie details not available.",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

