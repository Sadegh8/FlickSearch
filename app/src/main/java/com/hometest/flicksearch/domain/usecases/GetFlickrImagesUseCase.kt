package com.hometest.flicksearch.domain.usecases

import com.hometest.flicksearch.data.dto.FlickrImage
import com.hometest.flicksearch.domain.FlickrRepository
import javax.inject.Inject

class GetFlickrImagesUseCase @Inject constructor(
    private val repository: FlickrRepository
) {
    suspend operator fun invoke(tags: String): List<FlickrImage> {
        if (tags.isBlank()) return emptyList()
        return repository.searchImages(tags)
    }
}
