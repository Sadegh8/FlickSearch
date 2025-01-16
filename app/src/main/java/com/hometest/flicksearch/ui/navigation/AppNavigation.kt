package com.hometest.flicksearch.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hometest.flicksearch.ui.flicker.FlickListScreen
import com.hometest.flicksearch.ui.flicker.FlickrViewModel
import com.hometest.flicksearch.ui.flicker.data.FlickListAction
import com.hometest.flicksearch.ui.flicker.detail.FlickDetailsScreen

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    padding: PaddingValues = PaddingValues(0.dp),
    viewModel: FlickrViewModel = hiltViewModel<FlickrViewModel>(),
) {
    NavHost(
        navController = navController,
        startDestination = FlickListDetailPane,
        modifier = modifier,
    ) {
        addFlickListDetailPane(
            padding = padding,
            viewModel = viewModel,
            navController = navController,
        )

        addFlickList(
            padding = padding,
            navController = navController,
            viewModel = viewModel,
        )

        addFlickDetail(
            padding = padding,
            viewModel = viewModel,
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addFlickListDetailPane(
    padding: PaddingValues,
    viewModel: FlickrViewModel,
    navController: NavController,
) {
    composable<FlickListDetailPane> {
        AdaptiveFlickListDetailPane(
            modifier = Modifier.padding(padding),
            viewModel = viewModel
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addFlickList(
    padding: PaddingValues,
    navController: NavController,
    viewModel: FlickrViewModel,
) {
    composable<FlickList> {
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        FlickListScreen(
            modifier = Modifier.padding(padding),
            uiState = state,
            onAction = { action ->
                when (action) {
                    is FlickListAction.OnEventClick -> {
                        viewModel.onAction(action)
                        navController.navigate(FlickDetail)
                    }

                    is FlickListAction.OnRetryClick -> {
                        viewModel.searchImages(viewModel.currentTags)
                    }

                    is FlickListAction.OnSearchQueryChanged -> {
                        viewModel.onAction(action)
                    }
                }
            }
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addFlickDetail(
    padding: PaddingValues,
    viewModel: FlickrViewModel,
) {
    composable<FlickDetail> {
        val selectedImage = viewModel.selectedImage
        selectedImage?.let {
            FlickDetailsScreen(
                event = it,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
