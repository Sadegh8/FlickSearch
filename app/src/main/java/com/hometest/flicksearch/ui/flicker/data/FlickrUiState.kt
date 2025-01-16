package com.hometest.flicksearch.ui.flicker.data

import com.hometest.flicksearch.data.dto.FlickrImage

sealed class FlickrUiState {
    object Idle : FlickrUiState()
    object Loading : FlickrUiState()
    data class Success(val images: List<FlickrImage>) : FlickrUiState()
    data class Error(val message: String) : FlickrUiState()
    object Empty : FlickrUiState()
}
