package com.hometest.flicksearch.data

import com.hometest.flicksearch.data.dto.FlickrImage
import com.hometest.flicksearch.data.dto.FlickrResponse
import com.hometest.flicksearch.domain.FlickrRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class FlickrRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : FlickrRepository {
    override suspend fun searchImages(tags: String): List<FlickrImage> {
        val url = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=$tags"
        return httpClient.get(url).body<FlickrResponse>().items
    }
}
