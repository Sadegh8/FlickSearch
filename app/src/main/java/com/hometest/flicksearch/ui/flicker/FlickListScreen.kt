package com.hometest.flicksearch.ui.flicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hometest.flicksearch.data.dto.FlickrImage
import com.hometest.flicksearch.data.dto.Media
import com.hometest.flicksearch.ui.flicker.components.ImageCard
import com.hometest.flicksearch.ui.flicker.data.FlickListAction
import com.hometest.flicksearch.ui.flicker.data.FlickrUiState

@Composable
fun FlickListScreen(
    modifier: Modifier = Modifier,
    uiState: FlickrUiState,
    onAction: (FlickListAction) -> Unit,
) {
    Column(modifier = modifier) {
        // Search Bar
        var query = remember { mutableStateOf("") }
        var lastQuery = remember { mutableStateOf("") }
        OutlinedTextField(
            value = query.value,
            onValueChange = { newQuery ->
                query.value = newQuery
            },
            label = { Text(text = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LaunchedEffect(query.value) {
            kotlinx.coroutines.delay(500)
            if (query.value != lastQuery.value) {
                lastQuery.value = query.value
                if (query.value.isNotBlank()) {
                    onAction(FlickListAction.OnSearchQueryChanged(query.value))
                }
            }
        }

        when (uiState) {
            is FlickrUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().testTag("CircularProgressIndicator"), contentAlignment = Alignment.Center) {  }
                CircularProgressIndicator(modifier = modifier)
            }
            is FlickrUiState.Success -> {
                LazyVerticalGrid(columns = GridCells.Adaptive(128.dp), modifier = modifier) {
                    items(uiState.images) { image ->
                        ImageCard(
                            image = image,
                            onClick = { onAction(FlickListAction.OnEventClick(image)) }
                        )
                    }
                }
            }
            is FlickrUiState.Empty -> {
                Text("No images found.", modifier = modifier.padding(8.dp))
            }
            is FlickrUiState.Error -> {
                Column(modifier = modifier.padding(8.dp)) {
                    Text("Error: ${uiState.message}")
                    Button(onClick = { onAction(FlickListAction.OnRetryClick) }) {
                        Text("Retry")
                    }
                }
            }
            FlickrUiState.Idle -> {
                Text("Start searching!", modifier = modifier.padding(8.dp))
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FlickListScreenPreview() {
    val mockImages = listOf(
        FlickrImage(
            title = "Sample Image 1",
            media = Media(imageUrl = "https://via.placeholder.com/150"),
            description = """
                <p><a href="https://www.flickr.com/people/sample_user1/">Sample User 1</a> posted a photo:</p>
                <p><a href="https://www.flickr.com/photos/sample_user1/sample_photo1/"><img src="https://via.placeholder.com/150" alt="Sample Image 1" /></a></p>
            """,
            author = "nobody@flickr.com (\"Sample User 1\")",
            published = "2023-12-31T12:34:56Z",
            tags = "tag1 tag2 tag3"
        ),
        FlickrImage(
            title = "Sample Image 2",
            media = Media(imageUrl = "https://via.placeholder.com/150"),
            description = """
                <p><a href="https://www.flickr.com/people/sample_user2/">Sample User 2</a> posted a photo:</p>
                <p><a href="https://www.flickr.com/photos/sample_user2/sample_photo2/"><img src="https://via.placeholder.com/150" alt="Sample Image 2" /></a></p>
            """,
            author = "nobody@flickr.com (\"Sample User 2\")",
            published = "2024-01-01T08:15:00Z",
            tags = "tag4 tag5 tag6"
        )
    )
    FlickListScreen(
        uiState = FlickrUiState.Success(mockImages),
        onAction = {}
    )
}
