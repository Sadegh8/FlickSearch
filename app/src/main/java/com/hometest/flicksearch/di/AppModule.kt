package com.hometest.flicksearch.di

import com.hometest.flicksearch.data.FlickrRepositoryImpl
import com.hometest.flicksearch.domain.FlickrRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15_000
        }
    }

    @Provides
    @Singleton
    fun provideFlickrRepository(httpClient: HttpClient): FlickrRepository =
        FlickrRepositoryImpl(httpClient)
}
