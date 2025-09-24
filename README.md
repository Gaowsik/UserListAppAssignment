# Random User App

## Overview
This is a simple Android application that demonstrates consuming a public API, managing navigation, and structuring a maintainable codebase. The app uses modern Android architecture patterns and Kotlin features.

The app has **two screens**:

1. **User List Screen** – Displays a scrollable list of users fetched from the [Random User API](https://randomuser.me/api/?results=20). Each item shows the user's name and thumbnail image.
2. **User Detail Screen** – Shows detailed information of a selected user, including full name, email, phone number, and a larger profile picture.

---

## Features

### Functional
- Scrollable list of 20 users fetched from the Random User API.
- Each list item shows the user's name and thumbnail image.
- Tap a list item to navigate to the detail screen.
- Displays full user information on the detail screen.
- Loading and error states implemented.
- Pull-to-refresh on the user list.
- Search or filter users by name.

---

## Technology Stack
- **Language:** Kotlin
- **Architecture:** MVVM (Modern Android Architecture)
- **Dependency Injection:** Dagger Hilt
- **Networking:** Retrofit
- **Asynchronous Handling:** Kotlin Flow
- **Unit Testing:** Implemented
- **Image Loading:** Glide 
- **Navigation:** Jetpack Navigation Component

---

## Project Structure
- `ui/` – Activities, Fragments, Adapters
- `viewmodel/` – ViewModel classes
- `data/` – API interfaces, Repository, and Models
- `di/` – Dagger Hilt modules for dependency injection
- `utils/` – Helper classes and utilities
- `tests/` – Unit tests for ViewModels and Repositories

---

## Installation

1. **Clone the repository**
   ```bash
   git clone <https://github.com/Gaowsik/UserListAppAssignment>
