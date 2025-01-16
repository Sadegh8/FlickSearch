package com.hometest.flicksearch.ui.flicker.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hometest.flicksearch.data.dto.FlickrImage
import com.hometest.flicksearch.data.dto.Media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.hometest.flicksearch.ui.flicker.components.Chip
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FlickDetailsScreen(event: FlickrImage, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = event.media.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(8.dp),
            contentScale = ContentScale.Fit
        )

        StyledText(label = "Title:", value = event.title)
        StyledText(label = "Author:", value = extractAuthorName(event.author))
        StyledText(label = "Published:", value = formatToUserTime(event.published))
        StyledText(label = "Description:", value = extractDescription(event.description))

        if (event.tags.isNotBlank()) {
            Text(
                text = "Tags:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                val tags = event.tags.split(" ")
                items(tags.size) { index ->
                    Chip(
                        label = tags[index],
                        onClick = {  },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StyledText(label: String, value: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) {
                append("$label ")
            }
            append(value)
        },
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

// Helper function to format date
fun formatToUserTime(dateString: String): String {
    return try {
        val serverFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        serverFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = serverFormat.parse(dateString)

        val userFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        userFormat.timeZone = TimeZone.getDefault()
        date?.let { userFormat.format(it) } ?: "Unknown Date"
    } catch (e: Exception) {
        "Unknown Date"
    }
}

// Helper function to extract author name
fun extractAuthorName(author: String): String {
    val startIndex = author.indexOf('(')
    val endIndex = author.indexOf(')')
    return if (startIndex != -1 && endIndex != -1) {
        author.substring(startIndex + 1, endIndex)
    } else {
        author
    }
}

// Helper function to extract relevant part of the description
fun extractDescription(description: String): String {
    return Regex(".*?<p><a .*?>(.*?)</a> posted a photo:.*").find(description)?.groupValues?.get(1)
        ?: "No description available"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FlickDetailsScreenPreview() {
    val mockFlickrImage = FlickrImage(
        title = "Sample Image",
        media = Media(imageUrl = "https://via.placeholder.com/150"),
        description = """
            <p><a href="https://www.flickr.com/people/sample_user/">Sample User</a> posted a photo:</p>
            <p><a href="https://www.flickr.com/photos/sample_user/sample_photo/"><img src="https://via.placeholder.com/150" alt="Sample Image" /></a></p>
        """,
        author = "nobody@flickr.com (\"Sample User\")",
        published = "2025-01-16T07:51:56Z",
        tags = "sample tag1 tag2 tag3"
    )
    FlickDetailsScreen(event = mockFlickrImage)
}
