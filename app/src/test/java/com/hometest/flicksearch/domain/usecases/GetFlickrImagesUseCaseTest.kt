package com.hometest.flicksearch.domain.usecases
import com.hometest.flicksearch.data.dto.FlickrImage
import com.hometest.flicksearch.data.dto.Media
import com.hometest.flicksearch.domain.FlickrRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFlickrImagesUseCaseTest {

    private lateinit var useCase: GetFlickrImagesUseCase
    private val repository: FlickrRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetFlickrImagesUseCase(repository)
    }

    @Test
    fun `invoke returns empty list when tags are blank`() = runTest {
        // Act
        val result = useCase("")

        // Assert
        assertEquals(emptyList<FlickrImage>(), result)
    }

    @Test
    fun `invoke returns images when repository provides data`() = runTest {
        // Arrange
        val mockImages = listOf(
            FlickrImage(
                title = "Sample Image 1",
                media = Media(imageUrl = "https://via.placeholder.com/150"),
                description = "Description 1",
                author = "Author 1",
                published = "2023-12-31T12:34:56Z",
                tags = "tag1 tag2"
            ),
            FlickrImage(
                title = "Sample Image 2",
                media = Media(imageUrl = "https://via.placeholder.com/150"),
                description = "Description 2",
                author = "Author 2",
                published = "2024-01-01T08:15:00Z",
                tags = "tag3 tag4"
            )
        )
        coEvery { repository.searchImages("sample") } returns mockImages

        // Act
        val result = useCase("sample")

        // Assert
        assertEquals(mockImages, result)
    }

    @Test
    fun `invoke propagates exception from repository`() = runTest {
        // Arrange
        val exception = Exception("Network error")
        coEvery { repository.searchImages("sample") } throws exception

        // Act & Assert
        try {
            useCase("sample")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
}
