# Data Layer

This document describes the data layer of the application, including the data sources, repositories, and models.

## Data Sources

The application uses DataStore for local data persistence. DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.

## Repositories

The `LicenseRepository` is responsible for managing the license data. It provides a simple API for storing and retrieving license information from DataStore.

## Models

The `License` data class is the primary model in the application. It represents a user's license and contains the following properties:

* `name`: The user's name.
* `photoUri`: The URI of the user's photo.
* `issueDate`: The date the license was issued.
* `expiryDate`: The date the license expires.
* `address`: The user's address.
* `licenseNumber`: The license number.
