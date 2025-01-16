package com.hometest.flicksearch.ui.flicker.data

import com.hometest.flicksearch.data.dto.FlickrImage

sealed class FlickListAction {
    data class OnEventClick(val image: FlickrImage) : FlickListAction()
    object OnRetryClick : FlickListAction()
    data class OnSearchQueryChanged(val query: String) : FlickListAction()
}
