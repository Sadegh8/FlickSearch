@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.hometest.flicksearch.ui.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hometest.flicksearch.ui.flicker.FlickListScreen
import com.hometest.flicksearch.ui.flicker.FlickrViewModel
import com.hometest.flicksearch.ui.flicker.data.FlickListAction
import com.hometest.flicksearch.ui.flicker.detail.FlickDetailsScreen


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveFlickListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: FlickrViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                FlickListScreen(
                    uiState = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        if (action is FlickListAction.OnEventClick) {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                viewModel.selectedImage?.let {
                    FlickDetailsScreen(event = it)
                }
            }
        },
        modifier = modifier
    )
}
