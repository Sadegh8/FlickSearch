package com.hometest.flicksearch.ui.flicker

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hometest.flicksearch.data.dto.FlickrImage
import com.hometest.flicksearch.data.dto.Media
import com.hometest.flicksearch.ui.flicker.data.FlickrUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FlickListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockImages = listOf(
        FlickrImage(
            title = "Sample Image 1",
            media = Media(imageUrl = "https://via.placeholder.com/150"),
            description = "Sample Description 1",
            author = "Sample Author 1",
            published = "2023-12-31",
            tags = "tag1 tag2"
        ),
        FlickrImage(
            title = "Sample Image 2",
            media = Media(imageUrl = "https://via.placeholder.com/150"),
            description = "Sample Description 2",
            author = "Sample Author 2",
            published = "2024-01-01",
            tags = "tag3 tag4"
        )
    )

    @Test
    fun showsIdleState() {
        composeTestRule.setContent {
            FlickListScreen(
                uiState = FlickrUiState.Idle,
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithText("Start searching!")
            .assertIsDisplayed()
    }

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            FlickListScreen(
                uiState = FlickrUiState.Loading,
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithTag("CircularProgressIndicator")
            .assertIsDisplayed()
    }

    @Test
    fun showsSuccessState() {
        composeTestRule.setContent {
            FlickListScreen(
                uiState = FlickrUiState.Success(mockImages),
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithText("Sample Image 1")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Sample Image 2")
            .assertIsDisplayed()
    }

    @Test
    fun showsEmptyState() {
        composeTestRule.setContent {
            FlickListScreen(
                uiState = FlickrUiState.Empty,
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithText("No images found.")
            .assertIsDisplayed()
    }

    @Test
    fun showsErrorState() {
        composeTestRule.setContent {
            FlickListScreen(
                uiState = FlickrUiState.Error("Something went wrong"),
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithText("Error: Something went wrong")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Retry")
            .assertIsDisplayed()
    }

    @Test
    fun searchQueryUpdates() = runTest {
        composeTestRule.setContent {
            FlickListScreen(
                uiState = FlickrUiState.Idle,
                onAction = {}
            )
        }
        val searchField = composeTestRule.onNodeWithText("Search")
        searchField.performTextInput("test query")
        searchField.assertTextContains("test query")
    }
}
