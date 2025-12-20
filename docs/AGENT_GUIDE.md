# AI Agent Guide

This guide provides a more detailed set of instructions for AI agents working on this project. It is an extension of the `AGENTS.md` file and should be read and understood in its entirety.

## Project Overview

NoLAWallet is a digital wallet application for storing and managing digital licenses. It allows users to:

* Securely store their license information.
* Protect access to their wallet with a PIN.
* Share their license with others.
* A "secret" configuration screen.

The application is built using the following technologies:

* **Kotlin:** The primary programming language.
* **Jetpack Compose:** The UI toolkit for building the user interface.
* **DataStore:** For local data persistence.
* **Coil:** For image loading.
* **ML Kit:** For image manipulation (background removal).

## Architecture

The application follows a simple MVVM (Model-View-ViewModel) architecture:

* **View:** The UI is built with Jetpack Compose. The `MainActivity` is the entry point, and the `Navigation.kt` file defines the different screens and the navigation flow.
* **ViewModel:** The `WalletViewModel` is responsible for managing the data for the UI and handling user interactions.
* **Model:** The `LicenseRepository` is responsible for managing the license data, using DataStore for persistence.

## Getting Started

To get started with the project, you will need to:

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.

## Development Workflow

Please refer to the `workflow.md` file for a detailed description of the development workflow.

## Code Style

Please follow the official Kotlin coding conventions and the Android coding guidelines.

## Testing

Please refer to the `testing.md` file for a detailed description of the testing strategy.
