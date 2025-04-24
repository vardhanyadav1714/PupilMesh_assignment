package `in`.innovaticshub.pupilmesh_assignment.manga_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity.MangaDataTable
import `in`.innovaticshub.pupilmesh_assignment.presentation.PupilMeshScreens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MangaScreen(navController: NavHostController) {
    val viewModel: MangaViewModel = hiltViewModel()
    val isOnline by viewModel.isOnline.collectAsState()
    val lazyGridState = rememberLazyGridState()
    val mangaItems = viewModel.mangaPagingFlow.collectAsLazyPagingItems()
    val isRefreshing = remember { derivedStateOf { mangaItems.loadState.refresh is LoadState.Loading } }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = { viewModel.refreshData() }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        Column {
            if (!isOnline) {
                OfflineBanner()
            }

            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(mangaItems.itemCount) { index ->
                    mangaItems[index]?.let { manga ->
                        MangaItem(manga, navController)
                    }
                }

                when (mangaItems.loadState.append) {
                    is LoadState.Loading -> {
                        item { LoadingIndicator() }
                    }
                    is LoadState.Error -> {
                        item {
                            ErrorItem(
                                error = (mangaItems.loadState.append as LoadState.Error).error,
                                onRetry = { mangaItems.retry() }
                            )
                        }
                    }
                    else -> {}
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing.value,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        if (mangaItems.loadState.refresh is LoadState.Loading) {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        if (mangaItems.loadState.refresh is LoadState.Error) {
            ErrorItem(
                error = (mangaItems.loadState.refresh as LoadState.Error).error,
                onRetry = { mangaItems.refresh() },
             )
        }
    }
}

@Composable
private fun OfflineBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Yellow.copy(alpha = 0.8f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Offline Mode - Showing Cached Data", color = Color.Black)
    }
}

@Composable
private fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MangaItem(manga: MangaDataTable, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(0.7f)
            .clickable {
                navController.navigate("${PupilMeshScreens.MANGA_DESCRIPTION_SCREEN.name}/${manga.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = manga.thumb,
                contentDescription = manga.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

        }
    }
}
@Composable
private fun ErrorItem(error: Throwable, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error.localizedMessage ?: "Error loading data",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
