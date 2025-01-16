package com.hometest.flicksearch.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrResponse(
    @SerialName("items") val items: List<FlickrImage>
)

@Serializable
data class FlickrImage(
    @SerialName("title") val title: String,
    @SerialName("media") val media: Media,
    @SerialName("description") val description: String,
    @SerialName("author") val author: String,
    @SerialName("published") val published: String,
    @SerialName("tags") val tags: String
)

@Serializable
data class Media(
    @SerialName("m") val imageUrl: String
)
