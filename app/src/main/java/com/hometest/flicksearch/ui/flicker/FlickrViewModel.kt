package com.hometest.flicksearch.ui.flicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hometest.flicksearch.data.dto.FlickrImage
import com.hometest.flicksearch.domain.usecases.GetFlickrImagesUseCase
import com.hometest.flicksearch.ui.flicker.data.FlickListAction
import com.hometest.flicksearch.ui.flicker.data.FlickrUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val getFlickrImagesUseCase: GetFlickrImagesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FlickrUiState>(FlickrUiState.Idle)
    val uiState: StateFlow<FlickrUiState> = _uiState

    private var _selectedImage: FlickrImage? = null
    val selectedImage: FlickrImage?
        get() = _selectedImage

    var currentTags: String = ""

    fun searchImages(tags: String) {
        currentTags = tags
        viewModelScope.launch {
            _uiState.value = FlickrUiState.Loading
            try {
                val images = getFlickrImagesUseCase(tags)
                _uiState.value = if (images.isNotEmpty()) {
                    FlickrUiState.Success(images)
                } else {
                    FlickrUiState.Empty
                }
            } catch (e: Exception) {
                _uiState.value = FlickrUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun onAction(action: FlickListAction) {
        when (action) {
            is FlickListAction.OnEventClick -> {
                _selectedImage = action.image
            }

            is FlickListAction.OnRetryClick -> {
                if (_uiState.value is FlickrUiState.Error) {
                    searchImages(currentTags)
                }
            }

            is FlickListAction.OnSearchQueryChanged -> {
                if (action.query.isNotBlank()) {
                    searchImages(action.query)
                } else {
                    _uiState.value = FlickrUiState.Idle
                }
            }
        }
    }
}
