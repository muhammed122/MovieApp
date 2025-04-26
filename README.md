🎬 Movie App
This is a simple Android application that displays an infinite list of movies using pagination.
Users can select any movie to navigate to a detailed view of that movie.

---

## 📱 Features

✅ Infinite movies list with pagination

✅ View detailed information for each movie

✅ Modern Android UI with Jetpack Compose

✅ Clean Architecture with MVI pattern

✅ Modularized project structure
---

## 🧱 Architecture

The app is built following **Clean Architecture** principles:
presentation │ ├── Home (ViewModel, State, Action, Event) │
data ├── Repositories & Data Sources │
domain ├── UseCases & Business Models


---

## 🧩 Tech Stack

| Layer       | Libraries & Tools                          |
|-------------|--------------------------------------------|
| UI          | Jetpack Compose, Material3, Coil           |
| State Mgmt  | MVI Architecture                           |
| DI          | Hilt                                       |
| Networking  | Retrofit                                   |
| Async       | Kotlin Coroutines, Flow                    |
| Modularity  | `core-ui`, `core-network`, `core-utils`    |
| Testing     | Compose UI Test, Hilt, JUnit, Turbine      |

---

## 🗂️ Modules

- **app** - Main application module
- **core-ui** - Shared UI components
- **core-network** - Retrofit setup, API services
- **core-utils** - Utility classes like NetworkChecker
- **data** - Data source, repository, DTOs
- **domain** - Business models and use cases

---
