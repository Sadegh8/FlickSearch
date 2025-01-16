package com.hometest.flicksearch.domain

import com.hometest.flicksearch.data.dto.FlickrImage

interface FlickrRepository {
    suspend fun searchImages(tags: String): List<FlickrImage>
}
