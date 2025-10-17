package com.uvg.pinterestfeed.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import coil.util.DebugLogger
import com.uvg.pinterestfeed.data.Photo
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    vm: FeedViewModel,
    onOpenDetail: (String, String, String, Int, Int) -> Unit
) {
    val photos = vm.feed.collectAsLazyPagingItems()


    val savedIndex by vm.scrollIndex.collectAsState()
    val savedOffset by vm.scrollOffset.collectAsState()
    val state = rememberLazyStaggeredGridState(
        initialFirstVisibleItemIndex = savedIndex,
        initialFirstVisibleItemScrollOffset = savedOffset
    )


    val ctx = LocalContext.current
    val loader = remember {
        ImageLoader.Builder(ctx)
            .logger(DebugLogger())
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .respectCacheHeaders(false)
            .build()
    }


    LaunchedEffect(state.firstVisibleItemIndex, photos.itemCount) {
        val startNow = state.firstVisibleItemIndex
        val count = photos.itemCount
        if (count <= 0) return@LaunchedEffect

        val start = startNow.coerceIn(0, max(0, count - 1))
        val end = min(count, start + 10)

        for (i in start until end) {

            val ph = photos.peek(i) ?: continue
            loader.enqueue(
                ImageRequest.Builder(ctx)
                    .data(ph.url)
                    .size(Size.ORIGINAL)
                    .build()
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pinterest",
                        fontFamily = FontFamily.Serif,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { pad ->
        Box(Modifier.padding(pad).fillMaxSize()) {


            when (photos.loadState.refresh) {
                is LoadState.Loading -> {
                    SkeletonGrid()
                    return@Box
                }
                is LoadState.Error -> {
                    ErrorState { photos.retry() }
                    return@Box
                }
                else -> Unit
            }

            if (photos.itemCount == 0) {
                EmptyState { photos.retry() }
                return@Box
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 180.dp),
                state = state,
                contentPadding = PaddingValues(8.dp),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    count = photos.itemCount,
                    key = { i -> photos[i]?.id ?: "ph_$i" }
                ) { index ->
                    val ph = photos[index]
                    if (ph == null) {
                        PhotoPlaceholderCard()
                        return@items
                    }

                    PhotoCard(photo = ph) {
                        vm.saveScroll(
                            state.firstVisibleItemIndex,
                            state.firstVisibleItemScrollOffset
                        )
                        onOpenDetail(ph.id, ph.url, ph.title, ph.width, ph.height)
                    }
                }


                item(span = StaggeredGridItemSpan.FullLine) {
                    when (photos.loadState.append) {
                        is LoadState.Loading -> LinearProgressIndicator(Modifier.fillMaxWidth())
                        is LoadState.Error -> Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Error cargando más")
                            TextButton(onClick = { photos.retry() }) { Text("Reintentar") }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoCard(photo: Photo, onClick: () -> Unit) {
    val desiredW = 180.dp
    val ratio = if (photo.width > 0) photo.height.toFloat() / photo.width.toFloat() else 1f
    val targetH = desiredW * ratio

    ElevatedCard(onClick = onClick, modifier = Modifier.width(desiredW).height(targetH)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.url)
                .crossfade(true)
                .build(),
            contentDescription = "Imagen ${photo.title}",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun PhotoPlaceholderCard() {
    ElevatedCard(modifier = Modifier.width(180.dp).height(240.dp)) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}

@Composable
private fun SkeletonGrid() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 180.dp),
        contentPadding = PaddingValues(8.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(12) { PhotoPlaceholderCard() }
    }
}

@Composable
private fun ErrorState(onRetry: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Error de red")
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetry) { Text("Reintentar") }
    }
}

@Composable
private fun EmptyState(onRetry: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sin datos")
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetry) { Text("Recargar") }
    }
}
