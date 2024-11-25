# Recruitment Task - Android Developer

## Project Overview
This project implements an Android application consisting of two screens as described in the recruitment task. The app allows users to view a list of photos and navigate to a details screen for more information about a selected photo. The data is fetched from the [Picsum API](https://picsum.photos/).

## Data Loading
I implemented two approaches:
1. **Direct API Data Loading (Used in final build)**:
   - Data is fetched directly from the API using Retrofit when the user scrolls to the bottom of the list. (API call is purposely delayed by 1 second to display loading states)
2. **Data Mediator with Local Cache**:
   - Utilized `RemoteMediator` with the Paging 3 library to load data and cache it in a local Room database.

## Features
### Main Screen (List Screen)
- Loads 20 photos at first.
- Loads additional photos (20 each time) when the user scrolls to the bottom.
- Each item contains a photo and the ID number.
- A loading animation is displayed during data fetching:
  - On the top when list is empty.
  - On the bottom while scrolling.
- Errors are displayed as a snackbar.

### Details Screen
- Displays the selected photo and other information about it: id, author, width, height, and download URL.
- Photo can be clicked to be displayed by itself

## Technical Details
### Architecture
- Used the **MVI (Model-View-Intent)**.
- Utilized **Clean Architecture**.

### Libraries
- **Jetpack Compose**: For the UI layer.
- **Retrofit**: For API communication.
- **Paging 3**: For paginated data loading and `RemoteMediator`.
- **Room**: For local caching in the database.
- **Hilt**: For dependency injection.
- **Coil**: For loading images.

### Development Tools
- Written entirely in **Kotlin**.
- Minimum supported Android SDK version: **7.0 - Nougat**.

## Testing
### Retrofit Tests
- **Tested API Service:** 
  - Used `MockWebServer` to simulate API responses.
  - Validated correct deserialization of responses.
  - Ensured proper error handling.
