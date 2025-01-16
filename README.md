# FlickSearch

FlickSearch is an Android application built using Jetpack Compose. It allows users to search for images on Flickr by entering search tags and displays results in a grid. Users can view image details, including the title, description, author, and published date.

## Features

- **Search Bar**: Search for images on Flickr by entering tags.
- **Image Grid**: Display results in a responsive grid.
- **Detail View**: View image details, including:
  - Title
  - Description
  - Author (extracted name only)
  - Tags (displayed as chips)
  - Published date (formatted in user's time zone)
- **Error Handling**: Retry option in case of a failed search.
- **Loading Indicator**: Shows progress while fetching images.

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Networking**: Ktor
- **Testing**:
  - Unit Tests: JUnit and MockK
  - UI Tests: Jetpack Compose Testing Framework
- **Image Loading**: Coil

## Screenshots

### Home Screen
![telegram-cloud-photo-size-4-5785378127067857296-y](https://github.com/user-attachments/assets/3ccead66-1425-47ee-a0d6-06af6400a486)


### Detail View
![telegram-cloud-photo-size-4-5785378127067857295-y](https://github.com/user-attachments/assets/9e504c30-cf7f-48b6-98e9-6bbe32f66457)

